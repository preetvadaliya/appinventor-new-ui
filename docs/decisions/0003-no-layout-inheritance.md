# 0003 No layout extends another layout

Date: 2026-10-15 · Status: accepted

## Context
Neo views subclass classic views, re-declare the same `@UiField`s and copy them
back (`super.x = x`). Classic's logic toggles neo-only buttons by name. A third
layout built the same way would triple the duplication.

## Decision
Logic lives in presenters in `core/presenters`, each with a `View` interface.
Each layout implements the views independently in `layouts/<x>/views/`. A
layout may extend the neutral `controls/standard`, never another layout. Shared
screens live in `features/` and use control interfaces. A test enforces the import
rules (P7-40).

## Consequences
Each layout can change structure (where a button sits, which items a menu has)
freely. About 37,700 lines of shared screens stay single-copy. Each layout pays
for its own small views (about 15 lines of Java each, plus templates).
