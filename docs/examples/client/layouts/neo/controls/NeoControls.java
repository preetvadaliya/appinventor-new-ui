// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.layouts.neo.controls;

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
import com.google.appinventor.client.layouts.neo.icons.NeoIcons;

/** Neo's set: the factory that Controls.active() returns in neo. */
public class NeoControls implements Controls {
  private final Icons icons = new NeoIcons();

  @Override
  public Button button() {
    return new NeoButton();
  }

  @Override
  public IconButton iconButton() {
    return new NeoIconButton();
  }

  @Override
  public MenuButton menuButton() {
    return new NeoMenuButton();
  }

  @Override
  public MenuItem menuItem() {
    return new NeoMenuItem();
  }

  @Override
  public Dialog dialog() {
    return new NeoDialog();
  }

  @Override
  public TextField textField() {
    return new NeoTextField();
  }

  @Override
  public Select select() {
    return new NeoSelect();
  }

  @Override
  public Checkbox checkbox() {
    return new NeoCheckbox();
  }

  @Override
  public Radio radio() {
    return new NeoRadio();
  }

  @Override
  public Collapsible collapsible() {
    return new NeoCollapsible();
  }

  @Override
  public Icons icons() {
    return icons;
  }
}
