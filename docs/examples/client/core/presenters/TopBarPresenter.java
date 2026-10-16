// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import com.google.appinventor.client.core.properties.choices.Choice;
import java.util.List;
import java.util.function.Consumer;

/**
 * The rules behind the top bar (P4-19). Hiding Delete account or the
 * project links is an action's visibility, never a widget name, so the
 * View has no method for it.
 */
public class TopBarPresenter {

  /** What any layout's top bar must be able to do. */
  public interface View {
    void showLogo(String url);

    void showReadOnly(boolean readOnly);

    /** The language menu; the server supplies the list. */
    void showLanguages(List<Choice> languages, String current);

    void onLanguageChosen(Consumer<String> handler);

    /** The account menu's caption. */
    void showUserEmail(String email);
  }
}
