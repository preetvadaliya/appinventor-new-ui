// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.editor;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.core.icons.ComponentIconSource;
import com.google.appinventor.client.core.presenters.ComponentTreePresenter;
import com.google.appinventor.client.layouts.neo.controls.NeoButton;
import com.google.appinventor.client.layouts.neo.controls.NeoTree;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import java.util.Set;

/** Neo's Components region; markup in NeoComponentTree.ui.xml. */
public class NeoComponentTree extends Composite
    implements ComponentTreePresenter.View {

  interface Binder extends UiBinder<Widget, NeoComponentTree> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField NeoTree tree;
  @UiField NeoButton rename;
  @UiField NeoButton delete;

  public NeoComponentTree() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void clear() {
    tree.clear();
  }

  @Override
  public void addNode(String parentId, String id, String caption,
      ComponentIconSource icon) {
    tree.addNode(parentId, id, Controls.active().icons().component(icon),
        caption);
  }

  @Override
  public void setExpanded(String id, boolean expanded) {
    tree.setExpanded(id, expanded);
  }

  @Override
  public void showSelection(Set<String> ids) {
    tree.showSelection(ids);
  }

  @Override
  public void setEditable(boolean canRename, boolean canDelete) {
    rename.setEnabled(canRename);
    delete.setEnabled(canDelete);
  }

  @Override
  public void onNodeChosen(ComponentTreePresenter.NodeHandler handler) {
    tree.onNodeChosen(handler::chosen);
  }

  @Override
  public void onRename(Runnable handler) {
    rename.addClickHandler(e -> handler.run());
  }

  @Override
  public void onDelete(Runnable handler) {
    delete.addClickHandler(e -> handler.run());
  }
}
