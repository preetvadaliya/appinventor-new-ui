// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.icons;

import com.google.appinventor.client.controls.Icons;
import com.google.appinventor.client.core.actions.IconKey;
import com.google.appinventor.client.core.icons.BlockCategory;
import com.google.appinventor.client.core.icons.ComponentIconSource;
import com.google.appinventor.client.core.icons.Icon;
import com.google.appinventor.client.features.blocks.BlockCategoryImages;
import com.google.appinventor.client.features.designer.palette.ComponentIcons;
import com.google.gwt.core.client.GWT;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Neo: Material Symbols for buttons, its own PNGs (light and dark) for
 * the 84 components it redraws today, and the shared PNGs for the rest.
 */
public class NeoIcons implements Icons {
  private static final String MATERIAL = "material-icons";
  private static final String PALETTE =
      GWT.getModuleBaseURL() + "neo/palette/";

  /** Components neo has its own light image for (ImagesNeo today). */
  private static final Set<String> LIGHT = new HashSet<>(Arrays.asList(
      "absoluteArrangement", "accelerometersensor", "activityStarter",
      "anomaly", "arduino", "ball", "barcodeScanner", "barometer",
      "bluetooth", "button", "camcorder", "camera", "canvas", "chart",
      "chartData", "chatbot", "checkbox", "circle", "circularProgress",
      "clock", "cloudDB", "contactPicker", "dataFile", "datePicker",
      "emailPicker", "featurecollection", "filepicker", "firebaseDB",
      "gyroscopesensor", "horizontal", "hygrometer", "image", "imagePicker",
      "imageSprite", "label", "legoMindstormsEv3", "legoMindstormsNxt",
      "lightsensor", "linearProgress", "linestring", "listPicker",
      "listView", "locationSensor", "magneticSensor", "map", "marker",
      "navigation", "nearfield", "notifier", "orientationsensor",
      "paintpalette", "passwordtextbox", "pedometer", "phoneCall",
      "phoneNumberPicker", "player", "polygon", "proximitysensor",
      "radiobutton", "rectangle", "recyclerView", "regression", "sharing",
      "slider", "soundEffect", "soundRecorder", "speechRecognizer",
      "spinner", "spreadsheet", "switch", "textToSpeech", "textbox",
      "texting", "thermometer", "timePicker", "tinyDB", "tinyWebDB",
      "translator", "trendline", "twitter", "vertical", "videoPlayer",
      "web", "webviewer"));

  /** Components neo has its own dark image for (DarkModeImagesNeo). */
  private static final Set<String> DARK = new HashSet<>(Arrays.asList(
      "absoluteArrangement", "accelerometersensor", "activityStarter",
      "anomaly", "arduino", "ball", "barcodeScanner", "barometer",
      "bluetooth", "button", "camcorder", "camera", "canvas", "chart",
      "chartData", "chatbot", "checkbox", "circle", "circularProgress",
      "clock", "cloudDB", "contactPicker", "dataFile", "datePicker",
      "emailPicker", "featurecollection", "filepicker", "firebaseDB",
      "fusiontables", "gyroscopesensor", "horizontal", "hygrometer",
      "image", "imagePicker", "imageSprite", "label", "legoMindstormsEv3",
      "legoMindstormsNxt", "lightsensor", "linearProgress", "linestring",
      "listPicker", "listView", "locationSensor", "magneticSensor", "map",
      "marker", "navigation", "nearfield", "notifier", "orientationsensor",
      "paintpalette", "passwordtextbox", "pedometer", "phoneCall",
      "phoneNumberPicker", "player", "polygon", "proximitysensor",
      "radiobutton", "rectangle", "recyclerView", "regression", "sharing",
      "slider", "soundEffect", "soundRecorder", "speechRecognizer",
      "spinner", "spreadsheet", "switch", "textToSpeech", "textbox",
      "texting", "thermometer", "timePicker", "tinyDB", "tinyWebDB",
      "translator", "trendline", "twitter", "vertical", "videoPlayer",
      "web", "webviewer"));

  @Override
  public Icon action(IconKey key) {
    switch (key) {
      case ADD:            return Icon.glyph(MATERIAL, "add");
      case IMPORT:         return Icon.glyph(MATERIAL, "file_open");
      case TEMPLATE:       return Icon.glyph(MATERIAL, "cloud_download");
      case FOLDER_NEW:     return Icon.glyph(MATERIAL, "create_new_folder");
      case MOVE:           return Icon.glyph(MATERIAL, "drive_file_move");
      case EXPORT:         return Icon.glyph(MATERIAL, "file_download");
      case GALLERY_LOGIN:  return Icon.glyph(MATERIAL, "key");
      case PUBLISH:        return Icon.glyph(MATERIAL, "public");
      case RESTORE:        return Icon.glyph(MATERIAL, "restore");
      case PROJECTS:       return Icon.glyph(MATERIAL, "inventory");
      case TRASH:          return Icon.glyph(MATERIAL, "folder_delete");
      case DELETE:         return Icon.glyph(MATERIAL, "delete");
      case DELETE_FOREVER: return Icon.glyph(MATERIAL, "delete_forever");
      case HELP:           return Icon.glyph(MATERIAL, "help");
      case CLOSE:          return Icon.glyph(MATERIAL, "close");
      default:             return Icon.NONE;
    }
  }

  @Override
  public Icon blockCategory(BlockCategory category) {
    return BlockCategoryImages.of(category);   // same images as classic
  }

  @Override
  public Icon component(ComponentIconSource source) {
    if (source.isExtension()) {
      return ComponentIcons.defaultIcon(source);
    }
    String name = source.getFileName().replace(".png", "");
    if (!LIGHT.contains(name)) {
      return ComponentIcons.defaultIcon(source);
    }
    String light = PALETTE + "light/" + name + ".png";
    return DARK.contains(name)
        ? Icon.image(light, PALETTE + "dark/" + name + ".png")
        : Icon.image(light);
  }
}
