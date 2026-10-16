# 0010 CSS is split into small files by job

Date: 2026-10-16 · Status: accepted (refines 0002)

## Decision
Shared, always loaded, from `features/public/features/`:

- `reset.css`: page defaults (box-sizing, html/body), values from tokens
- `layout.css`: flex utilities only; no colours, fonts, borders or sizes
- feature files added as rules leave `Ya.css`: `properties.css`, `dialogs.css`,
  `blocks.css`; phone skins and mock rules in `mocks/` (`mocks.css` plus the
  four skin files)

Per layout, from `layouts/<x>/public/<x>/`, loaded only for that layout:

- `tokens.css`: variables only, `:root` (light) and `:root[data-theme="dark"]`
- `controls.css`: how that layout's controls look
- `views/shell.css`, `views/projects.css`, `views/editor.css`: how its regions
  look, including its sizes (column widths are a design choice per layout)

Load order: reset, layout, shared feature files, then the layout's tokens,
controls and views. `index.jsp` links the shared files; each layout lists its
own in `Layout.stylesheets()` and `LayoutRegistry` adds them.

The console's look lives in each layout's `views/editor.css`, the admin
list's in `views/shell.css`.

`reset.css` arrives in stages so nothing changes on screen early: P4-15 adds
only harmless rules, P4-21 adds `overflow: hidden` on `html, body` together
with the full-height shell, and the global `box-sizing` rule lands in P6-35
only if the snapshot diff stays clean.

## Consequences
Light/dark touches only `tokens.css`. A new layout adds its own files and never
edits shared ones. Seven or so small files load per page; a build step can join
each group later without changing the sources.
