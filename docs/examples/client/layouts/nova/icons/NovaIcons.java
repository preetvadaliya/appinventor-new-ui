// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.nova.icons;

import com.google.appinventor.client.controls.Icons;
import com.google.appinventor.client.core.actions.IconKey;
import com.google.appinventor.client.core.icons.BlockCategory;
import com.google.appinventor.client.core.icons.ComponentIconSource;
import com.google.appinventor.client.core.icons.Icon;
import com.google.appinventor.client.features.designer.palette.ComponentIcons;
import java.util.HashMap;
import java.util.Map;

/**
 * Nova (later): Tabler outline glyphs wherever it has one. Components it
 * has no glyph for, and every extension, keep their image, so a palette
 * row may show a glyph or a picture and both look right.
 */
public class NovaIcons implements Icons {
  private static final Map<String, String> COMPONENTS = new HashMap<>();

  static {
    COMPONENTS.put("Button", "ti-click");
    COMPONENTS.put("CheckBox", "ti-checkbox");
    COMPONENTS.put("DatePicker", "ti-calendar");
    COMPONENTS.put("Image", "ti-photo");
    COMPONENTS.put("Label", "ti-letter-t");
    COMPONENTS.put("ListPicker", "ti-list-check");
    COMPONENTS.put("ListView", "ti-list");
    COMPONENTS.put("PasswordTextBox", "ti-password");
    COMPONENTS.put("Slider", "ti-adjustments-horizontal");
    COMPONENTS.put("Spinner", "ti-selector");
    COMPONENTS.put("Switch", "ti-toggle-left");
    COMPONENTS.put("TextBox", "ti-forms");
    COMPONENTS.put("TimePicker", "ti-clock");
    COMPONENTS.put("WebViewer", "ti-world");
    COMPONENTS.put("HorizontalArrangement", "ti-layout-columns");
    COMPONENTS.put("VerticalArrangement", "ti-layout-rows");
    COMPONENTS.put("TableArrangement", "ti-table");
    COMPONENTS.put("Camera", "ti-camera");
    COMPONENTS.put("Player", "ti-player-play");
    COMPONENTS.put("Sound", "ti-volume");
    COMPONENTS.put("SoundRecorder", "ti-microphone");
    COMPONENTS.put("Canvas", "ti-brush");
    COMPONENTS.put("Ball", "ti-ball-basketball");
    COMPONENTS.put("Clock", "ti-alarm");
    COMPONENTS.put("Notifier", "ti-message-circle");
    COMPONENTS.put("TinyDB", "ti-database");
    COMPONENTS.put("Web", "ti-cloud");
    COMPONENTS.put("BluetoothClient", "ti-bluetooth");
    COMPONENTS.put("Map", "ti-map");
    COMPONENTS.put("Marker", "ti-map-pin");
  }

  private static Icon tabler(String name) {
    return Icon.glyph("ti " + name, "");
  }

  @Override
  public Icon action(IconKey key) {
    switch (key) {
      case ADD:            return tabler("ti-plus");
      case IMPORT:         return tabler("ti-file-import");
      case TEMPLATE:       return tabler("ti-template");
      case FOLDER_NEW:     return tabler("ti-folder-plus");
      case MOVE:           return tabler("ti-arrows-move");
      case EXPORT:         return tabler("ti-download");
      case GALLERY_LOGIN:  return tabler("ti-key");
      case PUBLISH:        return tabler("ti-world-upload");
      case RESTORE:        return tabler("ti-restore");
      case PROJECTS:       return tabler("ti-folders");
      case TRASH:          return tabler("ti-trash");
      case DELETE:         return tabler("ti-trash");
      case DELETE_FOREVER: return tabler("ti-trash-x");
      case FOLDER:         return tabler("ti-folder");
      case EXPAND:         return tabler("ti-chevron-right");
      case COLLAPSE:       return tabler("ti-chevron-down");
      default:             return Icon.NONE;
    }
  }

  @Override
  public Icon blockCategory(BlockCategory category) {
    switch (category) {
      case CONTROL:       return tabler("ti-arrows-split");
      case LOGIC:         return tabler("ti-toggle-right");
      case MATH:          return tabler("ti-math");
      case MATRICES:      return tabler("ti-grid-dots");
      case TEXT:          return tabler("ti-letter-case");
      case LISTS:         return tabler("ti-list-numbers");
      case DICTIONARIES:  return tabler("ti-book");
      case COLORS:        return tabler("ti-palette");
      case VARIABLES:     return tabler("ti-variable");
      case PROCEDURES:    return tabler("ti-function");
      case ANY_COMPONENT: return tabler("ti-components");
      default:            return Icon.NONE;
    }
  }

  @Override
  public Icon component(ComponentIconSource source) {
    String glyph = source.isExtension()
        ? null : COMPONENTS.get(source.getType());
    return glyph != null
        ? tabler(glyph)
        : ComponentIcons.defaultIcon(source);    // picture fallback
  }
}
