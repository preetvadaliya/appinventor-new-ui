// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.projects;

import com.google.appinventor.client.core.presenters.ProjectListPresenter.Column;
import com.google.appinventor.client.core.presenters.ProjectListPresenter.Order;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import java.util.function.Consumer;

/**
 * A reusable column title. Like a React component:
 *   props  column, label    (template attributes, via setters)
 *   state  setSort(order)   (set by the parent from the presenter)
 *   event  onSort(handler)  (reported up, never handled here)
 */
public class ClassicSortHeader extends Composite {

  interface Binder extends UiBinder<Widget, ClassicSortHeader> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField FocusPanel button;
  @UiField InlineLabel label;
  @UiField InlineLabel arrow;

  private Column column;

  public ClassicSortHeader() {
    initWidget(BINDER.createAndBindUi(this));
    button.getElement().setAttribute("role", "columnheader");
  }

  /** column="NAME" in a template calls this. */
  public void setColumn(Column column) {
    this.column = column;
  }

  /** label="{messages.projectNameHeader}" in a template calls this. */
  public void setLabel(String text) {
    label.setText(text);
  }

  public void setSort(Order order) {
    arrow.setText(order == Order.ASCENDING ? "▲"
        : order == Order.DESCENDING ? "▼" : "");
    button.getElement().setAttribute("aria-sort",
        order == Order.ASCENDING ? "ascending"
            : order == Order.DESCENDING ? "descending" : "none");
  }

  public void onSort(Consumer<Column> handler) {
    button.addClickHandler(e -> handler.accept(column));
  }
}
