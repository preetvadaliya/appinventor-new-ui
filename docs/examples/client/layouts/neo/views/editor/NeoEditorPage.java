// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.editor;

import com.google.appinventor.client.core.presenters.EditorPagePresenter;
import com.google.appinventor.client.features.console.ConsolePanel;
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

/**
 * Neo's project editor page. The panes are the shared singletons; this
 * view only places them. Designer and Blocks differ by one CSS class.
 */
public class NeoEditorPage extends Composite
    implements EditorPagePresenter.View {

  interface Binder extends UiBinder<Widget, NeoEditorPage> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField NeoDesignToolbar toolbar;
  @UiField FlowPanel work;
  @UiField(provided = true) PaletteBox palette = PaletteBox.get();
  @UiField(provided = true) BlockSelectorBox drawers =
      BlockSelectorBox.get();
  @UiField(provided = true) ViewerBox viewer = ViewerBox.get();
  @UiField(provided = true) SourceStructureBox components =
      SourceStructureBox.get();
  @UiField(provided = true) AssetListBox assets = AssetListBox.get();
  @UiField(provided = true) PropertiesBox properties = PropertiesBox.get();
  @UiField ConsolePanel console;

  public NeoEditorPage() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void showMode(EditorPagePresenter.Mode mode) {
    boolean blocks = mode == EditorPagePresenter.Mode.BLOCKS;
    work.setStyleName("is-designer", !blocks);
    work.setStyleName("is-blocks", blocks);
  }

  @Override
  public void showConsole(boolean visible) {
    console.setVisible(visible);
  }
}
