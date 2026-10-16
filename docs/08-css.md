# CSS

Decisions: `0002-css-custom-properties.md`, `0010-css-files.md`.

## Today

| Sheet | Lines | Loaded |
|---|---|---|
| `war/static/css/Ya.css` | 4,137 | always |
| `war/static/css/gwt.css` | 1,153 | always |
| `style/classic/variableColors.css` + `light.css`/`dark.css` | 1,230 + 76/62 | classic, via `Ode.Resources` |
| `style/neo/neo.css` + `lightNeo.css`/`darkNeo.css` | 2,145 + 62/59 | neo, via `Ode.Resources` |
| `android_holo.css`, `android_material.css`, `iOS.css`, `DarkTheme.css` | 45, 39, 218, 64 | always (phone preview skins) |

Neo mostly overrides `Ya.css` (250 of its classes also appear there). There are
no CSS custom properties; about 570 hex colours are hard-coded. `Ya.css` is also
linked from `war/Ya_tos_form.html`. About 142 gallery rules in `Ya.css` (lines
2822-3647) are used by no client code.

## Target: small files, one job each

```
features/public/features/          shared, always loaded
├── reset.css        page defaults (box-sizing, html/body), from tokens
├── layout.css       flex utilities only
├── properties.css   shared property editor views
├── dialogs.css      shared default dialogs
└── blocks.css       Blockly overrides
features/designer/mocks/public/mocks/   phone preview skins (4 files)

layouts/<x>/public/<x>/            only for the active layout
├── tokens.css       variables: light on :root, dark on [data-theme="dark"]
├── controls.css     how that layout's controls look
└── views/
    ├── shell.css    top bar, status bar, tutorial, panes
    ├── projects.css projects page
    └── editor.css   designer and blocks pages, column widths
```

| File | Holds | Never holds |
|---|---|---|
| `reset.css` | page defaults, using tokens | values, layout classes |
| `layout.css` | `flex-row`, `flex-col`, `grow`, `overflow-auto`, `items-center`, `justify-end`, `gap-*`, `pad-*` | colours, fonts, borders, sizes |
| shared feature files | looks of shared parts, using tokens | values |
| `tokens.css` | variables only | selectors other than `:root` and `:root[data-theme="dark"]` |
| `controls.css` | that layout's controls | page layout |
| `views/*.css` | that layout's regions, and its sizes | variable values |

Column widths are a design choice per layout, so they live in the layout's
`views/editor.css` (`.classic-PaletteColumn { width: 240px }`), not in the
shared utilities.

## Load order

```
reset.css → layout.css → shared feature files
          → tokens.css → controls.css → views/shell.css → views/projects.css → views/editor.css
```

`index.jsp` links the shared files. Each layout lists its own, in order, in
`Layout.stylesheets()`, and `LayoutRegistry` adds a `<link>` for each. Classic's
CSS never loads in neo. A build step may later join each group into one served
file without changing the sources.

CSS served from `/ode/` needs a static-files rule in
`war/WEB-INF/appengine-web.xml` (P2-05/P2-06).

## Token contract

Every layout defines the same token names, in light and dark, so shared parts can
rely on them. P2-05 and P2-06 convert today's `@def` names one-to-one
(`--gray-300`) to keep the change mechanical; the semantic names below are
agreed before P6-33, when shared files start reading tokens. Typical groups:

- surfaces and text: `--color-page`, `--color-surface`, `--color-header`,
  `--color-text`, `--color-muted`, `--color-border`
- brand and state: `--color-primary`, `--color-danger`, `--color-row-highlight`
- type: `--font-body`, `--font-size`
- images: `--image-logo`, `--image-phone-portrait`, …

## Light and dark

`ThemeService` sets `data-theme="dark"` on `<html>`. Only the values in
`tokens.css` change; every other file uses `var(--color-…)` and repaints.

## Order of work

1. P2: tokens move to custom properties; the rules files stay where they are.
2. P4-15: `reset.css` and `layout.css` arrive with the first new templates.
3. P6-33, P6-34: shared-part rules leave `Ya.css` for the shared feature files.
4. P6-35: frame rules leave `Ya.css` for each layout's `views/*.css`.
5. P6-36: control rules go to each layout's `controls.css`.
6. P6-37: `Ya.css` and `Ode.Resources` are deleted; load order as above.
