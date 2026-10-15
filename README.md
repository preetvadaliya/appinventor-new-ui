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
| P5 · Controls | Each layout has its own controls; shared screens use control interfaces |
| P6 · CSS split | Each layout loads only its own CSS |
| P7 · Final moves | Final packages; a test enforces the import rules |
| Nova | A third layout, as one new folder |

## Read this first

- [AGENTS.md](AGENTS.md) — how to pick up a ticket (people and coding agents)
- [docs/00-overview.md](docs/00-overview.md) — goals, non-goals, scope
- [docs/02-target.md](docs/02-target.md) — the target structure
- [docs/09-migration.md](docs/09-migration.md) — the tickets, their order and the timeline
- [docs/11-folder-structure.md](docs/11-folder-structure.md) — the complete client tree after P7
- [docs/file-map.html](docs/file-map.html) — every client file, today and proposed
  (download and open locally)

## Layout of this repository

```
docs/            design, one topic per file
docs/decisions/  one short record per decision
plan/            milestones, labels and one file per ticket
tools/           sync.py (plan -> GitHub), upstream_status.py
```

`plan/` is the source of truth. `tools/sync.py` creates or updates the labels,
milestones, issues, dependencies and project from it, and is safe to re-run.
