# How the client works today

Measured on MIT master `ba85eb0d0` (October 2026). Paths are relative to
`appinventor/appengine/src/com/google/appinventor/client/` unless they start
with `appinventor/`.

## Modules

| Module | What it is |
|---|---|
| `appengine/` | The web IDE: GWT client, servlets, shared RPC interfaces |
| `blocklyeditor/` | App Inventor's Blockly extensions, built with Closure |
| `components/` | Android component runtime; generates `simple_components.json` |
| `common/` | Shared utilities and version info |
| `buildserver/` | Compiles projects into APK/AAB |

The client has 515 Java files and 38 `.ui.xml` files. GWT is 2.8.1-patched
(`appengine/build.xml:62`). `YaClient.gwt.xml` renames the module to `ode` and
sets `Ode` as the entry point.

## Boot and layout choice

`Ode.onModuleLoad` (`Ode.java:710`) runs one promise chain: system config,
terms of service, user settings, then `handleUiPreference` (`Ode.java:807`),
then `initializeUi` (`Ode.java:808`), then projects.

- **Layout** is a boolean, `getUserNewLayout()` (`Ode.java:1479`). The `?ui=`
  URL parameter wins by substring match (`classic`, `neo`); otherwise the
  `GeneralSettings.NewLayout` setting; default neo.
- **Theme** is a second boolean, `getUserDarkThemeEnabled()` (`Ode.java:1437`),
  from the same `?ui=` parameter or `DarkThemeEnabled`; default light.
- The layout is checked in three places: two `GWT.runAsync` branches in
  `handleUiPreference` (`Ode.java:968-1012`), a pick among four CSS bundles in
  `initializeUi` (`Ode.java:1055-1073`), and the template pick in
  `UiStyleFactory.createOde`.
- Changing layout or theme saves and calls `Window.Location.reload()`
  (`Ode.java:1520-1531`).

## The factory

`UiStyleFactory` has 6 hooks (7 methods). Classic is the base class;
`style/neo/UiFactoryNeo` overrides 5. `createNewYoungAndroidProjectWizard()` is
never called, so `NewYoungAndroidProjectWizardNeo` is dead code. Most neo views
are not made by the factory at all: neo's `Ode.ui.xml` names them directly.

## Views

| View | Classic | Neo (`style/neo/`) |
|---|---|---|
| Root template | `Ode.ui.xml` | `Ode.ui.xml` (about 90% identical) |
| Top bar, menus | `TopPanel`, `TopToolbar` (6 menus, 48 items) | `TopPanelNeo`, `TopToolbarNeo` (5 menus, 37 items) |
| Project list | `ProjectList`, `ProjectListItem`, `ProjectFolder`, `ProjectToolbar` | `…Neo` versions |
| Designer toolbar | `DesignToolbar` | `DesignToolbarNeo` |
| Viewer header | `YaVisibleComponentsPanel` | `YaVisibleComponentsPanelNeo` |
| Status bar | `StatusPanel` | `StatusPanelNeo` (byte-identical template) |

`YaVisibleComponentsPanel` is the whole phone-preview panel (size and preview
selectors, hidden-components checkbox, and the container for the mock form), not
only a header.

Each neo view subclasses its classic twin, declares its own UiBinder,
re-declares the same `@UiField`s and copies them back (`super.x = x`), for
example `TopToolbarNeo.java:44-48`.

Everything else is shared and built in Java: designer, `Mock*` components
(~104 files), properties (48 editors in `editor/youngandroid/properties`), Blockly wrapper (`BlocklyPanel`, about 49
JSNI methods), admin, wizards and dialogs.

## Actions

44 `Command` classes in `actions/` and 16 in `editor/youngandroid/actions/`.
Templates nest them: `<ai:ToolbarItem name="New"><actions:NewProjectAction/>`.
Code finds items by string name (`WIDGET_NAME_*`), 121 call sites such as
`setButtonEnabled("New", false)`. Every menu is defined once per layout, and some
names differ between the Java constants and the templates.

## Styling

- Always loaded: 14 `<link>`s in `appinventor/appengine/war/index.jsp:68-81`,
  including `Ya.css` (4,137 lines).
- Per layout and theme: one GWT `ClientBundle`, `Ode.Resources`
  (`Ode.java:2753-2783`), with four entries pairing a token file with a rules
  file: classic `light.css`/`dark.css` + `variableColors.css` (1,230 lines);
  neo `lightNeo.css`/`darkNeo.css` + `neo.css` (2,145 lines).
- Tokens are GWT `@def` constants, not CSS custom properties; dark mode is a
  different compiled bundle. About 570 hex colours remain hard-coded.
- Neo mostly overrides `Ya.css` (250 of its classes also appear there).
- Images: `Images` (137), `DarkModeImages` (9 overrides), `ImagesNeo` (88),
  `DarkModeImagesNeo` (93), about 350 PNGs. Neo also overrides most component
  palette icons, and uses Material Symbols through `components/Icon.java`.
  Classic shows a few Material glyphs too (folder rows) and 9 PNG chrome images.
- Blockly uses `geras2` and the default Classic theme; dark mode is a 31-line
  `darkTheme.js` passed to `AI.inject`. It does not depend on the layout.

## Tests

Client Java tests: 7 files in `appengine/tests/com/google/appinventor/client`.
They run through the `ai.dojunit` macro with `haltonfailure="no"`
(`build-common.xml:219`), so a failing test does not fail `ant tests`: read the
report. P7-40 adds a check that does fail the build.
Blockly: Karma + mocha (`ant BlocklyTest`).
