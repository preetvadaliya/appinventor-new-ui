// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The rules behind the project list (P4-17): sorting, selection, folder
 * expand and collapse. Draws nothing; any layout's list can be plugged in.
 */
public class ProjectListPresenter {

  public enum SortField { NAME, CREATED, MODIFIED }

  /** A project, or a folder with its children. */
  public static final class Node {
    private final String id;
    private final String name;
    private final long created;
    private final long modified;
    private final boolean folder;
    private final List<Node> children;

    private Node(String id, String name, long created, long modified,
        boolean folder, List<Node> children) {
      this.id = id;
      this.name = name;
      this.created = created;
      this.modified = modified;
      this.folder = folder;
      this.children = children;
    }

    public static Node project(String id, String name, long created,
        long modified) {
      return new Node(id, name, created, modified, false,
          Collections.<Node>emptyList());
    }

    public static Node folder(String id, String name, long created,
        long modified, List<Node> children) {
      return new Node(id, name, created, modified, true,
          new ArrayList<>(children));
    }

    public String getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    /** Milliseconds since the epoch; the view formats them. */
    public long getCreated() {
      return created;
    }

    public long getModified() {
      return modified;
    }

    public boolean isFolder() {
      return folder;
    }

    public List<Node> getChildren() {
      return children;
    }
  }

  /** What any layout's project list must be able to do. */
  public interface View {
    /** Draws the root's children; an empty root shows the empty message. */
    void render(Node root);

    void setSelected(String id, boolean selected);

    void setExpanded(String id, boolean expanded);

    void setSelectAll(boolean selected);

    void showSort(SortField field, boolean ascending);

    void onChecked(BiConsumer<String, Boolean> handler);

    void onExpandToggled(Consumer<String> handler);

    void onOpen(Consumer<String> handler);

    void onSelectAllChecked(Consumer<Boolean> handler);

    void onSortClicked(Consumer<SortField> handler);
  }
}
