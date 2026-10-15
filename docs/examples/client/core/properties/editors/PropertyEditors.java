// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.editors;

import com.google.appinventor.client.core.properties.choices.LayoutChoices;
import com.google.appinventor.components.common.PropertyTypeConstants;

/**
 * Which logic model edits which property type. The type names are the
 * editor types each component declares in simple_components.json.
 */
public final class PropertyEditors {
  private PropertyEditors() {
  }

  public static PropertyEditor<?> forType(String type) {
    switch (type) {
      case PropertyTypeConstants.PROPERTY_TYPE_HORIZONTAL_ALIGNMENT:
        return new ChoiceEditorModel(LayoutChoices.HORIZONTAL_ALIGNMENT);
      case PropertyTypeConstants.PROPERTY_TYPE_VERTICAL_ALIGNMENT:
        return new ChoiceEditorModel(LayoutChoices.VERTICAL_ALIGNMENT);
      case PropertyTypeConstants.PROPERTY_TYPE_TEXTALIGNMENT:
        return new ChoiceEditorModel(LayoutChoices.TEXT_ALIGNMENT);
      case PropertyTypeConstants.PROPERTY_TYPE_BUTTON_SHAPE:
        return new ChoiceEditorModel(LayoutChoices.BUTTON_SHAPE);
      case PropertyTypeConstants.PROPERTY_TYPE_SIZING:
        return new ChoiceEditorModel(LayoutChoices.SIZING);
      case PropertyTypeConstants.PROPERTY_TYPE_STROKE_STYLE:
        return new ChoiceEditorModel(LayoutChoices.STROKE_STYLE);
      case PropertyTypeConstants.PROPERTY_TYPE_LISTVIEW_LAYOUT:
        return new ChoiceEditorModel(LayoutChoices.LIST_VIEW_LAYOUT);
      case PropertyTypeConstants.PROPERTY_TYPE_RECYCLERVIEW_ORIENTATION:
        return new ChoiceEditorModel(
            LayoutChoices.RECYCLER_VIEW_ORIENTATION);
      // The other choice lists (screen, sensors, Lego, charts, maps,
      // chatbot, locale) and the non-choice models (number, text,
      // color, asset, component …) are added the same way.
      default:
        return new TextEditorModel();
    }
  }
}
