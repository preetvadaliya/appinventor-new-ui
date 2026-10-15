// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.icons;

/**
 * Everything known about a component's icon before a layout picks one:
 * its type, the icon path it declares in simple_components.json
 * ("iconName"), and for an extension the URL of the icon it ships.
 */
public final class ComponentIconSource {
  private final String type;
  private final String declaredPath;
  private final String extensionIconUrl;

  public ComponentIconSource(String type, String declaredPath,
      String extensionIconUrl) {
    this.type = type;
    this.declaredPath = declaredPath;
    this.extensionIconUrl = extensionIconUrl;
  }

  /** The component type, e.g. "Button" or "com.example.MyExtension". */
  public String getType() {
    return type;
  }

  /** e.g. "images/button.png"; for extensions "aiwebres/icon.png". */
  public String getDeclaredPath() {
    return declaredPath;
  }

  /** The file name of the declared icon, e.g. "button.png". */
  public String getFileName() {
    return declaredPath.substring(declaredPath.lastIndexOf('/') + 1);
  }

  /** Where the extension's own icon is served, or null if built in. */
  public String getExtensionIconUrl() {
    return extensionIconUrl;
  }

  public boolean isExtension() {
    return extensionIconUrl != null;
  }
}
