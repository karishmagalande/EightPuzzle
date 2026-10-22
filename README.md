# 8-Puzzle Solver (A*)

Solves the 8-puzzle with A* search in Java. You enter a start and a goal arrangement of the 3×3 board, and it prints the sequence of boards from start to goal.

## How it works

- **Board.** Nine numbers entered row by row, with 0 for the blank.
- **Moves.** Slide a tile into the blank from the left, right, above, or below.
- **Search.** A* with f(n) = g(n) + h(n):
  - g(n) is the number of moves made so far.
  - h(n) is the number of tiles out of place, not counting the blank. It never overestimates, so the path found is the shortest.
- **No repeated states.** Expanded states and states waiting in the open list are kept in hash sets, so the same board is never queued twice.
- **Unsolvable puzzles.** Half of all arrangements cannot reach a given goal. The program checks the inversion parity of start and goal first, and stops at once if they don't match.

## Run it

```bash
javac EightPuzzle.java
java EightPuzzle
```

## Example

```
Start      Goal
1 3 4      1 2 3
8 6 2      8 0 4
7 0 5      7 6 5
```

A* expands 7 states and finds a 5-move solution. "Path Size" counts the boards on the path, including the start.

```
Path Size : 6
 1 3 4      1 3 4      1 3 4      1 3 0      1 0 3      1 2 3
 8 6 2  ->  8 0 2  ->  8 2 0  ->  8 2 4  ->  8 2 4  ->  8 0 4
 7 0 5      7 6 5      7 6 5      7 6 5      7 6 5      7 6 5
```

The program also prints every state it generates along the way, with its f(n). The output above shows only the final path.

Harder starts take longer. `8 6 7 / 2 5 4 / 3 0 1` to `1 2 3 / 4 5 6 / 7 8 0` needs 31 moves, the maximum for the 8-puzzle, and takes about 20 seconds.
