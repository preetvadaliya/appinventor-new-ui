// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.appinventor.client.core.presenters.EditorPagePresenter.Mode;
import com.google.appinventor.client.features.assets.AssetsView;
import com.google.appinventor.client.features.blocks.BlockDrawersView;
import com.google.appinventor.client.features.designer.palette.PaletteView;
import com.google.appinventor.client.features.designer.tree.ComponentTreeView;
import com.google.appinventor.client.features.designer.viewer.ViewerView;
import com.google.appinventor.client.features.properties.PropertiesView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Classic's arrangement of the shared designer and blocks views. The
 * views' presenters fill them; this class only places them and switches
 * between Designer and Blocks.
 */
public class ClassicWorkArea extends Composite {

  interface Binder extends UiBinder<Widget, ClassicWorkArea> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField FlowPanel work;
  @UiField PaletteView palette;
  @UiField ViewerView viewer;
  @UiField ComponentTreeView components;
  @UiField BlockDrawersView drawers;
  @UiField AssetsView assets;
  @UiField PropertiesView properties;

  public ClassicWorkArea() {
    initWidget(BINDER.createAndBindUi(this));
  }

  public void showMode(Mode mode) {
    work.setStyleName("is-designer", mode == Mode.DESIGNER);
    work.setStyleName("is-blocks", mode == Mode.BLOCKS);
  }

  public PaletteView palette() { return palette; }
  public ViewerView viewer() { return viewer; }
  public ComponentTreeView components() { return components; }
  public BlockDrawersView drawers() { return drawers; }
  public AssetsView assets() { return assets; }
  public PropertiesView properties() { return properties; }
}
