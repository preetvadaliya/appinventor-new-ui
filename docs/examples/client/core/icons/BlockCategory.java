// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.icons;

/** The built-in drawers of the Blocks editor, in the order they appear. */
public enum BlockCategory {
  CONTROL("Control"),
  LOGIC("Logic"),
  MATH("Math"),
  MATRICES("Matrices"),
  TEXT("Text"),
  LISTS("Lists"),
  DICTIONARIES("Dictionaries"),
  COLORS("Colors"),
  VARIABLES("Variables"),
  PROCEDURES("Procedures"),
  ANY_COMPONENT("AnyComponent");

  private final String drawerName;

  BlockCategory(String drawerName) {
    this.drawerName = drawerName;
  }

  /** The name Blockly and the project files use for this drawer. */
  public String getDrawerName() {
    return drawerName;
  }
}
