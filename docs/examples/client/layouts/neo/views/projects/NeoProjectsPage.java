// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.projects;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Neo's projects page: the project toolbar, then the project list. */
public class NeoProjectsPage extends Composite {

  interface Binder extends UiBinder<Widget, NeoProjectsPage> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField NeoProjectToolbar toolbar;
  @UiField NeoProjectList list;

  public NeoProjectsPage() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
