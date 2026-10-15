// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.blocks;

import com.google.appinventor.client.core.icons.BlockCategory;
import com.google.appinventor.client.core.icons.Icon;
import com.google.gwt.core.client.GWT;

/**
 * The shared default images for the Blocks editor's built-in drawers,
 * today's control.png, logic.png …, served from features/blocks/public/.
 */
public final class BlockCategoryImages {
  private static final String BASE = GWT.getModuleBaseURL() + "blocks/";

  private BlockCategoryImages() {
  }

  public static Icon of(BlockCategory category) {
    if (category == BlockCategory.ANY_COMPONENT) {
      return Icon.NONE;                  // shown as text today
    }
    return Icon.image(BASE + category.name().toLowerCase() + ".png");
  }
}
