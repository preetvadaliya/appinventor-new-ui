// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.controls;

import com.google.appinventor.client.controls.Button;
import com.google.appinventor.client.core.actions.Action;
import com.google.appinventor.client.core.actions.ActionId;
import com.google.appinventor.client.controls.behaviour.ActionBinding;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Behaviour of classic's button; its markup is ClassicButton.ui.xml. */
public class ClassicButton extends Composite implements Button {

  interface Binder extends UiBinder<Widget, ClassicButton> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField com.google.gwt.user.client.ui.Button button;

  private String captionOverride;

  public ClassicButton() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void setAction(ActionId id) {
    Action action = ActionBinding.bind(id, this::render);
    button.addClickHandler(e -> ActionBinding.click(action));
  }

  private void render(Action action) {
    button.setText(captionOverride != null
        ? captionOverride : action.getLabel());
    button.setEnabled(action.isEnabled());
    setVisible(action.isVisible());
  }

  @Override
  public void setVariant(Variant variant) {
    button.setStyleName("classic-Button-primary", variant == Variant.PRIMARY);
    button.setStyleName("classic-Button-quiet", variant == Variant.QUIET);
    button.setStyleName("classic-Button-danger", variant == Variant.DANGER);
  }

  @Override
  public void setEnabled(boolean enabled) {
    button.setEnabled(enabled);
  }

  @Override
  public String getText() {
    return button.getText();
  }

  /** text="…" in a template replaces the action's label here only. */
  @Override
  public void setText(String text) {
    captionOverride = text;
    button.setText(text);
  }

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return button.addClickHandler(handler);
  }
}
