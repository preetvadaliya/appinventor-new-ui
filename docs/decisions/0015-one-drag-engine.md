# 0015 One drag engine for every drag in the designer

Date: 2026-10-16 · Status: accepted (resolves open questions 1 and 2 of
`docs/14-drag-and-drop.md`)

## Context
Dragging in the designer runs on two mechanisms: a home-made engine on GWT 1.x
mouse listeners (palette to viewer, moves in the viewer, the non-visible strip)
and the browser's HTML5 drag and drop, written inline, for reordering the
component tree. They draw different hints, ask different questions about what
may go where, and neither can be unit-tested or give a layout its own preview.
Keeping the tree on HTML5 would have saved 1.5 days.

## Decision
- Every drag between components goes through one engine,
  `controls/behaviour/DragDrop`, written once for every layout (P5-47). The
  component tree moves onto it too (P5-48), so there is one hint style and one
  code path. Files dragged in from the desktop stay on the browser's native
  drag and drop (P4-46).
- The rule "can X go into Y" is `core/dnd/ComponentRules`, asked by drag, tree,
  paste and keyboard alike (P3-45).
- Escape cancels a drag: the preview goes, the original comes back, no command
  runs. Today it does not. This is the one visible change in the work and
  P5-47 says so.

## Consequences
P5-47 and P5-48 together are 4.5 days, on top of the rest of P5. The drag
topic is a part of the proposal now, not a side note. Layouts get a preview
hook, `Controls.dragPreview()`, and classic and neo keep today's look.
