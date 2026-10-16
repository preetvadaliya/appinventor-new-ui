# 0011 Property editors are models; choice lists are data

Date: 2026-10-16 · Status: accepted

## Context
There are 36 editors that extend `ChoicePropertyEditor` only to list options:
33 in `editor/youngandroid/properties/` and 3 in `widgets/properties/`
(Language, Country, Scaling). Each editor mixes value checks with
widget code.

## Decision
- Editor logic becomes models in `core/properties/editors/` (`ChoiceEditorModel`,
  `NumberEditorModel`, `TextEditorModel`, `ColorEditorModel`,
  `AssetEditorModel` …), each with a `View` interface.
- The 36 option lists become data in `core/properties/choices/`, grouped in a few
  files (`LayoutChoices`, `ScreenChoices`, `SensorChoices`, `LegoChoices`,
  `ChartChoices`, `MapChoices`, `ChatbotChoices`, `LocaleChoices`), with
  today's values and captions unchanged.
- `PropertyEditors.forType(type)` maps each editor type from
  `simple_components.json` to its model.
- One shared default view per model in `features/properties/editors/`, written
  like every other view (decision 0008): a `.ui.xml` template whose `controls:`
  elements are control interfaces, plus behaviour-only Java extending
  `SharedView`. The controls make it look right in each layout, so one
  template serves classic and neo.
- A layout replaces an editor view only when it wants a different
  arrangement, not different styling: it implements the model's `View` in
  `layouts/<layout>/views/properties/` and returns it from its own
  `EditorViews` (`Layout.editorViews()`). The model does not change.
- Wrong values are reported through `View.showError`, which shows today's
  `Window.alert` until a later ticket switches to inline errors (decision 0012).

Two exceptions: the chatbot provider and model lists come from the chat
proxy, so their loader lives outside `core/` (it does HTTP) and feeds a
`Choices` at run time; the MinSdk list is built in code from the SDK table.

## Consequences
About 36 classes become entries in 8 files. Value checks are unit-testable
without a browser.
