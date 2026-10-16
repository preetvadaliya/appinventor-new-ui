// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.editors;

import static com.google.appinventor.client.core.i18n.OdeMessages.MESSAGES;

/**
 * Logic for Width and Height. Replaces the checks and value encoding in
 * YoungAndroidLengthPropertyEditor (245 lines); the radios, fields and
 * popup are the view's business.
 *
 * Stored values, unchanged from today: "-1" automatic, "-2" fill parent,
 * "120" pixels, and percent as -1000 - n, so 50 percent is "-1050".
 */
public class LengthEditorModel
    extends PropertyEditor<LengthEditorModel.View> {

  public enum Kind { AUTOMATIC, FILL_PARENT, PIXELS, PERCENT }

  /** What any length editor view must be able to do. */
  public interface View extends EditorView {
    /** ImageSprite has no percent option. */
    void showPercent(boolean shown);

    /** The closed editor's text, e.g. "120 pixels". */
    void setSummary(String summary);

    /** Checks one option; amount goes in that option's field. */
    void show(Kind kind, String amount);

    Kind kind();

    /** The text in the checked option's field; "" for the others. */
    String amount();

    void showError(String message);

    void close();

    void onOk(Runnable handler);

    void onCancel(Runnable handler);
  }

  // Same numbers as MockVisibleComponent.LENGTH_*; core cannot import it.
  static final String AUTOMATIC = "-1";
  static final String FILL_PARENT = "-2";
  static final int PERCENT_TAG = -1000;

  private final boolean percent;

  private LengthEditorModel(boolean percent) {
    this.percent = percent;
  }

  public static LengthEditorModel withPercent() {
    return new LengthEditorModel(true);
  }

  /** MockImageSprite's Width and Height. */
  public static LengthEditorModel pixelsOnly() {
    return new LengthEditorModel(false);
  }

  @Override
  protected View createView(EditorViews views) {
    return views.length();
  }

  @Override
  protected void bind() {
    view.showPercent(percent);
    view.onOk(this::save);
    view.onCancel(this::refresh);
  }

  @Override
  public void refresh() {
    String value = property.getValue();
    view.show(kindOf(value), amountOf(value));
    view.setSummary(summary(value));
  }

  private void save() {
    String error = check(view.kind(), view.amount());
    if (error != null) {
      view.showError(error);       // today: Window.alert, dialog stays open
      return;
    }
    property.setValue(encode(view.kind(), view.amount()));
    view.close();
  }

  static Kind kindOf(String value) {
    if (value.equals(AUTOMATIC)) {
      return Kind.AUTOMATIC;
    } else if (value.equals(FILL_PARENT)) {
      return Kind.FILL_PARENT;
    }
    return Integer.parseInt(value) <= PERCENT_TAG ? Kind.PERCENT : Kind.PIXELS;
  }

  static String amountOf(String value) {
    switch (kindOf(value)) {
      case PIXELS:
        return value;
      case PERCENT:
        return Integer.toString(PERCENT_TAG - Integer.parseInt(value));
      default:
        return "";
    }
  }

  /** Null when the amount fits the kind; otherwise the message to show. */
  static String check(Kind kind, String amount) {
    if (kind == Kind.PIXELS && !isWhole(amount, Integer.MAX_VALUE)) {
      return MESSAGES.nonnumericInputError();
    }
    if (kind == Kind.PERCENT && !isWhole(amount, 100)) {
      return MESSAGES.nonvalidPercentValue();
    }
    return null;
  }

  static String encode(Kind kind, String amount) {
    switch (kind) {
      case AUTOMATIC:
        return AUTOMATIC;
      case FILL_PARENT:
        return FILL_PARENT;
      case PERCENT:
        return Integer.toString(PERCENT_TAG - Integer.parseInt(amount.trim()));
      default:
        return amount.trim();
    }
  }

  static String summary(String value) {
    switch (kindOf(value)) {
      case AUTOMATIC:
        return MESSAGES.automaticCaption();
      case FILL_PARENT:
        return MESSAGES.fillParentCaption();
      case PERCENT:
        return MESSAGES.percentSummary(amountOf(value));
      default:
        return MESSAGES.pixelsSummary(value);
    }
  }

  private static boolean isWhole(String text, int max) {
    try {
      int n = Integer.parseInt(text.trim());
      return n >= 0 && n <= max;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
