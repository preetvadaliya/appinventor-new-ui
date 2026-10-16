// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.properties.editors;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.TextField;
import com.google.appinventor.client.core.properties.editors.NumberEditorModel;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.Composite;
import java.util.function.Consumer;

/**
 * The shared view for every number property: the active layout's
 * TextField, with typing filtered and the error shown under the field.
 */
public class NumberEditorView extends Composite
    implements NumberEditorModel.View {

  private final TextField field = Controls.active().textField();
  private String allowed = "";

  public NumberEditorView() {
    initWidget(field.asWidget());
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
