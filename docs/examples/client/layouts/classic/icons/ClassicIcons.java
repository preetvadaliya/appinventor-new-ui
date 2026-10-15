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

/**
 * Classic: text-only buttons, and the shared PNGs (with their dark versions)
 * for components and block drawers.
 */
public class ClassicIcons implements Icons {

  @Override
  public Icon action(IconKey key) {
    return Icon.NONE;
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
