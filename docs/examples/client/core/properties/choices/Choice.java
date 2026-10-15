// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0


package com.google.appinventor.client.core.properties.choices;

/** One option of a choice property: what the user sees, what is saved. */
public final class Choice {
  private final String caption;
  private final String value;

  public Choice(String caption, String value) {
    this.caption = caption;
    this.value = value;
  }

  public static Choice choice(String caption, String value) {
    return new Choice(caption, value);
  }

  public String getCaption() {
    return caption;
  }

  public String getValue() {
    return value;
  }
}
