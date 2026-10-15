// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.nova;

import com.google.appinventor.client.core.presenters.project.NewProjectPresenter;
import com.google.appinventor.client.layouts.Layout;
import com.google.appinventor.client.layouts.nova.views.dialogs.NovaNewProjectDialog;

/**
 * Only the part of NovaLayout that matters here: nova replaces the shared
 * New Project dialog with its own. Classic and neo do not override this
 * method, so they keep features/dialogs/project/NewProjectDialog.
 */
public abstract class NovaLayout implements Layout {

  @Override
  public NewProjectPresenter.View newProjectDialog() {
    return new NovaNewProjectDialog();
  }
}
