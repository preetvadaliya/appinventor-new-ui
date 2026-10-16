# 0008 Templates read like React: HTMLPanels and flexbox, one per region

Date: 2026-10-16 · Status: accepted · Amended 2026-10-16: boxes and text
were plain `<div>`/`<span>`; now every one is a widget, so there is one rule.

## Context
Today's templates nest GWT panels (`FlowPanel`, `HorizontalPanel`, `DeckPanel`)
and keep the real layout in large CSS files (grid areas, absolute positioning,
`setVisible` calls from `Ode.java`). Reading a screen means reading Java, a
template and CSS together. Some templates are long (`TopToolbar.ui.xml`, 218
lines), others too small to be worth a file.

## Decision
- Every box in a template, the root and every nested one, is a
  `<gwt:HTMLPanel styleName="…">` with flex utility classes from the shared
  `layout.css`, named like Tailwind (`flex-row`, `flex-col`, `grow`,
  `overflow-auto`, `items-center`, `justify-end`, `gap-*`, `pad-*`) so they
  don't clash with today's `.row`, `.col` and `.center` classes.
- No raw HTML elements: no `<div>`, no `<span>`. Any box can later get a
  `ui:field` and be shown, hidden, moved or filled from Java as a widget,
  without changing what it is.
- Text is a widget too: `<gwt:Label>` for text on its own line,
  `<gwt:InlineLabel>` for text inside a row, both with `text="{messages.…}"`.
- `<gwt:FlowPanel>` where Java adds or removes children (lists);
  `<gwt:SimplePanel>` for one slot Java fills; `FocusPanel` for clickable rows;
  the layout's controls for buttons, inputs and menus.
- One template per **page region**: top bar, main menu, project toolbar, project
  list, design toolbar, palette, viewer, component tree, media, properties,
  block drawers, blocks workspace. Small templates only for items repeated in a
  list (project row, folder, palette item, property row).
- Controls (`ClassicButton` …) have their own template plus behaviour-only Java.
- Namespace prefixes are the last word of the package, so a tag says where
  its class lives: `ui:` (UiBinder itself), `gwt:` (GWT widgets, whose package
  ends in `ui`), `controls:` (any `…controls` package: the shared contracts
  in a shared view, the layout's own in a layout template), `editor:`,
  `projects:`, `topbar:`, `console:` and so on. In the rare template that
  needs both its layout's controls and the shared `client.controls` (for
  `IconView`), the layout's stay `controls:` and the shared one is
  `shared:`. Every template a ticket creates or converts uses these
  prefixes; untouched master templates keep theirs.
- On a component use `addStyleNames`, never `styleName`, so it keeps its class.
- The window never scrolls: the shell is `flex-col screen`, `grow` passes
  height down, only elements marked `overflow-auto` scroll.
- The Designer and the Blocks editor are two separate areas; switching shows one
  and hides the other.

## Consequences
Until P5-26 brings each layout's own buttons, P4 templates wrap today's
`widgets/Toolbar` inside an HTMLPanel root, because a `ToolbarItem` only works
inside a `Toolbar`.

A template shows the structure of its region at a glance, like JSX. Every
element is a widget, so nobody chooses between a div and a panel, and Java
can reach any box by adding a `ui:field`. Each box costs a Java object; that
is negligible in regions, which are built once per page, and item templates
repeated in long lists (project rows) keep their boxes few. Templates are a
little longer than JSX (`<gwt:HTMLPanel styleName=…>` instead of `<div class=…>`).
Changing where things sit means editing a template, not CSS. The rule set is
small and the same in every layout.
