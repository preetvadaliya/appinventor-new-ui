// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.actions;

import java.util.EnumMap;
import java.util.Map;

/** Holds the single instance of every action. Filled once at start-up. */
public final class ActionRegistry {
  private final Map<ActionId, Action> actions =
      new EnumMap<>(ActionId.class);

  public void register(Action action) {
    if (actions.containsKey(action.getId())) {
      throw new IllegalStateException(
          "Action registered twice: " + action.getId());
    }
    actions.put(action.getId(), action);
  }

  public Action get(ActionId id) {
    Action action = actions.get(id);
    if (action == null) {
      throw new IllegalArgumentException("No action " + id);
    }
    return action;
  }

  /** Enables or disables several actions at once. */
  public void enable(boolean enabled, ActionId... ids) {
    for (ActionId id : ids) {
      get(id).setEnabled(enabled);
    }
  }

  /** Shows or hides several actions everywhere they are placed. */
  public void show(boolean visible, ActionId... ids) {
    for (ActionId id : ids) {
      get(id).setVisible(visible);
    }
  }
}
