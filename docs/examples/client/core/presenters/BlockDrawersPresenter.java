// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import com.google.appinventor.client.core.icons.BlockCategory;
import com.google.appinventor.client.core.icons.ComponentIconSource;
import java.util.function.Consumer;

/**
 * The rules behind the Blocks editor's drawer list (P5-43): the built-in
 * drawers, then the screen's components.
 */
public class BlockDrawersPresenter {

  /** What any layout's drawer list must be able to do. */
  public interface View {
    void clear();

    void addBuiltIn(BlockCategory category, String caption);

    /** A null parent adds a top-level component. */
    void addComponent(String parentId, String id, String caption,
        ComponentIconSource icon);

    /** Blockly's drawer name: "Control", or a component's id. */
    void onDrawerChosen(Consumer<String> handler);
  }
}
