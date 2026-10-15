# 0005 Pure package moves happen last

Date: 2026-10-15 · Status: accepted

## Context
Moving packages (for example `explorer/project` to `core/project`) conflicts with
every open pull request from other contributors.

## Decision
New code is created in its final package from the start, and old files move
when a PR already changes them. The remaining pure moves happen in P7-38 and
P7-39 only, in a window agreed with MIT (C-02), merged the same day, with no
edits besides package and import lines.

## Consequences
Until P7 the tree is mixed: new packages next to old ones. Reviewers see logic
changes and moves separately.
