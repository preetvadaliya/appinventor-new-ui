// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor.columns;

import com.google.appinventor.client.features.assets.AssetsView;
import com.google.appinventor.client.features.designer.tree.ComponentTreeView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** One column of classic's editor; markup in ClassicComponentsColumn.ui.xml. */
public class ClassicComponentsColumn extends Composite {

  interface Binder extends UiBinder<Widget, ClassicComponentsColumn> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField ComponentTreeView components;
  @UiField AssetsView assets;

  public ClassicComponentsColumn() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
