// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.projects;

import com.google.appinventor.client.core.presenters.ProjectToolbarPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/** Classic's project toolbar: one row of text buttons. */
public class ClassicProjectToolbar extends Composite
    implements ProjectToolbarPresenter.View {

  interface Binder extends UiBinder<Widget, ClassicProjectToolbar> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField Label title;
  @UiField DivElement projectButtons;
  @UiField DivElement trashButtons;

  public ClassicProjectToolbar() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void showTitle(String text) {
    title.setText(text);
  }

  @Override
  public void showTrash(boolean trash) {
    UIObject.setVisible(projectButtons, !trash);
    UIObject.setVisible(trashButtons, trash);
  }
}
