// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.controls;

import com.google.appinventor.client.controls.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/** Behaviour of classic's text field; markup is ClassicTextField.ui.xml. */
public class ClassicTextField extends Composite implements TextField {

  interface Binder extends UiBinder<Widget, ClassicTextField> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);
  private static int nextId;

  @UiField Label label;
  @UiField TextBox input;
  @UiField Label error;

  public ClassicTextField() {
    initWidget(BINDER.createAndBindUi(this));
    String id = "classic-field-" + (nextId++);
    input.getElement().setId(id);
    label.getElement().setAttribute("for", id);
    error.getElement().setId(id + "-error");
    error.getElement().setAttribute("role", "alert");
  }

  @Override
  public void setLabel(String text) {
    label.setText(text);
  }

  /** placeholder="…" in a template. */
  public void setPlaceholder(String text) {
    input.getElement().setAttribute("placeholder", text);
  }

  @Override
  public void setMaxLength(int length) {
    input.setMaxLength(length);
  }

  @Override
  public void setError(String message) {
    error.setText(message);
    error.setVisible(true);
    input.getElement().setAttribute("aria-invalid", "true");
    input.getElement().setAttribute("aria-describedby", error.getElement().getId());
  }

  @Override
  public void clearError() {
    error.setVisible(false);
    input.getElement().removeAttribute("aria-invalid");
    input.getElement().removeAttribute("aria-describedby");
  }

  @Override
  public void focus() {
    input.setFocus(true);
  }

  @Override
  public String getText() {
    return input.getText();
  }

  @Override
  public void setText(String text) {
    input.setText(text);
  }

  @Override
  public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
    return input.addKeyUpHandler(handler);
  }

  @Override
  public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
    return input.addKeyPressHandler(handler);
  }

  @Override
  public HandlerRegistration addFocusHandler(FocusHandler handler) {
    return input.addFocusHandler(handler);
  }

  @Override
  public HandlerRegistration addValueChangeHandler(
      ValueChangeHandler<String> handler) {
    return input.addValueChangeHandler(handler);
  }
}
