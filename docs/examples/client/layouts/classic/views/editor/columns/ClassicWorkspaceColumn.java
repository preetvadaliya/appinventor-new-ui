// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor.columns;

import com.google.appinventor.client.features.blocks.BlocksWorkspaceView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** One column of classic's editor; markup in ClassicWorkspaceColumn.ui.xml. */
public class ClassicWorkspaceColumn extends Composite {

  interface Binder extends UiBinder<Widget, ClassicWorkspaceColumn> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField BlocksWorkspaceView workspace;

  public ClassicWorkspaceColumn() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
