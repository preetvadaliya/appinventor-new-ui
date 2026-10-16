// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

/**
 * The rules behind the main menu (P3-11, P3-12). Items are bound to
 * actions in the template, so their state is not the View's business.
 */
public class MainMenuPresenter {

  /** What any layout's main menu must be able to do. */
  public interface View {
    /** The Admin menu: only for administrators. */
    void showAdminMenu(boolean visible);
  }
}
