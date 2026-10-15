// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.choices;

import static com.google.appinventor.client.core.i18n.OdeMessages.MESSAGES;
import static com.google.appinventor.client.core.properties.choices.Choice.choice;

/** Options offered when a project is created. */
public final class ProjectChoices {
  private ProjectChoices() {
  }

  /** The same list the Screen's Theme property offers. */
  public static final Choices THEMES = Choices.of(
      choice(MESSAGES.classicTheme(), "Classic"),
      choice(MESSAGES.defaultTheme(), "AppTheme.Light.DarkActionBar"),
      choice(MESSAGES.blackTitleTheme(), "AppTheme.Light"),
      choice(MESSAGES.darkTheme(), "AppTheme"));

  public static final String DEFAULT_THEME =
      "AppTheme.Light.DarkActionBar";

  /**
   * Blocks toolkits. The real editor also lets the user upload a custom
   * toolkit file; that part is SubsetJsonEditorModel's job.
   */
  public static final Choices TOOLKITS = Choices.of(
      choice(MESSAGES.allBlocksToolkit(), ""),
      choice(MESSAGES.beginnerToolkit(), "beginner"),
      choice(MESSAGES.intermediateToolkit(), "intermediate"));

  public static final String DEFAULT_TOOLKIT = "";
}
