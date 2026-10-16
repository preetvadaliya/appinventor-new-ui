// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.controls;

import com.google.appinventor.client.controls.Radio;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * Behaviour of neo's radio button; markup is NeoRadio.ui.xml.
 * The template's name="" is a placeholder: setGroup sets the real one.
 */
public class NeoRadio extends Composite implements Radio {

  interface Binder extends UiBinder<Widget, NeoRadio> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField RadioButton radio;

  public NeoRadio() {
    initWidget(BINDER.createAndBindUi(this));
  }

  /** group="…" in a template, or Java for one group per instance. */
  @Override
  public void setGroup(String group) {
    radio.setName(group);
  }

  @Override
  public void focus() {
    radio.setFocus(true);
  }

  @Override
  public String getText() {
    return radio.getText();
  }

  @Override
  public void setText(String text) {
    radio.setText(text);
  }

  @Override
  public Boolean getValue() {
    return radio.getValue();
  }

  @Override
  public void setValue(Boolean value) {
    radio.setValue(value);
  }

  @Override
  public void setValue(Boolean value, boolean fireEvents) {
    radio.setValue(value, fireEvents);
  }

  @Override
  public HandlerRegistration addValueChangeHandler(
      ValueChangeHandler<Boolean> handler) {
    return radio.addValueChangeHandler(handler);
  }
}
