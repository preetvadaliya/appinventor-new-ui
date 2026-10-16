// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.menus;

import com.google.appinventor.client.core.presenters.MainMenuPresenter;
import com.google.appinventor.client.layouts.classic.controls.ClassicMenuButton;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Classic's main menu: six menus; markup in ClassicMainMenu.ui.xml. */
public class ClassicMainMenu extends Composite
    implements MainMenuPresenter.View {

  interface Binder extends UiBinder<Widget, ClassicMainMenu> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField ClassicMenuButton adminMenu;

  public ClassicMainMenu() {
    initWidget(BINDER.createAndBindUi(this));
    adminMenu.setVisible(false);
  }

  @Override
  public void showAdminMenu(boolean visible) {
    adminMenu.setVisible(visible);
  }
}
