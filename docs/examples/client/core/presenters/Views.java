// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import com.google.appinventor.client.core.presenters.project.NewProjectPresenter;

/**
 * The views the logic can ask for. The active layout implements this:
 * frame views always come from the layout, shared dialogs come from
 * features/ unless the layout replaces them.
 */
public interface Views {
  ProjectToolbarPresenter.View projectToolbar();

  NewProjectPresenter.View newProjectDialog();
}
