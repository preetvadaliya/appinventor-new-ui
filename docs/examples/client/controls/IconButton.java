// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.appinventor.client.core.actions.ActionId;
import com.google.appinventor.client.core.actions.IconKey;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * A button that shows only an icon (help, close). It has no visible text,
 * so every layout puts its name in aria-label and title (decision 0004).
 */
public interface IconButton extends IsWidget, HasText, HasClickHandlers {
  /**
   * Binds the button to an action: the action's icon, its label as the
   * name, its enabled and visible state, and running it on click.
   */
  void setAction(ActionId action);

  /** icon="HELP" in a template, for a button that runs no action. */
  void setIcon(IconKey icon);

  /** A longer tooltip than the name, e.g. a property's help text. */
  void setTooltip(String text);

  void setEnabled(boolean enabled);
}
