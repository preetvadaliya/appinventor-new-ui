# Views

Decisions: `0003-no-layout-inheritance.md`, `0008-templates-like-react.md`,
`0009-designer-regions-per-layout.md`. Snippets: `docs/12-patterns.md`.

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

- Every box is a `<gwt:HTMLPanel>`, the root and every nested one, with the flex
  classes from `layout.css`: `flex-row`, `flex-col`, `grow`, `overflow-auto`, `items-center`, `justify-end`,
  `gap-*`, `pad-*` (Tailwind-style names, so they don't clash with today's
  `.row`, `.col` and `.center`).
- No raw `<div>` or `<span>` (decision 0008): any box can get a `ui:field` and
  be handled from Java as a widget.
- `<gwt:FlowPanel>` only where Java adds or removes children (rows, items).
- `<gwt:SimplePanel>` only for one slot Java fills (the phone screen, an editor).
- Buttons, inputs and menus are the layout's controls
  (`<controls:ClassicButton action="NEW_PROJECT"/>`); `FocusPanel` only for the clickable rows that use it today.
- On a component use `addStyleNames`, never `styleName`, so it keeps its own
  class.
- Prefixes are the last word of the package (decision 0008): `gwt:` for GWT
  widgets, `controls:` for controls, `editor:`, `projects:` … for views;
  `ui:` is UiBinder's own.
- Text is `<gwt:Label>` on its own line or `<gwt:InlineLabel>` inside a row, with
  `text="{messages.…}"`.

```xml
<!-- layouts/classic/views/projects/ClassicProjectsPage.ui.xml -->
<gwt:HTMLPanel styleName="flex-col grow">
  <projects:ClassicProjectToolbar ui:field="toolbar"/>
  <projects:ClassicProjectList ui:field="list" addStyleNames="grow"/>
</gwt:HTMLPanel>
```

```xml
<!-- layouts/classic/views/editor/ClassicDesignerArea.ui.xml -->
<gwt:HTMLPanel styleName="flex-row grow gap-sm pad-sm">
  <editor:ClassicPalette ui:field="palette" addStyleNames="classic-PaletteColumn"/>
  <editor:ClassicViewer ui:field="viewer" addStyleNames="grow classic-ViewerColumn"/>
  <gwt:HTMLPanel styleName="flex-col classic-SideColumn gap-sm">
    <editor:ClassicComponentTree ui:field="components" addStyleNames="grow"/>
    <editor:ClassicMedia ui:field="media" addStyleNames="classic-MediaPane"/>
  </gwt:HTMLPanel>
  <editor:ClassicProperties ui:field="properties"
      addStyleNames="classic-PropertiesColumn"/>
</gwt:HTMLPanel>
```

## Checked by a compile test

On 2026-10-16 a throwaway GWT module (deleted afterwards) compiled and ran a
classic project toolbar and a shared Length editor in this style, with App
Inventor's own `lib/gwt/2.8.1-patched` and `lib-gwt-svg` jars on the
classpath, as in `YaClientApp`. It proved:

- interface-typed elements (`<controls:Button>`, `<controls:Radio>`,
  `<controls:TextField>`) built by `@UiFactory` methods on an abstract
  superclass, once the methods are `public`;
- enum attributes on interface setters (`action="NEW_PROJECT"`,
  `variant="PRIMARY"`), an int attribute (`maxLength="4"`), and a template
  attribute (`group="…"`) that Java can override after binding;
- nested `<gwt:HTMLPanel ui:field="…" visible="false">` boxes that Java shows
  and hides with `setVisible`, and an empty `HTMLPanel`;
- `Label`/`InlineLabel` with `text="{messages.…}"` from `<ui:with>`, and an
  entity (`text="&#x25BE;"`);
- `@UiField(provided = true)` for a widget with no zero-argument constructor
  (`IconView`);
- long prefixes (`gwt:`, `controls:`, `shared:`) side by side;
- `<gwt:HTMLPanel tag="button">` as a control root: a real, focusable
  `<button>` whose clicks reach Java, with no hidden input.

Not covered: `@ElementParserToUse` parsers (P5-23's `ActionItemParser`),
`CssResource`, and a build of the whole client.

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
  @UiField HTMLPanel projectButtons;
  @UiField HTMLPanel trashButtons;

  public void showTitle(String text) {
    title.setText(text);
  }

  public void showTrash(boolean trash) {
    projectButtons.setVisible(!trash);
    trashButtons.setVisible(trash);
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
interfaces (`<controls:Button>`, `<controls:TextField>`) through `SharedView`, so they look
right in any layout. A layout may replace one by implementing the presenter's
`View` and returning it from the matching `Layout` method; the presenter does
not change.

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
