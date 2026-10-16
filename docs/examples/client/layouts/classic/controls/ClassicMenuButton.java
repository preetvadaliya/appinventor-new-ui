// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.controls;

import com.google.appinventor.client.controls.Button.Variant;
import com.google.appinventor.client.controls.MenuButton;
import com.google.appinventor.client.controls.MenuItem;
import com.google.appinventor.client.controls.behaviour.ActionBinding;
import com.google.appinventor.client.controls.behaviour.MenuKeyboard;
import com.google.appinventor.client.core.actions.Action;
import com.google.appinventor.client.core.actions.ActionId;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;

/**
 * Behaviour of classic's menu button; its markup is
 * ClassicMenuButton.ui.xml, the menu it opens ClassicMenu. Classic shows
 * the label and the caret, never the action's icon.
 */
public class ClassicMenuButton extends Composite implements MenuButton {

  interface Binder extends UiBinder<Widget, ClassicMenuButton> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField HTMLPanel root;
  @UiField InlineLabel caption;

  private final ButtonElement button;
  private final ClassicMenu menu = new ClassicMenu();
  private final List<MenuItem> items = new ArrayList<>();
  private final MenuKeyboard keyboard;
  private String captionOverride;

  public ClassicMenuButton() {
    initWidget(BINDER.createAndBindUi(this));
    button = ButtonElement.as(root.getElement());
    button.setAttribute("type", "button");
    button.setAttribute("aria-haspopup", "menu");
    keyboard = new MenuKeyboard(root, menu, items);
    root.addDomHandler(e -> keyboard.toggle(), ClickEvent.getType());
  }

  @Override
  public void setAction(ActionId id) {
    ActionBinding.bind(id, this::render);
  }

  private void render(Action action) {
    caption.setText(captionOverride != null
        ? captionOverride : action.getLabel());
    setEnabled(action.isEnabled());
    setVisible(action.isVisible());
  }

  /** Menus in bars are QUIET; the default looks like a button. */
  @Override
  public void setVariant(Variant variant) {
    root.setStyleName("classic-MenuButton-quiet", variant == Variant.QUIET);
  }

  @Override
  public void setEnabled(boolean enabled) {
    button.setDisabled(!enabled);
  }

  /** Choosing an item closes the menu after the item has run. */
  @Override
  public void addItem(MenuItem item) {
    items.add(item);
    menu.addItem(item);
    item.addClickHandler(e -> keyboard.close());
  }

  @Override
  public void addSeparator() {
    menu.addSeparator();
  }

  @Override
  public void clearItems() {
    items.clear();
    menu.clearItems();
  }

  @Override
  public String getText() {
    return caption.getText();
  }

  /** text="…" in a template replaces the action's label here only. */
  @Override
  public void setText(String text) {
    captionOverride = text;
    caption.setText(text);
  }
}
