# Rules for every pull request

## Size

- One ticket, one PR.
- At most about 400 added lines and about 20 files. If a ticket cannot fit,
  split it on the ticket before you start.
- Pure package-move PRs (P7-38, P7-39) are exempt from the size limit and
  contain no edits besides package and import lines.
- CSS rules moved verbatim between files do not count towards the limit; check
  with `git diff --color-moved`. A ticket that still grows too large ships as
  `(1/2)` and `(2/2)` PRs. (Agreed 2026-10-15.)

## Behaviour

- No visual change unless the ticket says so. Attach the snapshot diff
  (8 screens × classic/neo × light/dark) to the PR.
- Old and new code paths coexist inside a phase. Do not remove an old path
  before the ticket that says so.
- Read-only accounts, the trash view and the gallery switches must behave as
  before; check them by hand when your ticket touches toolbars or menus.

## Structure

- New code goes into its final package (`core/`, `controls/`, `features/`,
  `layouts/<x>/`).
- `core/` never imports GWT widgets or `controls/`, `features/`, `layouts/`.
- `features/` use control interfaces only.
- A layout never imports or extends another layout.
- Menus and toolbars name actions by `ActionId`; never find a widget by name.
- Icons are `IconKey`s, never glyph names, outside a layout's icon set.

## Style

- Google Java Style, strictly, plus our stricter rules (decision 0014,
  `docs/13-code-style.md`): every enum constant on its own line, every `case`
  body on its own lines, no horizontal alignment, 100 columns.
- `ant checkstyle` shows no warnings on the lines the PR adds or changes.
  Lines only moved keep their formatting.

## Checks

```sh
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
ant -Dskip.ios=true noplay
ant checkstyle -Dcommit=upstream/master
(cd appengine && ant tests)
ant BlocklyTest
```

`AiClientLib` alone does not catch UiBinder or CssResource errors; run the full
build before opening the PR. `ant tests` stays green even when a client test
fails (`haltonfailure="no"`): read the test report.

## Naming

| Thing | Pattern | Example |
|---|---|---|
| Ticket | `[<KEY>] <end state>` | `[P3-10] Project toolbar runs on action ids` |
| Branch | `ui/<key>-<slug>` | `ui/p3-10-project-toolbar-actions` |
| Upstream PR | `[new-ui <KEY>] <ticket title>` | `[new-ui P3-10] Project toolbar runs on action ids` |
| Commit | conventional, short | `refactor(actions): project toolbar runs on action ids` |

Ticket titles describe the result, not the activity: no "Refactor …" or
"Update …". Sentence case, at most 60 characters, no full stop.

No AI attribution lines (`Co-Authored-By` and similar) in commits or PRs.

## Review

- Link the ticket in the upstream PR description.
- Put the upstream PR link in the project's **Upstream PR** field.
- Keep two or three PRs open at most, so reviews do not pile up.
