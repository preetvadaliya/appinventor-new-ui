// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.projects;

import com.google.appinventor.client.core.presenters.ProjectToolbarPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/** Neo's project toolbar: grouped icon buttons and an Export menu. */
public class NeoProjectToolbar extends Composite
    implements ProjectToolbarPresenter.View {

  interface Binder extends UiBinder<Widget, NeoProjectToolbar> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField Label title;
  @UiField HTMLPanel projectButtons;
  @UiField HTMLPanel trashButtons;

  public NeoProjectToolbar() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void showTitle(String text) {
    // Neo shows the title only in the trash; the project list has tabs.
    title.setText(text);
  }

  @Override
  public void showTrash(boolean trash) {
    title.setVisible(trash);
    projectButtons.setVisible(!trash);
    trashButtons.setVisible(trash);
  }
}
