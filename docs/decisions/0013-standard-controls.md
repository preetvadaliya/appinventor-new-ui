# 0013 StandardControls is a bridge, deleted in P7

Date: 2026-10-16 · Status: accepted (decided in C-05)

## Context
P5-23 and P5-25 add `controls/standard/StandardControls`, which wraps today's
widgets (`TextButton`, `DropDownButton`, `LabeledTextBox` …) so views can move
to control contracts without any visual change. P5-27 has classic's and neo's
first dialogs and inputs extend those standard pieces to keep the DOM
identical. Once both layouts have their own controls, the standard set has no
job, and keeping it means a third way to build a control that must stay
compiled and tested.

## Decision
- `controls/standard/` is a bridge for P5 only. While it exists, a layout's
  control may extend or delegate to a `Standard*` class.
- By the end of P7 every layout supplies every control as its own template
  plus behaviour-only Java (decision 0008), and `controls/standard/` is
  deleted with the old widgets that only it used (P7-44).
- Shared keyboard, focus and ARIA code lives in `controls/behaviour/`, so a
  layout's control reuses behaviour without extending anything.
- A new layout (nova) writes its own controls; it never extends a standard
  piece or another layout's control.

## Consequences
P7-44 gives the remaining extended pieces their own templates and deletes
`controls/standard/`; P7-40's import test also fails on any import of
`controls.standard`. A new layout has about 15 small controls to write before
its first screen; in return each layout's markup is entirely its own.
