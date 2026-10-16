// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.editors;

/**
 * Marker for every editor view. Each model's View extends it, so the
 * properties panel can place any editor without knowing its kind. Every
 * implementation is also a widget; core never needs to know that.
 */
public interface EditorView {
}
