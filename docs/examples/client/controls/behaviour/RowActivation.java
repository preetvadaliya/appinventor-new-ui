// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls.behaviour;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Makes a clickable row act like a button: a click, Enter or Space runs it.
 * Written once for every layout. Clicks and keys that start inside a
 * control nested in the row (a checkbox, a help button) are left to it.
 */
public final class RowActivation {
  private RowActivation() {
  }

  public static void on(FocusPanel row, Runnable activated,
      IsWidget... nested) {
    row.addClickHandler(e -> {
      if (!inside(e.getNativeEvent(), nested)) {
        activated.run();
      }
    });
    row.addKeyDownHandler(e -> {
      int key = e.getNativeKeyCode();
      if ((key == KeyCodes.KEY_ENTER || key == ' ')
          && !inside(e.getNativeEvent(), nested)) {
        e.preventDefault();   // Space must not scroll the list
        activated.run();
      }
    });
  }

  private static boolean inside(NativeEvent event, IsWidget[] nested) {
    Element target = Element.as(event.getEventTarget());
    for (IsWidget widget : nested) {
      if (widget.asWidget().getElement().isOrHasChild(target)) {
        return true;
      }
    }
    return false;
  }
}
