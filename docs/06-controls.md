# Controls

Each layout has its own set of building blocks: buttons, menus, dialogs,
inputs. Shared screens use them through interfaces, so they look like whichever
layout is active.

## Three parts

| Part | Where | What |
|---|---|---|
| Contract | `controls/` | Interfaces: what a Button, Dialog, TextField can do |
| Behaviour | `controls/behaviour/` | Keyboard, focus and ARIA code shared by every layout's controls |
| Implementations | `layouts/<x>/controls/` | What classic's or neo's button looks like |

`controls/standard/` wraps today's widgets so views can move to the contracts with no
visual change. It is a bridge for P5 only (decision 0013): by the end of P7 every layout
writes every control as its own template plus behaviour Java, and `controls/standard/`
is deleted (P7-44). A layout never extends another layout's controls.

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
| `Icon` | `components/Icon` (Material glyph); `widgets/Icon` is an image-and-caption used only by `MockNonVisibleComponent` and stays with the mocks |

## Using the controls from shared screens

GWT 2.8.1's UiBinder accepts an interface-typed element when a `@UiFactory`
method creates it, and it looks for `@UiFactory` methods on superclasses
(`OwnerClass.findUiFactories` recurses). So every shared screen extends one base
class:

```java
// controls/SharedView.java
public abstract class SharedView extends Composite {
  @UiFactory Button button() {
    return Controls.active().button();
  }
  @UiFactory TextField textField() {
    return Controls.active().textField();
  }
  …
}
```

```xml
<!-- features/properties/PropertiesPanel.ui.xml -->
<gwt:FlowPanel>
  <controls:TextField ui:field="search"/>
  <controls:Button ui:field="reset" text="Reset"/>
</gwt:FlowPanel>
```

In classic this renders `ClassicTextField` and `ClassicButton`; in neo,
`NeoTextField` and `NeoButton`.

## Layout views use their own controls directly

A layout's own views may name their concrete control classes
(`<controls:NeoButton action="NEW_PROJECT"/>`), because they never run in another
layout.

## Each control is a template plus behaviour

A control's markup is its own `.ui.xml`; its Java only does behaviour (action
binding, focus, ARIA, events). For example
`layouts/classic/controls/ClassicTextField.ui.xml`:

```xml
<gwt:HTMLPanel styleName="flex-col gap-xs classic-TextField">
  <gwt:Label ui:field="label" styleName="classic-Label"/>
  <gwt:TextBox ui:field="input" styleName="classic-Input"/>
  <gwt:Label ui:field="error" styleName="classic-Error" visible="false"/>
</gwt:HTMLPanel>
```

`ClassicTextField.java` wires `for`/`id`, `aria-invalid` and the error text,
and never builds markup. How the control looks is in the layout's
`controls.css`. Page templates use controls; they never open them up.

## Behaviour lives once

`controls/behaviour` holds what every layout's controls need and nobody should rewrite:

- `MenuKeyboard`: open/close, arrow keys, Escape, ARIA for menus
- `DialogA11y`, `FocusTrap`: dialog roles, focus trapping and return
- `RovingTabIndex`: arrow-key navigation in toolbars and lists
- today's `TableAccessibility`, `ImageAccessibility`, `FocusImpl*`
- `ActionBinding`: connects a control to an `Action`

Each layout's piece is then mostly markup and class names.
