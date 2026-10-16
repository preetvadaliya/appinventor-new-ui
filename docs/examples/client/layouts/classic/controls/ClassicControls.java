// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.classic.controls;

import com.google.appinventor.client.controls.Button;
import com.google.appinventor.client.controls.Checkbox;
import com.google.appinventor.client.controls.Collapsible;
import com.google.appinventor.client.controls.Controls;
import com.google.appinventor.client.controls.Dialog;
import com.google.appinventor.client.controls.IconButton;
import com.google.appinventor.client.controls.Icons;
import com.google.appinventor.client.controls.MenuButton;
import com.google.appinventor.client.controls.MenuItem;
import com.google.appinventor.client.controls.Radio;
import com.google.appinventor.client.controls.Select;
import com.google.appinventor.client.controls.TextField;
import com.google.appinventor.client.layouts.classic.icons.ClassicIcons;

/** Classic's set: the factory that Controls.active() returns in classic. */
public class ClassicControls implements Controls {
  private final Icons icons = new ClassicIcons();

  @Override
  public Button button() {
    return new ClassicButton();
  }

  @Override
  public IconButton iconButton() {
    return new ClassicIconButton();
  }

  @Override
  public MenuButton menuButton() {
    return new ClassicMenuButton();
  }

  @Override
  public MenuItem menuItem() {
    return new ClassicMenuItem();
  }

  @Override
  public Dialog dialog() {
    return new ClassicDialog();
  }

  @Override
  public TextField textField() {
    return new ClassicTextField();
  }

  @Override
  public Select select() {
    return new ClassicSelect();
  }

  @Override
  public Checkbox checkbox() {
    return new ClassicCheckbox();
  }

  @Override
  public Radio radio() {
    return new ClassicRadio();
  }

  @Override
  public Collapsible collapsible() {
    return new ClassicCollapsible();
  }

  @Override
  public Icons icons() {
    return icons;
  }
}
