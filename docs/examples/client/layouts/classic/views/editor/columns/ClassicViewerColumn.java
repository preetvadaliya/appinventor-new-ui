// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor.columns;

import com.google.appinventor.client.features.designer.viewer.ViewerView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** One column of classic's editor; markup in ClassicViewerColumn.ui.xml. */
public class ClassicViewerColumn extends Composite {

  interface Binder extends UiBinder<Widget, ClassicViewerColumn> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField ViewerView viewer;

  public ClassicViewerColumn() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
