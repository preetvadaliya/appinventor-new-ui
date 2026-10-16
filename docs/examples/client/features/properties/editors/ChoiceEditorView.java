// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.properties.editors;

import com.google.appinventor.client.controls.Select;
import com.google.appinventor.client.controls.SharedView;
import com.google.appinventor.client.core.properties.choices.Choice;
import com.google.appinventor.client.core.properties.editors.ChoiceEditorModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import java.util.function.Consumer;

/**
 * The shared view for every choice property. The template's <controls:Select>
 * is ClassicSelect in classic and NeoSelect in neo.
 */
public class ChoiceEditorView extends SharedView
    implements ChoiceEditorModel.View {

  interface Binder extends UiBinder<Widget, ChoiceEditorView> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField
  Select select;

  public ChoiceEditorView() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void setChoices(List<Choice> choices) {
    select.clear();
    for (Choice choice : choices) {
      select.addItem(choice.getCaption(), choice.getValue());
    }
  }

  @Override
  public void select(String value) {
    select.setValue(value);
  }

  @Override
  public void onPick(Consumer<String> handler) {
    select.addChangeHandler(e -> handler.accept(select.getValue()));
  }
}
