// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

/** A labelled single-line input with room for an error message. */
public interface TextField extends IsWidget, HasText, HasKeyUpHandlers,
    HasKeyPressHandlers, HasFocusHandlers, HasValueChangeHandlers<String> {
  void setLabel(String label);

  /** Templates set it as an attribute: maxLength="4". */
  void setMaxLength(int length);

  void setError(String message);

  void clearError();

  void focus();
}
