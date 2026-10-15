#!/usr/bin/env python3
"""Copy the state of each ticket's upstream pull request into the project.

For every project item whose "Upstream PR" field holds a pull request URL on
mit-cml/appinventor-sources:
  open    -> Status "Upstream review"
  merged  -> Status "Merged", and the ticket is closed
  closed without merge -> Status "In progress" (needs attention)

Run daily by .github/workflows/upstream-status.yml, or by hand.
Needs a token with the repo and project scopes (GH_TOKEN).
"""
import json
import re
import subprocess

OWNER = "preetvadaliya"
REPO = "preetvadaliya/appinventor-new-ui"
PROJECT_TITLE = "App Inventor New UI"
PR_RE = re.compile(r"github\.com/([^/]+/[^/]+)/pull/(\d+)")


def gh(*args, body=None):
    res = subprocess.run(["gh", *args], input=body, capture_output=True,
                         text=True, check=True)
    return res.stdout


def gql(query, **variables):
    res = json.loads(gh("api", "graphql", "--input", "-", body=json.dumps(
        {"query": query, "variables": variables})))
    if res.get("errors"):
        raise RuntimeError(res["errors"])
    return res["data"]


def main():
    d = gql("""query($l:String!){user(login:$l){projectsV2(first:100){nodes{
        id title fields(first:50){nodes{... on ProjectV2SingleSelectField{
        id name options{id name}}}}}}}}""", l=OWNER)
    proj = next(p for p in d["user"]["projectsV2"]["nodes"]
                if p["title"] == PROJECT_TITLE)
    status = next(f for f in proj["fields"]["nodes"]
                  if f and f.get("name") == "Status")
    option = {o["name"]: o["id"] for o in status["options"]}

    cursor = None
    while True:
        page = gql("""query($p:ID!,$c:String){node(id:$p){... on ProjectV2{
            items(first:100,after:$c){pageInfo{hasNextPage endCursor} nodes{
            id content{... on Issue{number state}}
            pr: fieldValueByName(name:"Upstream PR"){
              ... on ProjectV2ItemFieldTextValue{text}}
            st: fieldValueByName(name:"Status"){
              ... on ProjectV2ItemFieldSingleSelectValue{name}}}}}}}""",
                   p=proj["id"], c=cursor)["node"]["items"]
        for item in page["nodes"]:
            url = (item.get("pr") or {}).get("text") or ""
            m = PR_RE.search(url)
            if not m or not item["content"]:
                continue
            pr = json.loads(gh("pr", "view", m.group(2), "--repo",
                               m.group(1), "--json", "state"))
            want = {"OPEN": "Upstream review", "MERGED": "Merged",
                    "CLOSED": "In progress"}[pr["state"]]
            current = (item.get("st") or {}).get("name")
            if current != want:
                print(f"#{item['content']['number']}: {current} -> {want}")
                gql("""mutation($p:ID!,$i:ID!,$f:ID!,$o:String!){
                    updateProjectV2ItemFieldValue(input:{projectId:$p,
                    itemId:$i,fieldId:$f,value:{singleSelectOptionId:$o}}){
                    projectV2Item{id}}}""", p=proj["id"], i=item["id"],
                    f=status["id"], o=option[want])
            if want == "Merged" and item["content"]["state"] == "OPEN":
                gh("issue", "close", str(item["content"]["number"]),
                   "--repo", REPO, "--reason", "completed",
                   "--comment", f"Merged upstream: {url}")
        if not page["pageInfo"]["hasNextPage"]:
            break
        cursor = page["pageInfo"]["endCursor"]


if __name__ == "__main__":
    main()
