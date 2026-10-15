// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.editors;

import com.google.appinventor.client.core.properties.Property;

/**
 * The logic side of every property editor. A model is given the property
 * it edits and a view to draw it; it decides what may be saved.
 *
 * @param <V> the view this model drives
 */
public abstract class PropertyEditor<V> {
  protected Property property;
  protected V view;

  /** Connects the model to a property and a view, then draws. */
  public final void attach(Property property, V view) {
    this.property = property;
    this.view = view;
    bind();
  }

  /** Shows the property's current value in the view. */
  public abstract void refresh();

  /** Wires the view's events to this model. Called once by attach(). */
  protected abstract void bind();
}
