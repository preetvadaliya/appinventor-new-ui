# Theming: tokens, live light/dark, icons

## Tokens become CSS custom properties

Today the tokens are GWT `@def` constants compiled into a `ClientBundle`, so
light and dark are two different compiled bundles and switching needs a reload.
GWT 2.8.1's CSS parser predates custom properties, so tokens move out of GWT
entirely, into static files each layout serves from its public folder:

```css
/* layouts/neo/public/neo/tokens.css */
:root {
  --color-surface: #ffffff;
  --color-text: #333333;
  --color-primary: #a5cf47;
}
:root[data-theme="dark"] {
  --color-surface: #111111;
  --color-text: #aaaaaa;
}
```

The rules files (`variableColors.css`, `neo.css`, later `views.css` and
`kit.css`) refer to tokens with `var(--name)`. Both rules files already declare
`@external .*;`, so class names are unobfuscated today and nothing in the
templates changes.

## Switching without a reload

```java
// core/theme/ThemeService.java
public void setMode(ThemeMode mode) {   // LIGHT, DARK, SYSTEM
  apply(resolve(mode));                 // data-theme on <html>
  settings.save(mode);                  // no reload
  bus.fire(new ThemeChangedEvent(resolve(mode)));
}
```

- `SYSTEM` follows `prefers-color-scheme` through `matchMedia` and updates when
  the OS setting changes.
- CSS repaints at once. Anything not drawn by CSS listens for
  `ThemeChangedEvent`:
  - Blockly: `workspace.setTheme(…)` instead of picking the theme only at
    `AI.inject` (`BlocksEditor.java:649`, `BlocklyPanel.java:759`).
  - `DataVisualizerPanel` (the CloudDB table dialog) toggles its `cdbviz-dark`
    class, which today it sets only once (`DataVisualizerPanel.java:130, 658`).
- The appearance dialog (today `UISettingsWizard`) calls `ThemeService` and no
  longer saves-and-reloads for light/dark.

## Images and icons

Live switching cannot swap a PNG `ImageResource` through CSS. Two steps:

1. **Bridge (P2-07).** `ThemedImage` holds the light and dark resource and swaps
   on `ThemeChangedEvent`. It replaces the 25 places in 17 files that pick an
   image once (20 `Ode.getImageBundle()` calls in 16 files and 5 `GWT.create`
   picks).
2. **Icon sets (P4-22).** Each layout gets its own icon set keyed by
   `IconKey`: classic mostly draws text (plus a few glyphs and PNGs it shows
   today), neo maps keys to Material Symbols. Icons that
   are glyphs or SVG with `currentColor` follow the theme with no swap at all.

Palette icons for App Inventor components (Button, Label, Canvas …) come from a
shared default set, `ComponentIcons`; a layout overrides them per icon. Neo
does today for about 85 of them.

## Icons are meanings, not glyph names

An action names an `IconKey` (`ADD`, `IMPORT`, `FOLDER_NEW` …), never a font
glyph. Each layout maps keys to its own glyphs:

```java
// layouts/neo/icons/NeoIcons.java
case ADD:        return "add";        // Material
case FOLDER_NEW: return "create_new_folder";
```

A later layout can use a different icon font (for example Tabler,
`ti ti-plus`) without touching any action. Its font files ship in its own
public folder and load only for that layout. A unit test checks that every
`IconKey` is mapped by every layout that draws icons.

If a layout shows an icon without text, the kit puts the action's label into
`aria-label` and the tooltip, so icon-only buttons stay accessible.
