// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.features.properties.editors;

import com.google.appinventor.client.controls.Button;
import com.google.appinventor.client.controls.Radio;
import com.google.appinventor.client.controls.SharedView;
import com.google.appinventor.client.controls.TextField;
import com.google.appinventor.client.core.properties.editors.LengthEditorModel;
import com.google.appinventor.client.core.properties.editors.LengthEditorModel.Kind;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The shared view for Width and Height. Markup is in the template; this
 * class only opens and closes the popup and keeps radios and fields in
 * step. Which values are valid is LengthEditorModel's business.
 */
public class LengthEditorView extends SharedView
    implements LengthEditorModel.View {

  interface Binder extends UiBinder<Widget, LengthEditorView> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  // Each editor needs its own group, or all Width editors share one.
  private static int groups = 0;

  @UiField Button summary;
  @UiField HTMLPanel form;
  @UiField Radio automatic;
  @UiField Radio fillParent;
  @UiField Radio pixels;
  @UiField TextField pixelsAmount;
  @UiField HTMLPanel percentRow;
  @UiField Radio percent;
  @UiField TextField percentAmount;
  @UiField Label error;
  @UiField Button cancel;
  @UiField Button ok;

  // Until the controls have a popover, the form opens in a PopupPanel
  // under the summary button, as AdditionalChoicePropertyEditor does.
  private final PopupPanel popup = new PopupPanel(true);

  public LengthEditorView() {
    initWidget(BINDER.createAndBindUi(this));
    popup.setWidget(form);
    String group = "LengthType-" + (++groups);
    for (Radio radio : new Radio[] {automatic, fillParent, pixels, percent}) {
      radio.setGroup(group);
    }

    summary.addClickHandler(e -> {
      popup.showRelativeTo(summary.asWidget());
      automatic.focus();
    });
    // Picking a fixed option empties both fields; typing in a field
    // checks its radio and empties the other field.
    automatic.addValueChangeHandler(e -> clearAmounts());
    fillParent.addValueChangeHandler(e -> clearAmounts());
    pixelsAmount.addFocusHandler(e -> pick(pixels, percentAmount));
    percentAmount.addFocusHandler(e -> pick(percent, pixelsAmount));
    cancel.addClickHandler(e -> close());
  }

  @Override
  public void showPercent(boolean shown) {
    percentRow.setVisible(shown);
  }

  @Override
  public void setSummary(String text) {
    summary.setText(text);
  }

  @Override
  public void show(Kind kind, String amount) {
    radioFor(kind).setValue(true);
    pixelsAmount.setText(kind == Kind.PIXELS ? amount : "");
    percentAmount.setText(kind == Kind.PERCENT ? amount : "");
    error.setVisible(false);
  }

  @Override
  public Kind kind() {
    for (Kind kind : Kind.values()) {
      if (radioFor(kind).getValue()) {
        return kind;
      }
    }
    return Kind.AUTOMATIC;
  }

  @Override
  public String amount() {
    switch (kind()) {
      case PIXELS:
        return pixelsAmount.getText();
      case PERCENT:
        return percentAmount.getText();
      default:
        return "";
    }
  }

  @Override
  public void showError(String message) {
    error.setText(message);
    error.setVisible(true);
  }

  @Override
  public void close() {
    popup.hide();
  }

  @Override
  public void onOk(Runnable handler) {
    ok.addClickHandler(e -> handler.run());
  }

  @Override
  public void onCancel(Runnable handler) {
    cancel.addClickHandler(e -> handler.run());
  }

  private Radio radioFor(Kind kind) {
    switch (kind) {
      case FILL_PARENT:
        return fillParent;
      case PIXELS:
        return pixels;
      case PERCENT:
        return percent;
      default:
        return automatic;
    }
  }

  private void pick(Radio radio, TextField other) {
    if (!radio.getValue()) {
      radio.setValue(true);
      other.setText("");
    }
  }

  private void clearAmounts() {
    pixelsAmount.setText("");
    percentAmount.setText("");
  }
}
