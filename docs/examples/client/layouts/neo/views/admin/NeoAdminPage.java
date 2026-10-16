// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.admin;

import com.google.appinventor.client.features.workspace.boxes.AdminUserListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Neo's admin page: the user list, which stays a shared box. */
public class NeoAdminPage extends Composite {

  interface Binder extends UiBinder<Widget, NeoAdminPage> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField AdminUserListBox users;

  public NeoAdminPage() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
