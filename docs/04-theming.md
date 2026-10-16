# Theming: tokens, live light/dark, icons

## Tokens become CSS custom properties

Today the tokens are GWT `@def` constants compiled into a `ClientBundle`, so
light and dark are two different compiled bundles and switching needs a reload.
GWT 2.8.1's CSS parser predates custom properties, so tokens move out of GWT
entirely, into static files each layout serves from its public folder:

```css
/* layouts/neo/public/neo/tokens.css */
:root {
  --color-surface: #fff;
  --color-text: #333;
  --color-primary: #a5cf47;
}

:root[data-theme="dark"] {
  --color-surface: #111;
  --color-text: #aaa;
}
```

The rules files (`variableColors.css`, `neo.css`, later each layout's
`controls.css` and `views/*.css`) refer to tokens with `var(--name)`. Both rules files already declare
`@external .*;`, so class names are unobfuscated today and nothing in the
templates changes.

## Switching without a reload

```java
// core/theme/ThemeService.java
/** Switches to LIGHT, DARK or SYSTEM without a reload. */
public void setMode(ThemeMode mode) {
  Theme theme = resolve(mode);
  // sets data-theme on <html>; the tokens do the rest
  apply(theme);
  settings.save(mode);
  bus.fire(new ThemeChangedEvent(theme));
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

An icon is either a **picture** or a **glyph from an icon font**, and each
layout decides which (decision `0006-icons-images-or-glyphs.md`).

```java
// core/icons/Icon.java: a plain value, no widget code

// a picture, with its dark version
Icon.image("…/button.png", "…/dark/button.png");

// a Material Symbols ligature
Icon.glyph("material-icons", "add");

// a Tabler icon: the class draws it, no text
Icon.glyph("ti ti-click", "");

// text only
Icon.NONE;
```

Each layout supplies an icon set with three lookups, and every one may answer
with a picture or a glyph:

```java
// controls/Icons.java
/** Toolbar and menu icons. */
Icon action(IconKey key);

/** The Blocks editor's drawers. */
Icon blockCategory(BlockCategory category);

/** Palette, component tree and non-visible strip. */
Icon component(ComponentIconSource source);
```

| | Actions | Block drawers | Components |
|---|---|---|---|
| classic | none (text) | shared PNGs | shared PNGs (9 with dark versions) |
| neo | Material glyphs | shared PNGs | its own PNGs for 84 components (85 dark), shared PNGs for the rest |
| nova (later) | Tabler glyphs | Tabler glyphs | Tabler glyphs where it has one, shared PNGs for the rest |

One shared widget, `controls/IconView`, draws any `Icon`: an `<img>` for a
picture, an `<i>` for a glyph. It swaps a picture's dark version on
`ThemeChangedEvent`, so light/dark needs no reload. The component palette, the
component tree, the non-visible components strip and the Blocks editor's drawer
list all draw through it, so the same code shows a PNG in classic and neo and a
Tabler glyph in nova.

**Extensions** always keep their own picture (`aiwebres/…` in the extension),
unless a layout maps that extension type explicitly. A nova palette can
therefore show glyphs and pictures side by side; the layout's CSS gives both
the same box size.

Pictures become static files, so no Java image bundle is needed per layout
and theme: shared ones under `features/designer/palette/public/palette/` and
`features/blocks/public/blocks/`, a layout's own under
`layouts/<x>/public/<x>/palette/{light,dark}/`.

## Icons are meanings, not glyph names

Actions name an `IconKey` (`ADD`, `IMPORT`, `FOLDER_NEW` …), never a glyph; a
layout's icon set turns the key into its own picture or glyph. A layout's icon
font ships in its own public folder and loads only for that layout. If a
control shows an icon without text, it puts the action's label into
`aria-label`, so icon-only buttons stay accessible. A unit test checks that
every `IconKey` and `BlockCategory` is answered by every layout that draws
icons.
