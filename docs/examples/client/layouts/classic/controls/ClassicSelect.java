// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.controls;

import com.google.appinventor.client.controls.Select;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/** Behaviour of classic's drop-down list; markup is ClassicSelect.ui.xml. */
public class ClassicSelect extends Composite implements Select {

  interface Binder extends UiBinder<Widget, ClassicSelect> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);
  private static int nextId;

  @UiField Label label;
  @UiField ListBox list;

  public ClassicSelect() {
    initWidget(BINDER.createAndBindUi(this));
    String id = "classic-select-" + (nextId++);
    label.getElement().setId(id);
    list.getElement().setAttribute("aria-labelledby", id);
    label.setVisible(false);
  }

  /** label="{messages.…}" in a template; no label, no label row. */
  @Override
  public void setLabel(String text) {
    label.setText(text);
    label.setVisible(text != null && !text.isEmpty());
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public void addItem(String caption, String value) {
    list.addItem(caption, value);
  }

  @Override
  public String getValue() {
    int index = list.getSelectedIndex();
    return index < 0 ? null : list.getValue(index);
  }

  @Override
  public void setValue(String value) {
    for (int i = 0; i < list.getItemCount(); i++) {
      if (list.getValue(i).equals(value)) {
        list.setSelectedIndex(i);
        return;
      }
    }
  }

  @Override
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return list.addChangeHandler(handler);
  }
}
