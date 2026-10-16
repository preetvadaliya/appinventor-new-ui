// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.editors;

import com.google.appinventor.client.core.properties.choices.LayoutChoices;
import com.google.appinventor.components.common.PropertyTypeConstants;

/**
 * Which logic model edits which property type. Replaces the 140-line
 * if/else chain in PropertiesUtil.createPropertyEditor. The type names
 * are the editor types components declare in simple_components.json.
 */
public final class PropertyEditors {
  private PropertyEditors() {
  }

  public static PropertyEditor<?> forType(String type) {
    switch (type) {
      // choices: one line each instead of one class each
      case PropertyTypeConstants.PROPERTY_TYPE_HORIZONTAL_ALIGNMENT:
        return new ChoiceEditorModel(LayoutChoices.HORIZONTAL_ALIGNMENT);
      case PropertyTypeConstants.PROPERTY_TYPE_VERTICAL_ALIGNMENT:
        return new ChoiceEditorModel(LayoutChoices.VERTICAL_ALIGNMENT);
      case PropertyTypeConstants.PROPERTY_TYPE_BUTTON_SHAPE:
        return new ChoiceEditorModel(LayoutChoices.BUTTON_SHAPE);

      // numbers: one model, configured
      case PropertyTypeConstants.PROPERTY_TYPE_FLOAT:
        return NumberEditorModel.decimal();
      case PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_FLOAT:
        return NumberEditorModel.nonNegativeDecimal();
      case PropertyTypeConstants.PROPERTY_TYPE_INTEGER:
        return NumberEditorModel.integer();
      case PropertyTypeConstants.PROPERTY_TYPE_LATITUDE:
        return NumberEditorModel.range(-90, 90);
      case PropertyTypeConstants.PROPERTY_TYPE_LONGITUDE:
        return NumberEditorModel.range(-180, 180);
      case PropertyTypeConstants.PROPERTY_TYPE_UNIT_COORDINATE:
        return NumberEditorModel.range(0, 1);

      // the other choice lists and the text, color, asset, component …
      // models follow the same pattern (ticket P5-31)
      default:
        return new TextEditorModel();
    }
  }
}
