// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

/** Which page the IDE shows, and whether the tutorial is open. */
public class ShellPresenter {

  public enum Page { PROJECTS, EDITOR, ADMIN }

  public interface View {
    void showPage(Page page);

    void showTutorial(boolean visible);
  }

  private final View view;
  private Page page = Page.PROJECTS;

  public ShellPresenter(View view) {
    this.view = view;
    view.showPage(page);
  }

  public void show(Page next) {
    if (next != page) {
      page = next;
      view.showPage(next);
    }
  }

  public Page current() {
    return page;
  }
}
