// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import com.google.appinventor.client.core.icons.ComponentIconSource;
import java.util.Set;

/**
 * The rules behind the component tree (P5-32): the tree of the current
 * screen, selection with Shift and Ctrl, rename and delete.
 */
public class ComponentTreePresenter {

  /** Shift or Ctrl was down when a node was chosen. */
  public interface NodeHandler {
    void chosen(String id, boolean extend, boolean toggle);
  }

  /** What any layout's component tree must be able to do. */
  public interface View {
    void clear();

    /** A null parent adds a top-level node. */
    void addNode(String parentId, String id, String caption,
        ComponentIconSource icon);

    void setExpanded(String id, boolean expanded);

    void showSelection(Set<String> ids);

    /** Rename needs exactly one selected component, Delete at least one. */
    void setEditable(boolean rename, boolean delete);

    void onNodeChosen(NodeHandler handler);

    void onRename(Runnable handler);

    void onDelete(Runnable handler);
  }
}
