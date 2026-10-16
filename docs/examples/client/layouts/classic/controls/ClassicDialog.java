// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.controls;

import static com.google.appinventor.client.core.i18n.OdeMessages.MESSAGES;

import com.google.appinventor.client.controls.Dialog;
import com.google.appinventor.client.controls.behaviour.DialogA11y;
import com.google.appinventor.client.core.actions.IconKey;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Behaviour of classic's dialog frame: a modal popup around
 * ClassicDialog.ui.xml. Focus trap, Escape and ARIA come from DialogA11y.
 */
public class ClassicDialog implements Dialog {

  interface Binder extends UiBinder<Widget, ClassicDialog> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField Label caption;
  @UiField ClassicIconButton close;
  @UiField SimplePanel content;

  private final PopupPanel popup = new PopupPanel(false, true);
  private Runnable dismiss = () -> { };

  public ClassicDialog() {
    popup.setWidget(BINDER.createAndBindUi(this));
    popup.setGlassEnabled(true);
    popup.setStyleName("classic-DialogPopup");
    close.setIcon(IconKey.CLOSE);
    close.setText(MESSAGES.dismissButton());
    close.addClickHandler(e -> dismiss.run());
    DialogA11y.install(popup, caption, () -> dismiss.run());
  }

  @Override
  public void setCaption(String text) {
    caption.setText(text);
  }

  @Override
  public void setContent(IsWidget widget) {
    content.setWidget(widget);
  }

  @Override
  public void onDismiss(Runnable handler) {
    dismiss = handler;
  }

  @Override
  public void open() {
    popup.center();
  }

  @Override
  public void close() {
    popup.hide();
  }
}
