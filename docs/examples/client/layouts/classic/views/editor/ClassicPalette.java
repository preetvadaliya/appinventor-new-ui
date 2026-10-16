// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.appinventor.client.core.presenters.PalettePresenter;
import com.google.appinventor.client.core.presenters.PalettePresenter.Entry;
import com.google.appinventor.client.layouts.classic.controls.ClassicButton;
import com.google.appinventor.client.layouts.classic.controls.ClassicCollapsible;
import com.google.appinventor.client.layouts.classic.controls.ClassicTextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/** Classic's Palette region; markup in ClassicPalette.ui.xml. */
public class ClassicPalette extends Composite
    implements PalettePresenter.View {

  interface Binder extends UiBinder<Widget, ClassicPalette> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField ClassicTextField search;
  @UiField FlowPanel results;
  @UiField FlowPanel categories;
  @UiField ClassicButton importExtension;

  private final Map<String, ClassicCollapsible> groups = new HashMap<>();
  private Consumer<String> picked = type -> { };
  private Consumer<String> dragStarted = type -> { };

  public ClassicPalette() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void clear() {
    categories.clear();
    results.clear();
    groups.clear();
  }

  @Override
  public void addCategory(String category) {
    ClassicCollapsible group = new ClassicCollapsible();
    group.setTitle(category);
    group.setOpen(groups.isEmpty());   // the first one starts open
    groups.put(category, group);
    categories.add(group);
  }

  @Override
  public void addItem(String category, Entry entry) {
    groups.get(category).add(item(entry));
  }

  @Override
  public void showResults(List<Entry> matches) {
    results.clear();
    if (matches != null) {
      for (Entry entry : matches) {
        results.add(item(entry));
      }
    }
    results.setVisible(matches != null);
    categories.setVisible(matches == null);
  }

  private ClassicPaletteItem item(Entry entry) {
    ClassicPaletteItem item = new ClassicPaletteItem();
    item.show(entry);
    item.onPicked(() -> picked.accept(entry.getType()));
    item.onDragStarted(() -> dragStarted.accept(entry.getType()));
    return item;
  }

  @Override
  public void onSearch(Consumer<String> handler) {
    search.addKeyUpHandler(e -> handler.accept(search.getText()));
  }

  @Override
  public void onPicked(Consumer<String> handler) {
    picked = handler;
  }

  @Override
  public void onDragStarted(Consumer<String> handler) {
    dragStarted = handler;
  }

  @Override
  public void onImportExtension(Runnable handler) {
    importExtension.addClickHandler(e -> handler.run());
  }
}
