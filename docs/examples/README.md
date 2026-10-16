# Code examples

Complete example files for four pieces of the new structure, written against
MIT master's real names and values: the New Project action, the project
toolbar (classic and neo), the New Project wizard, the layout choice
property editors, and icons that may be pictures or font glyphs (including
nova's, for later). They show the shape and size of each file; they have not
been compiled, and the tickets decide the final code.

Paths mirror `appengine/src/com/google/appinventor/client/`.

| Piece | Files |
|---|---|
| Action | `core/actions/{ActionId,IconKey,Action,ActionRegistry}.java`, `core/actions/project/NewProjectAction.java`, `core/app/App.java`, `core/presenters/Views.java` |
| Controls | `controls/{Controls,Button,IconButton,MenuButton,MenuItem,Collapsible,Dialog,TextField,Select,Checkbox,Radio,SharedView}.java`, `controls/behaviour/{ActionBinding,MenuKeyboard}.java`, `layouts/classic/controls/Classic{Button,IconButton,MenuButton,Menu,MenuItem,Collapsible,Select,Checkbox,Radio}.{java,ui.xml}`, `layouts/neo/controls/Neo{Button,IconButton,MenuButton,Menu,MenuItem,Collapsible,Select,Checkbox,Radio}.{java,ui.xml}`, `features/public/features/reset.css` (resets `<button>`), `layouts/neo/icons/NeoIcons.java` |
| Project toolbar | `core/presenters/ProjectToolbarPresenter.java`, `layouts/classic/views/projects/ClassicProjectToolbar.{java,ui.xml}`, `layouts/neo/views/projects/NeoProjectToolbar.{java,ui.xml}` |
| New Project wizard | `core/presenters/project/NewProjectPresenter.java`, `features/dialogs/project/NewProjectDialog.{java,ui.xml}`, `core/properties/choices/ProjectChoices.java` |
| Property editors | `core/properties/choices/{Choice,Choices,LayoutChoices}.java`, `core/properties/editors/{PropertyEditor,ChoiceEditorModel,PropertyEditors}.java`, `features/properties/editors/ChoiceEditorView.java` |
| Icons | `core/icons/{Icon,BlockCategory,ComponentIconSource}.java`, `controls/{Icons,IconView}.java`, `features/designer/palette/ComponentIcons.java`, `features/blocks/{BlockCategoryImages,BlockDrawerItem}.java`, `layouts/{classic,neo,nova}/icons/*Icons.java` |
| Nova's own dialog (later) | `layouts/nova/views/dialogs/NovaNewProjectDialog.{java,ui.xml}`, `layouts/nova/public/nova/dialog.css`, `layouts/nova/NovaLayout.java` |
| Classic pages by region | `layouts/classic/views/`: one template per region. Projects: `ClassicTopBar`, `ClassicMainMenu`, `ClassicProjectToolbar`, `ClassicProjectList` (+ `ClassicProjectRow`, `ClassicProjectFolder` per item). Designer: `ClassicDesignToolbar`, `ClassicDesignerArea` with `ClassicPalette`, `ClassicViewer`, `ClassicComponentTree`, `ClassicMedia`, `ClassicProperties` (+ `ClassicPaletteItem`, `ClassicPropertyRow` per item); Blocks: `ClassicBlocksArea`, `ClassicBlockDrawers`, `ClassicBlocksWorkspace`. Every box is a `<gwt:HTMLPanel>` and text a `Label`/`InlineLabel`, with flex classes from `features/public/features/layout.css`. |
| Classic controls | `layouts/classic/controls/*.ui.xml` with behaviour-only Java (`ClassicButton`, `ClassicIconButton`, `ClassicMenuButton`, `ClassicMenu`, `ClassicMenuItem`, `ClassicCollapsible`, `ClassicTextField`, `ClassicSelect`, `ClassicCheckbox`, `ClassicRadio`, `ClassicDialog`, `ClassicControls`). Everything pressed (buttons, icon buttons, menu buttons, menu items, a collapsible's header) is a `<gwt:HTMLPanel tag="button">` root: focus, Enter and Space come from the browser, disabled is the button's `disabled` property. Buttons and menus show the label only, a menu button adds the caret ▾, and `ClassicIconButton` shows today's help and close pictures with its name in `aria-label` and `title`. |
| Neo pages by region | `layouts/neo/views/`: the same templates file for file with the `Neo` prefix (`NeoShell`, `NeoTopBar`, `NeoMainMenu`, `NeoStatusBar`, `NeoProjectsPage` … `NeoBlocksWorkspace`, `NeoAdminPage`) and the same `.java` companions. Neo's own look: every region is a rounded card (`neo-Card` with a `neo-Card-title` row), Help sits in `NeoTopBar` instead of `NeoMainMenu`, the project toolbar groups icon buttons and an Export menu, the viewer's preview options sit in its card title, and Designer / Blocks is a segmented switch. |
| Neo controls | `layouts/neo/controls/*.ui.xml` with behaviour-only Java (`NeoButton`, `NeoIconButton`, `NeoMenuButton`, `NeoMenu`, `NeoMenuItem`, `NeoCollapsible`, `NeoTextField`, `NeoSelect`, `NeoCheckbox`, `NeoRadio`, `NeoDialog`, `NeoControls`), with the same `<button>` roots as classic; `NeoButton`, `NeoMenuButton` and `NeoMenuItem` show the action's Material glyph from `NeoIcons`, then the label; `NeoIconButton` has only the glyph, its name in `aria-label` and `title`. Both layouts' menu buttons open their menu through `controls/behaviour/MenuKeyboard`. |
| CSS files | Shared, always loaded: `features/public/features/{reset,layout}.css`. Classic only: `layouts/classic/public/classic/{tokens,controls}.css` and `views/{shell,projects,editor}.css`, listed in load order by `layouts/classic/ClassicLayout.java`. Neo only: the same five files under `layouts/neo/public/neo/` (`tokens.css` adds neo's radii and shadows), listed by `layouts/neo/NeoLayout.java`. |
| Property editors end to end | `core/properties/editors/{PropertyEditor,EditorView,EditorViews,ChoiceEditorModel,NumberEditorModel,LengthEditorModel,PropertyEditors}.java`, `core/properties/choices/*`, `core/presenters/PropertiesPresenter.java`, `features/properties/editors/{ChoiceEditorView,NumberEditorView,LengthEditorView}.{ui.xml,java}`, `features/properties/editors/SharedEditorViews.java`, `controls/{Radio,TextField,SharedView}.java`, `layouts/classic/controls/ClassicRadio.{ui.xml,java}`, `layouts/neo/controls/NeoRadio.{ui.xml,java}`, `layouts/classic/views/editor/{ClassicProperties,ClassicPropertyRow}.{ui.xml,java}`, `layouts/neo/views/editor/{NeoProperties,NeoPropertyRow}.{ui.xml,java}` |

## Known gaps

The examples show the shape of the code; they are not compiled, and a few
things are left out on purpose or not finished:

- Neo's token values in `layouts/neo/public/neo/tokens.css` are placeholders;
  P2-06 converts the real ones from today's `lightNeo.css`/`darkNeo.css`.
- `IconView` fields in templates need `@UiField(provided = true)`, because
  `IconView` has no zero-argument constructor; the controls show it, the
  palette item templates have no Java yet.
- Menu items and separators written inside a menu button in a template
  reach `addItem` and `addSeparator` through P5-23's `ActionItemParser`,
  which the examples leave out. `MenuKeyboard` is a minimal stand-in for
  the one P5-23 extracts from `DropDownButton` (no type-ahead).
- `ClassicTree`/`NeoTree` and 16 region and item templates per layout under
  `layouts/*/views/` (top bar, main menu, palette, project rows …) have no
  owner class yet, and a `.ui.xml` cannot compile without one.
- `ClassicMenuSeparator.ui.xml` and `NeoMenuSeparator.ui.xml` have no class
  of their own: `ClassicMenu`/`NeoMenu` bind them with a second `@UiTemplate`
  binder, one copy per separator.
- The active half of neo's Designer / Blocks switch has no style yet.

