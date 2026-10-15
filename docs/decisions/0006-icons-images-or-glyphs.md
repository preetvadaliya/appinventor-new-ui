# 0006 An icon is an image or a glyph, chosen per layout

Date: 2026-10-16 · Status: accepted

## Context
Classic and neo draw the component palette, the component tree, the
non-visible components strip and the Blocks editor's drawer list with PNG
images (95 component icons, 10 drawer icons, with dark versions). Nova will use
Tabler icon-font glyphs instead. Extensions ship their own PNG, which no icon
font can replace.

## Decision
- `core/icons/Icon` is a plain value: `NONE`, an image (with an optional dark
  version) or a glyph (font class plus optional ligature text).
- Each layout supplies `controls/Icons` with three lookups:
  `action(IconKey)`, `blockCategory(BlockCategory)` and
  `component(ComponentIconSource)`. Each may answer with an image or a glyph.
- One shared `controls/IconView` draws any `Icon`, swaps dark images on
  `ThemeChangedEvent`, and labels it for screen readers.
- Fallback for components, in order: the layout's own icon for that type, then
  the shared default PNG (`ComponentIcons`). Extensions always keep their own
  image unless a layout maps that extension type explicitly.
- Images become static files: shared ones under
  `features/designer/palette/public/palette/` and `features/blocks/public/blocks/`,
  a layout's own under `layouts/<x>/public/<x>/palette/{light,dark}/`.

## Consequences
A palette row can mix a glyph and a picture and both render at the same size,
styled by the layout's CSS. Nova can start with glyphs for a few components and
fall back to pictures for the rest. Classic and neo keep exactly today's
images.
