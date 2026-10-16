// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.editor;

import com.google.appinventor.client.core.presenters.EditorPagePresenter;
import com.google.appinventor.client.features.console.ConsolePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Neo's editor page: shows the Designer area or the Blocks area. */
public class NeoEditorPage extends Composite
    implements EditorPagePresenter.View {

  interface Binder extends UiBinder<Widget, NeoEditorPage> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField NeoDesignToolbar toolbar;
  @UiField NeoDesignerArea designer;
  @UiField NeoBlocksArea blocks;
  @UiField ConsolePanel console;

  public NeoEditorPage() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void showMode(EditorPagePresenter.Mode mode) {
    designer.setVisible(mode == EditorPagePresenter.Mode.DESIGNER);
    blocks.setVisible(mode == EditorPagePresenter.Mode.BLOCKS);
  }

  @Override
  public void showConsole(boolean visible) {
    console.setVisible(visible);
  }
}
