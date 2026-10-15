// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.kit;

import com.google.appinventor.client.core.actions.Action;
import com.google.appinventor.client.core.actions.ActionId;
import com.google.appinventor.client.kit.Button;
import com.google.appinventor.client.kit.behaviour.ActionBinding;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

/** Classic's button: text only, the classic look. */
public class ClassicButton extends Composite implements Button {
  private final com.google.gwt.user.client.ui.Button button =
      new com.google.gwt.user.client.ui.Button();
  private String captionOverride;

  public ClassicButton() {
    initWidget(button);
    setStyleName("classic-Button");
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
    setStyleName("classic-Button-primary", variant == Variant.PRIMARY);
    setStyleName("classic-Button-danger", variant == Variant.DANGER);
  }

  @Override
  public void setEnabled(boolean enabled) {
    button.setEnabled(enabled);
  }

  @Override
  public String getText() {
    return button.getText();
  }

  /** A template's text="…" replaces the action's label here only. */
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
