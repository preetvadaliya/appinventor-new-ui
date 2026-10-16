// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.admin;

import com.google.appinventor.client.features.workspace.boxes.AdminUserListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Classic's admin page: the user list, which stays a shared box. */
public class ClassicAdminPage extends Composite {

  interface Binder extends UiBinder<Widget, ClassicAdminPage> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField AdminUserListBox users;

  public ClassicAdminPage() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
