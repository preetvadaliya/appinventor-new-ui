// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.properties.choices;

import static com.google.appinventor.client.core.i18n.OdeMessages.MESSAGES;
import static com.google.appinventor.client.core.properties.choices.Choice.choice;

import com.google.appinventor.components.common.ComponentConstants;

/**
 * Option lists for layout-related properties. Each entry replaces one of
 * today's YoungAndroid*ChoicePropertyEditor classes; values are unchanged.
 */
public final class LayoutChoices {
  private LayoutChoices() {
  }

  private static final String LEFT =
      ComponentConstants.GRAVITY_LEFT + "";
  private static final String CENTER_H =
      ComponentConstants.GRAVITY_CENTER_HORIZONTAL + "";
  private static final String RIGHT =
      ComponentConstants.GRAVITY_RIGHT + "";
  private static final String TOP =
      ComponentConstants.GRAVITY_TOP + "";
  private static final String CENTER_V =
      ComponentConstants.GRAVITY_CENTER_VERTICAL + "";
  private static final String BOTTOM =
      ComponentConstants.GRAVITY_BOTTOM + "";

  /** Was YoungAndroidHorizontalAlignmentChoicePropertyEditor. */
  public static final Choices HORIZONTAL_ALIGNMENT = Choices.of(
      choice(MESSAGES.horizontalAlignmentChoiceLeft() + " : " + LEFT,
          LEFT),
      choice(MESSAGES.horizontalAlignmentChoiceCenter() + " : "
          + CENTER_H, CENTER_H),
      choice(MESSAGES.horizontalAlignmentChoiceRight() + " : " + RIGHT,
          RIGHT));

  /** Was YoungAndroidVerticalAlignmentChoicePropertyEditor. */
  public static final Choices VERTICAL_ALIGNMENT = Choices.of(
      choice(MESSAGES.verticalAlignmentChoiceTop() + " : " + TOP, TOP),
      choice(MESSAGES.verticalAlignmentChoiceCenter() + " : "
          + CENTER_V, CENTER_V),
      choice(MESSAGES.verticalAlignmentChoiceBottom() + " : " + BOTTOM,
          BOTTOM));

  /** Was YoungAndroidAlignmentChoicePropertyEditor (text alignment). */
  public static final Choices TEXT_ALIGNMENT = Choices.of(
      choice(MESSAGES.leftTextAlignment() + " : 0", "0"),
      choice(MESSAGES.centerTextAlignment() + " : 1", "1"),
      choice(MESSAGES.rightTextAlignment() + " : 2", "2"));

  /** Was YoungAndroidButtonShapeChoicePropertyEditor. */
  public static final Choices BUTTON_SHAPE = Choices.of(
      choice(MESSAGES.defaultButtonShape() + " : 0", "0"),
      choice(MESSAGES.roundedButtonShape() + " : 1", "1"),
      choice(MESSAGES.rectButtonShape() + " : 2", "2"),
      choice(MESSAGES.ovalButtonShape() + " : 3", "3"));

  /** Was YoungAndroidSizingChoicePropertyEditor. */
  public static final Choices SIZING = Choices.of(
      choice(MESSAGES.fixedSizing(), "Fixed"),
      choice(MESSAGES.responsiveSizing(), "Responsive"));

  /** Was YoungAndroidStrokeStylePropertyEditor. */
  public static final Choices STROKE_STYLE = Choices.of(
      choice(MESSAGES.strokeStyleSolid(), "1"),
      choice(MESSAGES.strokeStyleDashed(), "2"),
      choice(MESSAGES.strokeStyleDotted(), "3"));

  /** Was YoungAndroidListViewLayoutChoicePropertyEditor. */
  public static final Choices LIST_VIEW_LAYOUT = Choices.of(
      choice(MESSAGES.singleTextLayout(), "0"),
      choice(MESSAGES.twoTextLayout(), "1"),
      choice(MESSAGES.twoTextLinearLayout(), "2"),
      choice(MESSAGES.imageSingleTextLayout(), "3"),
      choice(MESSAGES.imageTwoTextLayout(), "4"),
      choice(MESSAGES.imageTopTwoTextLayout(), "5"));

  /** Was YoungAndroidRecyclerViewOrientationPropertyEditor. */
  public static final Choices RECYCLER_VIEW_ORIENTATION = Choices.of(
      choice(MESSAGES.verticalOrientation(), "0"),
      choice(MESSAGES.horisontalOrientation(), "1"));
}
