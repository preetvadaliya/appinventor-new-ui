// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.core.layout.LayoutId;
import com.google.appinventor.client.layouts.Layout;
import com.google.appinventor.client.layouts.classic.controls.ClassicControls;
import java.util.Arrays;
import java.util.List;

/**
 * Only the parts that matter for CSS: classic's stylesheets, in load
 * order. LayoutRegistry adds a <link> for each after the shared ones
 * (reset.css, layout.css), so classic's CSS never loads in neo.
 */
public abstract class ClassicLayout implements Layout {

  @Override
  public LayoutId id() {
    return LayoutId.CLASSIC;
  }

  @Override
  public Controls controls() {
    return new ClassicControls();
  }

  @Override
  public List<String> stylesheets() {
    return Arrays.asList(
        "classic/tokens.css",          // values, light and dark
        "classic/controls.css",        // buttons, menus, inputs, dialogs
        "classic/views/shell.css",     // top bar, status bar, panes
        "classic/views/projects.css",  // projects page
        "classic/views/editor.css");   // designer and blocks pages
  }
}
