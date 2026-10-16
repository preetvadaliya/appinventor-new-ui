// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.editor;

import com.google.appinventor.client.core.presenters.PanelContent;
import com.google.appinventor.client.core.presenters.ViewerPresenter;
import com.google.appinventor.client.core.properties.choices.Choice;
import com.google.appinventor.client.layouts.neo.controls.NeoCheckbox;
import com.google.appinventor.client.layouts.neo.controls.NeoSelect;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import java.util.function.Consumer;

/** Neo's Viewer region; markup in NeoViewer.ui.xml. */
public class NeoViewer extends Composite
    implements ViewerPresenter.View {

  interface Binder extends UiBinder<Widget, NeoViewer> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField NeoCheckbox showHidden;
  @UiField NeoSelect deviceSize;
  @UiField NeoSelect previewStyle;
  @UiField SimplePanel screen;
  @UiField Label nonVisibleHeading;
  @UiField FlowPanel nonVisible;

  public NeoViewer() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void setDeviceSizes(List<Choice> sizes, String selected) {
    fill(deviceSize, sizes, selected);
  }

  @Override
  public void setPreviewStyles(List<Choice> styles, String selected) {
    fill(previewStyle, styles, selected);
  }

  @Override
  public void setShowHidden(boolean show) {
    showHidden.setValue(show);
  }

  @Override
  public void showScreen(PanelContent content) {
    screen.setWidget((IsWidget) content);   // every content is a widget
  }

  @Override
  public void showNonVisible(List<PanelContent> components) {
    nonVisible.clear();
    for (PanelContent component : components) {
      nonVisible.add((IsWidget) component);
    }
    nonVisibleHeading.setVisible(!components.isEmpty());
  }

  @Override
  public void onDeviceSizeChosen(Consumer<String> handler) {
    deviceSize.addChangeHandler(e -> handler.accept(deviceSize.getValue()));
  }

  @Override
  public void onPreviewStyleChosen(Consumer<String> handler) {
    previewStyle.addChangeHandler(
        e -> handler.accept(previewStyle.getValue()));
  }

  @Override
  public void onShowHiddenChanged(Consumer<Boolean> handler) {
    showHidden.addValueChangeHandler(e -> handler.accept(e.getValue()));
  }

  private static void fill(NeoSelect select, List<Choice> choices,
      String selected) {
    select.clear();
    for (Choice choice : choices) {
      select.addItem(choice.getCaption(), choice.getValue());
    }
    select.setValue(selected);
  }
}
