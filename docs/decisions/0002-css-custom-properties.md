# 0002 Tokens are CSS custom properties in static files

Date: 2026-10-15 · Status: accepted

## Context
Tokens are GWT `@def` constants compiled into `Ode.Resources`; light and dark are
separate compiled bundles, so switching needs a reload. GWT 2.8.1's CSS parser
predates custom properties.

## Decision
Tokens move to static CSS files with custom properties, light values on `:root`
and dark values on `:root[data-theme="dark"]`. They are served from each
layout's public folder and never pass through `CssResource`. `ThemeService`
switches `data-theme` and fires `ThemeChangedEvent` for what CSS cannot reach
(Blockly, charts, PNG images).

## Consequences
Light/dark switches instantly. A `system` mode is cheap. PNG images need a
bridge (`ThemedImage`) until each layout has an icon set (0004).
