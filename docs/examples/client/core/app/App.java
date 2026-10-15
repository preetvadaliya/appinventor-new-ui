// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.app;

import com.google.appinventor.client.core.actions.ActionRegistry;
import com.google.appinventor.client.core.presenters.Views;
import com.google.appinventor.client.core.project.ProjectService;
import com.google.appinventor.client.core.session.Session;

/**
 * The few shared services the logic needs, set once at start-up. Views is
 * supplied by the active layout, which is how core opens a dialog without
 * importing any UI code.
 */
public final class App {
  private static ActionRegistry actions;
  private static Views views;
  private static ProjectService projects;
  private static Session session;

  private App() {
  }

  public static void init(ActionRegistry actions, Views views,
      ProjectService projects, Session session) {
    App.actions = actions;
    App.views = views;
    App.projects = projects;
    App.session = session;
  }

  public static ActionRegistry actions() {
    return actions;
  }

  public static Views views() {
    return views;
  }

  public static ProjectService projects() {
    return projects;
  }

  public static Session session() {
    return session;
  }
}
