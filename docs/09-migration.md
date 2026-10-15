# Migration plan

42 code tickets in 8 phases, about 97.5 developer-days; several ship as two
PRs. With a 20% buffer that is about 23 weeks full-time or 47 weeks half-time. Each phase is a GitHub
milestone; each PR has a ticket in `plan/tickets/`.

PR sizes follow the table-removal series #4148–#4154 (7–19 files, +91 to +403
lines each), which MIT merged within 1–4 days of opening.

| Phase | Tickets | Days | Outcome |
|---|---|---|---|
| P0 · Groundwork | P0-01, P0-02 | 3 | Dead code gone; snapshot diff for every PR |
| P1 · Layouts by id | P1-03, P1-04 | 3.5 | `LayoutId`, `Layout`, `LayoutRegistry` |
| P2 · Live light/dark | P2-05 … P2-08 | 11 | Tokens as custom properties; no reload |
| P3 · Actions | P3-09 … P3-14 | 15 | Every item runs on an `ActionId` |
| P4 · Independent views | P4-15 … P4-22 | 18.5 | Views per layout; no inheritance |
| P5 · Kits | P5-23 … P5-32, P5-41, P5-42 | 32.5 | A kit per layout; shared screens use it; actions in core |
| P6 · CSS split | P6-33 … P6-37 | 11 | No global stylesheet |
| P7 · Final moves | P7-38 … P7-40 | 3 | Final packages; import rules enforced |

Coordination tickets: C-01 (share the plan with MIT), C-02 (agree the
package-move window), C-03 (decide nova's scope).

## Order and overlap

- P2 (theme) and P3 (actions) are independent: after P1, one PR from each can be
  in review at the same time.
- P5-23 … P5-25 (kit contracts) can start while P4 is in review.
- P6 needs the views (P4) and the kits (P5).
- P7 comes last, by decision `0005-package-moves-last.md`.

The exact dependencies are recorded on each ticket as **blocked by** links.

## Rules that keep every step safe

- No visual change unless the ticket says so, proven by the snapshot diff.
- Old and new paths coexist within a phase; the phase's last ticket removes
  the old path (for example, toolbar items accept a `Command` or an `ActionId`
  until P3-14).
- New code goes straight into its final package; old files move when a PR
  already touches them. Pure moves wait for P7.
- Every PR runs `AiClientLib`, `ant tests` and `ant BlocklyTest`.

## Timeline (half-time from 2026-10-19)

| Milestone | Due |
|---|---|
| P0 | 2026-10-28 |
| P1 | 2026-11-09 |
| P2 | 2026-12-16 |
| P3 | 2027-02-04 |
| P4 | 2027-04-07 |
| P5 | 2027-07-26 |
| P6 | 2027-09-01 |
| P7 | 2027-09-11 |

Ticket start and target dates in the project are computed from the estimates by
`tools/sync.py`, in ticket order.
