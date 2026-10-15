// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

/**
 * Designer or Blocks: which panes the project editor shows. Replaces the
 * four setVisible(...) calls in Ode.java:493-510 and BlocksEditor.java.
 */
public class EditorPagePresenter {

  public enum Mode { DESIGNER, BLOCKS }

  public interface View {
    void showMode(Mode mode);

    void showConsole(boolean visible);
  }

  private final View view;
  private Mode mode = Mode.DESIGNER;

  public EditorPagePresenter(View view) {
    this.view = view;
    view.showMode(mode);
  }

  public void showDesigner() {
    mode = Mode.DESIGNER;
    view.showMode(mode);
  }

  public void showBlocks() {
    mode = Mode.BLOCKS;
    view.showMode(mode);
  }

  public Mode current() {
    return mode;
  }
}
