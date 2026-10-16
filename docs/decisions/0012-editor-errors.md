# 0012 Property editors keep Window.alert until a ticket may change the look

Date: 2026-10-16 · Status: accepted (decided in C-04)

## Context
Every property editor that checks its value reports a wrong one with the
browser's `Window.alert` (for example `YoungAndroidLengthPropertyEditor`:
`MESSAGES.nonnumericInputError()`, `MESSAGES.nonvalidPercentValue()`). The
example models already report through their view
(`NumberEditorModel.View.showError`, `LengthEditorModel.View.showError`), so
an inline error under the field would be easy, but it changes what users see,
and P0–P7 promise no visual change.

## Decision
- Through P7, a shared editor view's `showError(message)` calls
  `Window.alert(message)`. Users see exactly what they see today.
- Models never call `Window.alert` themselves; they always go through
  `View.showError`, so switching to inline errors later changes views only.
- Inline errors (`TextField.setError`, a `properties-Error` label) come in a
  later ticket that is allowed to change the look, agreed with MIT.

## Consequences
P5-30 and P5-31 build the alert behaviour and verify it. The later switch is a
change to `features/properties/editors/` (or a layout's own editor views) and
needs no model change.
