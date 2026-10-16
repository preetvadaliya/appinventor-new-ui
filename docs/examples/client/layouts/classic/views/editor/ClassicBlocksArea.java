// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.appinventor.client.features.assets.AssetsView;
import com.google.appinventor.client.features.blocks.BlockDrawersView;
import com.google.appinventor.client.features.blocks.BlocksWorkspaceView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Classic's Blocks area: places the shared views; presenters fill them. */
public class ClassicBlocksArea extends Composite {

  interface Binder extends UiBinder<Widget, ClassicBlocksArea> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField BlockDrawersView drawers;
  @UiField AssetsView assets;
  @UiField BlocksWorkspaceView workspace;

  public ClassicBlocksArea() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
