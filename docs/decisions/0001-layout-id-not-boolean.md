# 0001 Layouts are ids, not a boolean

Date: 2026-10-15 · Status: accepted

## Context
The layout is a boolean, `Ode.getUserNewLayout()`, stored as `NewLayout`. Three
code paths branch on it (two `runAsync` branches, a four-way CSS pick, a
template pick). A third layout cannot be expressed.

## Decision
A `LayoutId` enum (`CLASSIC`, `NEO`, later `NOVA`) stored as `UiLayout`. The old
`NewLayout` value is migrated once. `?ui=` is parsed in one place. A
`LayoutRegistry` maps each id to a `Layout` behind its own `GWT.runAsync`.

## Consequences
Adding a layout means adding an enum value and a folder. Switching layout still
reloads the page; switching theme does not (0002).
