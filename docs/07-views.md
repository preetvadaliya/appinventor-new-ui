# Views

Decisions: `0003-no-layout-inheritance.md`, `0008-templates-like-react.md`,
`0009-designer-regions-per-layout.md`. Full examples: `docs/examples/`.

## One template per region

Each layout owns its views. A view is a `.ui.xml` template plus a small Java
class that implements a presenter's `View` interface. There is one template per
page region, and small ones only for items repeated in a list.

| Page | Templates (classic; neo has the same set) |
|---|---|
| Frame | `ClassicShell`, `ClassicTopBar`, `ClassicMainMenu`, `ClassicStatusBar` |
| Projects | `ClassicProjectsPage`, `ClassicProjectToolbar`, `ClassicProjectList`; per item `ClassicProjectRow`, `ClassicProjectFolder` |
| Editor | `ClassicEditorPage`, `ClassicDesignToolbar` |
| Designer | `ClassicDesignerArea` placing `ClassicPalette`, `ClassicViewer`, `ClassicComponentTree`, `ClassicMedia`, `ClassicProperties`; per item `ClassicPaletteItem`, `ClassicPropertyRow` |
| Blocks | `ClassicBlocksArea` placing `ClassicBlockDrawers`, `ClassicMedia`, `ClassicBlocksWorkspace` |
| Admin | `ClassicAdminPage` |

Designer and Blocks are two areas. `EditorPagePresenter` shows one and hides the
other; no pane is hidden by CSS.

## How a template is written

- The root is `<g:HTMLPanel>`. Layout inside it is plain `<div>`s with the flex
  classes from `layout.css`: `flex-row`, `flex-col`, `grow`, `overflow-auto`, `items-center`, `justify-end`,
  `gap-*`, `pad-*` (Tailwind-style names, so they don't clash with today's
  `.row`, `.col` and `.center`).
- `<g:FlowPanel>` only where Java adds or removes children (rows, items).
- `<g:SimplePanel>` only for one slot Java fills (the phone screen, an editor).
- Widgets only where there is behaviour: the layout's controls
  (`<c:ClassicButton action="NEW_PROJECT"/>`), `FocusPanel` for clickable rows.
- On a component use `addStyleNames`, never `styleName`, so it keeps its own
  class.
- Text set in the template uses `{messages.…}`; text in raw HTML uses
  `<ui:text from="{messages.…}"/>`.

```xml
<!-- layouts/classic/views/projects/ClassicProjectsPage.ui.xml -->
<g:HTMLPanel styleName="flex-col grow">
  <pr:ClassicProjectToolbar ui:field="toolbar"/>
  <pr:ClassicProjectList ui:field="list" addStyleNames="grow"/>
</g:HTMLPanel>
```

```xml
<!-- layouts/classic/views/editor/ClassicDesignerArea.ui.xml -->
<g:HTMLPanel styleName="flex-row grow gap-sm pad-sm">
  <ed:ClassicPalette ui:field="palette" addStyleNames="classic-PaletteColumn"/>
  <ed:ClassicViewer ui:field="viewer" addStyleNames="grow classic-ViewerColumn"/>
  <div class="flex-col classic-SideColumn gap-sm">
    <ed:ClassicComponentTree ui:field="components" addStyleNames="grow"/>
    <ed:ClassicMedia ui:field="media" addStyleNames="classic-MediaPane"/>
  </div>
  <ed:ClassicProperties ui:field="properties"
      addStyleNames="classic-PropertiesColumn"/>
</g:HTMLPanel>
```

## Full height

The window never scrolls. The shell is `flex-col screen`; `grow` passes the
remaining height down (page → area → column → region); only elements marked
`overflow-auto` scroll: the project rows, each region's body, the phone preview, the
trees. Blockly scrolls its own workspace.

## The Java behind a view

The view implements its presenter's `View` and holds no rules.

```java
// layouts/classic/views/projects/ClassicProjectToolbar.java
public class ClassicProjectToolbar extends Composite
    implements ProjectToolbarPresenter.View {
  @UiField Label title;
  @UiField DivElement projectButtons;
  @UiField DivElement trashButtons;

  public void showTitle(String text) {
    title.setText(text);
  }

  public void showTrash(boolean trash) {
    UIObject.setVisible(projectButtons, !trash);
    UIObject.setVisible(trashButtons, trash);
  }
}
```

The layout hands its views out:

```java
// layouts/classic/ClassicLayout.java
public ProjectToolbarPresenter.View projectToolbar() {
  return new ClassicProjectToolbar();
}
```

## Shared dialogs

Default dialogs live in `features/dialogs` and are built from control
interfaces (`<c:Button>`, `<c:TextField>`) through `SharedView`, so they look
right in any layout. A layout may replace one by implementing the presenter's
`View` and returning it from the matching `Layout` method; the presenter does
not change. Example: `NovaNewProjectDialog` in `docs/examples`.

## Property editor views

Property editors follow the same pattern (decision 0011). Each kind of editor
has a model in `core/properties/editors/` with a `View` interface, and one
shared default view in `features/properties/editors/`: a template plus
behaviour-only Java extending `SharedView`.

| Piece | File | Differs per layout? |
|---|---|---|
| Logic: valid values, encoding, summary | `core/properties/editors/LengthEditorModel.java` | no |
| Arrangement: what sits where | `features/properties/editors/LengthEditorView.ui.xml` | no, shared |
| Behaviour: popup, radios and fields in step | `features/properties/editors/LengthEditorView.java` | no, shared |
| Look of each control | `ClassicRadio`, `NeoRadio`, `ClassicTextField` … | yes, from `Controls.active()` |
| Colours, spacing | `tokens.css`, `controls.css`, shared `properties.css` | yes |

So classic and neo share every editor template and still look like
themselves. A layout writes its own editor view only to change the
arrangement (say, a segmented control instead of four radios), in
`layouts/<layout>/views/properties/`, returned from its `EditorViews`.
