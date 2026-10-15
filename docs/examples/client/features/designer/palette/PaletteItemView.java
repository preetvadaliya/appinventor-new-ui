// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.designer.palette;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.IconView;
import com.google.appinventor.client.core.icons.ComponentIconSource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;

/**
 * One row of the component palette: icon and name. The same code shows a
 * PNG in classic and neo and a Tabler glyph in nova; an extension's own
 * picture works in all three.
 */
public class PaletteItemView extends Composite {

  public PaletteItemView(ComponentIconSource source, String caption) {
    FlowPanel row = new FlowPanel();
    row.setStyleName("palette-Item");
    IconView icon = new IconView(
        Controls.active().icons().component(source), "");
    icon.addStyleName("palette-Item-icon");
    row.add(icon);
    row.add(new InlineLabel(caption));
    initWidget(row);
    getElement().setAttribute("data-component", source.getType());
  }
}
