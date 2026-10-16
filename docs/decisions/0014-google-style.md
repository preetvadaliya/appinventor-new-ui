# 0014 Code follows Google style, checked by App Inventor's checkstyle

Date: 2026-10-16 · Status: accepted

## Context
About 45 PRs from several contributors and coding agents will touch most of
the client. Without one written style, each PR formats differently and
reviews turn into formatting arguments. App Inventor already ships a Google
Java Style checkstyle configuration
(`appinventor/lib/checkstyle/appinventor-checks.xml`, 100 columns, 2-space
indent, no wildcard imports, a `default` in every switch …) and an
`ant checkstyle` target that checks only the lines a commit changes, but
it reports warnings and nothing enforces it.

## Decision
- Java follows the Google Java Style Guide strictly; UiBinder templates and
  CSS follow the rules in `docs/13-code-style.md` (CSS per Google's
  HTML/CSS guide).
- Stricter than Google: every enum constant on its own line; no
  horizontal alignment anywhere; lambdas of one expression or statement.
- Every PR runs `ant checkstyle` and has no warnings on the lines it adds
  or changes. Lines it only moves keep their formatting.
- Doc snippets follow the same rules with a 70-column limit.

## Consequences
Reviews look at design, not spacing. Old code is reformatted only where a
PR already changes it, so diffs stay small and moves stay moves.
