// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.projects;

import com.google.appinventor.client.controls.behaviour.RowActivation;
import com.google.appinventor.client.core.presenters.ProjectListPresenter.Node;
import com.google.appinventor.client.layouts.classic.controls.ClassicCheckbox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.Date;
import java.util.function.Consumer;

/** One project in the list; markup in ClassicProjectRow.ui.xml. */
public class ClassicProjectRow extends Composite {

  interface Binder extends UiBinder<Widget, ClassicProjectRow> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);
  private static final DateTimeFormat DATES =
      DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);

  @UiField FocusPanel row;
  @UiField ClassicCheckbox selected;
  @UiField Label name;
  @UiField Label created;
  @UiField Label modified;

  public ClassicProjectRow() {
    initWidget(BINDER.createAndBindUi(this));
  }

  public void show(Node project) {
    name.setText(project.getName());
    created.setText(date(project.getCreated()));
    modified.setText(date(project.getModified()));
  }

  public void setSelected(boolean value) {
    selected.setValue(value);
    row.setStyleName("classic-Row-selected", value);
  }

  public void onChecked(Consumer<Boolean> handler) {
    selected.addValueChangeHandler(e -> handler.accept(e.getValue()));
  }

  /** Click, Enter or Space on the row, not on its checkbox. */
  public void onOpen(Runnable handler) {
    RowActivation.on(row, handler, selected);
  }

  static String date(long millis) {
    return DATES.format(new Date(millis));
  }
}
