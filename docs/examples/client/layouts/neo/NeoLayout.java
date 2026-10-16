// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.core.layout.LayoutId;
import com.google.appinventor.client.layouts.Layout;
import com.google.appinventor.client.layouts.neo.controls.NeoControls;
import java.util.Arrays;
import java.util.List;

/**
 * Only the parts that matter for CSS: neo's stylesheets, in load order.
 * LayoutRegistry adds a <link> for each after the shared ones (reset.css,
 * layout.css), so neo's CSS never loads in classic.
 */
public abstract class NeoLayout implements Layout {

  @Override
  public LayoutId id() {
    return LayoutId.NEO;
  }

  @Override
  public Controls controls() {
    return new NeoControls();
  }

  @Override
  public List<String> stylesheets() {
    return Arrays.asList(
        "neo/tokens.css",          // values, light and dark
        "neo/controls.css",        // buttons, menus, inputs, dialogs
        "neo/views/shell.css",     // top bar, status bar, cards
        "neo/views/projects.css",  // projects page
        "neo/views/editor.css");   // designer and blocks pages
  }
}
