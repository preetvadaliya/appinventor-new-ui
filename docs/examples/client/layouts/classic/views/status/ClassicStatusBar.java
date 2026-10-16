// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.views.status;

import com.google.appinventor.client.core.presenters.StatusBarView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/** Classic's status bar: the privacy and terms link. */
public class ClassicStatusBar extends Composite implements StatusBarView {

  interface Binder extends UiBinder<Widget, ClassicStatusBar> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField Anchor terms;

  public ClassicStatusBar() {
    initWidget(BINDER.createAndBindUi(this));
    terms.getElement().setAttribute("rel", "noopener");   // target is _blank
  }

  @Override
  public void showTermsLink(String url) {
    terms.setHref(url);
  }
}
