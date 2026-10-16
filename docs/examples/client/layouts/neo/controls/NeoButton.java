// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.controls;

import com.google.appinventor.client.controls.Button;
import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.IconView;
import com.google.appinventor.client.controls.behaviour.ActionBinding;
import com.google.appinventor.client.core.actions.Action;
import com.google.appinventor.client.core.actions.ActionId;
import com.google.appinventor.client.core.icons.Icon;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

/** Behaviour of neo's button; its markup is NeoButton.ui.xml. */
public class NeoButton extends Composite implements Button {

  interface Binder extends UiBinder<Widget, NeoButton> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField FocusPanel button;
  @UiField(provided = true) IconView icon = new IconView(Icon.NONE, "");
  @UiField InlineLabel label;

  private String captionOverride;
  private boolean enabled = true;

  public NeoButton() {
    initWidget(BINDER.createAndBindUi(this));
    button.getElement().setAttribute("role", "button");
    // Enter and Space press it, as they do a native button
    button.addKeyDownHandler(e -> {
      int key = e.getNativeKeyCode();
      if (key == KeyCodes.KEY_ENTER || key == KeyCodes.KEY_SPACE) {
        e.preventDefault();
        DomEvent.fireNativeEvent(Document.get().createClickEvent(
            0, 0, 0, 0, 0, false, false, false, false), button);
      }
    });
  }

  @Override
  public void setAction(ActionId id) {
    Action action = ActionBinding.bind(id, this::render);
    addClickHandler(e -> ActionBinding.click(action));
  }

  /** Neo's glyph for the action's IconKey first, then the label. */
  private void render(Action action) {
    String text = captionOverride != null
        ? captionOverride : action.getLabel();
    label.setText(text);
    icon.setIcon(Controls.active().icons().action(action.getIcon()));
    button.setTitle(text);
    setEnabled(action.isEnabled());
    setVisible(action.isVisible());
  }

  @Override
  public void setVariant(Variant variant) {
    button.setStyleName("neo-Button-primary", variant == Variant.PRIMARY);
    button.setStyleName("neo-Button-quiet", variant == Variant.QUIET);
    button.setStyleName("neo-Button-danger", variant == Variant.DANGER);
  }

  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    button.setStyleName("neo-Button-disabled", !enabled);
    button.getElement().setAttribute("aria-disabled",
        String.valueOf(!enabled));
    button.setTabIndex(enabled ? 0 : -1);
  }

  @Override
  public String getText() {
    return label.getText();
  }

  /** text="…" in a template replaces the action's label here only. */
  @Override
  public void setText(String text) {
    captionOverride = text;
    label.setText(text);
    button.setTitle(text);
  }

  /** A FocusPanel has no disabled state, so clicks are dropped here. */
  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return button.addClickHandler(e -> {
      if (enabled) {
        handler.onClick(e);
      }
    });
  }
}
