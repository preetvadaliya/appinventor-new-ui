// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.kit;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * A dialog frame. Each layout's kit draws its own; focus trapping, Escape
 * and ARIA come from kit/behaviour/DialogA11y in every implementation.
 */
public interface Dialog {
  void setCaption(String caption);

  void setContent(IsWidget content);

  /** Called when the user presses Escape or the close button. */
  void onDismiss(Runnable handler);

  void open();

  void close();
}
