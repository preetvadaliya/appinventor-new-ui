// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0


package com.google.appinventor.client.controls;

/**
 * The building blocks a layout supplies: one factory method per control.
 * ClassicControls and NeoControls implement it; the active layout's set is
 * installed once at start-up and shared screens ask Controls.active().
 */
public interface Controls {
  Button button();

  IconButton iconButton();

  MenuButton menuButton();

  MenuItem menuItem();

  Dialog dialog();

  TextField textField();

  Select select();

  Checkbox checkbox();

  Radio radio();

  Collapsible collapsible();

  /** The layout's icon set: images, glyphs, or both. */
  Icons icons();

  /** Called once by LayoutRegistry with the chosen layout's controls. */
  static void install(Controls controls) {
    Holder.active = controls;
  }

  static Controls active() {
    if (Holder.active == null) {
      throw new IllegalStateException("No layout installed yet");
    }
    return Holder.active;
  }

  /** Interfaces cannot hold mutable state, so a small holder does. */
  final class Holder {
    private static Controls active;

    private Holder() {
    }
  }
}
