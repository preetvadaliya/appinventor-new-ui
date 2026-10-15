// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.kit.behaviour;

import com.google.appinventor.client.core.actions.Action;
import com.google.appinventor.client.core.actions.ActionId;
import com.google.appinventor.client.core.app.App;
import java.util.function.Consumer;

/**
 * Connects a kit control to an action, so each kit's button only has to
 * draw itself. Written once for every layout.
 */
public final class ActionBinding {
  private ActionBinding() {
  }

  /**
   * Draws the control now and after every change of the action, and
   * returns the action so the caller can run it on click.
   */
  public static Action bind(ActionId id, Consumer<Action> render) {
    Action action = App.actions().get(id);
    action.onChange(() -> render.accept(action));
    render.accept(action);
    return action;
  }

  /** Runs the action if it is still enabled when the click arrives. */
  public static void click(Action action) {
    if (action.isEnabled()) {
      action.run();
    }
  }
}
