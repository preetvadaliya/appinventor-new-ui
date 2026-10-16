// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.editors;

import static com.google.appinventor.client.core.i18n.OdeMessages.MESSAGES;

import java.util.function.Consumer;

/**
 * Logic for every number property. Replaces FloatPropertyEditor,
 * IntegerPropertyEditor, NonNegativeFloatPropertyEditor,
 * NonNegativeIntegerPropertyEditor, YoungAndroidFloatRangePropertyEditor
 * and YoungAndroidIntegerRangePropertyEditor: one class, configured.
 */
public class NumberEditorModel
    extends PropertyEditor<NumberEditorModel.View> {

  /** What any number editor view must be able to do. */
  public interface View extends EditorView {
    void setText(String text);

    /** Which characters the field accepts while typing. */
    void allowCharacters(String allowed);

    void showError(String message);

    void clearError();

    /** Enter, blur or change: the user is done typing. */
    void onCommit(Consumer<String> handler);

    /** Escape: throw the typing away. */
    void onCancel(Runnable handler);
  }

  private final boolean decimals;
  private final double min;
  private final double max;

  private NumberEditorModel(boolean decimals, double min, double max) {
    this.decimals = decimals;
    this.min = min;
    this.max = max;
  }

  public static NumberEditorModel decimal() {
    return new NumberEditorModel(true, -Double.MAX_VALUE, Double.MAX_VALUE);
  }

  public static NumberEditorModel nonNegativeDecimal() {
    return new NumberEditorModel(true, 0, Double.MAX_VALUE);
  }

  public static NumberEditorModel integer() {
    return new NumberEditorModel(false, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  public static NumberEditorModel nonNegativeInteger() {
    return new NumberEditorModel(false, 0, Integer.MAX_VALUE);
  }

  /** Latitude is range(-90, 90); longitude range(-180, 180). */
  public static NumberEditorModel range(double min, double max) {
    return new NumberEditorModel(true, min, max);
  }

  @Override
  protected View createView(EditorViews views) {
    return views.number();
  }

  @Override
  protected void bind() {
    view.allowCharacters(decimals ? "0123456789+-." : "0123456789+-");
    view.onCommit(text -> {
      String error = check(text);
      if (error == null) {
        view.clearError();
        property.setValue(text.trim());
      } else {
        view.showError(error);       // today: Window.alert
        refresh();                   // keep the last good value
      }
    });
    view.onCancel(this::refresh);
  }

  @Override
  public void refresh() {
    view.setText(property.getValue());
  }

  /** Null when the text is a valid value; otherwise the message to show. */
  String check(String text) {
    double value;
    try {
      value = decimals ? Double.parseDouble(text.trim())
          : Integer.parseInt(text.trim());
    } catch (NumberFormatException e) {
      return decimals ? MESSAGES.notAFloat(text) : MESSAGES.notAnInteger(text);
    }
    if (value < min || value > max) {
      return MESSAGES.valueNotInRange(text, format(min), format(max));
    }
    return null;
  }

  private String format(double bound) {
    return decimals ? Double.toString(bound) : Long.toString((long) bound);
  }
}
