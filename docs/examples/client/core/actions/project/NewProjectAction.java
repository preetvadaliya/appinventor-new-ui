// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.actions.project;

import static com.google.appinventor.client.core.i18n.OdeMessages.MESSAGES;

import com.google.appinventor.client.core.actions.Action;
import com.google.appinventor.client.core.actions.ActionId;
import com.google.appinventor.client.core.actions.IconKey;
import com.google.appinventor.client.core.app.App;
import com.google.appinventor.client.core.presenters.project.NewProjectPresenter;

/** Opens the New Project dialog. Shared by every layout. */
public class NewProjectAction extends Action {

  public NewProjectAction() {
    super(ActionId.NEW_PROJECT, MESSAGES.newProjectButton(),
        IconKey.ADD);
  }

  @Override
  public void run() {
    // Disabled while the dialog is open, so it cannot be opened twice.
    setEnabled(false);
    new NewProjectPresenter(App.views().newProjectDialog())
        .start(() -> setEnabled(!App.session().isReadOnly()));
  }
}
