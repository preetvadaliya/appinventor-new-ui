# Actions and presenters

## The problem

Today a menu item is a `Command` nested inside a template, and its label lives
on the button. Logic finds buttons by string name:

```java
// explorer/youngandroid/ProjectToolbar.java (master)
private static final String WIDGET_NAME_NEW = "New";
…
setButtonEnabled(WIDGET_NAME_MOVE,
    numSelectedProjects > 0);
setButtonVisible("ImportProject", visible);  // neo only
```

The rules live in the classic widget, neo inherits them, and classic's code
switches buttons that exist only in neo's template.

## Actions

```java
// core/actions/ActionId.java
public enum ActionId {
  NEW_PROJECT, IMPORT_PROJECT, IMPORT_TEMPLATE,
  NEW_FOLDER, MOVE_PROJECTS, DELETE_PROJECT, …
}
```

```java
// core/actions/Action.java
public abstract class Action {
  private final ActionId id;
  private String label;
  private final IconKey icon;
  private boolean enabled = true;
  private boolean visible = true;
  private final List<Runnable> listeners =
      new ArrayList<>();

  public abstract void run();

  public void setEnabled(boolean on) {
    enabled = on;
    changed();
  }
  public void setVisible(boolean on) { … }
  public void setLabel(String text) { … }
  public void onChange(Runnable r) {
    listeners.add(r);
  }
}
```

```java
// core/actions/project/NewProjectAction.java
public class NewProjectAction extends Action {
  public NewProjectAction() {
    super(ActionId.NEW_PROJECT,
        MESSAGES.newProjectButton(), IconKey.ADD);
  }

  @Override
  public void run() {
    setEnabled(false);              // no double click
    new NewProjectPresenter(
        App.views().newProjectDialog())
      .start(() -> setEnabled(true));
  }
}
```

`App.views()` is an interface declared in `core`; the active layout supplies
it, so `core` never imports a layout.

## Binding a button to an action

Kit buttons take an action id. UiBinder fills enum-typed setters straight from
attributes (GWT 2.8.1 ships `EnumAttributeParser`), so no custom parser is
needed:

```xml
<k:NeoButton action="NEW_PROJECT"/>
```

A helper in `kit/behaviour` attaches the click handler and listens for
changes; each layout's button only renders:

```java
private void render(Action a) {
  label.setText(a.getLabel());
  icon.setName(a.getIcon());
  setVisible(a.isVisible());
  button.setEnabled(a.isEnabled());
}
```

`actions.get(NEW_PROJECT).setEnabled(false)` updates every bound button in every
layout. Nothing finds a widget by name.

## Presenters

The rules that decide *when* an action is enabled or visible live in a
presenter in `core/presenters`, next to the `View` interface the layouts draw:

```java
// core/presenters/ProjectToolbarPresenter.java
public interface View {
  void showTitle(String title);
  void showTrash(boolean trash);
}

private void update() {
  int n = selection.count();
  boolean ro = App.session().isReadOnly();
  view.showTrash(trash);   // which buttons this
                           // toolbar shows
  enable(!ro, NEW_PROJECT);
  enable(!ro && n > 0, MOVE_PROJECTS,
      DELETE_PROJECT, RESTORE_PROJECT,
      DELETE_FOREVER);
}
```

Which buttons a toolbar shows in the project list versus the trash belongs to
that toolbar (`View.showTrash`), not to the action: the same action may also sit
in a menu that stays visible. Hiding an action hides it everywhere.

If a layout does not place an action at all, nothing breaks: no widget is bound
to it.

## From Command to Action

P3-09 adds a transitional `actions/CommandAction` that wraps today's `Command`
classes, so P3-10 to P3-13 can give every item an `ActionId` without rewriting 60
classes. P5-42 turns them into `Action` subclasses in `core/actions/<area>/` once
the dialogs they open have presenters, and deletes `CommandAction`.

## What a view may and may not do

A view arranges, styles and reports user input. It never decides: no
validation, no RPC calls, no enable/visible rules. That is what lets each
layout change its structure freely.
