// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.appinventor.client.core.actions.ActionId;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

/** A button. Classic and neo each implement it in their own controls. */
public interface Button extends IsWidget, HasText, HasClickHandlers {

  enum Variant { DEFAULT, PRIMARY, QUIET, DANGER }

  /**
   * Binds the button to an action: it then shows the action's label and
   * icon, follows its enabled and visible state, and runs it on click.
   * UiBinder calls this for action="NEW_PROJECT" in a template.
   */
  void setAction(ActionId action);

  void setVariant(Variant variant);

  void setEnabled(boolean enabled);
}
