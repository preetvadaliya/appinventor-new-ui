// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

/** One option of a group; checking it unchecks the others. */
public interface Radio extends IsWidget, HasText, HasValue<Boolean> {
  /** Radios with the same group name are one group. */
  void setGroup(String group);

  void focus();
}
