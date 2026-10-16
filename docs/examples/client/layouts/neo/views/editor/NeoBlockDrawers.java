// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.editor;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.core.icons.BlockCategory;
import com.google.appinventor.client.core.icons.ComponentIconSource;
import com.google.appinventor.client.core.presenters.BlockDrawersPresenter;
import com.google.appinventor.client.features.blocks.BlockDrawerItem;
import com.google.appinventor.client.layouts.neo.controls.NeoTree;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.function.Consumer;

/** Neo's block drawer list; markup in NeoBlockDrawers.ui.xml. */
public class NeoBlockDrawers extends Composite
    implements BlockDrawersPresenter.View {

  interface Binder extends UiBinder<Widget, NeoBlockDrawers> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField FlowPanel builtIn;
  @UiField NeoTree components;

  private Consumer<String> chosen = name -> { };

  public NeoBlockDrawers() {
    initWidget(BINDER.createAndBindUi(this));
    components.onNodeChosen((id, extend, toggle) -> chosen.accept(id));
  }

  @Override
  public void clear() {
    builtIn.clear();
    components.clear();
  }

  @Override
  public void addBuiltIn(BlockCategory category, String caption) {
    BlockDrawerItem item = new BlockDrawerItem(category, caption);
    item.addDomHandler(e -> chosen.accept(category.getDrawerName()),
        ClickEvent.getType());
    builtIn.add(item);
  }

  @Override
  public void addComponent(String parentId, String id, String caption,
      ComponentIconSource icon) {
    components.addNode(parentId, id,
        Controls.active().icons().component(icon), caption);
  }

  @Override
  public void onDrawerChosen(Consumer<String> handler) {
    chosen = handler;
  }
}
