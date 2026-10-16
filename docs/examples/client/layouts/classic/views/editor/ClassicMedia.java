// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.appinventor.client.core.icons.Icon;
import com.google.appinventor.client.core.presenters.AssetsPresenter;
import com.google.appinventor.client.layouts.classic.controls.ClassicButton;
import com.google.appinventor.client.layouts.classic.controls.ClassicTree;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import java.util.function.Consumer;

/** Classic's Media region; markup in ClassicMedia.ui.xml. */
public class ClassicMedia extends Composite implements AssetsPresenter.View {

  interface Binder extends UiBinder<Widget, ClassicMedia> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField ClassicTree files;
  @UiField ClassicButton upload;

  public ClassicMedia() {
    initWidget(BINDER.createAndBindUi(this));
  }

  /** A file's name is its id: asset names are unique in a project. */
  @Override
  public void showFiles(List<String> names) {
    files.clear();
    for (String name : names) {
      files.addNode(null, name, Icon.NONE, name);
    }
  }

  @Override
  public void setUploadEnabled(boolean enabled) {
    upload.setEnabled(enabled);
  }

  @Override
  public void onFileChosen(Consumer<String> handler) {
    files.onNodeChosen((id, extend, toggle) -> handler.accept(id));
  }

  @Override
  public void onUpload(Runnable handler) {
    upload.addClickHandler(e -> handler.run());
  }
}
