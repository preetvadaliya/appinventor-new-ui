// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.editor;

import com.google.appinventor.client.core.presenters.DesignToolbarPresenter;
import com.google.appinventor.client.layouts.neo.controls.NeoMenuButton;
import com.google.appinventor.client.layouts.neo.controls.NeoMenuItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import java.util.function.Consumer;

/** Neo's design toolbar: project name, screens, editor switch. */
public class NeoDesignToolbar extends Composite
    implements DesignToolbarPresenter.View {

  interface Binder extends UiBinder<Widget, NeoDesignToolbar> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField Label projectName;
  @UiField NeoMenuButton screens;

  private Consumer<String> screenChosen = screen -> { };

  public NeoDesignToolbar() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void showProjectName(String name) {
    projectName.setText(name);
  }

  @Override
  public void showScreens(List<String> names) {
    screens.clearItems();
    for (String name : names) {
      NeoMenuItem item = new NeoMenuItem();
      item.setText(name);
      item.addClickHandler(e -> screenChosen.accept(name));
      screens.addItem(item);
    }
  }

  @Override
  public void showCurrentScreen(String screen) {
    screens.setText(screen);
  }

  @Override
  public void onScreenChosen(Consumer<String> handler) {
    screenChosen = handler;
  }
}
