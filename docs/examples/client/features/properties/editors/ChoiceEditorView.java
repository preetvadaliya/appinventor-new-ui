// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.properties.editors;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.Select;
import com.google.appinventor.client.core.properties.choices.Choice;
import com.google.appinventor.client.core.properties.editors.ChoiceEditorModel;
import com.google.gwt.user.client.ui.Composite;
import java.util.List;
import java.util.function.Consumer;

/**
 * The shared view for every choice property. Uses the active layout's
 * Select, so it is ClassicSelect in classic and NeoSelect in neo.
 */
public class ChoiceEditorView extends Composite
    implements ChoiceEditorModel.View {

  private final Select select = Controls.active().select();

  public ChoiceEditorView() {
    initWidget(select.asWidget());
    setStyleName("properties-ChoiceEditor");
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
