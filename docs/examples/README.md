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
| Page layout (full height, panes scroll) | `layouts/{classic,neo}/views/{shell,topbar,status,projects,editor,admin}/*.ui.xml`, `*Shell.java`, `*EditorPage.java`, `core/presenters/{ShellPresenter,EditorPagePresenter}.java`, `layouts/{classic,neo}/public/*/layout.css` |
| Classic split into small components | `layouts/classic/views/**/*.ui.xml` (27 templates: shell, top bar parts, one file per menu, projects page parts with a reusable `ClassicSortHeader`, editor page parts), `ClassicSortHeader.java`, `ClassicWorkArea.java` |
| Designer split into small components | `features/workspace/Pane.{ui.xml,java}`, `features/designer/palette/{PaletteView,PaletteCategory,PaletteItem}.ui.xml`, `features/designer/viewer/{ViewerView,ViewerHeader,PhonePreview,NonVisibleStrip}.ui.xml`, `features/designer/tree/{ComponentTreeView,ComponentActions}.ui.xml`, `features/properties/{PropertiesView,PropertyCategory,PropertyRow}.ui.xml`, `features/assets/AssetsView.ui.xml`, `features/blocks/BlockDrawersView.ui.xml`, `layouts/classic/views/editor/Classic{Designer,Blocks}Area.{ui.xml,java}` |
| React-style markup | `features/public/features/flex.css` (row, col, grow, gap …); classic templates and the shared designer parts use `<g:HTMLPanel>` with plain `<div>`s; `layouts/classic/public/classic/layout.css` keeps only colours and borders. Neo's examples still use the older grid version. |
