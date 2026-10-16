// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.controls;

import com.google.appinventor.client.controls.IconView;
import com.google.appinventor.client.core.icons.Icon;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

/**
 * One item of a ClassicTree; markup is ClassicTreeNode.ui.xml. The click
 * handler is here because a GWT Tree's own selection event does not say
 * whether Shift or Ctrl was down.
 */
public class ClassicTreeNode extends Composite implements HasClickHandlers {

  interface Binder extends UiBinder<Widget, ClassicTreeNode> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField(provided = true) IconView icon = new IconView(Icon.NONE, "");
  @UiField InlineLabel caption;

  public ClassicTreeNode() {
    initWidget(BINDER.createAndBindUi(this));
  }

  /** The icon is decorative: the caption already names the node. */
  public void setIcon(Icon value) {
    icon.setIcon(value);
    icon.setVisible(value.getKind() != Icon.Kind.NONE);
  }

  public void setCaption(String text) {
    caption.setText(text);
  }

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return addDomHandler(handler, ClickEvent.getType());
  }
}
