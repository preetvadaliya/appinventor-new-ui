# Code style

Every PR follows the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
strictly, plus the few stricter rules marked **(ours)** below. App Inventor
already checks Google style: `appinventor/lib/checkstyle/appinventor-checks.xml`,
run on the lines a commit changes with:

```sh
cd appinventor
ant checkstyle                 # lines changed since HEAD
ant checkstyle -Dcommit=upstream/master
```

A PR has no checkstyle warnings on the lines it adds or changes (decision
0014). Code it only moves keeps its old formatting, so a move stays a move.

The snippets in these docs follow the same rules, with a 70-column limit so
they fit a page.

## Java

### Source level

- The client is compiled by GWT 2.8.1 at Java 8: no `var`, no switch
  expressions, no records, no text blocks, no `List.of`.

### Formatting

- Indent 2 spaces; a wrapped line is indented 4 more. No tabs.
- Lines at most 100 characters (70 in doc snippets). `package` and
  `import` lines are never wrapped.
- One statement per line; one variable per declaration.
- Braces always, even around a one-line `if`, `for` or `while`; opening
  brace at the end of the line (K&R). An empty block is `{}`.
- One blank line between members (fields may be grouped without one) and
  between logical sections of a method. Never two blank lines in a row.
- No horizontal alignment: never pad with spaces to line up `=`, values
  or trailing comments.

Wrong:

```java
private int count;      // projects shown
private String title;   // toolbar title
```

Right:

```java
// projects shown
private int count;

// toolbar title
private String title;
```

### Wrapping

- Break before `.`, `::`, `+`, `&&`, `||`, `?` and `:`; break after `,`.
- Prefer breaking at the highest syntactic level: arguments before
  their inner calls.

```java
actions.get(EXPORT_PROJECT).setLabel(selected > 1
    ? MESSAGES.exportSelectedProjectsMenuItem(selected)
    : MESSAGES.exportProjectMenuItem());
```

### Enums **(ours)**

Google allows a short enum on one line; we do not. Every constant is on
its own line, in a meaningful order, with an optional comment line above
a group:

```java
public enum Variant {
  DEFAULT,
  PRIMARY,
  QUIET,
  DANGER
}
```

```java
public enum ActionId {
  // project
  NEW_PROJECT,
  IMPORT_PROJECT,
  DELETE_PROJECT,

  // help
  ABOUT,
  OPEN_GUIDE
}
```

### Switch

- Each `case` label on its own line; its statements on the following
  lines, indented 2 more than the label.
- Every switch has a `default`, last. A case that falls through says so
  with `// fall through`.

```java
switch (key) {
  case FOLDER:
    return Icon.image(ICONS + "folder.png");
  case HELP:
    return Icon.image(ICONS + "help.png");
  default:
    return Icon.NONE;
}
```

### Imports

- No wildcard imports.
- Static imports in one block, then all other imports in one block,
  each sorted in ASCII order, one blank line between the blocks.

### Names

- Classes `UpperCamelCase`; methods, fields, parameters and locals
  `lowerCamelCase`; constants (`static final`, deeply immutable)
  `UPPER_SNAKE_CASE`; packages all lower case, no underscores.
- Acronyms are words: `LayoutId`, `XmlParser`, `parseUrl`, never
  `LayoutID` (GWT's own names such as `HTMLPanel` stay as they are).
- No prefixes or suffixes such as `mName`, `sInstance`, `nameStr`.
- A name says what a thing is or does; no `Impl` unless there is an
  interface it implements and nothing better to call it, no `V2`.

### Annotations

- On a class, method or constructor: one per line, above it.
- On a field: may share the line (`@UiField Label title;`).
- `@Override` is always used when it applies.

### Members and order

- One top-level class per file, named after the file.
- Order: constants, fields, constructors, then methods grouped by what
  they do; overloads next to each other, never split.
- A field holds its type's interface where one exists
  (`@UiField Button ok;`, not `ClassicButton`, in shared views).

### Javadoc

- Every public class, and every public method that is not an obvious
  `@Override`, starts with a Javadoc summary fragment:
  `/** Shows the trash buttons instead of the project buttons. */`.
- One line when it fits; otherwise a summary line, a blank `*` line, and
  `<p>` paragraphs.
- Comments say why, not what the next line does.

### Lambdas

- A lambda is short: one expression or one statement. Anything longer is
  a named private method passed as a method reference.

```java
selection.onChange(this::update);
ok.addClickHandler(e -> save());
```

## UiBinder templates (`.ui.xml`)

Google has no XML guide; these follow the Java rules where they apply,
plus decision 0008.

- Indent 2 spaces; wrapped attributes are indented 4 more than the tag.
- Lines at most 100 characters (70 in doc snippets). When a tag does not
  fit, put the attributes after the first one on following lines.
- One element per line. A self-closing element ends with `/>`.
- Namespace prefixes are the package's last word (`gwt:`, `controls:`,
  `editor:` …); declare only the ones the template uses.
- Every box is a `<gwt:HTMLPanel>`; text is `<gwt:Label>` or
  `<gwt:InlineLabel>`; no raw HTML elements.
- A comment at the top says what the region is, in one or two lines.

```xml
<gwt:HTMLPanel styleName="flex-row items-center gap-sm">
  <controls:ClassicButton action="NEW_PROJECT"/>
  <controls:ClassicButton action="DELETE_PROJECT"
      variant="DANGER"/>
</gwt:HTMLPanel>
```

## CSS

Following the [Google HTML/CSS Style Guide](https://google.github.io/styleguide/htmlcssguide.html):

- Indent 2 spaces. One selector per line; one declaration per line,
  each ending with `;`.
- A space after `:` and before `{`; one blank line between rules.
- Lower-case hex colours, shortened only when exact (`#fff`).
- No `!important`, no IDs as selectors, no units on `0`.
- Values from tokens (`var(--color-text)`) in every file but `tokens.css`.
- No horizontal alignment.

```css
.classic-Button,
.classic-MenuButton {
  padding: 4px 8px;
  border: 1px solid var(--color-border);
  border-radius: 2px;
}

.classic-Button:disabled {
  color: var(--color-muted);
}
```

## Commits

- Short conventional subjects: `refactor(controls): neo draws its own
  dialog`. No AI attribution lines.
