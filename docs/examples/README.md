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
| Controls | `controls/{Controls,Button,Dialog,TextField,Select,SharedView}.java`, `controls/behaviour/ActionBinding.java`, `layouts/classic/controls/ClassicButton.java`, `layouts/neo/controls/NeoButton.java`, `layouts/neo/icons/NeoIcons.java` |
| Project toolbar | `core/presenters/ProjectToolbarPresenter.java`, `layouts/classic/views/projects/ClassicProjectToolbar.{java,ui.xml}`, `layouts/neo/views/projects/NeoProjectToolbar.{java,ui.xml}` |
| New Project wizard | `core/presenters/project/NewProjectPresenter.java`, `features/dialogs/project/NewProjectDialog.{java,ui.xml}`, `core/properties/choices/ProjectChoices.java` |
| Property editors | `core/properties/choices/{Choice,Choices,LayoutChoices}.java`, `core/properties/editors/{PropertyEditor,ChoiceEditorModel,PropertyEditors}.java`, `features/properties/editors/ChoiceEditorView.java` |
| Icons | `core/icons/{Icon,BlockCategory,ComponentIconSource}.java`, `controls/{Icons,IconView}.java`, `features/designer/palette/ComponentIcons.java`, `features/blocks/{BlockCategoryImages,BlockDrawerItem}.java`, `layouts/{classic,neo,nova}/icons/*Icons.java` |
| Nova's own dialog (later) | `layouts/nova/views/dialogs/NovaNewProjectDialog.{java,ui.xml}`, `layouts/nova/public/nova/dialog.css`, `layouts/nova/NovaLayout.java` |
| Classic pages by region | `layouts/classic/views/`: one template per region. Projects: `ClassicTopBar`, `ClassicMainMenu`, `ClassicProjectToolbar`, `ClassicProjectList` (+ `ClassicProjectRow`, `ClassicProjectFolder` per item). Designer: `ClassicDesignToolbar`, `ClassicDesignerArea` with `ClassicPalette`, `ClassicViewer`, `ClassicComponentTree`, `ClassicMedia`, `ClassicProperties` (+ `ClassicPaletteItem`, `ClassicPropertyRow` per item); Blocks: `ClassicBlocksArea`, `ClassicBlockDrawers`, `ClassicBlocksWorkspace`. Every box is a `<gwt:HTMLPanel>` and text a `Label`/`InlineLabel`, with flex classes from `features/public/features/layout.css`. |
| Classic controls | `layouts/classic/controls/*.ui.xml` with behaviour-only Java (`ClassicButton`, `ClassicTextField`, `ClassicDialog`, `ClassicControls`) |
| CSS files | Shared, always loaded: `features/public/features/{reset,layout}.css`. Classic only: `layouts/classic/public/classic/{tokens,controls}.css` and `views/{shell,projects,editor}.css`, listed in load order by `layouts/classic/ClassicLayout.java`. Neo's example CSS still uses the older single `layout.css`. |
| Property editors end to end | `core/properties/editors/{PropertyEditor,EditorView,EditorViews,ChoiceEditorModel,NumberEditorModel,LengthEditorModel,PropertyEditors}.java`, `core/properties/choices/*`, `core/presenters/PropertiesPresenter.java`, `features/properties/editors/{ChoiceEditorView,NumberEditorView,LengthEditorView}.{ui.xml,java}`, `features/properties/editors/SharedEditorViews.java`, `controls/{Radio,TextField,SharedView}.java`, `layouts/classic/controls/ClassicRadio.ui.xml`, `layouts/classic/views/editor/{ClassicProperties,ClassicPropertyRow}.{ui.xml,java}` |
