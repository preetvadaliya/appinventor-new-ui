# Code examples

Complete example files for four pieces of the new structure, written against
MIT master's real names and values: the New Project action, the project
toolbar (classic and neo), the New Project wizard, and the layout choice
property editors. They show the shape and size of each file; they have not
been compiled, and the tickets decide the final code.

Paths mirror `appengine/src/com/google/appinventor/client/`.

| Piece | Files |
|---|---|
| Action | `core/actions/{ActionId,IconKey,Action,ActionRegistry}.java`, `core/actions/project/NewProjectAction.java`, `core/app/App.java`, `core/presenters/Views.java` |
| Controls | `controls/{Controls,Button,Dialog,TextField,Select,SharedView}.java`, `controls/behaviour/ActionBinding.java`, `layouts/classic/controls/ClassicButton.java`, `layouts/neo/controls/NeoButton.java`, `layouts/neo/icons/NeoIcons.java` |
| Project toolbar | `core/presenters/ProjectToolbarPresenter.java`, `layouts/classic/views/projects/ClassicProjectToolbar.{java,ui.xml}`, `layouts/neo/views/projects/NeoProjectToolbar.{java,ui.xml}` |
| New Project wizard | `core/presenters/project/NewProjectPresenter.java`, `features/dialogs/project/NewProjectDialog.{java,ui.xml}`, `core/properties/choices/ProjectChoices.java` |
| Property editors | `core/properties/choices/{Choice,Choices,LayoutChoices}.java`, `core/properties/editors/{PropertyEditor,ChoiceEditorModel,PropertyEditors}.java`, `features/properties/editors/ChoiceEditorView.java` |
