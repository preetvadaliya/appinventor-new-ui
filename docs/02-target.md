# Target structure

Four packages under `client/`. Everything that belongs to only one layout lives
inside that layout; the other three exist only for code every layout needs.

```
client/
├── core/       logic: no widgets, no CSS
│   ├── actions/      ActionId, Action, ActionRegistry, IconKey,
│   │                 one class per action, by area
│   ├── presenters/   logic behind each view + its View interface
│   │                 (Shell, ProjectList, Palette, Viewer, Properties …)
│   ├── properties/   property model, editors/ (logic models),
│   │                 choices/ (option lists as data)
│   ├── theme/        ThemeService, ThemeMode, ThemeChangedEvent
│   ├── layout/       LayoutId
│   └── project/ settings/ commands/ rpc/ util/ i18n/ …
├── controls/   the contract every layout's building blocks fill
│   ├── Button, MenuButton, Dialog, TextField, Select, Tree …
│   ├── behaviour/    keyboard, focus, ARIA, written once
│   ├── standard/     a plain default set
│   ├── SharedView    base class: @UiFactory -> active controls
│   └── Controls      the factory; holds the active set
├── features/   shared parts, look from the active controls
│   ├── designer/mocks/   the user's app components (Mock*), phone skins
│   ├── properties/editors/  default editor views (ChoiceEditorView …)
│   ├── blocks/       Blockly bridge (BlocklyPanel)
│   ├── dialogs/      default dialogs a layout may replace
│   ├── console/ tutorial/ dataviz/ admin/
│   └── public/features/  reset.css, layout.css, shared feature CSS
└── layouts/
    ├── Layout        what a layout must provide
    ├── LayoutRegistry
    ├── classic/
    │   ├── controls/     ClassicButton … (template + behaviour)
    │   ├── views/        shell/ topbar/ menus/ status/ projects/
    │   │                 editor/ (designer and blocks regions) admin/
    │   ├── icons/
    │   └── public/classic/  tokens.css, controls.css, views/*.css
    └── neo/          same shape
```

## Why four packages and not just `layouts/`

About 37,700 lines of client code are needed by every layout: the `Mock*`
components (19,422 lines), wizards and dialogs (4,637), property editors
(3,976 + 3,144), the blocks wrapper (2,309), the palette (2,251) and more. All of
neo's own views today are 24 files and about 2,600 lines.

If everything lived under `layouts/`, that shared code would either be copied
into each layout or owned by one layout that the others import. The first
triples every fix; the second makes one layout the base again, which is the
problem being solved.

The controls contract lives outside the layouts for the same reason: a shared screen
can only refer to "a Button" if the interface sits where every layout and every
feature can see it.

## Four kinds of UI code

| Kind | Examples | Where | Per layout |
|---|---|---|---|
| Controls | Button, Menu, Dialog, TextField | `layouts/<x>/controls/` | yes |
| Layout views | shell, top bar, toolbars, project list, and the designer and blocks regions (palette, viewer, component tree, media, properties, drawers) | `layouts/<x>/views/` | yes |
| Shared parts | property editor views, default dialogs, Blockly bridge, console, tutorial | `features/` | no; a layout may replace one |
| App components | `MockButton`, `MockForm` | `features/designer/mocks/` | no; phone theme |

The designer and blocks regions belong to each layout (decision
`0009-designer-regions-per-layout.md`); their logic is shared through
presenters. Templates follow decision `0008-templates-like-react.md`.

## Import rules

A test (P7-40) fails the build when one of these breaks:

1. `core` imports no GWT widget classes and nothing from `controls`, `features` or
   `layouts`.
2. `features` import `controls` interfaces only, never a concrete layout class.
3. `layouts/x` never imports `layouts/y`. A new layout may extend
   `controls/standard`, never another layout.
4. Only the active layout's CSS loads: its `tokens.css`, `controls.css` and
   `views/*.css`, after the shared `reset.css`, `layout.css` and shared feature
   files (decision `0010-css-files.md`).
5. Menus and toolbars name actions by `ActionId`.

## Ode.java

`Ode.java` is the GWT entry point (`YaClient.gwt.xml:4`). It shrinks as its
parts move out (layout choice, theme, dialogs, shell wiring). Moving the class
itself to `core/boot/` needs MIT's agreement to change the entry point; until
then it stays in `client/`.

## Naming

"Component" already means the user's app components (Button, Label in the
palette). The per-layout building blocks are therefore called the **controls**:
`ClassicButton`, `NeoDialog`. `widgets/boxes/Layout` is renamed `PaneLayout`,
because "layout" now means classic, neo or nova.
