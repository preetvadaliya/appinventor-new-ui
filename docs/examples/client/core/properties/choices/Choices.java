// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.choices;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** An ordered, fixed list of options for one kind of property. */
public final class Choices {
  private final List<Choice> choices;

  private Choices(List<Choice> choices) {
    this.choices = Collections.unmodifiableList(choices);
  }

  public static Choices of(Choice... choices) {
    return new Choices(Arrays.asList(choices));
  }

  public List<Choice> list() {
    return choices;
  }

  public boolean contains(String value) {
    for (Choice choice : choices) {
      if (choice.getValue().equals(value)) {
        return true;
      }
    }
    return false;
  }

  /** The caption for a saved value, or the value itself if unknown. */
  public String captionOf(String value) {
    for (Choice choice : choices) {
      if (choice.getValue().equals(value)) {
        return choice.getCaption();
      }
    }
    return value;
  }
}
