// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.nova.views.dialogs;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.IconView;
import com.google.appinventor.client.core.actions.IconKey;
import com.google.appinventor.client.core.presenters.project.NewProjectPresenter;
import com.google.appinventor.client.core.properties.choices.Choice;
import com.google.appinventor.client.layouts.nova.controls.NovaButton;
import com.google.appinventor.client.layouts.nova.controls.NovaDialog;
import com.google.appinventor.client.layouts.nova.controls.NovaSelect;
import com.google.appinventor.client.layouts.nova.controls.NovaTextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;

/**
 * Nova's own New Project dialog: icon and title, a rule, the fields, a
 * rule, then the actions. Same View as the shared dialog, so the
 * presenter (name check, project creation) does not change.
 */
public class NovaNewProjectDialog extends Composite
    implements NewProjectPresenter.View {

  interface Binder extends UiBinder<Widget, NovaNewProjectDialog> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField IconView icon;
  @UiField NovaTextField name;
  @UiField NovaSelect toolkit;
  @UiField NovaSelect theme;
  @UiField NovaButton cancel;
  @UiField NovaButton create;

  private final NovaDialog dialog = new NovaDialog();

  public NovaNewProjectDialog() {
    initWidget(BINDER.createAndBindUi(this));
    dialog.setContent(this);
    dialog.setWidth("440px");
  }

  /** The header icon is a Tabler glyph from nova's icon set. */
  @UiFactory
  IconView icon() {
    return new IconView(
        Controls.active().icons().action(IconKey.FOLDER_NEW), "");
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
    name.selectAll();
  }

  @Override
  public void showNameError(String message) {
    name.setError(message);          // red text under the field
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
    dialog.onDismiss(handler);       // Escape, or a click on the backdrop
  }

  @Override
  public void close() {
    dialog.close();
  }

  private static void fill(NovaSelect select, List<Choice> choices,
      String selected) {
    select.clear();
    for (Choice choice : choices) {
      select.addItem(choice.getCaption(), choice.getValue());
    }
    select.setValue(selected);
  }
}
