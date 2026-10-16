// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import com.google.appinventor.client.core.app.App;
import com.google.appinventor.client.core.properties.EditableProperty;
import com.google.appinventor.client.core.properties.editors.EditorView;
import com.google.appinventor.client.core.properties.editors.PropertyEditor;
import com.google.appinventor.client.core.properties.editors.PropertyEditors;
import java.util.List;

/**
 * The logic behind the Properties region: when a component is selected,
 * group its visible properties by category, make the right editor for
 * each, and hand the rows to whichever layout's view is showing.
 */
public class PropertiesPresenter {

  /** What any layout's Properties region must be able to do. */
  public interface View {
    void clear();

    void showComponent(String name, String type);

    void addCategory(String category);

    /** One row: caption, optional help, and the editor to place. */
    void addRow(String category, String caption, String help,
        EditorView editor);
  }

  private final View view;

  public PropertiesPresenter(View view) {
    this.view = view;
  }

  /** Called by the designer when the selection changes. */
  public void show(String name, String type,
      List<EditableProperty> properties) {
    view.clear();
    view.showComponent(name, type);
    String category = null;
    for (EditableProperty property : properties) {
      if (!property.isVisible()) {
        continue;
      }
      if (!property.getCategory().equals(category)) {
        category = property.getCategory();
        view.addCategory(category);
      }
      PropertyEditor<?> model = PropertyEditors.forType(
          property.getEditorType());
      EditorView editor = model.attach(property,
          App.views().editorViews());
      view.addRow(category, property.getCaption(),
          property.getDescription(), editor);
    }
  }
}
