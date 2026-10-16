// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.appinventor.client.core.presenters.BlocksWorkspacePresenter;
import com.google.appinventor.client.core.presenters.PanelContent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/** Classic's Blocks workspace: a titled box around the Blockly panel. */
public class ClassicBlocksWorkspace extends Composite
    implements BlocksWorkspacePresenter.View {

  interface Binder extends UiBinder<Widget, ClassicBlocksWorkspace> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField HTMLPanel blockly;

  public ClassicBlocksWorkspace() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void showBlockly(PanelContent content) {
    blockly.clear();
    blockly.add(((IsWidget) content).asWidget());   // the Blockly panel is a widget
  }
}
