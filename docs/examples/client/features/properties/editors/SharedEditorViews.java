// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.properties.editors;

import com.google.appinventor.client.core.properties.editors.ChoiceEditorModel;
import com.google.appinventor.client.core.properties.editors.EditorViews;
import com.google.appinventor.client.core.properties.editors.LengthEditorModel;
import com.google.appinventor.client.core.properties.editors.NumberEditorModel;

/**
 * The default editor views every layout gets. A layout that wants its own
 * number field, say, extends this and overrides number() only.
 */
public class SharedEditorViews implements EditorViews {

  @Override
  public ChoiceEditorModel.View choice() {
    return new ChoiceEditorView();
  }

  @Override
  public NumberEditorModel.View number() {
    return new NumberEditorView();
  }

  @Override
  public LengthEditorModel.View length() {
    return new LengthEditorView();
  }
}
