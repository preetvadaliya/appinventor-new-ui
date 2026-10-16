// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.editor;

import com.google.appinventor.client.core.presenters.PropertiesPresenter;
import com.google.appinventor.client.core.properties.editors.EditorView;
import com.google.appinventor.client.layouts.neo.controls.NeoCollapsible;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashMap;
import java.util.Map;

/** Neo's Properties region; markup in NeoProperties.ui.xml. */
public class NeoProperties extends Composite
    implements PropertiesPresenter.View {

  interface Binder extends UiBinder<Widget, NeoProperties> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField Label componentName;
  @UiField Label componentType;
  @UiField FlowPanel categories;

  private final Map<String, NeoCollapsible> groups = new HashMap<>();

  public NeoProperties() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void clear() {
    categories.clear();
    groups.clear();
  }

  @Override
  public void showComponent(String name, String type) {
    componentName.setText(name);
    componentType.setText(type);
  }

  @Override
  public void addCategory(String category) {
    NeoCollapsible group = new NeoCollapsible();
    group.setTitle(category);
    group.setOpen(true);
    groups.put(category, group);
    categories.add(group);
  }

  @Override
  public void addRow(String category, String caption, String help,
      EditorView editor) {
    NeoPropertyRow row = new NeoPropertyRow();
    row.setCaption(caption);
    row.setHelp(help);
    row.setEditor((IsWidget) editor);   // every editor view is a widget
    groups.get(category).add(row);
  }
}
