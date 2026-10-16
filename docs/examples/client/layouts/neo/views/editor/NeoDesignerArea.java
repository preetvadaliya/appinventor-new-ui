// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Neo's Designer area: palette, viewer, components, media, properties. Each region is its own view. */
public class NeoDesignerArea extends Composite {

  interface Binder extends UiBinder<Widget, NeoDesignerArea> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField NeoPalette palette;
  @UiField NeoViewer viewer;
  @UiField NeoComponentTree components;
  @UiField NeoMedia media;
  @UiField NeoProperties properties;

  public NeoDesignerArea() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
