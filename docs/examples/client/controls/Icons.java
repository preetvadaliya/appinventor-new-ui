// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.appinventor.client.core.actions.IconKey;
import com.google.appinventor.client.core.icons.BlockCategory;
import com.google.appinventor.client.core.icons.ComponentIconSource;
import com.google.appinventor.client.core.icons.Icon;

/**
 * A layout's icon set. Every method may answer with an image or a glyph;
 * IconView draws either. Classic and neo answer mostly with images, a
 * layout like nova with Tabler glyphs.
 */
public interface Icons {

  /** Toolbar and menu icons. Return Icon.NONE to show text only. */
  Icon action(IconKey key);

  /** The built-in drawers in the Blocks editor's list. */
  Icon blockCategory(BlockCategory category);

  /**
   * A component in the palette, the component tree, the non-visible
   * components strip and the Blocks editor's list. Extensions must always
   * get an icon: if the layout has none of its own, use the extension's.
   */
  Icon component(ComponentIconSource source);
}
