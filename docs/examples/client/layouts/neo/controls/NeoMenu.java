// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.controls;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The floating card a NeoMenuButton opens: a popup around NeoMenu.ui.xml.
 * Each separator is a fresh copy of NeoMenuSeparator.ui.xml.
 */
public class NeoMenu extends PopupPanel {

  interface Binder extends UiBinder<Widget, NeoMenu> {
  }

  @UiTemplate("NeoMenuSeparator.ui.xml")
  interface SeparatorBinder extends UiBinder<Widget, NeoMenu> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);
  private static final SeparatorBinder SEPARATOR =
      GWT.create(SeparatorBinder.class);

  @UiField FlowPanel items;

  public NeoMenu() {
    super(true);   // auto-hide: a click elsewhere closes it
    setWidget(BINDER.createAndBindUi(this));
    setStyleName("neo-MenuPopup");
    items.getElement().setAttribute("role", "menu");
  }

  public void addItem(IsWidget item) {
    items.add(item);
  }

  public void addSeparator() {
    Widget line = SEPARATOR.createAndBindUi(this);
    line.getElement().setAttribute("role", "separator");
    items.add(line);
  }

  public void clearItems() {
    items.clear();
  }
}
