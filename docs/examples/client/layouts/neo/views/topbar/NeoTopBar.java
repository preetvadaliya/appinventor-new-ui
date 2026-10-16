// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.views.topbar;

import com.google.appinventor.client.core.presenters.TopBarPresenter;
import com.google.appinventor.client.core.properties.choices.Choice;
import com.google.appinventor.client.layouts.neo.controls.NeoMenuButton;
import com.google.appinventor.client.layouts.neo.controls.NeoMenuItem;
import com.google.appinventor.client.layouts.neo.views.menus.NeoMainMenu;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import java.util.function.Consumer;

/** Neo's top bar: logo, main menu, Help, language, account. */
public class NeoTopBar extends Composite implements TopBarPresenter.View {

  interface Binder extends UiBinder<Widget, NeoTopBar> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField Image logo;
  @UiField NeoMainMenu mainMenu;
  @UiField Label readOnly;
  @UiField NeoMenuButton help;
  @UiField NeoMenuButton language;
  @UiField NeoMenuButton account;

  private Consumer<String> languageChosen = value -> { };

  public NeoTopBar() {
    initWidget(BINDER.createAndBindUi(this));
  }

  @Override
  public void showLogo(String url) {
    logo.setUrl(url);
  }

  @Override
  public void showReadOnly(boolean visible) {
    readOnly.setVisible(visible);
  }

  @Override
  public void showLanguages(List<Choice> languages, String current) {
    language.clearItems();
    for (Choice choice : languages) {
      NeoMenuItem item = new NeoMenuItem();
      item.setText(choice.getCaption());
      item.addClickHandler(e -> languageChosen.accept(choice.getValue()));
      language.addItem(item);
      if (choice.getValue().equals(current)) {
        language.setText(choice.getCaption());
      }
    }
  }

  @Override
  public void onLanguageChosen(Consumer<String> handler) {
    languageChosen = handler;
  }

  @Override
  public void showUserEmail(String email) {
    account.setText(email);
  }
}
