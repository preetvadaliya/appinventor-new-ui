// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.dialogs.project;

import static com.google.appinventor.client.core.i18n.OdeMessages.MESSAGES;

import com.google.appinventor.client.controls.Button;
import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.Dialog;
import com.google.appinventor.client.controls.Select;
import com.google.appinventor.client.controls.SharedView;
import com.google.appinventor.client.controls.TextField;
import com.google.appinventor.client.core.presenters.project.NewProjectPresenter;
import com.google.appinventor.client.core.properties.choices.Choice;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;

/**
 * The shared New Project dialog. Built from control interfaces, so it looks
 * like classic in classic and like neo in neo. Holds no logic.
 */
public class NewProjectDialog extends SharedView
    implements NewProjectPresenter.View {

  interface Binder extends UiBinder<Widget, NewProjectDialog> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField TextField name;
  @UiField Select theme;
  @UiField Select toolkit;
  @UiField Button create;
  @UiField Button cancel;

  private final Dialog dialog = Controls.active().dialog();

  public NewProjectDialog() {
    initWidget(BINDER.createAndBindUi(this));
    dialog.setCaption(MESSAGES.newYoungAndroidProjectWizardCaption());
    dialog.setContent(this);
  }

  @Override
  public void setThemes(List<Choice> themes, String selected) {
    fill(theme, themes, selected);
  }

  @Override
  public void setToolkits(List<Choice> toolkits, String selected) {
    fill(toolkit, toolkits, selected);
  }

  @Override
  public void open(String suggestedName) {
    name.setText(suggestedName);
    name.clearError();
    dialog.open();
    name.focus();
  }

  @Override
  public void showNameError(String message) {
    name.setError(message);
    name.focus();
  }

  @Override
  public void onCreate(NewProjectPresenter.CreateHandler handler) {
    Runnable submit = () -> handler.create(name.getText(),
        theme.getValue(), toolkit.getValue());
    create.addClickHandler(e -> submit.run());
    name.addKeyUpHandler(e -> {
      if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
        submit.run();
      }
    });
  }

  @Override
  public void onCancel(Runnable handler) {
    cancel.addClickHandler(e -> handler.run());
    dialog.onDismiss(handler);
  }

  @Override
  public void close() {
    dialog.close();
  }

  private static void fill(Select select, List<Choice> choices,
      String selected) {
    select.clear();
    for (Choice choice : choices) {
      select.addItem(choice.getCaption(), choice.getValue());
    }
    select.setValue(selected);
  }
}
