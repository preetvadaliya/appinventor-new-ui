// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.controls;

import com.google.appinventor.client.controls.Button.Variant;
import com.google.appinventor.client.controls.MenuItem;
import com.google.appinventor.client.controls.behaviour.ActionBinding;
import com.google.appinventor.client.core.actions.Action;
import com.google.appinventor.client.core.actions.ActionId;
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
 * Behaviour of classic's menu entry; its markup is ClassicMenuItem.ui.xml.
 * The label only: classic menus show no icons.
 */
public class ClassicMenuItem extends Composite implements MenuItem {

  interface Binder extends UiBinder<Widget, ClassicMenuItem> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField HTMLPanel root;
  @UiField InlineLabel label;
  @UiField InlineLabel shortcut;

  private final ButtonElement button;
  private String captionOverride;

  public ClassicMenuItem() {
    initWidget(BINDER.createAndBindUi(this));
    button = ButtonElement.as(root.getElement());
    button.setAttribute("type", "button");
    button.setAttribute("role", "menuitem");
    button.setTabIndex(-1);   // MenuKeyboard moves focus; Tab leaves the menu
    shortcut.setVisible(false);
  }

  @Override
  public void setAction(ActionId id) {
    Action action = ActionBinding.bind(id, this::render);
    addClickHandler(e -> ActionBinding.click(action));
  }

  private void render(Action action) {
    label.setText(captionOverride != null
        ? captionOverride : action.getLabel());
    setEnabled(action.isEnabled());
    setVisible(action.isVisible());
  }

  @Override
  public void setVariant(Variant variant) {
    root.setStyleName("classic-MenuItem-danger", variant == Variant.DANGER);
  }

  @Override
  public void setEnabled(boolean enabled) {
    button.setDisabled(!enabled);
  }

  @Override
  public boolean isEnabled() {
    return !button.isDisabled();
  }

  @Override
  public void setShortcut(String keys) {
    shortcut.setText(keys);
    shortcut.setVisible(keys != null && !keys.isEmpty());
  }

  @Override
  public void focus() {
    button.focus();
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
  }

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return root.addDomHandler(handler, ClickEvent.getType());
  }
}
