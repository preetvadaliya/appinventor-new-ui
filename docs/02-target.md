# Target structure

Four packages under `client/`. Everything that belongs to only one layout lives
inside that layout; the other three exist only for code every layout needs.

```
client/
├── core/       logic: no widgets, no CSS
│   ├── actions/      ActionId, Action, ActionRegistry, IconKey,
│   │                 one class per action, by area
│   ├── presenters/   logic behind each view + its View interface
│   ├── theme/        ThemeService, ThemeMode, ThemeChangedEvent
│   ├── layout/       LayoutId
│   └── project/ settings/ properties/ commands/ rpc/ util/ i18n/ …
├── kit/        the contract every layout's building blocks fill
│   ├── Button, MenuButton, Dialog, TextField, Select, Tree …
│   ├── behaviour/    keyboard, focus, ARIA, written once
│   ├── standard/     a plain default kit
│   ├── KitView       base class: @UiFactory -> active kit
│   └── Ui            holds the active kit
├── features/   written once, look from the active kit
│   ├── designer/     viewer, palette, component tree,
│   │   └── mocks/    the user's app components (Mock*)
│   ├── properties/   panel and editors
│   ├── blocks/ assets/ console/ tutorial/ dataviz/ admin/
│   ├── dialogs/      project, files, component, settings, system
│   └── public/features/base.css
└── layouts/
    ├── Layout        what a layout must provide
    ├── LayoutRegistry
    ├── classic/      kit/ views/ menus/ icons/ public/classic/
    └── neo/          kit/ views/ menus/ icons/ public/neo/
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

The kit contract lives outside the layouts for the same reason: a shared screen
can only refer to "a Button" if the interface sits where every layout and every
feature can see it.

## Four kinds of UI code

| Kind | Examples | Where | Per layout |
|---|---|---|---|
| Kit | Button, Menu, Dialog, TextField | `layouts/<x>/kit/` | yes |
| Layout views | shell, top bar, project list, toolbars | `layouts/<x>/views/` | yes |
| Shared features | designer, properties, blocks, dialogs | `features/` | no; a layout may replace one |
| App components | `MockButton`, `MockForm` | `features/designer/mocks/` | no; phone theme |

A layout can replace a shared feature completely by implementing that feature's
presenter `View` interface; it never subclasses another layout.

## Import rules

A test (P7-40) fails the build when one of these breaks:

1. `core` imports no GWT widget classes and nothing from `kit`, `features` or
   `layouts`.
2. `features` import `kit` interfaces only, never a concrete layout class.
3. `layouts/x` never imports `layouts/y`. A new layout may extend
   `kit/standard`, never another layout.
4. Only the active layout's CSS loads: its `tokens.css`, `kit.css` and
   `views.css`, plus the shared `base.css`.
5. Menus and toolbars name actions by `ActionId`.

## Ode.java

`Ode.java` is the GWT entry point (`YaClient.gwt.xml:4`). It shrinks as its
parts move out (layout choice, theme, dialogs, shell wiring). Moving the class
itself to `core/boot/` needs MIT's agreement to change the entry point; until
then it stays in `client/`.

## Naming

"Component" already means the user's app components (Button, Label in the
palette). The per-layout building blocks are therefore called the **kit**:
`ClassicButton`, `NeoDialog`. `widgets/boxes/Layout` is renamed `PaneLayout`,
because "layout" now means classic, neo or nova.
