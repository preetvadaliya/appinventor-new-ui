// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

/** What any layout's status bar must be able to do. */
public interface StatusBarView {
  /** The privacy and terms link; its text comes from the template. */
  void showTermsLink(String url);
}
