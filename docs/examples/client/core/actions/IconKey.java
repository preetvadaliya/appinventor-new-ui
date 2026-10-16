// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0


package com.google.appinventor.client.core.actions;

/**
 * What an icon means, never which glyph draws it. Each layout maps these
 * keys to its own icons; classic draws text and ignores most of them.
 */
public enum IconKey {
  NONE,
  ADD, IMPORT, TEMPLATE, FOLDER_NEW, MOVE, EXPORT, GALLERY_LOGIN,
  PUBLISH, RESTORE, PROJECTS, TRASH, DELETE, DELETE_FOREVER,
  // not actions: the icon-only buttons
  HELP, CLOSE
}
