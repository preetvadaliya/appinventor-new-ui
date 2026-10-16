# Code patterns

What the code looks like after the refactor, as short snippets: one per
pattern, enough to write a ticket or review a PR. This is not a code base;
the real code is written in the PRs on `appinventor-sources`. Snippets leave
out license headers and imports. Paths are relative to
`appinventor/appengine/src/com/google/appinventor/client/`.

Where a pattern has a rule, the rule is in a decision record
(`docs/decisions/`); this file only shows it.

## Contents

1. Template rules
2. A region view
3. A list and its row
4. A page that places regions
5. A presenter and its View
6. Actions
7. A layout
8. A control
9. A shared view
10. Property editors
11. Icons
12. CSS
13. How neo differs

## 1. Template rules

Decision 0008, in one place:

- One template per page region (top bar, project toolbar, palette …);
  small templates only for items repeated in a list.
- Every box is a `<gwt:HTMLPanel styleName="…">`; no raw `<div>` or
  `<span>`. Text is `<gwt:Label>` (own line) or `<gwt:InlineLabel>`
  (inside a row), with `text="{messages.…}"`.
- `<gwt:FlowPanel>` only for lists Java fills, `<gwt:SimplePanel>` only
  for one slot. `FocusPanel` only for clickable rows that use it today.
- Layout comes from the flex classes in `layout.css`: `flex-row`,
  `flex-col`, `grow`, `overflow-auto`, `items-center`, `justify-end`,
  `gap-*`, `pad-*`.
- Prefixes are the package's last word: `ui:`, `gwt:`, `controls:`,
  `editor:`, `projects:` …; `shared:` for `client.controls` in the rare
  layout template that also needs it.
- On a child view use `addStyleNames`, never `styleName`.
- Java behind a template only binds fields and passes events on; it holds
  no markup and no rules.

## 2. A region view

The project toolbar (P4-16). The template:

```xml
<!-- layouts/classic/views/projects/
     ClassicProjectToolbar.ui.xml -->
<ui:UiBinder
    xmlns:ui="urn:ui:com.google.gwt.uibinder"
    xmlns:gwt="urn:import:com.google.gwt.user.client.ui"
    xmlns:controls="urn:import:…classic.controls">
  <gwt:HTMLPanel
      styleName="flex-row items-center gap-md pad-md">
    <gwt:Label ui:field="title" styleName="classic-Title"/>

    <gwt:HTMLPanel ui:field="projectButtons"
        styleName="flex-row items-center gap-sm">
      <controls:ClassicButton action="NEW_PROJECT"/>
      <controls:ClassicButton action="NEW_FOLDER"/>
      <controls:ClassicButton action="MOVE_PROJECTS"/>
      <controls:ClassicButton action="DELETE_PROJECT"/>
    </gwt:HTMLPanel>

    <gwt:HTMLPanel ui:field="trashButtons"
        styleName="flex-row items-center gap-sm"
        visible="false">
      <controls:ClassicButton action="RESTORE_PROJECT"/>
      <controls:ClassicButton action="DELETE_FOREVER"/>
    </gwt:HTMLPanel>
  </gwt:HTMLPanel>
</ui:UiBinder>
```

(`…` stands for `com.google.appinventor.client.layouts`; a real template
spells the package out.)

The Java implements the presenter's `View` and nothing else:

```java
// layouts/classic/views/projects/ClassicProjectToolbar.java
public class ClassicProjectToolbar extends Composite
    implements ProjectToolbarPresenter.View {

  interface Binder
      extends UiBinder<Widget, ClassicProjectToolbar> {
  }

  private static final Binder BINDER =
      GWT.create(Binder.class);

  @UiField Label title;
  @UiField HTMLPanel projectButtons;
  @UiField HTMLPanel trashButtons;

  public ClassicProjectToolbar() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void showTitle(String text) {
    title.setText(text);
  }

  @Override
  public void showTrash(boolean trash) {
    projectButtons.setVisible(!trash);
    trashButtons.setVisible(trash);
  }
}
```

The status bar (P4-15), top bar (P4-19) and design toolbar (P4-20) follow
the same shape. A menu is a menu button whose items name actions:

```xml
<controls:ClassicMenuButton
    text="{messages.projectsTabName}" variant="QUIET">
  <controls:ClassicMenuItem action="NEW_PROJECT"/>
  <controls:ClassicMenuItem action="IMPORT_PROJECT"/>
  <controls:ClassicMenuSeparator/>
  <controls:ClassicMenuItem action="EXPORT_PROJECT"/>
</controls:ClassicMenuButton>
```

Child items reach the menu through P5-23's `ActionItemParser`.

## 3. A list and its row

The list region holds a header and a `FlowPanel` that Java fills; each row
is a small template of its own (P4-18, P5-32):

```xml
<!-- ClassicProjectList.ui.xml (body) -->
<gwt:HTMLPanel styleName="flex-col classic-ProjectList">
  <gwt:HTMLPanel
      styleName="flex-row items-center classic-ListHeader">
    <controls:ClassicCheckbox ui:field="selectAll"/>
    <gwt:FocusPanel ui:field="sortByName"
        styleName="grow classic-SortHeader">
      <gwt:InlineLabel
          text="{messages.projectNameHeader}"/>
    </gwt:FocusPanel>
  </gwt:HTMLPanel>

  <gwt:FlowPanel ui:field="rows"
      styleName="flex-col grow overflow-auto"/>
</gwt:HTMLPanel>
```

```xml
<!-- ClassicProjectRow.ui.xml (body) -->
<gwt:FocusPanel ui:field="row" styleName="classic-Row">
  <gwt:HTMLPanel styleName="flex-row items-center">
    <controls:ClassicCheckbox ui:field="selected"/>
    <gwt:Label ui:field="name" styleName="grow"/>
    <gwt:Label ui:field="modified"
        styleName="classic-DateColumn"/>
  </gwt:HTMLPanel>
</gwt:FocusPanel>
```

The list view passes events to the presenter as callbacks
(`onChecked`, `onOpen`, `onSortClicked`); it never sorts or counts.

## 4. A page that places regions

Pages only place regions; sizes are classes from the layout's CSS
(decision 0009, P4-21, P5-43):

```xml
<!-- ClassicDesignerArea.ui.xml (body) -->
<gwt:HTMLPanel styleName="flex-row grow gap-sm pad-sm">
  <editor:ClassicPalette ui:field="palette"
      addStyleNames="classic-PaletteColumn"/>
  <editor:ClassicViewer ui:field="viewer"
      addStyleNames="grow classic-ViewerColumn"/>
  <gwt:HTMLPanel
      styleName="flex-col classic-SideColumn gap-sm">
    <editor:ClassicComponentTree ui:field="components"
        addStyleNames="grow"/>
    <editor:ClassicMedia ui:field="media"
        addStyleNames="classic-MediaPane"/>
  </gwt:HTMLPanel>
  <editor:ClassicProperties ui:field="properties"
      addStyleNames="classic-PropertiesColumn"/>
</gwt:HTMLPanel>
```

The window never scrolls. The shell is `flex-col screen`; `grow` passes
the height down; only `overflow-auto` boxes scroll:

```xml
<!-- ClassicShell.ui.xml (body) -->
<gwt:HTMLPanel styleName="flex-col screen classic-Shell">
  <topbar:ClassicTopBar ui:field="topBar"/>
  <gwt:HTMLPanel styleName="flex-col grow">
    <projects:ClassicProjectsPage ui:field="projectsPage"/>
    <editor:ClassicEditorPage ui:field="editorPage"
        visible="false"/>
  </gwt:HTMLPanel>
  <status:ClassicStatusBar ui:field="statusBar"/>
</gwt:HTMLPanel>
```

## 5. A presenter and its View

Rules live in a presenter in `core/presenters/`; it talks to a `View`
interface and knows no widget (P3, P4):

```java
// core/presenters/ProjectToolbarPresenter.java
public class ProjectToolbarPresenter {

  public interface View {
    void showTitle(String title);

    void showTrash(boolean trash);
  }

  private final View view;
  private final ActionRegistry actions = App.actions();
  private final ProjectSelection selection;

  public ProjectToolbarPresenter(View view,
      ProjectSelection selection) {
    this.view = view;
    this.selection = selection;
    selection.onChange(this::update);
  }

  public void showTrash() {
    view.showTitle(MESSAGES.trashButton());
    view.showTrash(true);
    update();
  }

  private void update() {
    int selected = selection.count();
    boolean readOnly = App.session().isReadOnly();
    actions.enable(!readOnly, NEW_PROJECT);
    actions.enable(selected > 0, MOVE_PROJECTS);
    actions.get(EXPORT_PROJECT).setLabel(selected > 1
        ? MESSAGES.exportSelectedProjectsMenuItem(selected)
        : MESSAGES.exportProjectMenuItem());
  }
}
```

The layout hands out its view; the presenter does not change per layout:

```java
// layouts/classic/ClassicLayout.java
public ProjectToolbarPresenter.View projectToolbar() {
  return new ClassicProjectToolbar();
}
```

## 6. Actions

Every command has an `ActionId`; an `Action` holds its label, icon and
state, and every button bound to it follows that state (P3):

```java
// core/actions/project/NewProjectAction.java
public class NewProjectAction extends Action {
  public NewProjectAction() {
    super(ActionId.NEW_PROJECT,
        MESSAGES.newProjectButton(), IconKey.ADD);
  }

  @Override
  public void run() {
    setEnabled(false);   // the dialog cannot open twice
    new NewProjectPresenter(App.views().newProjectDialog())
        .start(() -> setEnabled(
            !App.session().isReadOnly()));
  }
}
```

```java
// core/actions/ActionRegistry.java (use)
App.actions().register(new NewProjectAction());
App.actions().enable(false, DELETE_PROJECT);
App.actions().show(galleryOn, SEND_TO_GALLERY);
```

A control binds once and redraws on every change:

```java
// controls/behaviour/ActionBinding.java
public static Action bind(ActionId id,
    Consumer<Action> render) {
  Action action = App.actions().get(id);
  action.onChange(() -> render.accept(action));
  render.accept(action);
  return action;
}
```

## 7. A layout

A layout is an id, its controls, its views and its stylesheets
(`docs/03-layouts.md`, P1, P6-37):

```java
// layouts/classic/ClassicLayout.java
public class ClassicLayout implements Layout {
  @Override
  public LayoutId id() {
    return LayoutId.CLASSIC;
  }

  @Override
  public Controls controls() {
    return new ClassicControls();
  }

  @Override
  public List<String> stylesheets() {
    return Arrays.asList(
        "classic/tokens.css",
        "classic/controls.css",
        "classic/views/shell.css",
        "classic/views/projects.css",
        "classic/views/editor.css");
  }

  // one method per view: shell(), projectToolbar() …
}
```

## 8. A control

A contract in `controls/`, and each layout's own template plus
behaviour-only Java (P5-23, P5-26). Setters a template uses as attributes
are on the interface:

```java
// controls/Button.java
public interface Button
    extends IsWidget, HasText, HasClickHandlers {

  enum Variant { DEFAULT, PRIMARY, QUIET, DANGER }

  void setAction(ActionId action);   // action="NEW_PROJECT"

  void setVariant(Variant variant);  // variant="PRIMARY"

  void setEnabled(boolean enabled);
}
```

Anything pressed is a real `<button>` (an `HTMLPanel` with
`tag="button"`), never a `FocusPanel`:

```xml
<!-- layouts/classic/controls/ClassicButton.ui.xml (body) -->
<gwt:HTMLPanel tag="button" ui:field="root"
    styleName="classic-Button">
  <gwt:InlineLabel ui:field="label"/>
</gwt:HTMLPanel>
```

```java
// layouts/classic/controls/ClassicButton.java
public class ClassicButton extends Composite
    implements Button {

  @UiField HTMLPanel root;
  @UiField InlineLabel label;
  private final ButtonElement button;

  public ClassicButton() {
    initWidget(BINDER.createAndBindUi(this));
    button = ButtonElement.as(root.getElement());
    button.setAttribute("type", "button");
  }

  @Override
  public void setAction(ActionId id) {
    Action action = ActionBinding.bind(id, this::render);
    addClickHandler(e -> ActionBinding.click(action));
  }

  private void render(Action action) {
    label.setText(action.getLabel());
    setEnabled(action.isEnabled());
    setVisible(action.isVisible());
  }

  @Override
  public void setEnabled(boolean enabled) {
    button.setDisabled(!enabled);
  }

  @Override
  public HandlerRegistration addClickHandler(
      ClickHandler handler) {
    return root.addDomHandler(handler,
        ClickEvent.getType());
  }
}
```

Icon-only buttons put the action's label into `aria-label` and `title`
(decision 0004). Keyboard, focus and ARIA code shared by every layout
lives in `controls/behaviour/` (`ActionBinding`, `MenuKeyboard`).

## 9. A shared view

Screens written once (dialogs, property editors) extend `SharedView` and
name contracts; UiBinder asks the active layout for each control
(`docs/06-controls.md`). The factories must be `public`:

```java
// controls/SharedView.java
public abstract class SharedView extends Composite {
  @UiFactory
  public Button button() {
    return Controls.active().button();
  }

  @UiFactory
  public TextField textField() {
    return Controls.active().textField();
  }

  // one per control: select(), checkbox(), radio() …
}
```

```xml
<!-- features/dialogs/projects/NewProjectDialog.ui.xml -->
<gwt:HTMLPanel styleName="flex-col gap-md pad-md">
  <controls:TextField ui:field="name"
      label="{messages.projectNameLabel}"/>
  <gwt:HTMLPanel styleName="flex-row justify-end gap-sm">
    <controls:Button ui:field="cancel"
        text="{messages.cancelButton}"/>
    <controls:Button ui:field="ok"
        text="{messages.okButton}" variant="PRIMARY"/>
  </gwt:HTMLPanel>
</gwt:HTMLPanel>
```

Here `controls:` is the contracts package, so `<controls:Button>` is
`ClassicButton` in classic and `NeoButton` in neo. A layout replaces a
shared view only to change its arrangement: it implements the presenter's
`View` and returns its own from the matching `Layout` method.

## 10. Property editors

Decisions 0011 and 0012 (P5-30, P5-31). Logic is a model in
`core/properties/editors/` with a `View` interface; it saves only values
it accepts and reports errors through the view:

```java
// core/properties/editors/LengthEditorModel.java
public class LengthEditorModel
    extends PropertyEditor<LengthEditorModel.View> {

  public enum Kind { AUTOMATIC, FILL_PARENT, PIXELS, PERCENT }

  public interface View extends EditorView {
    void setSummary(String summary);  // "120 pixels"

    void show(Kind kind, String amount);

    Kind kind();

    String amount();

    void showError(String message);   // Window.alert (0012)

    void onOk(Runnable handler);
  }

  @Override
  protected void bind() {
    view.onOk(this::save);
  }

  private void save() {
    String error = check(view.kind(), view.amount());
    if (error != null) {
      view.showError(error);
      return;
    }
    property.setValue(encode(view.kind(), view.amount()));
  }

  // Stored values, as today: "-1" automatic,
  // "-2" fill parent, "120" pixels, "-1050" = 50 %
  static String encode(Kind kind, String amount) { … }

  static String check(Kind kind, String amount) { … }
}
```

One shared view per kind of editor, a template plus behaviour Java; the
controls make it look right in each layout:

```xml
<!-- features/properties/editors/LengthEditorView.ui.xml -->
<gwt:HTMLPanel styleName="flex-col gap-sm pad-md">
  <controls:Radio ui:field="automatic"
      text="{messages.automaticCaption}"/>
  <controls:Radio ui:field="fillParent"
      text="{messages.fillParentCaption}"/>
  <gwt:HTMLPanel styleName="flex-row items-center gap-xs">
    <controls:Radio ui:field="pixels"/>
    <controls:TextField ui:field="pixelsAmount"
        maxLength="4"/>
    <gwt:InlineLabel text="{messages.pixelsCaption}"/>
  </gwt:HTMLPanel>
  <gwt:HTMLPanel styleName="flex-row justify-end gap-sm">
    <controls:Button ui:field="cancel"
        text="{messages.cancelButton}"/>
    <controls:Button ui:field="ok"
        text="{messages.okButton}" variant="PRIMARY"/>
  </gwt:HTMLPanel>
</gwt:HTMLPanel>
```

Option lists are data, one entry per old editor class:

```java
// core/properties/choices/LayoutChoices.java
/** Was YoungAndroidButtonShapeChoicePropertyEditor. */
public static final Choices BUTTON_SHAPE = Choices.of(
    choice(MESSAGES.defaultButtonShape() + " : 0", "0"),
    choice(MESSAGES.roundedButtonShape() + " : 1", "1"),
    choice(MESSAGES.rectButtonShape() + " : 2", "2"),
    choice(MESSAGES.ovalButtonShape() + " : 3", "3"));
```

```java
// core/properties/editors/PropertyEditors.java
switch (type) {
  case PROPERTY_TYPE_BUTTON_SHAPE:
    return new ChoiceEditorModel(LayoutChoices.BUTTON_SHAPE);
  case PROPERTY_TYPE_NON_NEGATIVE_FLOAT:
    return NumberEditorModel.nonNegativeDecimal();
  case PROPERTY_TYPE_LENGTH:
    return LengthEditorModel.withPercent();
  default:
    return new TextEditorModel();
}
```

## 11. Icons

Code names a meaning (`IconKey`); each layout maps it to a picture or a
glyph (decisions 0004, 0006, P4-22):

```java
// layouts/classic/icons/ClassicIcons.java
public Icon action(IconKey key) {
  switch (key) {
    case FOLDER: return Icon.image(ICONS + "folder.png");
    case HELP:   return Icon.image(ICONS + "help.png");
    default:     return Icon.NONE;   // text-only buttons
  }
}
```

```java
// layouts/neo/icons/NeoIcons.java
public Icon action(IconKey key) {
  switch (key) {
    case ADD:    return Icon.glyph(MATERIAL, "add");
    case FOLDER: return Icon.glyph(MATERIAL, "folder");
    default:     return Icon.NONE;
  }
}
```

`IconView` draws either kind. It has no zero-argument constructor, so a
template field needs `@UiField(provided = true)`:

```java
@UiField(provided = true)
IconView icon = new IconView(Icon.NONE);
```

## 12. CSS

Decisions 0002 and 0010 (P4-15, P6-33 to P6-37). Shared files load first,
then the layout's own, in `stylesheets()` order:

```
features/public/features/reset.css    page defaults
features/public/features/layout.css   flex utilities
<layout>/tokens.css                   values, light and dark
<layout>/controls.css                 buttons, menus, inputs
<layout>/views/shell.css              top bar, status bar
<layout>/views/projects.css           projects page
<layout>/views/editor.css             designer, blocks
```

```css
/* layout.css, complete: layout only, no colours,
   fonts, borders or sizes */
.flex-row {
  display: flex;
  flex-direction: row;
  min-width: 0;
  min-height: 0;
}
.flex-col {
  display: flex;
  flex-direction: column;
  min-width: 0;
  min-height: 0;
}
.grow   { flex: 1 1 0; min-width: 0; min-height: 0; }
.fixed  { flex: none; }
.screen {
  width: 100vw;
  height: 100vh;
  height: 100dvh;
  overflow: hidden;
}

.overflow-auto   { overflow: auto; }
.overflow-hidden { overflow: hidden; }

.items-center    { align-items: center; }
.justify-end     { justify-content: flex-end; }
.justify-between { justify-content: space-between; }
.flex-wrap       { flex-wrap: wrap; }

.gap-xs { gap: 2px; }
.gap-sm { gap: 4px; }
.gap-md { gap: 8px; }
.gap-lg { gap: 16px; }

.pad-sm { padding: 4px; }
.pad-md { padding: 8px; }
```

```css
/* classic/tokens.css: the only file that changes
   with the theme */
:root {
  --color-page: #ffffff;
  --color-text: #333333;
  --color-primary: #a4cf47;
  --font-body: "Poppins", sans-serif;
}

:root[data-theme="dark"] {
  --color-page: #1e1e1e;
  --color-text: #dddddd;
}
```

```css
/* reset.css: page defaults; values come from the
   active layout's tokens.css */
*,
*::before,
*::after {
  box-sizing: border-box;
}

html,
body {
  margin: 0;
  overflow: hidden;   /* the window never scrolls */
  background: var(--color-page);
  color: var(--color-text);
  font-family: var(--font-body);
}

/* buttons are real <button>s; the layout's
   controls.css gives them their look */
button {
  font: inherit;
  color: inherit;
  background: none;
  border: 0;
  padding: 0;
  margin: 0;
}
```

Light and dark switch by setting `data-theme` on `<html>`; no reload
(`docs/04-theming.md`).

## 13. How neo differs

Neo has the same files as classic, with a `Neo` prefix, and never imports
classic. The differences are its own markup, icons and tokens. Its
regions are cards, and its buttons show the action's glyph before the
label:

```xml
<!-- layouts/neo/controls/NeoButton.ui.xml (body) -->
<gwt:HTMLPanel tag="button" ui:field="root"
    styleName="flex-row items-center gap-sm neo-Button">
  <controls:IconView ui:field="icon"
      addStyleNames="neo-Button-icon"/>
  <gwt:InlineLabel ui:field="label"
      styleName="neo-Button-label"/>
</gwt:HTMLPanel>
```

```xml
<!-- a neo region: a card with a title row -->
<gwt:HTMLPanel styleName="flex-col neo-Card">
  <gwt:HTMLPanel
      styleName="flex-row items-center neo-Card-title">
    <gwt:InlineLabel text="{messages.paletteBoxCaption}"/>
  </gwt:HTMLPanel>
  <gwt:FlowPanel ui:field="items"
      styleName="flex-col grow overflow-auto"/>
</gwt:HTMLPanel>
```

## Checked by a compile test

On 2026-10-16 a throwaway GWT module (deleted afterwards) compiled and ran
patterns 2, 8, 9 and 10 with App Inventor's own `lib/gwt/2.8.1-patched`
and `lib-gwt-svg` jars. Results are in `docs/07-views.md`, "Checked by a
compile test".
