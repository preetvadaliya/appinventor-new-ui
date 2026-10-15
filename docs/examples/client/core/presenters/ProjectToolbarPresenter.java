// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import static com.google.appinventor.client.core.actions.ActionId.DELETE_FOREVER;
import static com.google.appinventor.client.core.actions.ActionId.DELETE_PROJECT;
import static com.google.appinventor.client.core.actions.ActionId.EXPORT_PROJECT;
import static com.google.appinventor.client.core.actions.ActionId.LOGIN_TO_GALLERY;
import static com.google.appinventor.client.core.actions.ActionId.MOVE_PROJECTS;
import static com.google.appinventor.client.core.actions.ActionId.NEW_PROJECT;
import static com.google.appinventor.client.core.actions.ActionId.RESTORE_PROJECT;
import static com.google.appinventor.client.core.actions.ActionId.SEND_TO_GALLERY;
import static com.google.appinventor.client.core.i18n.OdeMessages.MESSAGES;

import com.google.appinventor.client.core.actions.ActionRegistry;
import com.google.appinventor.client.core.app.App;
import com.google.appinventor.client.core.project.ProjectSelection;
import com.google.appinventor.client.core.session.Session;

/**
 * The rules behind the project toolbar, for every layout: what is enabled,
 * what the Export item says, and whether the toolbar is in trash mode.
 */
public class ProjectToolbarPresenter {

  /** What any project toolbar must be able to do. */
  public interface View {
    void showTitle(String title);

    /** Switches between the project-list buttons and the trash buttons. */
    void showTrash(boolean trash);
  }

  private final View view;
  private final ActionRegistry actions;
  private final ProjectSelection selection;
  private final Session session;

  public ProjectToolbarPresenter(View view, ProjectSelection selection) {
    this.view = view;
    this.actions = App.actions();
    this.selection = selection;
    this.session = App.session();
    selection.onChange(this::update);
    showGallery();
  }

  public void showProjects() {
    view.showTitle(MESSAGES.projectsTabName());
    view.showTrash(false);
    update();
  }

  public void showTrash() {
    view.showTitle(MESSAGES.trashButton());
    view.showTrash(true);
    update();
  }

  private void update() {
    int selected = selection.count();
    boolean readOnly = session.isReadOnly();

    actions.enable(!readOnly, NEW_PROJECT);
    actions.enable(selected > 0, MOVE_PROJECTS);
    actions.enable(!readOnly && selected > 0,
        DELETE_PROJECT, RESTORE_PROJECT, DELETE_FOREVER);

    actions.get(EXPORT_PROJECT).setLabel(selected > 1
        ? MESSAGES.exportSelectedProjectsMenuItem(selected)
        : MESSAGES.exportProjectMenuItem());
  }

  private void showGallery() {
    boolean gallery = session.isGalleryEnabled();
    actions.show(gallery, LOGIN_TO_GALLERY);
    actions.show(gallery && !session.isGalleryReadOnly(),
        SEND_TO_GALLERY);
  }
}
