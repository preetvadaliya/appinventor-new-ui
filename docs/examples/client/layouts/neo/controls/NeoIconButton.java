// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.controls;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.IconButton;
import com.google.appinventor.client.controls.IconView;
import com.google.appinventor.client.controls.behaviour.ActionBinding;
import com.google.appinventor.client.core.actions.Action;
import com.google.appinventor.client.core.actions.ActionId;
import com.google.appinventor.client.core.actions.IconKey;
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
import com.google.gwt.user.client.ui.Widget;

/**
 * Behaviour of neo's round icon button (help, close); its markup is
 * NeoIconButton.ui.xml. Only the Material glyph shows, so the name goes
 * in aria-label and title (decision 0004).
 */
public class NeoIconButton extends Composite implements IconButton {

  interface Binder extends UiBinder<Widget, NeoIconButton> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField HTMLPanel root;
  @UiField(provided = true) IconView icon = new IconView(Icon.NONE, "");

  private final ButtonElement button;
  private String name = "";
  private String nameOverride;
  private String tooltip;

  public NeoIconButton() {
    initWidget(BINDER.createAndBindUi(this));
    button = ButtonElement.as(root.getElement());
    button.setAttribute("type", "button");
  }

  @Override
  public void setAction(ActionId id) {
    Action action = ActionBinding.bind(id, this::render);
    addClickHandler(e -> ActionBinding.click(action));
  }

  private void render(Action action) {
    setIcon(action.getIcon());
    name(nameOverride != null ? nameOverride : action.getLabel());
    setEnabled(action.isEnabled());
    setVisible(action.isVisible());
  }

  /** Neo's Material glyph for the key, from NeoIcons. */
  @Override
  public void setIcon(IconKey key) {
    icon.setIcon(Controls.active().icons().action(key));
  }

  private void name(String text) {
    name = text;
    button.setAttribute("aria-label", text);
    button.setTitle(tooltip != null ? tooltip : text);
  }

  @Override
  public void setTooltip(String text) {
    tooltip = text;
    button.setTitle(text != null ? text : name);
  }

  @Override
  public void setEnabled(boolean enabled) {
    button.setDisabled(!enabled);
  }

  @Override
  public String getText() {
    return name;
  }

  /** text="…" names the button; it replaces the action's label. */
  @Override
  public void setText(String text) {
    nameOverride = text;
    name(text);
  }

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return root.addDomHandler(handler, ClickEvent.getType());
  }
}
