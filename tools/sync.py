#!/usr/bin/env python3
"""Create or update everything on GitHub from plan/.

Reads plan/labels.yml, plan/milestones.yml and plan/tickets/*.md, then makes
the repository and the project match them:

  labels      created, updated; with --prune, labels not in the plan are deleted
  milestones  created, updated (title, description, due date)
  issues      one per ticket, titled "[KEY] title"; body, labels and milestone
              are kept in sync; state (open/closed) is never touched
  blocked-by  GitHub issue dependencies from each ticket's blocked_by
  project     fields, items, field values and views

Live state is never overwritten: an item's Status is set only when it has none,
and the Upstream PR field is left alone.

Usage:
  tools/sync.py --dry-run      show what would change
  tools/sync.py [--prune]      apply

Needs `gh` signed in with the repo and project scopes, and PyYAML.
"""
import argparse
import datetime as dt
import glob
import json
import os
import re
import subprocess
import sys
import time

import yaml

REPO = "preetvadaliya/appinventor-new-ui"
OWNER = REPO.split("/")[0]
PROJECT_TITLE = "App Inventor New UI"
PROJECT_DESCRIPTION = (
    "Restructuring the MIT App Inventor web client into self-contained "
    "layouts (classic, neo, later nova) on shared logic.")
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
KEY_RE = re.compile(r"^\[(P\d-\d\d|C-\d\d)\]")
FOOTER = ("\n\n---\n_Source: [`plan/tickets/{key}.md`]"
          "(https://github.com/{repo}/blob/main/plan/tickets/{key}.md). "
          "Edit the file and run `tools/sync.py`; edits made here are "
          "overwritten._\n")

STATUS_OPTIONS = [
    ("Backlog", "GRAY", "Not ready: waiting on a blocking ticket"),
    ("Ready", "BLUE", "Can be picked up now"),
    ("In progress", "YELLOW", "Someone is working on it"),
    ("Upstream review", "PURPLE", "PR open on mit-cml/appinventor-sources"),
    ("Merged", "GREEN", "PR merged upstream"),
    ("Blocked", "RED", "Waiting on something outside this repo"),
]
SIZE_OPTIONS = [("S", "GREEN", "Up to 1.5 days"),
                ("M", "YELLOW", "2 to 3 days"),
                ("L", "ORANGE", "3.5 days or more")]
AREA_OPTIONS = [("layout", "YELLOW"), ("theme", "ORANGE"),
                ("actions", "PURPLE"), ("views", "BLUE"), ("controls", "BLUE"),
                ("css", "PINK"), ("packaging", "GREEN"),
                ("coordination", "GRAY")]

DRY = False


# --------------------------------------------------------------- gh helpers
TRANSIENT = ("timed out", "connection reset", "EOF", "502", "503", "504",
             "TLS handshake", "no such host")


def gh(*args, body=None):
    """Run gh; retry a few times when the network drops."""
    for attempt in range(4):
        res = subprocess.run(["gh", *args], input=body, capture_output=True,
                             text=True)
        if res.returncode == 0:
            return res.stdout
        err = res.stderr.strip()
        if attempt < 3 and any(t in err for t in TRANSIENT):
            time.sleep(2 * (attempt + 1))
            continue
        raise RuntimeError(f"gh {' '.join(args)}\n{err}")


def rest(method, path, data=None):
    args = ["api", "-X", method, path]
    if data is not None:
        args += ["--input", "-"]
    out = gh(*args, body=json.dumps(data) if data is not None else None)
    return json.loads(out) if out.strip() else None


def rest_list(path):
    try:
        out = gh("api", "--paginate", path, "--jq", ".[] | @json")
    except RuntimeError as e:
        if "Not Found" in str(e) or "404" in str(e):
            return []
        raise
    return [json.loads(line) for line in out.splitlines() if line.strip()]


def gql(query, **variables):
    out = gh("api", "graphql", "--input", "-",
             body=json.dumps({"query": query, "variables": variables}))
    res = json.loads(out)
    if res.get("errors"):
        raise RuntimeError(json.dumps(res["errors"], indent=1))
    return res["data"]


def act(msg, fn, *args, **kw):
    print(("would " if DRY else "") + msg)
    if not DRY:
        return fn(*args, **kw)
    return None


# --------------------------------------------------------------- plan
def load_plan():
    labels = yaml.safe_load(open(os.path.join(ROOT, "plan/labels.yml")))
    ms = yaml.safe_load(open(os.path.join(ROOT, "plan/milestones.yml")))
    tickets = []
    for path in sorted(glob.glob(os.path.join(ROOT, "plan/tickets/*.md"))):
        text = open(path, encoding="utf-8").read()
        m = re.match(r"^---\n(.*?)\n---\n(.*)$", text, re.S)
        if not m:
            sys.exit(f"{path}: no front matter")
        meta = yaml.safe_load(m.group(1))
        # "[type: refactor, …]" parses as one-key mappings in YAML flow style
        meta["labels"] = [next(f"{k}: {v}" for k, v in l.items())
                          if isinstance(l, dict) else l
                          for l in meta["labels"]]
        meta["body"] = m.group(2).strip()
        meta["blocked_by"] = meta.get("blocked_by") or []
        tickets.append(meta)
    keys = {t["key"] for t in tickets}
    for t in tickets:
        for b in t["blocked_by"]:
            if b not in keys:
                sys.exit(f"{t['key']}: blocked_by {b} has no ticket")
        for name in t["labels"]:
            if name not in {l["name"] for l in labels["labels"]}:
                sys.exit(f"{t['key']}: label '{name}' is not in labels.yml")
    schedule(tickets, ms)
    for m in ms["milestones"]:            # due = last target in the phase
        ends = [t["target"] for t in tickets if t["milestone"] == m["key"]
                and t["key"].startswith("P")]
        if ends:
            m["due"] = max(ends)
    return labels["labels"], ms, tickets


def schedule(tickets, ms):
    """Start/target dates from the estimates, in ticket order."""
    start = ms["start"] if isinstance(ms["start"], dt.date) else \
        dt.date.fromisoformat(ms["start"])
    per_day = 7 * ms["buffer"] / ms["days_per_week"]   # calendar days

    def at(days):
        return start + dt.timedelta(days=round(days * per_day))

    by_key = {t["key"]: t for t in tickets}
    cum = 0.0
    for t in sorted((t for t in tickets if t["key"].startswith("P")),
                    key=lambda t: (int(t["key"][1]), int(t["key"][3:]))):
        t["start"] = at(cum)
        cum += float(t["estimate"])
        t["target"] = at(cum) - dt.timedelta(days=1)
    for t in tickets:
        if not t["key"].startswith("C"):
            continue
        anchor = [by_key[b] for b in t["blocked_by"] if b in by_key]
        t["start"] = (max(a["target"] for a in anchor) + dt.timedelta(days=1)
                      if anchor else start)
        t["target"] = t["start"] + dt.timedelta(
            days=max(2, round(float(t["estimate"]) * per_day)))


def issue_title(t):
    return f"[{t['key']}] {t['title']}"


def issue_body(t):
    return t["body"] + FOOTER.format(key=t["key"], repo=REPO)


def area_of(t):
    for name in t["labels"]:
        if name.startswith("area: "):
            return name[6:]
        if name == "type: coordination":
            return "coordination"
    return None


# --------------------------------------------------------------- repo
def sync_labels(labels, prune):
    have = {l["name"].lower(): l for l in rest_list(f"repos/{REPO}/labels")}
    for l in labels:
        cur = have.pop(l["name"].lower(), None)
        data = {"color": l["color"].lower(), "description": l["description"]}
        if cur is None:
            act(f"create label {l['name']}", rest, "POST",
                f"repos/{REPO}/labels", {"name": l["name"], **data})
        elif (cur["color"].lower(), cur.get("description") or "") != \
                (data["color"], data["description"]) or cur["name"] != l["name"]:
            act(f"update label {l['name']}", rest, "PATCH",
                f"repos/{REPO}/labels/{_q(cur['name'])}",
                {"new_name": l["name"], **data})
    for cur in have.values():
        if prune:
            act(f"delete label {cur['name']}", rest, "DELETE",
                f"repos/{REPO}/labels/{_q(cur['name'])}")
        else:
            print(f"keep label not in plan: {cur['name']} (use --prune)")


def _q(name):
    from urllib.parse import quote
    return quote(name, safe="")


def sync_milestones(ms):
    have = {m["title"]: m for m in rest_list(
        f"repos/{REPO}/milestones?state=all&per_page=100")}
    numbers = {}
    for m in ms["milestones"]:
        due = (f"{m['due']}T23:59:59Z" if m.get("due") else None)
        data = {"title": m["title"], "description": m["description"],
                "due_on": due}
        cur = have.get(m["title"])
        if cur is None:
            if due is None:
                del data["due_on"]      # POST rejects null
            res = act(f"create milestone {m['title']}", rest, "POST",
                      f"repos/{REPO}/milestones", data)
            numbers[m["key"]] = res["number"] if res else None
        else:
            numbers[m["key"]] = cur["number"]
            cur_due = (cur.get("due_on") or "")[:10] or None
            if (cur["description"] or "") != data["description"] or \
                    cur_due != (str(m["due"]) if m.get("due") else None):
                act(f"update milestone {m['title']}", rest, "PATCH",
                    f"repos/{REPO}/milestones/{cur['number']}", data)
    return numbers


def sync_issues(tickets, milestone_numbers):
    have = {}
    for i in rest_list(f"repos/{REPO}/issues?state=all&per_page=100"):
        if "pull_request" in i:
            continue
        m = KEY_RE.match(i["title"])
        if m:
            have[m.group(1)] = i
    issues = {}
    for t in tickets:
        data = {"title": issue_title(t), "body": issue_body(t),
                "labels": t["labels"],
                "milestone": milestone_numbers.get(t["milestone"])}
        cur = have.get(t["key"])
        if cur is None:
            res = act(f"create issue {data['title']}", rest, "POST",
                      f"repos/{REPO}/issues", data)
            issues[t["key"]] = res
        else:
            issues[t["key"]] = cur
            same = (cur["title"] == data["title"]
                    and (cur["body"] or "").strip() == data["body"].strip()
                    and sorted(l["name"] for l in cur["labels"])
                    == sorted(data["labels"])
                    and (cur["milestone"] or {}).get("number")
                    == data["milestone"])
            if not same:
                act(f"update issue {data['title']}", rest, "PATCH",
                    f"repos/{REPO}/issues/{cur['number']}", data)
    return issues


def sync_dependencies(tickets, issues):
    owner, name = REPO.split("/")
    for t in tickets:
        me = issues.get(t["key"])
        if not me or not t["blocked_by"]:
            continue
        cur = gql("""query($o:String!,$n:String!,$i:Int!){repository(owner:$o,
            name:$n){issue(number:$i){blockedBy(first:50){nodes{number}}}}}""",
                  o=owner, n=name, i=me["number"])
        blocked = {n["number"] for n in
                   cur["repository"]["issue"]["blockedBy"]["nodes"]}
        for b in t["blocked_by"]:
            other = issues.get(b)
            if other and other["number"] not in blocked:
                act(f"link {t['key']} blocked by {b}", gql,
                    """mutation($a:ID!,$b:ID!){addBlockedBy(input:{issueId:$a,
                    blockingIssueId:$b}){issue{number}}}""",
                    a=me["node_id"], b=other["node_id"])


# --------------------------------------------------------------- project
FIELDS_Q = """fields(first:50){nodes{
  ... on ProjectV2FieldCommon{id name dataType}
  ... on ProjectV2SingleSelectField{options{id name}}}}"""


def find_project():
    d = gql("""query($l:String!){user(login:$l){id projectsV2(first:100){
        nodes{id title number url}}}}""", l=OWNER)
    for p in d["user"]["projectsV2"]["nodes"]:
        if p["title"] == PROJECT_TITLE:
            return d["user"]["id"], p
    return d["user"]["id"], None


def project_fields(pid):
    d = gql("query($p:ID!){node(id:$p){... on ProjectV2{%s}}}" % FIELDS_Q,
            p=pid)
    return {f["name"]: f for f in d["node"]["fields"]["nodes"] if f}


def opts(options, with_desc=True):
    return [{"name": o[0], "color": o[1],
             "description": o[2] if with_desc and len(o) > 2 else ""}
            for o in options]


def ensure_select(pid, fields, name, options):
    want = [o[0] for o in options]
    cur = fields.get(name)
    if cur is None:
        act(f"create field {name}", gql, """mutation($p:ID!,$n:String!,
            $o:[ProjectV2SingleSelectFieldOptionInput!]!){createProjectV2Field(
            input:{projectId:$p,dataType:SINGLE_SELECT,name:$n,
            singleSelectOptions:$o}){projectV2Field{... on
            ProjectV2SingleSelectField{id}}}}""", p=pid, n=name, o=opts(options))
    elif [o["name"] for o in cur.get("options", [])] != want:
        act(f"set options of {name}", gql, """mutation($f:ID!,
            $o:[ProjectV2SingleSelectFieldOptionInput!]!){updateProjectV2Field(
            input:{fieldId:$f,singleSelectOptions:$o}){projectV2Field{... on
            ProjectV2SingleSelectField{id}}}}""", f=cur["id"], o=opts(options))


def ensure_field(pid, fields, name, dtype):
    if name not in fields:
        act(f"create field {name}", gql, """mutation($p:ID!,$n:String!,
            $t:ProjectV2CustomFieldType!){createProjectV2Field(input:{
            projectId:$p,dataType:$t,name:$n}){projectV2Field{
            ... on ProjectV2Field{id}}}}""", p=pid, n=name, t=dtype)


README = """Tracking for the App Inventor new UI work: restructuring the web client \
into self-contained layouts on shared logic. Each item is one pull request on \
mit-cml/appinventor-sources.

Start with AGENTS.md and docs/ in the repository.

Status: Backlog → Ready → In progress → Upstream review → Merged."""

VIEWS = [
    # name, layout, filter
    ("All tickets", "TABLE_LAYOUT", ""),
    ("Board", "BOARD_LAYOUT", ""),
    ("By milestone", "TABLE_LAYOUT", ""),
    ("Roadmap", "ROADMAP_LAYOUT", ""),
    ("Ready to pick up", "TABLE_LAYOUT", "status:Ready no:assignee"),
    ("Upstream review", "TABLE_LAYOUT", 'status:"Upstream review"'),
    ("Decisions", "TABLE_LAYOUT", 'label:"type: decision"'),
    ("My work", "BOARD_LAYOUT", "assignee:@me"),
]
TABLE_FIELDS = ["Title", "Status", "Milestone", "Labels", "Size", "Estimate",
                "Start", "Target", "Assignees", "Upstream PR"]


def sync_project(tickets, issues, public):
    owner_id, proj = find_project()
    repo_id = gql("""query($o:String!,$n:String!){repository(owner:$o,name:$n)
        {id}}""", o=OWNER, n=REPO.split("/")[1])["repository"]["id"] \
        if not DRY or proj else None
    if proj is None:
        res = act(f"create project {PROJECT_TITLE}", gql, """mutation($o:ID!,
            $t:String!,$r:ID){createProjectV2(input:{ownerId:$o,title:$t,
            repositoryId:$r}){projectV2{id title number url}}}""",
                  o=owner_id, t=PROJECT_TITLE, r=repo_id)
        if DRY:
            print("would add fields, items and views to the new project")
            return
        proj = res["createProjectV2"]["projectV2"]
    pid = proj["id"]
    act("update project description and readme", gql, """mutation($p:ID!,
        $d:String!,$r:String!){updateProjectV2(input:{
        projectId:$p,shortDescription:$d,readme:$r}){
        projectV2{id}}}""", p=pid, d=PROJECT_DESCRIPTION, r=README)
    if public is not None:              # otherwise leave visibility alone
        act(f"make project {'public' if public else 'private'}", gql,
            """mutation($p:ID!,$pub:Boolean!){updateProjectV2(input:{
            projectId:$p,public:$pub}){projectV2{id}}}""", p=pid, pub=public)

    fields = project_fields(pid)
    ensure_select(pid, fields, "Status", STATUS_OPTIONS)
    ensure_select(pid, fields, "Size", SIZE_OPTIONS)
    ensure_select(pid, fields, "Area", [(a, c) for a, c in AREA_OPTIONS])
    ensure_field(pid, fields, "Estimate", "NUMBER")
    ensure_field(pid, fields, "Start", "DATE")
    ensure_field(pid, fields, "Target", "DATE")
    ensure_field(pid, fields, "Upstream PR", "TEXT")
    if DRY:
        fields = project_fields(pid)
    else:
        fields = project_fields(pid)

    items = {}
    cursor = None
    while True:
        d = gql("""query($p:ID!,$c:String){node(id:$p){... on ProjectV2{
            items(first:100,after:$c){pageInfo{hasNextPage endCursor}
            nodes{id content{... on Issue{number}}
            fieldValues(first:30){nodes{
            ... on ProjectV2ItemFieldSingleSelectValue{name field{
              ... on ProjectV2FieldCommon{name}}}
            ... on ProjectV2ItemFieldNumberValue{number field{
              ... on ProjectV2FieldCommon{name}}}
            ... on ProjectV2ItemFieldDateValue{date field{
              ... on ProjectV2FieldCommon{name}}}}}}}}}}""", p=pid, c=cursor)
        page = d["node"]["items"]
        for n in page["nodes"]:
            if n["content"]:
                vals = {}
                for v in n["fieldValues"]["nodes"]:
                    if v and v.get("field"):
                        vals[v["field"]["name"]] = (
                            v.get("name") or v.get("date")
                            if "number" not in v else v["number"])
                items[n["content"]["number"]] = (n["id"], vals)
        if not page["pageInfo"]["hasNextPage"]:
            break
        cursor = page["pageInfo"]["endCursor"]

    for t in tickets:
        issue = issues.get(t["key"])
        if not issue:
            continue
        if issue["number"] in items:
            item_id, vals = items[issue["number"]]
        else:
            res = act(f"add {t['key']} to project", gql, """mutation($p:ID!,
                $c:ID!){addProjectV2ItemById(input:{projectId:$p,contentId:$c})
                {item{id}}}""", p=pid, c=issue["node_id"])
            if DRY:
                continue
            item_id, vals = res["addProjectV2ItemById"]["item"]["id"], {}
        want = {"Size": ("single", t["size"]),
                "Estimate": ("number", float(t["estimate"])),
                "Start": ("date", str(t["start"])),
                "Target": ("date", str(t["target"]))}
        if area_of(t):
            want["Area"] = ("single", area_of(t))
        for name, (kind, value) in want.items():
            if vals.get(name) != value:      # skip what is already right
                set_value(pid, fields, item_id, name, **{kind: value})
        if "Status" not in vals:
            status = "Backlog" if t["blocked_by"] else "Ready"
            set_value(pid, fields, item_id, "Status", single=status)
    sync_views(pid, fields)
    print(f"project: {proj.get('url', '')}")


def set_value(pid, fields, item_id, name, single=None, number=None,
              date=None):
    f = fields[name]
    if single is not None:
        opt = next(o["id"] for o in f["options"] if o["name"] == single)
        value = {"singleSelectOptionId": opt}
    elif number is not None:
        value = {"number": number}
    else:
        value = {"date": date}
    gql("""mutation($p:ID!,$i:ID!,$f:ID!,$v:ProjectV2FieldValue!){
        updateProjectV2ItemFieldValue(input:{projectId:$p,itemId:$i,
        fieldId:$f,value:$v}){projectV2Item{id}}}""",
        p=pid, i=item_id, f=f["id"], v=value)


def sync_views(pid, fields):
    d = gql("""query($p:ID!){node(id:$p){... on ProjectV2{views(first:30){
        nodes{id name number layout filter}}}}}""", p=pid)
    have = {v["name"]: v for v in d["node"]["views"]["nodes"]}
    if "All tickets" not in have and "View 1" in have:
        v = have.pop("View 1")
        act("rename View 1 to All tickets", gql, """mutation($v:ID!){
            updateProjectV2View(input:{viewId:$v,name:"All tickets"}){
            projectV2View{id}}}""", v=v["id"])
        have["All tickets"] = v
    visible = [fields[n]["id"] for n in TABLE_FIELDS if n in fields]
    for name, layout, filt in VIEWS:
        cur = have.get(name)
        if cur is None:
            res = act(f"create view {name}", gql, """mutation($p:ID!,
                $n:String!,$l:ProjectV2ViewLayout!,
                $c:ProjectV2ViewConfigurationInput){createProjectV2View(input:{
                projectId:$p,name:$n,layout:$l,configuration:$c}){
                projectV2View{id}}}""", p=pid, n=name, l=layout,
                      c={"visibleFieldIds": visible}
                      if layout == "TABLE_LAYOUT" else None)
            vid = res["createProjectV2View"]["projectV2View"]["id"] \
                if res else None
        else:
            vid = cur["id"]
            if (cur.get("filter") or "") == filt and cur["layout"] == layout:
                continue
        if vid:
            act(f"set view {name}: {layout} {filt!r}", gql, """mutation(
                $v:ID!,$l:ProjectV2ViewLayout!,$f:String){updateProjectV2View(
                input:{viewId:$v,layout:$l,filter:$f}){projectV2View{id}}}""",
                v=vid, l=layout, f=filt)


# --------------------------------------------------------------- main
def main():
    global DRY
    ap = argparse.ArgumentParser()
    ap.add_argument("--dry-run", action="store_true")
    ap.add_argument("--prune", action="store_true",
                    help="delete labels that are not in the plan")
    vis = ap.add_mutually_exclusive_group()
    vis.add_argument("--public", dest="public", action="store_true",
                     default=None, help="make the project public")
    vis.add_argument("--private", dest="public", action="store_false",
                     help="make the project private")
    a = ap.parse_args()
    DRY = a.dry_run
    labels, ms, tickets = load_plan()
    print(f"plan: {len(labels)} labels, {len(ms['milestones'])} milestones, "
          f"{len(tickets)} tickets")
    sync_labels(labels, a.prune)
    numbers = sync_milestones(ms)
    issues = sync_issues(tickets, numbers)
    if DRY and not any(issues.values()):
        print("would link dependencies and build the project")
        return
    sync_dependencies(tickets, issues)
    sync_project(tickets, issues, a.public)


if __name__ == "__main__":
    main()
