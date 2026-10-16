// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.projects;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.IconView;
import com.google.appinventor.client.controls.behaviour.RowActivation;
import com.google.appinventor.client.core.actions.IconKey;
import com.google.appinventor.client.core.icons.Icon;
import com.google.appinventor.client.core.presenters.ProjectListPresenter.Node;
import com.google.appinventor.client.layouts.classic.controls.ClassicCheckbox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.function.Consumer;

/** One folder in the list, with its children; markup in ClassicProjectFolder.ui.xml. */
public class ClassicProjectFolder extends Composite {

  interface Binder extends UiBinder<Widget, ClassicProjectFolder> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField FocusPanel row;
  @UiField ClassicCheckbox selected;
  @UiField(provided = true) IconView expander = new IconView(Icon.NONE, "");
  @UiField(provided = true) IconView folderIcon = new IconView(Icon.NONE, "");
  @UiField Label name;
  @UiField Label created;
  @UiField Label modified;
  @UiField FlowPanel children;

  public ClassicProjectFolder() {
    initWidget(BINDER.createAndBindUi(this));
    folderIcon.setIcon(Controls.active().icons().action(IconKey.FOLDER));
    setExpanded(false);
  }

  public void show(Node folder) {
    name.setText(folder.getName());
    created.setText(ClassicProjectRow.date(folder.getCreated()));
    modified.setText(ClassicProjectRow.date(folder.getModified()));
  }

  public void setSelected(boolean value) {
    selected.setValue(value);
    row.setStyleName("classic-Row-selected", value);
  }

  public void setExpanded(boolean expanded) {
    children.setVisible(expanded);
    expander.setIcon(Controls.active().icons().action(
        expanded ? IconKey.COLLAPSE : IconKey.EXPAND));
    row.getElement().setAttribute("aria-expanded", String.valueOf(expanded));
  }

  public void addChild(IsWidget child) {
    children.add(child);
  }

  public void onChecked(Consumer<Boolean> handler) {
    selected.addValueChangeHandler(e -> handler.accept(e.getValue()));
  }

  /** Click, Enter or Space on the row, not on its checkbox. */
  public void onToggle(Runnable handler) {
    RowActivation.on(row, handler, selected);
  }
}
