# 0004 Icons are keys, not glyph names

Date: 2026-10-15 · Status: accepted

## Context
Neo templates name Material glyphs directly (`icon="file_open"`). Classic draws
text only. A later layout may use a different icon font.

## Decision
Actions name an `IconKey` (`ADD`, `IMPORT`, `FOLDER_NEW` …). Each layout maps
keys to its own glyphs in `layouts/<x>/icons/`; classic ignores them. A
layout's icon font ships in its own public folder and loads only for it. The
kit puts the action label into `aria-label` when a layout shows only an icon. A
unit test checks every key is mapped by every layout that draws icons.

## Consequences
Changing icon sets touches one layout. Component palette icons stay shared by
default.
