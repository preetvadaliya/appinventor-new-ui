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
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Behaviour of neo's button; its markup is NeoButton.ui.xml. A
 * real <button>, so focus, Enter and Space come from the browser.
 */
public class NeoButton extends Composite implements Button {

  interface Binder extends UiBinder<Widget, NeoButton> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField HTMLPanel root;
  @UiField(provided = true) IconView icon = new IconView(Icon.NONE, "");
  @UiField InlineLabel label;

  private final ButtonElement button;
  private String captionOverride;

  public NeoButton() {
    initWidget(BINDER.createAndBindUi(this));
    button = ButtonElement.as(root.getElement());
    button.setAttribute("type", "button");   // never submits a form
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
    root.setStyleName("neo-Button-primary", variant == Variant.PRIMARY);
    root.setStyleName("neo-Button-quiet", variant == Variant.QUIET);
    root.setStyleName("neo-Button-danger", variant == Variant.DANGER);
  }

  /** A disabled <button> takes no focus and fires no click. */
  @Override
  public void setEnabled(boolean enabled) {
    button.setDisabled(!enabled);
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

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return root.addDomHandler(handler, ClickEvent.getType());
  }
}
