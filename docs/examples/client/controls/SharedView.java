// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.user.client.ui.Composite;

/**
 * Base class for shared views. A template that says <controls:Button> gets the
 * active layout's button: UiBinder calls these @UiFactory methods, and it
 * finds them on superclasses.
 */
public abstract class SharedView extends Composite {

  @UiFactory
  public Button button() {
    return Controls.active().button();
  }

  @UiFactory
  public IconButton iconButton() {
    return Controls.active().iconButton();
  }

  @UiFactory
  public MenuButton menuButton() {
    return Controls.active().menuButton();
  }

  @UiFactory
  public MenuItem menuItem() {
    return Controls.active().menuItem();
  }

  @UiFactory
  public TextField textField() {
    return Controls.active().textField();
  }

  @UiFactory
  public Select select() {
    return Controls.active().select();
  }

  @UiFactory
  public Checkbox checkbox() {
    return Controls.active().checkbox();
  }

  @UiFactory
  public Radio radio() {
    return Controls.active().radio();
  }

  @UiFactory
  public Collapsible collapsible() {
    return Controls.active().collapsible();
  }
}
