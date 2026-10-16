// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.appinventor.client.core.presenters.EditorPagePresenter.Mode;
import com.google.appinventor.client.features.workspace.boxes.AssetListBox;
import com.google.appinventor.client.features.workspace.boxes.BlockSelectorBox;
import com.google.appinventor.client.features.workspace.boxes.PaletteBox;
import com.google.appinventor.client.features.workspace.boxes.PropertiesBox;
import com.google.appinventor.client.features.workspace.boxes.SourceStructureBox;
import com.google.appinventor.client.features.workspace.boxes.ViewerBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/** Places the shared panes for classic; switches Designer and Blocks. */
public class ClassicWorkArea extends Composite {

  interface Binder extends UiBinder<Widget, ClassicWorkArea> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField FlowPanel work;
  @UiField(provided = true) PaletteBox palette = PaletteBox.get();
  @UiField(provided = true) ViewerBox viewer = ViewerBox.get();
  @UiField(provided = true) SourceStructureBox components =
      SourceStructureBox.get();
  @UiField(provided = true) BlockSelectorBox drawers =
      BlockSelectorBox.get();
  @UiField(provided = true) AssetListBox assets = AssetListBox.get();
  @UiField(provided = true) PropertiesBox properties = PropertiesBox.get();

  public ClassicWorkArea() {
    initWidget(BINDER.createAndBindUi(this));
  }

  public void showMode(Mode mode) {
    work.setStyleName("is-designer", mode == Mode.DESIGNER);
    work.setStyleName("is-blocks", mode == Mode.BLOCKS);
  }
}
