# Drag and drop

Status: accepted for the drag engine (decision `0015`): every drag between
components, the component tree included, runs on one engine, and Escape
cancels a drag. Tickets P3-45, P5-47 and P5-48 are created; P4-46 (file
drops) and the P0-01 extension are still proposed.

## What the client does today

Five separate mechanisms, all on master (`ba85eb0d0`). Paths are relative
to `appinventor/appengine/src/com/google/appinventor/client/`.

| # | What is dragged | Mechanism | Code |
|---|---|---|---|
| 1 | Palette item to the viewer; a component within the viewer; to the non-visible strip; the sprite origin point | A home-made engine on GWT 1.x mouse and touch listeners | `widgets/dnd/` (827 lines; `DragSourceSupport` 609) |
| 2 | A component in the component tree, to reorder it | Browser HTML5 drag and drop, written inline | `explorer/SourceStructureExplorer.java:208` onward |
| 3 | Files from the desktop (`.aia`, `.aix`, keystore, assets) | A window-wide script that decides by file extension | `utils/html5dnd.js` (412), `utils/HTML5DragDrop.java` (329) |
| 4 | A project row to the desktop, to download its `.aia` | Inline JSNI setting `DownloadURL` | `explorer/youngandroid/ProjectListItem.java:120` |
| 5 | Boxes by their header, between columns | The gwt-dnd library | `widgets/boxes/WorkAreaPanel.java:120` |

Blockly drags its own blocks, including dropped block PNGs; that stays
Blockly's.

### The drag preview

- From the palette, the preview is a real mock of the component, the
  way it looks on the phone: `SimplePaletteItem.createDragWidget`
  (line 184) builds a mock with `createMockComponentFromPalette()`, sizes
  it to its preferred size and holds it at its centre. On drop,
  `MockContainer.onDrop` builds a second mock; the preview is thrown
  away.
- Moving a component, the preview is a DOM copy of it
  (`widgets/ClonedWidget`) held where it was grabbed; the original is
  hidden but keeps its space (`MockComponent.createDragWidget`,
  line 1044). `MockBall` adjusts its copy through
  `onDragWidgetCreated`.
- The preview sits in a `PopupPanel` that follows the pointer
  (`DragSourceSupport.DragWidgetPopup`).

### Drop feedback

Each mock layout draws its own hint while a component hovers over it:

- `MockHVLayoutBase` (765 lines): a blue insertion line between
  children, colour hard-coded as `#0000ff` (line 36).
- `MockAbsoluteLayout`: a drop area with a hard-coded border colour
  (line 99), placed by the pointer's offset in the preview.
- `MockTableLayout`: highlights the cell under the pointer.
- The tree draws its own line or box in three zones per row (before,
  into, after), `SourceStructureExplorer.java:82-90`.
- File drops show `div.dropdiv` (`Ya.css:3693-3711`).

## What is wrong with it

1. **One question, four answers.** "Can component X go into container
   Y?" is answered by:
   - `MockContainer.acceptableSource` with `instanceof` checks
     (`MockSprite`, `MockMapFeature`, `MockChartData`), for drops in
     the viewer;
   - `MockContainer.willAcceptComponentType` with type lists, for the
     tree and paste;
   - `SimpleNonVisibleComponentsPanel.onDragEnter` and
     `SimpleVisibleComponentsPanel.onDragEnter`, with
     `instanceof SimplePaletteItem` and a visible check;
   - `SimplePaletteItem.addComponent`, its own mix, for Enter and
     double-click.

   They can disagree, and none can be unit-tested.
2. **Rules sit inside widgets.** `MockContainer.onDrop` asks whether it
   is a `MockAbsoluteArrangement` and whether its editor is a
   `YaFormEditor`. `SimplePaletteItem.addComponent` finds the form
   through `getDropTargets()[0]`.
3. **Global state.** `DragSourceSupport.absX`/`absY` are
   `public static`; `MockTableLayout:178` and `MockTableArrangement:77`
   read them.
4. **Targets come from hand-made lists.** Each editor returns every
   target in depth-first order (`DesignerEditor.java:122`,
   `YaFormEditor.java:115`), found through Ode's active editor; the
   engine tests each one on every pointer move.
5. **File drops will break during P4 and P5.** They find targets by CSS
   class names inside JavaScript (`ode-Box`, `ode-DialogBox`,
   `blocklySvg`) and by singletons (`ProjectListBox`, `AssetListBox`,
   `PaletteBox`, `Ode.getOverDeckPanel()`,
   `HTML5DragDrop.java:303-329`) — exactly what P4 and P5 replace with
   layout regions.
6. **Old APIs.** The engine uses `MouseListener`, `SourcesMouseEvents`
   and `DOM.setCapture`, deprecated since GWT 1.6.
7. **Keyboard paths are separate code.** Enter or double-click adds a
   component from the palette; Alt+Up/Down moves one in the tree. They
   do not share code with the drag paths, so they drift.
8. **The previews cannot be changed per layout.** The preview is
   whatever the source widget builds; a layout has no say.
9. **Dead code.** Box dragging (mechanism 5) is unreachable: nothing
   calls `new WorkAreaPanel(`, and `Ode.switchToDebuggingView` shows
   deck slot 3 (`Ode.java:546-550`, `debuggingTabIndex = 3` at line
   1087) of a deck that has three children (`Ode.ui.xml:20-42`). The
   gwt-dnd jar (`lib/gwt_dragdrop/gwt-dnd-3.2.3.jar`,
   `YaClient.gwt.xml:24`) is used by nothing else.

## Requirements

- Escape cancels a drag (decision 0015); that is the only visible change
  for classic and neo. The palette preview stays the
  mock as it looks on the phone, the move preview stays a copy of the
  component, the hints stay where they are.
- A layout can replace the preview, and change it while dragging. Nova
  is expected to start a palette drag as a small card (icon and name)
  and turn it into the mock once the pointer is over the phone.
- One answer to "can X go into Y", used by every path: drag, tree,
  paste, keyboard.
- Every drag has a keyboard equivalent that runs the same command.
- Mouse, touch and pen work the same way.
- Targets register themselves; no lists, no singletons, no class-name
  lookups.

## Proposal

### Who does what

| Piece | Package | Knows widgets? |
|---|---|---|
| What is dragged (`DragPayload`), the one rule (`ComponentRules`), what a file means (`FileDropRouter`) | `core/dnd/` | no |
| The commands a drop runs (`AddComponent`, `MoveComponents`) | `features/designer/` | mocks only |
| The contracts (`Draggable`, `DropTarget`, `DragPreview`) and the engine (`DragDrop`) | `controls/dnd/`, `controls/behaviour/` | yes, no layout |
| The preview a layout shows | `Controls.dragPreview()` | its own |
| Hints inside the phone | the mock layouts | phone theme |
| Tree hint, file overlay | the layout's CSS | its own |

### The rule, once

```java
// core/dnd/ComponentRules.java
public final class ComponentRules {
  /** Whether a container of one type may hold a child. */
  public static boolean canContain(String containerType,
      String childType) {
    // Canvas: sprites only; Map: map features only;
    // Chart: chart data only; others: none of those
  }

  /** Non-visible components go on the screen only. */
  public static boolean canHoldNonVisible(
      String containerType) {
    // …
  }
}
```

Viewer drops, tree drops, paste, Enter and double-click on a palette
item, and Alt+Up/Down all ask `ComponentRules`; a drop then runs the same
`AddComponent` or `MoveComponents` command the keyboard runs.

### What is dragged

```java
// core/dnd/DragPayload.java
public final class DragPayload {
  public enum Kind {
    NEW_COMPONENT,
    COMPONENTS,
    SPRITE_ORIGIN
  }

  private final Kind kind;
  private final String componentType;
  private final List<String> componentNames;

  // factories: newComponent(type), components(names) …
}
```

A plain value: no widget, so rules and tests can read it.

### The contracts

```java
// controls/dnd/Draggable.java
public interface Draggable extends IsWidget {
  DragPayload payload();

  /** What the preview may show; built only when asked. */
  PreviewContent previewContent();

  /** E.g. hide the original while it moves. */
  void onDragStart();

  void onDragEnd(boolean dropped);
}
```

```java
// controls/dnd/DropTarget.java
public interface DropTarget extends IsWidget {
  boolean accepts(DragPayload payload);

  /** Shows where the drop would land. */
  void onHover(DragPayload payload, DropPoint point);

  void onLeave();

  void onDrop(DragPayload payload, DropPoint point);

  /** What kind of area this is, for the preview. */
  DropZone zone();
}
```

```java
// controls/dnd/DropZone.java
public enum DropZone {
  NONE,
  PHONE,
  NON_VISIBLE_STRIP,
  TREE,
  IMAGE
}
```

`DropPoint` carries the pointer position relative to the target and the
grab offset inside the preview, which `MockAbsoluteLayout` and
`MockCanvas` need; it replaces the static `absX`/`absY`.

### The preview, per layout

The source says what the preview may show; the layout decides how to
show it:

```java
// controls/dnd/PreviewContent.java
public interface PreviewContent {
  /** The component as it looks on the phone. */
  Widget mock();

  String name();

  Icon icon();

  /** Where the pointer holds it, inside mock(). */
  int grabX();

  int grabY();
}
```

```java
// controls/dnd/DragPreview.java
public interface DragPreview {
  void show(DragPayload payload, PreviewContent content,
      int clientX, int clientY);

  void move(int clientX, int clientY);

  /** The kind of area under the pointer changed. */
  void over(DropZone zone);

  void end(boolean dropped);
}
```

```java
// controls/Controls.java (added)
/** The preview for one drag; a layout may return its own. */
default DragPreview dragPreview() {
  return new MockDragPreview();
}
```

- `MockDragPreview` (`controls/behaviour/`, behaviour only) shows
  `content.mock()` at the grab point and ignores `over`: exactly
  today's look, for classic and neo.
- A future `NovaDragPreview` shows a card with `icon()` and `name()`,
  and swaps in `mock()` when `over(DropZone.PHONE)` arrives, and back
  when the pointer leaves the phone.
- `mock()` comes from the palette item
  (`createMockComponentFromPalette()`) or, for a move, a copy of the
  component (`ClonedWidget`); `MockBall`'s adjustment moves to its
  `previewContent()`.

### The engine

`controls/behaviour/DragDrop`, written once:

- Pointer Events (`pointerdown`, `pointermove`, `pointerup`,
  `setPointerCapture`) for mouse, touch and pen, through JsInterop;
  today's 5-pixel threshold.
- A target registers its element when attached and unregisters when
  detached. On each move the engine takes the element under the pointer
  (the preview has `pointer-events: none`) and walks up to the nearest
  registered target, so the innermost container wins with no ordering
  lists.
- It calls `accepts`, `onHover`, `onLeave` and `onDrop`, and tells the
  preview `over(target.zone())` whenever the zone changes.

### Files in and out

Files stay on the browser's native drag and drop, because they come from
the operating system:

```java
// controls/dnd/FileDropZone.java
public interface FileDropZone extends IsWidget {
  boolean accepts(FileKind kind);

  void onFiles(List<DroppedFile> files);
}
```

- `core/dnd/FileDropRouter` turns a file name into a `FileKind`
  (`PROJECT`, `EXTENSION`, `KEYSTORE`, `ASSET`, `NOT_SUPPORTED` for
  `.apk` and `.aab`), today's rules from `html5dnd.js:228-250`, unit
  tested.
- The project list and the empty projects page accept `PROJECT`; the
  palette accepts `EXTENSION`; media accepts `ASSET`; each region
  registers itself.
- The upload calls in `html5dnd.js` stay as they are at first; only the
  decisions move to Java.
- Dragging a project row out keeps its `DownloadURL`, as a small
  `controls/behaviour` helper instead of inline JSNI.

### Hints and colours

- The mock layouts keep drawing their own hints, but the colours come
  from a token, `--color-drop-hint`, instead of `#0000ff`.
- The tree hint and the file overlay are styled by the layout's CSS.

## Tickets

Numbers continue after P7-44. Estimates in days. P3-45, P5-47 and P5-48 are
in `plan/tickets/`; P4-46 and the P0-01 extension are proposed.

| Key | Title | Size | Blocked by | Blocks |
|---|---|---|---|---|
| P0-01 (extend) | Dead code gone: add `WorkAreaPanel`, `ColumnLayout`, `boxes/Layout`, gwt-dnd and the debugging view | +0.5 | — | — |
| P3-45 | Drop rules and designer commands have one home | 1.5 | — | P5-32, P5-47 |
| P4-46 | File drops find their targets through registered zones | 2 | P4-17 | P4-18, P4-21, P5-32, P5-43 |
| P5-47 | One drag engine with per-layout previews | 3, as (1/2) engine and palette, (2/2) viewer moves and delete `widgets/dnd/` | P5-23, P3-45 | P5-32, P5-43 |
| P5-48 | The component tree reorders on the drag engine | 1.5 | P5-47, P5-32 | P7-39 |

Elsewhere:

- C-03 (nova's scope): add "drag preview" to what nova decides; the hook
  is `Controls.dragPreview()`.
- P6-33 / P6-35: the `dropdiv` rules and the tree hint move into the
  layout's views CSS.
- About 8.5 days in total, half-time.

## Open questions

1. ~~Should Escape cancel a drag?~~ Yes (decision 0015). It is the one
   visible change, in P5-47.
2. ~~Should the tree keep HTML5 drag and drop?~~ No: it moves to the engine
   (P5-48), for one hint style and one code path. P5-47 and P5-48 are 4.5
   days together.
3. The debugging view behind "internal error, open the debugging
   view?" (`Ode.java:722-724`) cannot open today. Delete the prompt with
   the view, or replace it with the console?
