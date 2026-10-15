// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.controls;

import com.google.appinventor.client.controls.Button;
import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.IconView;
import com.google.appinventor.client.controls.behaviour.ActionBinding;
import com.google.appinventor.client.core.actions.Action;
import com.google.appinventor.client.core.actions.ActionId;
import com.google.appinventor.client.core.icons.Icon;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;

/** Neo's button: an icon from neo's icon set and a label. */
public class NeoButton extends Composite implements Button {
  private final FocusPanel root = new FocusPanel();
  private final IconView icon = new IconView(Icon.NONE, "");
  private final InlineLabel label = new InlineLabel();
  private String captionOverride;
  private boolean enabled = true;

  public NeoButton() {
    HTMLPanel inner = new HTMLPanel("");
    icon.addStyleName("neo-Button-icon");
    label.setStyleName("neo-Button-label");
    inner.add(icon);
    inner.add(label);
    root.setWidget(inner);
    initWidget(root);
    setStyleName("neo-Button");
    Element el = root.getElement();
    el.setAttribute("role", "button");
  }

  @Override
  public void setAction(ActionId id) {
    Action action = ActionBinding.bind(id, this::render);
    root.addClickHandler(e -> ActionBinding.click(action));
  }

  private void render(Action action) {
    String text = captionOverride != null
        ? captionOverride : action.getLabel();
    label.setText(text);
    icon.setIcon(Controls.active().icons().action(action.getIcon()));
    // An icon without text still needs a name for screen readers.
    root.getElement().setAttribute("aria-label", text);
    root.setTitle(text);
    setEnabled(action.isEnabled());
    setVisible(action.isVisible());
  }

  @Override
  public void setVariant(Variant variant) {
    setStyleName("neo-Button-primary", variant == Variant.PRIMARY);
    setStyleName("neo-Button-quiet", variant == Variant.QUIET);
    setStyleName("neo-Button-danger", variant == Variant.DANGER);
  }

  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    setStyleName("neo-Button-disabled", !enabled);
    root.getElement().setAttribute("aria-disabled",
        String.valueOf(!enabled));
    root.setTabIndex(enabled ? 0 : -1);
  }

  @Override
  public String getText() {
    return label.getText();
  }

  @Override
  public void setText(String text) {
    captionOverride = text;
    label.setText(text);
  }

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return root.addClickHandler(e -> {
      if (enabled) {
        handler.onClick(e);
      }
    });
  }
}
