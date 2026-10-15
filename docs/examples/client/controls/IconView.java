// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.controls;

import com.google.appinventor.client.core.icons.Icon;
import com.google.appinventor.client.core.theme.ThemeChangedEvent;
import com.google.appinventor.client.core.theme.ThemeService;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

/**
 * Draws any Icon: an <img> for images, an <i> or <span> for font glyphs.
 * Images with a dark version swap when the theme changes, without a reload.
 * Shared by every layout; layouts style it with the "icon" class.
 */
public class IconView extends Widget {
  private final String label;
  private Icon icon;
  private HandlerRegistration themeListener;

  /**
   * @param label what the icon stands for, read by screen readers when the
   *     icon is shown without text (e.g. "Button component"); empty when
   *     the icon sits next to its own text
   */
  public IconView(Icon icon, String label) {
    this.label = label;
    setElement(Document.get().createSpanElement());
    setStyleName("icon");
    setIcon(icon);
  }

  public void setIcon(Icon icon) {
    this.icon = icon;
    render();
  }

  private void render() {
    Element root = getElement();
    root.removeAllChildren();
    if (label.isEmpty()) {
      // Decorative: the surrounding control already says what it is.
      root.setAttribute("aria-hidden", "true");
    } else {
      root.setAttribute("role", "img");
      root.setAttribute("aria-label", label);
    }
    setStyleName("icon-image", icon.getKind() == Icon.Kind.IMAGE);
    setStyleName("icon-glyph", icon.getKind() == Icon.Kind.GLYPH);

    switch (icon.getKind()) {
      case IMAGE:
        ImageElement img = Document.get().createImageElement();
        img.setSrc(icon.getSrc(ThemeService.get().isDark()));
        img.setAlt("");                  // the label is on the span
        root.appendChild(img);
        break;
      case GLYPH:
        Element glyph = Document.get().createElement("i");
        glyph.setClassName(icon.getFontClass());
        glyph.setInnerText(icon.getGlyph());   // ligature fonts only
        glyph.setAttribute("aria-hidden", "true");
        root.appendChild(glyph);
        break;
      default:
        root.removeAttribute("role");
        root.removeAttribute("aria-label");
        break;
    }
  }

  @Override
  protected void onLoad() {
    themeListener = ThemeService.get().addHandler(
        (ThemeChangedEvent e) -> {
          if (icon.getKind() == Icon.Kind.IMAGE) {
            render();
          }
        });
  }

  @Override
  protected void onUnload() {
    themeListener.removeHandler();
  }
}
