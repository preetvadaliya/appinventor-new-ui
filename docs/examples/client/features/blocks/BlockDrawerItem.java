// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.blocks;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.IconView;
import com.google.appinventor.client.core.icons.BlockCategory;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;

/**
 * One built-in drawer in the Blocks editor's list (Control, Logic, Math …).
 * Replaces the HTML string BlockSelectorBox builds today around an <img>.
 */
public class BlockDrawerItem extends Composite {

  public BlockDrawerItem(BlockCategory category, String caption) {
    FlowPanel row = new FlowPanel();
    row.setStyleName("blocks-Drawer");
    IconView icon = new IconView(
        Controls.active().icons().blockCategory(category), "");
    icon.addStyleName("blocks-Drawer-icon");
    row.add(icon);
    row.add(new InlineLabel(caption));
    initWidget(row);
    getElement().setAttribute("data-name",
        "builtin_" + category.getDrawerName());
  }
}
