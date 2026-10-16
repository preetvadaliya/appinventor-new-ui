// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.controls;

import com.google.appinventor.client.core.icons.Icon;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Behaviour of neo's tree; markup is NeoTree.ui.xml. Nodes are
 * known by id; the component tree, the media list and the block drawers
 * all fill it the same way.
 */
public class NeoTree extends Composite {

  interface Binder extends UiBinder<Widget, NeoTree> {
  }

  /** A node was chosen; Shift or Ctrl was down for a click. */
  public interface NodeHandler {
    void chosen(String id, boolean extend, boolean toggle);
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField Tree tree;

  private final Map<String, TreeItem> items = new HashMap<>();
  private NodeHandler chosen = (id, extend, toggle) -> { };
  private String clicked;

  public NeoTree() {
    initWidget(BINDER.createAndBindUi(this));
    // A click reports itself with its modifier keys; a selection that
    // follows it is the same event. Any other selection is the keyboard's.
    tree.addSelectionHandler(e -> {
      String id = (String) e.getSelectedItem().getUserObject();
      if (!id.equals(clicked)) {
        chosen.chosen(id, false, false);
      }
    });
    tree.addKeyDownHandler(e -> clicked = null);
  }

  public void clear() {
    tree.clear();
    items.clear();
    clicked = null;
  }

  /** A null parent adds a top-level node. */
  public void addNode(String parentId, String id, Icon icon,
      String caption) {
    NeoTreeNode node = new NeoTreeNode();
    node.setIcon(icon);
    node.setCaption(caption);
    node.addClickHandler(e -> {
      clicked = id;
      chosen.chosen(id, e.isShiftKeyDown(),
          e.isControlKeyDown() || e.isMetaKeyDown());
    });
    TreeItem item = new TreeItem(node);
    item.setUserObject(id);
    items.put(id, item);
    if (parentId == null) {
      tree.addItem(item);
    } else {
      items.get(parentId).addItem(item);
    }
  }

  public void setExpanded(String id, boolean expanded) {
    items.get(id).setState(expanded);
  }

  /** GWT's Tree selects one item; several are marked with a style. */
  public void showSelection(Set<String> ids) {
    for (Map.Entry<String, TreeItem> entry : items.entrySet()) {
      boolean selected = ids.contains(entry.getKey());
      entry.getValue().setStyleName("neo-TreeNode-selected", selected);
    }
  }

  public void onNodeChosen(NodeHandler handler) {
    chosen = handler;
  }
}
