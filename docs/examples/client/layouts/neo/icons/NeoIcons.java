// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.icons;

import com.google.appinventor.client.core.actions.IconKey;

/** Neo draws icons with Material Symbols; this maps meanings to glyphs. */
public final class NeoIcons {
  private NeoIcons() {
  }

  public static String glyph(IconKey key) {
    switch (key) {
      case ADD:            return "add";
      case IMPORT:         return "file_open";
      case TEMPLATE:       return "cloud_download";
      case FOLDER_NEW:     return "create_new_folder";
      case MOVE:           return "drive_file_move";
      case EXPORT:         return "file_download";
      case GALLERY_LOGIN:  return "key";
      case PUBLISH:        return "public";
      case RESTORE:        return "restore";
      case PROJECTS:       return "inventory";
      case TRASH:          return "folder_delete";
      case DELETE:         return "delete";
      case DELETE_FOREVER: return "delete_forever";
      default:             return "";
    }
  }
}
