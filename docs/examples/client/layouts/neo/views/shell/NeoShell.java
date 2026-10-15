// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.shell;

import com.google.appinventor.client.core.presenters.ShellPresenter;
import com.google.appinventor.client.features.tutorial.TutorialPanel;
import com.google.appinventor.client.layouts.neo.views.admin.NeoAdminPage;
import com.google.appinventor.client.layouts.neo.views.editor.NeoEditorPage;
import com.google.appinventor.client.layouts.neo.views.projects.NeoProjectsPage;
import com.google.appinventor.client.layouts.neo.views.status.NeoStatusBar;
import com.google.appinventor.client.layouts.neo.views.topbar.NeoTopBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Neo's page frame: top bar, one page at a time, status bar. */
public class NeoShell extends Composite implements ShellPresenter.View {

  interface Binder extends UiBinder<Widget, NeoShell> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField NeoTopBar topBar;
  @UiField NeoProjectsPage projectsPage;
  @UiField NeoEditorPage editorPage;
  @UiField NeoAdminPage adminPage;
  @UiField TutorialPanel tutorial;
  @UiField NeoStatusBar statusBar;

  public NeoShell() {
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

  public NeoProjectsPage projectsPage() {
    return projectsPage;
  }

  public NeoEditorPage editorPage() {
    return editorPage;
  }
}
