// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.editors;

/**
 * Makes one view per kind of editor. The shared defaults live in
 * features/properties/editors/SharedEditorViews; a layout may return its
 * own for any kind through Layout.editorViews().
 */
public interface EditorViews {
  ChoiceEditorModel.View choice();

  NumberEditorModel.View number();
}
