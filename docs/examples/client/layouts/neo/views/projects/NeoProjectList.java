// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.projects;

import com.google.appinventor.client.controls.behaviour.RowActivation;
import com.google.appinventor.client.core.presenters.ProjectListPresenter;
import com.google.appinventor.client.core.presenters.ProjectListPresenter.Node;
import com.google.appinventor.client.core.presenters.ProjectListPresenter.SortField;
import com.google.appinventor.client.layouts.neo.controls.NeoCheckbox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Neo's project list: column headers, then a row per project and a
 * folder per folder. Markup in NeoProjectList.ui.xml; the rows are
 * NeoProjectRow and NeoProjectFolder.
 */
public class NeoProjectList extends Composite
    implements ProjectListPresenter.View {

  interface Binder extends UiBinder<Widget, NeoProjectList> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField NeoCheckbox selectAll;
  @UiField FocusPanel sortByName;
  @UiField InlineLabel nameHeader;
  @UiField FocusPanel sortByCreated;
  @UiField InlineLabel createdHeader;
  @UiField FocusPanel sortByModified;
  @UiField InlineLabel modifiedHeader;
  @UiField FlowPanel rows;
  @UiField Label empty;

  private final Map<String, NeoProjectRow> projects = new HashMap<>();
  private final Map<String, NeoProjectFolder> folders = new HashMap<>();
  private BiConsumer<String, Boolean> checked = (id, value) -> { };
  private Consumer<String> expandToggled = id -> { };
  private Consumer<String> opened = id -> { };

  public NeoProjectList() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void render(Node root) {
    rows.clear();
    projects.clear();
    folders.clear();
    add(rows::add, root.getChildren());
    empty.setVisible(root.getChildren().isEmpty());
  }

  private void add(Consumer<IsWidget> parent, List<Node> nodes) {
    for (Node node : nodes) {
      String id = node.getId();
      if (node.isFolder()) {
        NeoProjectFolder folder = new NeoProjectFolder();
        folder.show(node);
        folder.onChecked(value -> checked.accept(id, value));
        folder.onToggle(() -> expandToggled.accept(id));
        folders.put(id, folder);
        parent.accept(folder);
        add(folder::addChild, node.getChildren());
      } else {
        NeoProjectRow row = new NeoProjectRow();
        row.show(node);
        row.onChecked(value -> checked.accept(id, value));
        row.onOpen(() -> opened.accept(id));
        projects.put(id, row);
        parent.accept(row);
      }
    }
  }

  @Override
  public void setSelected(String id, boolean selected) {
    if (folders.containsKey(id)) {
      folders.get(id).setSelected(selected);
    } else if (projects.containsKey(id)) {
      projects.get(id).setSelected(selected);
    }
  }

  @Override
  public void setExpanded(String id, boolean expanded) {
    folders.get(id).setExpanded(expanded);
  }

  @Override
  public void setSelectAll(boolean selected) {
    selectAll.setValue(selected);
  }

  /** The arrow is the style's job; see .neo-SortHeader-asc in projects.css. */
  @Override
  public void showSort(SortField field, boolean ascending) {
    mark(sortByName, field == SortField.NAME, ascending);
    mark(sortByCreated, field == SortField.CREATED, ascending);
    mark(sortByModified, field == SortField.MODIFIED, ascending);
  }

  private static void mark(FocusPanel header, boolean active,
      boolean ascending) {
    header.setStyleName("neo-SortHeader-asc", active && ascending);
    header.setStyleName("neo-SortHeader-desc", active && !ascending);
  }

  @Override
  public void onChecked(BiConsumer<String, Boolean> handler) {
    checked = handler;
  }

  @Override
  public void onExpandToggled(Consumer<String> handler) {
    expandToggled = handler;
  }

  @Override
  public void onOpen(Consumer<String> handler) {
    opened = handler;
  }

  @Override
  public void onSelectAllChecked(Consumer<Boolean> handler) {
    selectAll.addValueChangeHandler(e -> handler.accept(e.getValue()));
  }

  @Override
  public void onSortClicked(Consumer<SortField> handler) {
    RowActivation.on(sortByName, () -> handler.accept(SortField.NAME));
    RowActivation.on(sortByCreated,
        () -> handler.accept(SortField.CREATED));
    RowActivation.on(sortByModified,
        () -> handler.accept(SortField.MODIFIED));
  }
}
