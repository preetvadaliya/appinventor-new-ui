# Working on a ticket

This file is the starting point for anyone, person or coding agent, who picks up
a ticket in this project. Read it fully before you start.

## What this project is

The MIT App Inventor web client (GWT 2.8.1) is being restructured so that each
look is a self-contained layout on top of shared logic. Today neo subclasses
classic, logic finds buttons by string name, and switching light/dark reloads the
page. The target and the reasons are in `docs/`.

Scope right now: **classic and neo only**. Do not add nova code.

## Read in this order

1. `docs/00-overview.md` — goals and non-goals
2. `docs/02-target.md` — the four packages and the import rules
3. The doc for your ticket's area (the ticket's "Read first" section)
4. `docs/10-pr-rules.md` — what every PR must satisfy
5. `docs/decisions/` — when you wonder why something is the way it is

`docs/01-today.md` explains how the client works on master, and
`docs/file-map.html` maps every client file to its new home.

## Where the code lives

- Upstream: `https://github.com/mit-cml/appinventor-sources`, branch `master`
- Work in a fork. One branch per ticket, cut from `upstream/master`:
  `ui/<key in lower case>-<short-slug>`, e.g. `ui/p3-10-project-toolbar-actions`
- Client code: `appinventor/appengine/src/com/google/appinventor/client/`

## Build and check

Java 17 only. Homebrew's `ant` picks a newer JDK unless `JAVA_HOME` is set,
and that silently produces class files the dev server cannot load.

```sh
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
cd appinventor
ant -Dskip.ios=true noplay           # full build
ant -q -Dskip.ios=true -f appengine/build.xml AiClientLib
                                     # ~10 s Java check, no GWT
(cd appengine && ant tests)          # server, shared, client tests;
                                     # read the report: failures do
                                     # not fail the build
ant BlocklyTest                      # blocks editor tests
```

Do not run `ant clean`: it wipes the local datastore. Dev server: build, then
serve `appengine/build/war/` on port 8888 and sign in with any email
(`test@example.com`).

Prove "no visual change" with the snapshot script from P0-02 (once it lands):
8 screens × classic/neo × light/dark, before and after your change.

## How to deliver

1. Read the ticket. If anything in it is wrong on today's master, comment on
   the ticket before you change course.
2. Keep to the ticket's "In scope" list. If you need more, say so on the ticket.
3. Open the upstream PR with the title `[new-ui <KEY>] <ticket title>`, link the
   ticket in the description, and attach the snapshot diff.
4. Put the upstream PR link in the project's **Upstream PR** field. The daily
   sync moves the card to *Upstream review* and, on merge, to *Merged*.

## Rules that never bend

- One ticket, one PR; at most ~400 added lines and ~20 files.
- No visual change unless the ticket says so.
- `core/` never imports GWT widgets or `controls/`, `features/`, `layouts/`.
- `features/` use control interfaces only, never a concrete layout class.
- A layout never imports or extends another layout.
- Menus and toolbars name actions by `ActionId`; never find a widget by name.
- Commit subjects are short and conventional, e.g.
  `refactor(actions): project toolbar runs on action ids`.
- No AI attribution lines (`Co-Authored-By` and similar) in commits or PRs.
