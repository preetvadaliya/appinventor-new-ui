// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.workspace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.Iterator;

/**
 * A titled panel. Like a React component with children:
 *   <w:Pane title="{messages.paletteBoxCaption}">
 *     <d:PaletteView/>                       children go in the body
 *     <w:action><c:Button …/></w:action>     extra header buttons
 *   </w:Pane>
 */
public class Pane extends Composite implements HasWidgets {

  interface Binder extends UiBinder<Widget, Pane> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField Label title;
  @UiField FlowPanel actions;
  @UiField FlowPanel body;

  public Pane() {
    initWidget(BINDER.createAndBindUi(this));
    title.getElement().setAttribute("role", "heading");
    title.getElement().setAttribute("aria-level", "2");
  }

  /** title="…" in a template. */
  public void setTitle(String text) {
    title.setText(text);
  }

  /** <w:action> children in a template. */
  @UiChild(tagname = "action")
  public void addAction(Widget action) {
    actions.add(action);
  }

  // Children of <w:Pane> go into the body.
  @Override
  public void add(Widget w) {
    body.add(w);
  }

  @Override
  public void clear() {
    body.clear();
  }

  @Override
  public Iterator<Widget> iterator() {
    return body.iterator();
  }

  @Override
  public boolean remove(Widget w) {
    return body.remove(w);
  }
}
