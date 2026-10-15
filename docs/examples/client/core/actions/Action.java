// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * One command: its label, icon and state, and what it does. Every button
 * or menu item bound to an action updates itself when the state changes.
 * No GWT widget code here.
 */
public abstract class Action {
  private final ActionId id;
  private final IconKey icon;
  private String label;
  private boolean enabled = true;
  private boolean visible = true;
  private final List<Runnable> listeners = new ArrayList<>();

  protected Action(ActionId id, String label, IconKey icon) {
    this.id = id;
    this.label = label;
    this.icon = icon;
  }

  /** Runs the command. Called only while the action is enabled. */
  public abstract void run();

  public ActionId getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public IconKey getIcon() {
    return icon;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setLabel(String label) {
    if (!label.equals(this.label)) {
      this.label = label;
      changed();
    }
  }

  public void setEnabled(boolean enabled) {
    if (enabled != this.enabled) {
      this.enabled = enabled;
      changed();
    }
  }

  public void setVisible(boolean visible) {
    if (visible != this.visible) {
      this.visible = visible;
      changed();
    }
  }

  /** Called after every change of label, enabled or visible. */
  public void onChange(Runnable listener) {
    listeners.add(listener);
  }

  private void changed() {
    for (Runnable listener : listeners) {
      listener.run();
    }
  }
}
