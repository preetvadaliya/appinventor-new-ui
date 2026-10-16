// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.appinventor.client.core.actions.ActionId;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

/** One entry in a MenuButton's menu. */
public interface MenuItem extends IsWidget, HasText, HasClickHandlers {
  /**
   * Binds the item to an action: it shows the action's label, follows its
   * enabled and visible state, and runs it when chosen. Items filled in
   * Java without an action (languages) use setText and addClickHandler.
   */
  void setAction(ActionId action);

  /** variant="DANGER" for entries that destroy something. */
  void setVariant(Button.Variant variant);

  void setEnabled(boolean enabled);

  /** shortcut="Ctrl+S" in a template; hidden when empty. */
  void setShortcut(String keys);

  /** MenuKeyboard calls it to move between items. */
  void focus();
}
