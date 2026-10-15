# Layouts

## Layout ids

A layout is named by an id, not a boolean:

```java
// core/layout/LayoutId.java
public enum LayoutId {
  CLASSIC, NEO
}
```

- Stored as the user setting `UiLayout` in `GeneralSettings`.
- The old boolean `NewLayout` is migrated once: `true` → `NEO`,
  `false` → `CLASSIC`, missing → the default (`NEO`, as today).
- `?ui=` is parsed in exactly one place. It accepts a layout id and a theme
  (`?ui=classic`, `?ui=neo-dark`); unknown values are ignored.

## The contract

```java
// layouts/Layout.java
public interface Layout {
  LayoutId id();
  Kit kit();
  List<String> stylesheets();     // tokens, kit, views
  Icons icons();

  ShellPresenter.View shell();
  StatusBarView statusBar();
  TopBarPresenter.View topBar();
  MainMenuPresenter.View mainMenu();
  ProjectToolbarPresenter.View projectToolbar();
  ProjectListPresenter.View projectList();
  DesignToolbarPresenter.View designToolbar();

  default ViewerHeaderView viewerHeader() {
    return new ViewerHeader();        // shared
  }

  default NewProjectPresenter.View newProjectDialog() {
    return new NewProjectDialog();    // shared feature
  }
}
```

Views every layout must draw are abstract. Shared features have `default`
methods that return the shared version, so a layout overrides only what it
wants to change. GWT 2.8 compiles Java 8 default methods.

## The registry

```java
// layouts/LayoutRegistry.java
public static void load(LayoutId id,
    Consumer<Layout> ready) {
  switch (id) {
    case CLASSIC:
      GWT.runAsync(new RunAsyncCallback() {
        public void onSuccess() {
          ready.accept(new ClassicLayout());
        }
        public void onFailure(Throwable t) { … }
      });
      break;
    case NEO: …
  }
}
```

One `runAsync` per layout keeps each layout in its own code fragment, so a
classic user never downloads neo's code. Boot then:

1. reads `UiLayout`;
2. loads the layout from the registry;
3. adds `<link>`s for the layout's stylesheets and sets
   `<html data-layout="neo" data-theme="dark">`;
4. installs the layout's kit (`Ui.install(layout.kit())`);
5. creates the presenters with the layout's views.

Switching layout still reloads the page: it swaps code and CSS, and it is rare.
Switching light/dark does not (see `04-theming.md`).

## What replaces what

| Today | After |
|---|---|
| `Ode.getUserNewLayout()` boolean | `UserPreferences.layout()` → `LayoutId` |
| `handleUiPreference` two `runAsync` branches | `LayoutRegistry.load(id, …)` |
| `UiStyleFactory`, `UiFactoryNeo` | `Layout`, `ClassicLayout`, `NeoLayout` |
| `UiStyleFactory.createOde` template switch | `Layout.shell()` |
| four `Ode.Resources` CSS entries | `Layout.stylesheets()` (static files) |
