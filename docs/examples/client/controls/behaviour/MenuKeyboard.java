// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls.behaviour;

import com.google.appinventor.client.controls.MenuItem;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;

/**
 * Opens, closes and drives a drop-down menu, the same for every layout's
 * menu button. The trigger is a real <button>: Enter and Space click it
 * and the menu button calls toggle(); Down opens it too. Up, Down, Home
 * and End move between enabled items; Escape closes it, Tab leaves it.
 * aria-expanded on the trigger follows the menu.
 *
 * A minimal version for the examples: P5-23 extracts the real one from
 * DropDownButton (lines 156-247), with type-ahead.
 */
public final class MenuKeyboard {
  private final Widget trigger;
  private final PopupPanel menu;
  private final List<? extends MenuItem> items;
  private HandlerRegistration preview;
  private int current;

  /** items is the menu button's live list; it may grow after this. */
  public MenuKeyboard(Widget trigger, PopupPanel menu,
      List<? extends MenuItem> items) {
    this.trigger = trigger;
    this.menu = menu;
    this.items = items;
    trigger.getElement().setAttribute("aria-expanded", "false");
    // a click on the trigger closes an open menu instead of reopening it
    menu.addAutoHidePartner(trigger.getElement());
    menu.addCloseHandler(e -> closed());
    trigger.addDomHandler(e -> {
      if (e.getNativeKeyCode() == KeyCodes.KEY_DOWN) {
        e.preventDefault();
        open();
      }
    }, KeyDownEvent.getType());
  }

  public void toggle() {
    if (menu.isShowing()) {
      close();
    } else {
      open();
    }
  }

  /** Shows the menu under the trigger and focuses its first item. */
  public void open() {
    menu.showRelativeTo(trigger);
    trigger.getElement().setAttribute("aria-expanded", "true");
    if (preview == null) {
      // added on open and removed on close, so none is left behind
      preview = Event.addNativePreviewHandler(this::onKey);
    }
    move(0, 1);
  }

  /**
   * Hides the menu. Focus goes back to the trigger, unless what the
   * chosen item opened (a dialog) has taken it already.
   */
  public void close() {
    boolean focusInMenu = menu.getElement().isOrHasChild(activeElement());
    menu.hide();
    if (focusInMenu) {
      trigger.getElement().focus();
    }
  }

  /** After every hide, also when a click elsewhere auto-hides it. */
  private void closed() {
    trigger.getElement().setAttribute("aria-expanded", "false");
    if (preview != null) {
      preview.removeHandler();
      preview = null;
    }
  }

  private void onKey(NativePreviewEvent event) {
    if (event.getTypeInt() != Event.ONKEYDOWN) {
      return;
    }
    switch (event.getNativeEvent().getKeyCode()) {
      case KeyCodes.KEY_DOWN:   move(current + 1, 1); break;
      case KeyCodes.KEY_UP:     move(current - 1, -1); break;
      case KeyCodes.KEY_HOME:   move(0, 1); break;
      case KeyCodes.KEY_END:    move(items.size() - 1, -1); break;
      case KeyCodes.KEY_ESCAPE: close(); break;
      case KeyCodes.KEY_TAB:    menu.hide(); return;   // focus moves on
      default:                  return;   // Enter and Space reach the item
    }
    event.getNativeEvent().preventDefault();
    event.cancel();
  }

  /** Focuses the first usable item from index on, wrapping around. */
  private void move(int index, int step) {
    int count = items.size();
    for (int i = 0; i < count; i++) {
      int at = ((index + i * step) % count + count) % count;
      MenuItem item = items.get(at);
      if (item.isEnabled() && item.asWidget().isVisible()) {
        current = at;
        item.focus();
        return;
      }
    }
  }

  private static native Element activeElement() /*-{
    return $doc.activeElement;
  }-*/;
}
