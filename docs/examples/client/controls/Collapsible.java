// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.gwt.user.client.ui.IsWidget;
import java.util.function.Consumer;

/**
 * A titled section that opens and closes its content (palette and
 * property categories). Enter and Space on the header toggle it, and
 * aria-expanded follows, in every layout.
 */
public interface Collapsible extends IsWidget {
  /** The header text; implementations override UIObject.setTitle. */
  void setTitle(String title);

  void add(IsWidget child);

  void setOpen(boolean open);

  boolean isOpen();

  void focus();

  /** Called with the new state each time the user opens or closes it. */
  void onToggle(Consumer<Boolean> handler);
}
