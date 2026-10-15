# Overview

## Goal

Make every look of the App Inventor IDE a self-contained **layout**, so that:

- classic and neo stop depending on each other (today neo subclasses classic);
- logic lives once, outside the UI, and every layout reuses it;
- light and dark switch instantly, without reloading the page;
- a new layout (nova) can be added as one folder, with its own structure,
  building blocks, colours and icons, without touching classic or neo.

## Scope

Phase one, the subject of milestones P0 to P7, restructures **classic and neo
only**. Nova starts after P7 (milestone *Nova*).

## Non-goals

- No visual redesign of classic or neo. Every PR in P0–P7 changes nothing on
  screen, except P2, which makes light/dark switch live.
- No changes to the Blocks editor's look beyond following light/dark.
- No changes to how the user's app components (`Mock*`) look; they follow the
  phone theme (Material, Holo, iOS), not the IDE layout.
- No server or build-server changes.

## Principles

1. **Logic once.** Actions, presenters and models live in `core/` and know
   nothing about widgets or CSS.
2. **Look per layout.** Each layout owns its frame (top bar, toolbars, project
   list), its kit (buttons, menus, dialogs, inputs), its tokens and its icons.
3. **Shared screens through interfaces.** The designer, properties, blocks and
   dialogs are written once against kit interfaces and take on the active
   layout's look.
4. **Small, safe steps.** About 45 PRs, each reviewable in one sitting, none of
   which changes what users see unless it says so.

## Documents

| File | Topic |
|---|---|
| `01-today.md` | How the client works on master |
| `02-target.md` | The four packages and the import rules |
| `03-layouts.md` | Layout ids, the `Layout` contract, the registry |
| `04-theming.md` | Tokens, live light/dark, icons |
| `05-actions.md` | `ActionId`, `Action`, presenters, binding |
| `06-kit.md` | Kit contracts, shared behaviour, the standard kit |
| `07-views.md` | Views without inheritance, worked examples |
| `08-css.md` | `base.css` and each layout's tokens, kit and views CSS |
| `09-migration.md` | The PRs, their order and the timeline |
| `10-pr-rules.md` | What every PR must satisfy |
| `file-map.html` | Every client file, today and proposed |
| `decisions/` | Why each key choice was made |
