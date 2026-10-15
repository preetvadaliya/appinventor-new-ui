// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.designer.palette;

import com.google.appinventor.client.core.icons.ComponentIconSource;
import com.google.appinventor.client.core.icons.Icon;
import com.google.gwt.core.client.GWT;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The shared default component icons: the PNGs App Inventor ships today,
 * served as static files from features/designer/palette/public/palette/.
 * Any layout falls back to these, and extensions always keep their own.
 */
public final class ComponentIcons {
  private static final String BASE = GWT.getModuleBaseURL() + "palette/";
  private static final Icon GENERIC = Icon.image(BASE + "extension.png");

  /** Icons with a dark version today (DarkModeImages). */
  private static final Set<String> DARK = new HashSet<>(Arrays.asList(
      "barometer", "camcorder", "hygrometer", "linestring",
      "circularProgress", "linearProgress", "listView", "sharing",
      "translator"));

  private ComponentIcons() {
  }

  public static Icon defaultIcon(ComponentIconSource source) {
    if (source.isExtension()) {
      return Icon.image(source.getExtensionIconUrl());
    }
    String path = source.getDeclaredPath();
    if (path == null || path.isEmpty()) {
      return GENERIC;
    }
    if (path.startsWith("http:") || path.startsWith("https:")) {
      return Icon.image(path);
    }
    String name = source.getFileName().replace(".png", "");
    return DARK.contains(name)
        ? Icon.image(BASE + name + ".png", BASE + "dark/" + name + ".png")
        : Icon.image(BASE + name + ".png");
  }
}
