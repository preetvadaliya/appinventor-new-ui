// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.editor;

import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.IconView;
import com.google.appinventor.client.controls.behaviour.RowActivation;
import com.google.appinventor.client.core.actions.IconKey;
import com.google.appinventor.client.core.icons.Icon;
import com.google.appinventor.client.core.presenters.PalettePresenter.Entry;
import com.google.appinventor.client.layouts.classic.controls.ClassicIconButton;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

/** One component in the palette; markup in ClassicPaletteItem.ui.xml. */
public class ClassicPaletteItem extends Composite {

  interface Binder extends UiBinder<Widget, ClassicPaletteItem> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField FocusPanel item;
  @UiField(provided = true) IconView icon = new IconView(Icon.NONE, "");
  @UiField InlineLabel name;
  @UiField ClassicIconButton help;

  public ClassicPaletteItem() {
    initWidget(BINDER.createAndBindUi(this));
    item.getElement().setDraggable("true");
    help.setIcon(IconKey.HELP);
  }

  public void show(Entry entry) {
    icon.setIcon(Controls.active().icons().component(entry.getIcon()));
    name.setText(entry.getCaption());
    help.setText(entry.getCaption() + " help");   // as the property rows do
    help.setTooltip(entry.getHelp());
    help.setVisible(entry.getHelp() != null && !entry.getHelp().isEmpty());
  }

  /** Click, Enter or Space on the item, not on its help button. */
  public void onPicked(Runnable handler) {
    RowActivation.on(item, handler, help);
  }

  public void onDragStarted(Runnable handler) {
    item.addDomHandler(e -> handler.run(), DragStartEvent.getType());
  }
}
