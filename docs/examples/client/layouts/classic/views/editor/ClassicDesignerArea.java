// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Classic's Designer area: palette, viewer, components, media, properties. Each region is its own view. */
public class ClassicDesignerArea extends Composite {

  interface Binder extends UiBinder<Widget, ClassicDesignerArea> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField ClassicPalette palette;
  @UiField ClassicViewer viewer;
  @UiField ClassicComponentTree components;
  @UiField ClassicMedia media;
  @UiField ClassicProperties properties;

  public ClassicDesignerArea() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
