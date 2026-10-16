// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.controls;

import com.google.appinventor.client.controls.Checkbox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Behaviour of classic's checkbox; markup is ClassicCheckbox.ui.xml. GWT's
 * CheckBox already ties its label to the box.
 */
public class ClassicCheckbox extends Composite implements Checkbox {

  interface Binder extends UiBinder<Widget, ClassicCheckbox> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField CheckBox box;

  public ClassicCheckbox() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void setLabel(String text) {
    box.setText(text);
  }

  @Override
  public void setEnabled(boolean enabled) {
    box.setEnabled(enabled);
  }

  @Override
  public String getText() {
    return box.getText();
  }

  @Override
  public void setText(String text) {
    box.setText(text);
  }

  @Override
  public Boolean getValue() {
    return box.getValue();
  }

  @Override
  public void setValue(Boolean value) {
    box.setValue(value);
  }

  @Override
  public void setValue(Boolean value, boolean fireEvents) {
    box.setValue(value, fireEvents);
  }

  @Override
  public HandlerRegistration addValueChangeHandler(
      ValueChangeHandler<Boolean> handler) {
    return box.addValueChangeHandler(handler);
  }
}
