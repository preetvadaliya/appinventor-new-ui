# CSS

## Today

| Sheet | Lines | Loaded |
|---|---|---|
| `war/static/css/Ya.css` | 4,137 | always |
| `war/static/css/gwt.css` | 1,153 | always |
| `style/classic/variableColors.css` + `light.css`/`dark.css` | 1,230 + 76/62 | classic, via `Ode.Resources` |
| `style/neo/neo.css` + `lightNeo.css`/`darkNeo.css` | 2,145 + 62/59 | neo, via `Ode.Resources` |
| `android_holo.css`, `android_material.css`, `iOS.css`, `DarkTheme.css` | 45, 39, 218, 64 | always (phone preview skins) |

Neo mostly overrides `Ya.css` (250 of its classes also appear there). The body
classes `.neo` and `.classic` are barely used in CSS. There are no CSS custom
properties; about 570 hex colours are hard-coded.

## Target

| File | Holds | Loaded |
|---|---|---|
| `features/public/features/base.css` | Rules for shared screens (designer, properties, palette, blocks, dialogs). Reads tokens only. | always |
| `layouts/<x>/public/<x>/tokens.css` | The layout's light and dark values | active layout |
| `layouts/<x>/public/<x>/kit.css` | The layout's buttons, menus, dialogs, inputs | active layout |
| `layouts/<x>/public/<x>/views.css` | The layout's frame: top bar, toolbars, project list, status | active layout |
| `features/designer/mocks/public/mocks/*.css` | Phone preview skins for the user's app | always |
| `kit/public/kit/gwt-reset.css` | GWT widget defaults | always |

`Ya.css` and the `Ode.Resources` bundle are deleted at the end (P6-37). `Ya.css`
is also linked from `war/Ya_tos_form.html`. CSS served from `/ode/` needs a
static-files rule in `war/WEB-INF/appengine-web.xml`, which P2-05/P2-06 add.
About 142 gallery rules in `Ya.css` (lines 2822-3647) are set by no client code;
P6-34 deletes them.

`index.jsp` links only shared CSS; `LayoutRegistry` adds the active layout's three
files.

## Token contract

Every layout defines the same token names, in light and dark, so shared screens
can rely on them. P2-05 and P2-06 convert today's `@def` names one-to-one
(`--gray-300`) to keep the change mechanical; the semantic names below are
agreed before P6-33, when `base.css` starts reading tokens. Typical groups:

- surfaces and text: `--color-surface`, `--color-surface-raised`,
  `--color-text`, `--color-text-muted`, `--color-border`
- brand and state: `--color-primary`, `--color-selected`, `--color-error`,
  `--color-row-highlight`
- type and shape: `--font-body`, `--radius-sm`, `--radius-md`
- images: `--image-logo`, `--image-phone-portrait`, …

## Order

1. P2: tokens move to custom properties; the rules files stay where they are.
2. P6-33, P6-34: shared-feature rules leave `Ya.css` for `base.css`.
3. P6-35: frame rules leave `Ya.css` for each layout's `views.css`.
4. P6-36: kit rules go to each layout's `kit.css`.
5. P6-37: `Ya.css` and `Ode.Resources` are deleted.
