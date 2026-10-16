// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

/** A box that is ticked or not, with its label. */
public interface Checkbox extends IsWidget, HasText, HasValue<Boolean> {
  /** Templates set it as an attribute: label="{messages.…}". */
  void setLabel(String label);

  void setEnabled(boolean enabled);
}
