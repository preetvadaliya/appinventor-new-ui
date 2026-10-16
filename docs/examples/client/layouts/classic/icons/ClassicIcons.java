// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.icons;

import com.google.appinventor.client.controls.Icons;
import com.google.appinventor.client.core.actions.IconKey;
import com.google.appinventor.client.core.icons.BlockCategory;
import com.google.appinventor.client.core.icons.ComponentIconSource;
import com.google.appinventor.client.core.icons.Icon;
import com.google.appinventor.client.features.blocks.BlockCategoryImages;
import com.google.appinventor.client.features.designer.palette.ComponentIcons;
import com.google.gwt.core.client.GWT;

/**
 * Classic: text-only buttons, today's two pictures for the icon-only
 * buttons (help, close), and the shared PNGs (with their dark versions)
 * for components and block drawers.
 */
public class ClassicIcons implements Icons {
  private static final String ICONS =
      GWT.getModuleBaseURL() + "classic/icons/";

  @Override
  public Icon action(IconKey key) {
    switch (key) {
      // the PNGs Images.help() and Images.close() point at today
      case HELP:  return Icon.image(ICONS + "help.png");
      case CLOSE: return Icon.image(ICONS + "close.png");
      default:    return Icon.NONE;
    }
  }

  @Override
  public Icon blockCategory(BlockCategory category) {
    return BlockCategoryImages.of(category);
  }

  @Override
  public Icon component(ComponentIconSource source) {
    return ComponentIcons.defaultIcon(source);
  }
}
