// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.editor;

import com.google.appinventor.client.layouts.neo.controls.NeoIconButton;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/** One property row; markup in NeoPropertyRow.ui.xml. */
public class NeoPropertyRow extends Composite {

  interface Binder extends UiBinder<Widget, NeoPropertyRow> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);
  private static int nextId;

  @UiField Label label;
  @UiField NeoIconButton help;
  @UiField SimplePanel editor;

  public NeoPropertyRow() {
    initWidget(BINDER.createAndBindUi(this));
    label.getElement().setId("property-label-" + (nextId++));
  }

  public void setCaption(String caption) {
    label.setText(caption);
  }

  /** No help text: no help button. */
  public void setHelp(String text) {
    help.setVisible(text != null && !text.isEmpty());
    help.setTooltip(text);
  }

  public void setEditor(IsWidget widget) {
    editor.setWidget(widget);
    // the editor's input is named by this row's label
    widget.asWidget().getElement().setAttribute("aria-labelledby",
        label.getElement().getId());
  }
}
