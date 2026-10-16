// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.controls;

import com.google.appinventor.client.controls.Collapsible;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import java.util.function.Consumer;

/**
 * Behaviour of neo's collapsible section; its markup is
 * NeoCollapsible.ui.xml. The header is a real <button>, so Enter and
 * Space toggle it. The caret sits at the end of the header and points
 * down when closed, up when open.
 */
public class NeoCollapsible extends Composite implements Collapsible {

  interface Binder extends UiBinder<Widget, NeoCollapsible> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);
  private static int nextId;

  @UiField HTMLPanel header;
  @UiField InlineLabel caret;
  @UiField InlineLabel title;
  @UiField FlowPanel body;

  private final ButtonElement button;
  private Consumer<Boolean> toggled = open -> { };

  public NeoCollapsible() {
    initWidget(BINDER.createAndBindUi(this));
    String id = "neo-collapsible-" + (nextId++);
    body.getElement().setId(id);
    button = ButtonElement.as(header.getElement());
    button.setAttribute("type", "button");
    button.setAttribute("aria-controls", id);
    button.setAttribute("aria-expanded", "false");
    caret.getElement().setAttribute("aria-hidden", "true");
    header.addDomHandler(e -> toggle(), ClickEvent.getType());
  }

  private void toggle() {
    setOpen(!isOpen());
    toggled.accept(isOpen());
  }

  /** The header text, not UIObject's tooltip. */
  @Override
  public void setTitle(String text) {
    title.setText(text);
  }

  @Override
  public void add(IsWidget child) {
    body.add(child);
  }

  @Override
  public void setOpen(boolean open) {
    body.setVisible(open);
    caret.setText(open ? "\u25B4" : "\u25BE");
    button.setAttribute("aria-expanded", String.valueOf(open));
  }

  @Override
  public boolean isOpen() {
    return body.isVisible();
  }

  @Override
  public void focus() {
    button.focus();
  }

  @Override
  public void onToggle(Consumer<Boolean> handler) {
    toggled = handler;
  }
}
