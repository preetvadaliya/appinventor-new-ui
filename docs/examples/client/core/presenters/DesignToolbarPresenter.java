// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import java.util.List;
import java.util.function.Consumer;

/**
 * The rules behind the design toolbar (P3-13, P4-20): the screens of the
 * project and which editor is showing. Enabled and visible state of the
 * buttons stays on their actions.
 */
public class DesignToolbarPresenter {

  /** What any layout's design toolbar must be able to do. */
  public interface View {
    void showProjectName(String name);

    void showScreens(List<String> screens);

    /** The screens menu shows the screen being designed. */
    void showCurrentScreen(String screen);

    void onScreenChosen(Consumer<String> handler);
  }
}
