# The kit

Each layout has its own set of building blocks: buttons, menus, dialogs,
inputs. Shared screens use them through interfaces, so they look like whichever
layout is active.

## Three parts

| Part | Where | What |
|---|---|---|
| Contract | `kit/` | Interfaces: what a Button, Dialog, TextField can do |
| Behaviour | `kit/behaviour/` | Keyboard, focus and ARIA code shared by every kit |
| Implementations | `layouts/<x>/kit/` | What classic's or neo's button looks like |

`kit/standard/` is a plain default implementation built from today's widgets. A
new layout starts by extending `StandardKit` and replaces pieces one at a time;
it never extends another layout's kit.

## Building blocks

From what the client uses today (92 `new Button(`, 26 `new DialogBox(`,
21 `new TextBox(`, 15 `new CheckBox(`, 13 `new ListBox(` …):

| Contract | Replaces today |
|---|---|
| `Button`, `IconButton` | `TextButton`, raw `Button`, `PushButton` |
| `Toolbar`, `ToolbarItem` | `widgets/Toolbar`, `ToolbarItem` |
| `MenuButton`, `MenuItem` | `DropDownButton` (602 lines), `DropDownItem`, `ActionDropDown` |
| `ContextMenu` | `widgets/ContextMenu` |
| `Dialog`, `Wizard` | `widgets/DialogBox`, `wizards/Dialog`, `wizards/Wizard` |
| `TextField` | `LabeledTextBox`, raw `TextBox` |
| `Select`, `Checkbox`, `Radio` | `ListBox`, `CheckBox`, `RadioButton` |
| `Tree` | `Tree`, `TreeItem` |
| `Collapsible` | `AbstractCollapsiblePanel` |
| `Icon` | `widgets/Icon`, `components/Icon` |

## Using the kit from shared screens

GWT 2.8.1's UiBinder accepts an interface-typed element when a `@UiFactory`
method creates it, and it looks for `@UiFactory` methods on superclasses
(`OwnerClass.findUiFactories` recurses). So every shared screen extends one base
class:

```java
// kit/KitView.java
public abstract class KitView extends Composite {
  @UiFactory Button button() {
    return Ui.kit().button();
  }
  @UiFactory TextField textField() {
    return Ui.kit().textField();
  }
  …
}
```

```xml
<!-- features/properties/PropertiesPanel.ui.xml -->
<g:FlowPanel>
  <kit:TextField ui:field="search"/>
  <kit:Button ui:field="reset" text="Reset"/>
</g:FlowPanel>
```

In classic this renders `ClassicTextField` and `ClassicButton`; in neo,
`NeoTextField` and `NeoButton`.

## Layout views use their own kit directly

A layout's own views may name their concrete kit classes
(`<k:NeoButton action="NEW_PROJECT"/>`), because they never run in another
layout.

## Behaviour lives once

`kit/behaviour` holds what every kit needs and nobody should rewrite:

- `MenuKeyboard`: open/close, arrow keys, Escape, ARIA for menus
- `DialogA11y`, `FocusTrap`: dialog roles, focus trapping and return
- `RovingTabIndex`: arrow-key navigation in toolbars and lists
- today's `TableAccessibility`, `ImageAccessibility`, `FocusImpl*`
- `ActionBinding`: connects a kit control to an `Action`

Each layout's piece is then mostly markup and class names.
