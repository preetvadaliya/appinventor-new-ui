// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.appinventor.client.layouts.classic.views.editor.columns.ClassicComponentsColumn;
import com.google.appinventor.client.layouts.classic.views.editor.columns.ClassicPaletteColumn;
import com.google.appinventor.client.layouts.classic.views.editor.columns.ClassicPropertiesColumn;
import com.google.appinventor.client.layouts.classic.views.editor.columns.ClassicViewerColumn;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Classic's Designer area: lines up its columns. Each column holds the shared
 * views that presenters fill.
 */
public class ClassicDesignerArea extends Composite {

  interface Binder extends UiBinder<Widget, ClassicDesignerArea> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField ClassicPaletteColumn palette;
  @UiField ClassicViewerColumn viewer;
  @UiField ClassicComponentsColumn components;
  @UiField ClassicPropertiesColumn properties;

  public ClassicDesignerArea() {
    initWidget(BINDER.createAndBindUi(this));
  }
}
