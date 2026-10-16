// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import com.google.appinventor.client.core.properties.choices.Choice;
import java.util.List;
import java.util.function.Consumer;

/**
 * The rules behind the viewer (P5-43): preview size and style, hidden
 * components, the non-visible components, the screen being designed.
 */
public class ViewerPresenter {

  /** What any layout's viewer must be able to do. */
  public interface View {
    void setDeviceSizes(List<Choice> sizes, String selected);

    void setPreviewStyles(List<Choice> styles, String selected);

    void setShowHidden(boolean show);

    /** The designed screen (a MockForm); features/ owns it. */
    void showScreen(PanelContent screen);

    /** The strip under the phone; no components, no heading. */
    void showNonVisible(List<PanelContent> components);

    void onDeviceSizeChosen(Consumer<String> handler);

    void onPreviewStyleChosen(Consumer<String> handler);

    void onShowHiddenChanged(Consumer<Boolean> handler);
  }
}
