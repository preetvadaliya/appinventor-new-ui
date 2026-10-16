// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Neo's Blocks area: drawers, media, blocks workspace. Each region is its own view. */
public class NeoBlocksArea extends Composite {

  interface Binder extends UiBinder<Widget, NeoBlocksArea> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField NeoBlockDrawers drawers;
  @UiField NeoMedia media;
  @UiField NeoBlocksWorkspace workspace;

  public NeoBlocksArea() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
