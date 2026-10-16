# Decisions

One short record per decision: the context, what was decided, and what follows
from it. Read the record before arguing with the rule; change the rule by
amending its record, never by quietly doing something else in a PR.

## Accepted

| # | Decision | In one line |
|---|---|---|
| [0001](0001-layout-id-not-boolean.md) | Layouts are ids, not a boolean | `LayoutId.CLASSIC`/`NEO`, so a third layout is one more enum value |
| [0002](0002-css-custom-properties.md) | Tokens are CSS custom properties in static files | Light/dark switches by changing `data-theme`, no reload |
| [0003](0003-no-layout-inheritance.md) | No layout extends another layout | Neo stops subclassing classic; shared code lives outside `layouts/` |
| [0004](0004-icons-as-keys.md) | Icons are keys, not glyph names | Code names an `IconKey`; each layout maps it to its own icon |
| [0005](0005-package-moves-last.md) | Pure package moves happen last | P7 moves files once everything else has landed |
| [0006](0006-icons-images-or-glyphs.md) | An icon is an image or a glyph | Classic and neo use pictures, nova may use Tabler glyphs |
| [0008](0008-templates-like-react.md) | Templates read like React | One template per region; every box an `HTMLPanel`, text a `Label`; prefixes are the package's last word |
| [0009](0009-designer-regions-per-layout.md) | Designer regions belong to each layout | Palette, viewer, tree, properties … are layout views; logic stays in presenters |
| [0010](0010-css-files.md) | CSS is split into small files by job | Shared `reset.css`/`layout.css`; per layout `tokens`, `controls`, `views/*` |
| [0011](0011-property-editors-as-models.md) | Property editors are models; choice lists are data | Logic in `core/`, one shared template view per kind, 36 choice lists as data |
| [0012](0012-editor-errors.md) | Editors keep `Window.alert` through P7 | Models report through `View.showError`; inline errors come in a later, look-changing ticket |
| [0013](0013-standard-controls.md) | `StandardControls` is a bridge, deleted in P7 | Every layout writes every control; shared behaviour lives in `controls/behaviour/` |
| [0014](0014-google-style.md) | Code follows Google style | Google Java Style strictly, enums one constant per line, `ant checkstyle` clean on changed lines |

## Waiting on a ticket

| # | Question | Ticket |
|---|---|---|
| 0007 | What nova is: screens, controls, colour, icons | C-03 |

## Writing a new one

Copy the shape of an existing record: `# NNNN Title`, a `Date · Status` line,
then `Context`, `Decision`, `Consequences`. Take the next free number, add it to
the table above, and open it as a PR to this repository with label
`type: decision`.
