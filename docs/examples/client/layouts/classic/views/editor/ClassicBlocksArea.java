// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Classic's Blocks area: drawers, media, blocks workspace. Each region is its own view. */
public class ClassicBlocksArea extends Composite {

  interface Binder extends UiBinder<Widget, ClassicBlocksArea> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField ClassicBlockDrawers drawers;
  @UiField ClassicMedia media;
  @UiField ClassicBlocksWorkspace workspace;

  public ClassicBlocksArea() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
