# App Inventor new UI

Planning and tracking for restructuring the MIT App Inventor web client so that
each look (classic, neo, and later nova) is a self-contained layout on top of
shared logic and shared screens.

The code changes land as small pull requests on
[mit-cml/appinventor-sources](https://github.com/mit-cml/appinventor-sources).
This repository holds the design, the decisions and the tickets; it holds no
App Inventor code.

## Status

Tracked in the GitHub project **App Inventor New UI** (Roadmap, Board,
By milestone, Ready to pick up, Upstream review, Decisions, My work).

| Milestone | Outcome |
|---|---|
| P0 · Groundwork | Dead code gone; every PR can prove it changes nothing on screen |
| P1 · Layouts by id | Layouts chosen by id, behind one `Layout` contract |
| P2 · Live light/dark | Switching light/dark no longer reloads the page |
| P3 · Actions | Every toolbar and menu item runs on an `ActionId` |
| P4 · Independent views | Classic and neo own their views; no inheritance |
| P5 · Controls | Each layout has its own controls and designer regions |
| P6 · CSS split | Each layout loads only its own CSS |
| P7 · Final moves | Final packages; a test enforces the import rules |
| Nova | A third layout, as one new folder |

## Getting started

New here? Read in this order; it takes about an hour.

1. [docs/00-overview.md](docs/00-overview.md) — goals, non-goals, scope
2. [docs/01-today.md](docs/01-today.md) — how the client works on master today
3. [docs/02-target.md](docs/02-target.md) — the four packages and the import rules
4. [docs/decisions/](docs/decisions/README.md) — every decision in one line, with links
5. [docs/examples/](docs/examples/README.md) — what the code looks like after the
   refactor: templates, controls, presenters, CSS, property editors
6. [AGENTS.md](AGENTS.md) — how to pick up a ticket (people and coding agents)

Then, for the ticket you pick, its "Read first" list.

Reference:

- [docs/09-migration.md](docs/09-migration.md) — the tickets, their order and the timeline
- [docs/10-pr-rules.md](docs/10-pr-rules.md) — what every PR must satisfy
- [docs/11-folder-structure.md](docs/11-folder-structure.md) — the complete client tree after P7
- [docs/file-map.html](docs/file-map.html) — every client file, today and proposed
  (download and open locally)

## Layout of this repository

```
docs/            design, one topic per file
docs/decisions/  one short record per decision, indexed in its README
docs/examples/   example code for the target structure (not compiled)
plan/            milestones, labels and one file per ticket
tools/           sync.py (plan -> GitHub), upstream_status.py
```

`plan/` is the source of truth. `tools/sync.py` creates or updates the labels,
milestones, issues, dependencies and project from it, and is safe to re-run.

## Maintaining this repository

Edit `plan/` (or `docs/`), commit, push, then sync:

```sh
python3 -m venv .venv
.venv/bin/pip install pyyaml
gh auth login --scopes project       # once; sync.py calls gh
.venv/bin/python tools/sync.py --dry-run
.venv/bin/python tools/sync.py
```

`sync.py` changes only what differs, retries network errors, and never deletes
issues; `--prune` also deletes labels that are not in `plan/labels.yml`.

The daily workflow `.github/workflows/upstream-status.yml` runs
`tools/upstream_status.py`, which moves cards when an upstream PR opens or
merges. It needs the repository secret `PROJECT_TOKEN`: a classic personal
access token with the `project` and `public_repo` scopes
(`gh secret set PROJECT_TOKEN --repo preetvadaliya/appinventor-new-ui`).

New ticket: copy an existing file in `plan/tickets/`, take the next key in its
phase, fill every section (summary, why with file and line evidence, read first,
in/out of scope, steps, acceptance criteria, rules, how to verify,
dependencies), then sync.

