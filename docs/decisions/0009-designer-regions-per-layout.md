# 0009 Designer regions belong to each layout

Date: 2026-10-16 · Status: accepted (refines 0003)

## Context
The first plan made the palette, viewer, component tree, properties and assets
shared screens in `features/`, built from control interfaces. Working through
the templates showed each layout wants its own structure for them (neo already
replaces the viewer header; nova will differ more), and the logic in them is
small next to their markup.

## Decision
- The designer and blocks regions are **views owned by each layout**:
  `layouts/<x>/views/editor/<X>Palette`, `<X>Viewer`, `<X>ComponentTree`,
  `<X>Media`, `<X>Properties`, `<X>BlockDrawers`, `<X>BlocksWorkspace`, placed
  by `<X>DesignerArea` and `<X>BlocksArea`.
- Their logic lives once in presenters in `core/presenters`
  (`PalettePresenter`, `ViewerPresenter`, `ComponentTreePresenter`,
  `AssetsPresenter`, `PropertiesPresenter`, `BlockDrawersPresenter`), each with
  a `View` interface the regions implement.
- Still shared in `features/`: the user's app components (`Mock*`) and phone
  skins, the property editor views (`ChoiceEditorView` …), the Blockly bridge
  (`BlocklyPanel`), the console, tutorial, data visualizer, admin list, and the
  default dialogs (which a layout may replace).

## Consequences
Each layout writes about seven small region templates (15–25 lines each) and
gets full control over them. No region logic is duplicated.
