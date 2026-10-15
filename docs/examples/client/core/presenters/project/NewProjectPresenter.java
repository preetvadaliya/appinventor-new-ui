// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters.project;

import com.google.appinventor.client.core.app.App;
import com.google.appinventor.client.core.properties.choices.Choice;
import com.google.appinventor.client.core.properties.choices.ProjectChoices;
import com.google.appinventor.client.core.validation.TextValidators;
import java.util.List;

/**
 * The logic of the New Project dialog: suggest a name, check it, create
 * the project and open it. Draws nothing; any View can be plugged in.
 */
public class NewProjectPresenter {

  /** What a New Project dialog must be able to do. */
  public interface View {
    void setThemes(List<Choice> themes, String selected);

    void setToolkits(List<Choice> toolkits, String selected);

    void open(String suggestedName);

    void showNameError(String message);

    void onCreate(CreateHandler handler);

    void onCancel(Runnable handler);

    void close();
  }

  /** What the user filled in. */
  public interface CreateHandler {
    void create(String name, String theme, String toolkit);
  }

  private final View view;

  public NewProjectPresenter(View view) {
    this.view = view;
  }

  /** Opens the dialog; calls done when it closes either way. */
  public void start(Runnable done) {
    view.setThemes(ProjectChoices.THEMES.list(),
        ProjectChoices.DEFAULT_THEME);
    view.setToolkits(ProjectChoices.TOOLKITS.list(),
        ProjectChoices.DEFAULT_TOOLKIT);

    view.onCreate((rawName, theme, toolkit) -> {
      String name = normalise(rawName);
      if (TextValidators.checkNewProjectName(name)
          != TextValidators.ProjectNameStatus.SUCCESS) {
        view.showNameError(TextValidators.getErrorMessage(rawName));
        return;
      }
      view.close();
      App.projects().createYoungAndroidProject(name, theme, toolkit,
          project -> App.projects().openInDesigner(project));
      done.run();
    });

    view.onCancel(() -> {
      view.close();
      done.run();
    });

    view.open(App.projects().nextUntitledName());
  }

  /** "my  app" becomes "my_app", as the wizard does today. */
  static String normalise(String name) {
    return name.trim().replaceAll("( )+", " ").replace(" ", "_");
  }
}
