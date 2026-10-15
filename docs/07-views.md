# Views without inheritance

## Today

```java
// style/neo/ProjectToolbarNeo.java (master)
public class ProjectToolbarNeo extends ProjectToolbar {
  @UiField Label projectLabel;   // hides the parent's
  @UiField Label trashLabel;

  public void bindProjectToolbar() {
    populateToolbar(uibinder.createAndBindUi(this));
    super.projectLabel = projectLabel;
    super.trashLabel = trashLabel;
  }
}
```

The same pattern appears in `TopToolbarNeo`, `TopPanelNeo`, `ProjectListNeo`,
`ProjectFolderNeo` and `DesignToolbarNeo`.

## After

The presenter in `core/presenters` owns the logic and declares a `View`
interface. Each layout has an independent class that implements it with its own
template.

```java
// layouts/neo/views/projects/NeoProjectToolbar.java
public class NeoProjectToolbar extends Composite
    implements ProjectToolbarPresenter.View {
  interface Binder
      extends UiBinder<Widget, NeoProjectToolbar> {}
  private static final Binder BINDER =
      GWT.create(Binder.class);

  @UiField Label title;

  public NeoProjectToolbar() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void showTitle(String text) {
    title.setText(text);
  }
}
```

```xml
<!-- layouts/neo/views/projects/NeoProjectToolbar.ui.xml -->
<g:FlowPanel styleName="neo-projectbar">
  <g:FlowPanel styleName="neo-projectbar-start">
    <k:NeoButton action="NEW_PROJECT"/>
    <k:NeoButton action="IMPORT_PROJECT"/>
    <k:NeoButton action="IMPORT_TEMPLATE"/>
    <k:NeoButton action="NEW_FOLDER"/>
  </g:FlowPanel>
  <g:FlowPanel styleName="neo-projectbar-center">
    <k:NeoButton action="MOVE_PROJECTS"/>
    <k:NeoMenuButton action="EXPORT">
      <k:NeoMenuItem action="EXPORT_PROJECT"/>
      <k:NeoMenuItem action="EXPORT_ALL_PROJECTS"/>
    </k:NeoMenuButton>
  </g:FlowPanel>
  <g:FlowPanel styleName="neo-projectbar-end">
    <k:NeoButton action="VIEW_TRASH"/>
    <k:NeoButton action="DELETE_PROJECT"
        variant="DANGER"/>
  </g:FlowPanel>
</g:FlowPanel>
```

`FlowPanel` renders a plain `<div>`; the layout's CSS arranges the groups.
`HorizontalPanel`, `VerticalPanel` and `Grid` render tables for layout and are
avoided.

The layout hands its view out:

```java
// layouts/neo/NeoLayout.java
@Override
public ProjectToolbarPresenter.View projectToolbar() {
  return new NeoProjectToolbar();
}
```

## Callers get simpler

| Today | After |
|---|---|
| `Ode.java:452-454` calls `enableStartButton()`, `setProjectTabButtonsVisible(true)`, `setTrashTabButtonsVisible(false)` | `projectToolbar.showProjects()` |
| `ProjectList.java:309` calls `getProjectToolbar().updateButtons()` | nothing; the presenter listens to the selection |
| `ProjectToolbarNeo extends ProjectToolbar` | independent views, about 15 lines each |

## Replacing a shared dialog

Shared dialogs live in `features/dialogs`. A layout has three options per
dialog:

1. Do nothing. The dialog is built from kit pieces and already looks like the
   layout.
2. Restyle it in the layout's CSS.
3. Replace it by implementing the presenter's `View` interface in the layout
   and returning it from the matching `Layout` method. The presenter (validation,
   RPC, what happens on OK) does not change.
