// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.shell;

import com.google.appinventor.client.core.presenters.ShellPresenter;
import com.google.appinventor.client.features.tutorial.TutorialPanel;
import com.google.appinventor.client.layouts.classic.views.admin.ClassicAdminPage;
import com.google.appinventor.client.layouts.classic.views.editor.ClassicEditorPage;
import com.google.appinventor.client.layouts.classic.views.projects.ClassicProjectsPage;
import com.google.appinventor.client.layouts.classic.views.status.ClassicStatusBar;
import com.google.appinventor.client.layouts.classic.views.topbar.ClassicTopBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Classic's page frame: top bar, one page at a time, status bar. */
public class ClassicShell extends Composite implements ShellPresenter.View {

  interface Binder extends UiBinder<Widget, ClassicShell> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField ClassicTopBar topBar;
  @UiField ClassicProjectsPage projectsPage;
  @UiField ClassicEditorPage editorPage;
  @UiField ClassicAdminPage adminPage;
  @UiField TutorialPanel tutorial;
  @UiField ClassicStatusBar statusBar;

  public ClassicShell() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void showPage(ShellPresenter.Page page) {
    projectsPage.setVisible(page == ShellPresenter.Page.PROJECTS);
    editorPage.setVisible(page == ShellPresenter.Page.EDITOR);
    adminPage.setVisible(page == ShellPresenter.Page.ADMIN);
  }

  @Override
  public void showTutorial(boolean visible) {
    tutorial.setVisible(visible);
  }

  public ClassicProjectsPage projectsPage() {
    return projectsPage;
  }

  public ClassicEditorPage editorPage() {
    return editorPage;
  }
}
