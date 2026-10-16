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
  protected Button button() {
    return Controls.active().button();
  }

  @UiFactory
  protected TextField textField() {
    return Controls.active().textField();
  }

  @UiFactory
  protected Select select() {
    return Controls.active().select();
  }

  @UiFactory
  protected Checkbox checkbox() {
    return Controls.active().checkbox();
  }

  @UiFactory
  protected Radio radio() {
    return Controls.active().radio();
  }
}
