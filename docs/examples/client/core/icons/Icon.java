// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.icons;

/**
 * What to draw for an icon: an image (with an optional dark version) or a
 * glyph from an icon font. A plain value, no widget code. IconView draws it.
 */
public final class Icon {

  public enum Kind { NONE, IMAGE, GLYPH }

  public static final Icon NONE = new Icon(Kind.NONE, null, null, null, null);

  private final Kind kind;
  private final String src;
  private final String darkSrc;
  private final String fontClass;
  private final String glyph;

  private Icon(Kind kind, String src, String darkSrc, String fontClass,
      String glyph) {
    this.kind = kind;
    this.src = src;
    this.darkSrc = darkSrc;
    this.fontClass = fontClass;
    this.glyph = glyph;
  }

  /** A picture, e.g. a bundled PNG or an extension's icon file. */
  public static Icon image(String src) {
    return new Icon(Kind.IMAGE, src, null, null, null);
  }

  /** A picture with a separate version for the dark theme. */
  public static Icon image(String src, String darkSrc) {
    return new Icon(Kind.IMAGE, src, darkSrc, null, null);
  }

  /**
   * A glyph from an icon font. Material Symbols use a ligature:
   * glyph("material-icons", "add"). Tabler uses a class and no text:
   * glyph("ti ti-plus", "").
   */
  public static Icon glyph(String fontClass, String glyph) {
    return new Icon(Kind.GLYPH, null, null, fontClass, glyph);
  }

  public Kind getKind() {
    return kind;
  }

  /** The image to show in the given theme. */
  public String getSrc(boolean dark) {
    return dark && darkSrc != null ? darkSrc : src;
  }

  public String getFontClass() {
    return fontClass;
  }

  public String getGlyph() {
    return glyph;
  }
}
