// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.properties.editors;

import com.google.appinventor.client.controls.SharedView;
import com.google.appinventor.client.controls.TextField;
import com.google.appinventor.client.core.properties.editors.NumberEditorModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import java.util.function.Consumer;

/**
 * The shared view for every number property. Markup is in the template;
 * this class only filters typing and reports Enter, blur and Escape.
 */
public class NumberEditorView extends SharedView
    implements NumberEditorModel.View {

  interface Binder extends UiBinder<Widget, NumberEditorView> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField
  TextField field;

  private String allowed = "";

  public NumberEditorView() {
    initWidget(BINDER.createAndBindUi(this));
    field.addKeyPressHandler(e -> {
      char c = e.getCharCode();
      if (c >= ' ' && allowed.indexOf(c) < 0) {
        e.preventDefault();          // ignore letters in a number field
      }
    });
  }

  @Override
  public void setText(String text) {
    field.setText(text);
  }

  @Override
  public void allowCharacters(String allowed) {
    this.allowed = allowed;
  }

  @Override
  public void showError(String message) {
    field.setError(message);
  }

  @Override
  public void clearError() {
    field.clearError();
  }

  @Override
  public void onCommit(Consumer<String> handler) {
    field.addValueChangeHandler(e -> handler.accept(field.getText()));
    field.addKeyUpHandler(e -> {
      if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
        handler.accept(field.getText());
      }
    });
  }

  @Override
  public void onCancel(Runnable handler) {
    field.addKeyUpHandler(e -> {
      if (e.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
        handler.run();
      }
    });
  }
}
