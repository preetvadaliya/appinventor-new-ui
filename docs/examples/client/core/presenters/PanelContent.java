// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

/**
 * Marker for a shared widget that a region hosts: the screen being
 * designed, the Blockly panel, a non-visible component. Like EditorView,
 * every implementation is also a widget; core never needs to know, and the
 * view that places it casts it to IsWidget.
 */
public interface PanelContent {
}
