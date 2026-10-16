// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import com.google.appinventor.client.core.icons.ComponentIconSource;
import java.util.List;
import java.util.function.Consumer;

/**
 * The rules behind the palette (P5-32): search, categories, the component
 * list, extension import and starting a drag.
 */
public class PalettePresenter {

  /** One component in the palette. */
  public static final class Entry {
    private final ComponentIconSource icon;
    private final String caption;
    private final String help;

    public Entry(ComponentIconSource icon, String caption, String help) {
      this.icon = icon;
      this.caption = caption;
      this.help = help;
    }

    public String getType() {
      return icon.getType();
    }

    public ComponentIconSource getIcon() {
      return icon;
    }

    public String getCaption() {
      return caption;
    }

    /** Empty when the component has no help text. */
    public String getHelp() {
      return help;
    }
  }

  /** What any layout's palette must be able to do. */
  public interface View {
    void clear();

    void addCategory(String category);

    void addItem(String category, Entry entry);

    /** Search matches in place of the categories; null brings them back. */
    void showResults(List<Entry> matches);

    void onSearch(Consumer<String> handler);

    /** Clicked or chosen with the keyboard, by component type. */
    void onPicked(Consumer<String> handler);

    void onDragStarted(Consumer<String> handler);

    void onImportExtension(Runnable handler);
  }
}
