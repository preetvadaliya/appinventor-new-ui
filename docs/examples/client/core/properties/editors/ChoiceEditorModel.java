// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.editors;

import com.google.appinventor.client.core.properties.choices.Choice;
import com.google.appinventor.client.core.properties.choices.Choices;
import java.util.List;
import java.util.function.Consumer;

/**
 * Logic for every property that is one of a fixed list of options:
 * 36 editors today. Only a listed value is ever saved.
 */
public class ChoiceEditorModel
    extends PropertyEditor<ChoiceEditorModel.View> {

  /** What any choice editor view must be able to do. */
  public interface View {
    void setChoices(List<Choice> choices);

    void select(String value);

    void onPick(Consumer<String> handler);
  }

  private final Choices choices;

  public ChoiceEditorModel(Choices choices) {
    this.choices = choices;
  }

  @Override
  protected void bind() {
    view.setChoices(choices.list());
    view.onPick(value -> {
      if (choices.contains(value)) {
        property.setValue(value);   // saves and redraws the designer
      } else {
        refresh();                  // undo an impossible pick
      }
    });
    refresh();
  }

  @Override
  public void refresh() {
    view.select(property.getValue());
  }
}
