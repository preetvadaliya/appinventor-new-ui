// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.kit;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;

/** A drop-down list of captions with values. */
public interface Select extends IsWidget, HasChangeHandlers {
  void clear();

  void addItem(String caption, String value);

  String getValue();

  void setValue(String value);

  void setLabel(String label);
}
