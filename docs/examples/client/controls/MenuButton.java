// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.appinventor.client.core.actions.ActionId;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * A button that opens a menu of MenuItems. Keyboard, aria-haspopup and
 * aria-expanded come from controls/behaviour/MenuKeyboard in every layout.
 * Child elements in a template (<controls:ClassicMenuItem>, a separator)
 * reach addItem and addSeparator through ActionItemParser (P5-23).
 */
public interface MenuButton extends IsWidget, HasText {
  /**
   * action="EXPORT": the button shows the action's label and follows its
   * enabled and visible state; a click opens the menu, it never runs it.
   */
  void setAction(ActionId action);

  void setVariant(Button.Variant variant);

  void setEnabled(boolean enabled);

  void addItem(MenuItem item);

  void addSeparator();

  /** For menus filled in Java: languages, the project's screens. */
  void clearItems();
}
