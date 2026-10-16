// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.editors;

import com.google.appinventor.client.core.properties.Property;

/**
 * The logic side of every property editor. Given a property and the
 * active layout's EditorViews, it creates its view, wires it, and decides
 * what may be saved. No widget code.
 *
 * @param <V> the view this model drives
 */
public abstract class PropertyEditor<V extends EditorView> {
  protected Property property;
  protected V view;

  /** Creates and wires the view; returns it so the panel can place it. */
  public final EditorView attach(Property property, EditorViews views) {
    this.property = property;
    this.view = createView(views);
    bind();
    refresh();
    return view;
  }

  /** Shows the property's current value, e.g. after undo. */
  public abstract void refresh();

  /** Which view this model needs, from the active layout's set. */
  protected abstract V createView(EditorViews views);

  /** Wires the view's events to this model. Called once by attach(). */
  protected abstract void bind();
}
