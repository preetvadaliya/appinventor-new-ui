# 0008 Templates read like React: divs and flexbox, one per region

Date: 2026-10-16 · Status: accepted

## Context
Today's templates nest GWT panels (`FlowPanel`, `HorizontalPanel`, `DeckPanel`)
and keep the real layout in large CSS files (grid areas, absolute positioning,
`setVisible` calls from `Ode.java`). Reading a screen means reading Java, a
template and CSS together. Some templates are long (`TopToolbar.ui.xml`, 218
lines), others too small to be worth a file.

## Decision
- Every template's root is a `<g:HTMLPanel>`; its layout is plain `<div>`s with
  flex utility classes from the shared `layout.css`, named like Tailwind
  (`flex-row`, `flex-col`, `grow`, `overflow-auto`, `items-center`,
  `justify-end`, `gap-*`, `pad-*`) so they don't clash with today's `.row`,
  `.col` and `.center` classes.
- `<g:FlowPanel>` only where Java adds or removes children (lists);
  `<g:SimplePanel>` only for one slot Java fills; widgets only where there is
  behaviour (controls, `FocusPanel` for clickable rows).
- One template per **page region**: top bar, main menu, project toolbar, project
  list, design toolbar, palette, viewer, component tree, media, properties,
  block drawers, blocks workspace. Small templates only for items repeated in a
  list (project row, folder, palette item, property row).
- Controls (`ClassicButton` …) have their own template plus behaviour-only Java.
- On a component use `addStyleNames`, never `styleName`, so it keeps its class.
- The window never scrolls: the shell is `flex-col screen`, `grow` passes
  height down, only elements marked `overflow-auto` scroll.
- The Designer and the Blocks editor are two separate areas; switching shows one
  and hides the other.

## Consequences
Until P5-26 brings each layout's own buttons, P4 templates wrap today's
`widgets/Toolbar` inside an HTMLPanel root, because a `ToolbarItem` only works
inside a `Toolbar`.

A template shows the structure of its region at a glance, like JSX. Plain divs
cost no Java objects. Changing where things sit means editing a template, not
CSS. The rule set is small and the same in every layout.
