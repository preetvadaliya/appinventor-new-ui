// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.appinventor.client.features.assets.AssetsView;
import com.google.appinventor.client.features.designer.palette.PaletteView;
import com.google.appinventor.client.features.designer.tree.ComponentTreeView;
import com.google.appinventor.client.features.designer.viewer.ViewerView;
import com.google.appinventor.client.features.properties.PropertiesView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Classic's Designer area: places the shared views; presenters fill them. */
public class ClassicDesignerArea extends Composite {

  interface Binder extends UiBinder<Widget, ClassicDesignerArea> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField PaletteView palette;
  @UiField ViewerView viewer;
  @UiField ComponentTreeView components;
  @UiField AssetsView assets;
  @UiField PropertiesView properties;

  public ClassicDesignerArea() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
