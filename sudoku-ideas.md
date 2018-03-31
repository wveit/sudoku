# Sudoku Concepts #

This document describes some of the ideas, algorithms, postulates, etc that are used in this library.


## Terminology ##

**Board** - a 9x9 grid of cells.

**Cell** - a place-holder for a single integer. Only the integers 0-9 are valid entries for a cell. 0 indicates an empty cell.

**3x3 Box** - a 3x3 group of cells on a board. Only the first 3, middle 3 or last 3 columns/rows can be grouped together to form a 3x3 box.

**3 Column** - a group of 3 adjacent columns. The allowable 3-columns are columns 0-1-2, 3-4-5, and 6-7-8.

**3 Row** - a group of 3 adjacent rows. The allowable 3-rows are rows 0-1-2, 3-4-5, and 6-7-8.

**Valid Board** - xxxxxxx

**Complete Board** - xxxx

**Board Mask** - xxxxx


## Shuffling Concepts ##

How are new puzzle boards made. We start with a known valid board (fully filled in). Then we shuffle the board. There are some rules for shuffling. As long as these rules are followed, the resulting shuffled board will be just as valid as the original board. Some of these rules were easy for me to prove to myself; I'll call these 'theorems'. Others I intuitively believe to be true, and haven't encountered any problems yet by using them; I'll call these 'postulates'.

### Shuffling Rules ###

* Theorem: Any 2 columns can be swapped without affecting the board's validity, as long as those columns belong to the same 3-column.

* Theorem: Any 2 rows can be swapped without affecting the board's validity, as long as those rows belong to the same 3-row.

* Theorem: Any 2 3-columns can be swapped without affecting the board's validity.

* Theorem: Any 2 3-rows can be swapped without affecting the board's validity.

* Theorem: Any board can be flipped horizontally and/or vertically without affecting the board's validity.

* Theorem: The cell values on a board can be rotated 0, 90, 180 or 280 degrees without affecting the board's validity.

* Theorem: Multiple flip moves in a shuffle sequence (however they are distributed), can be consolidated in a single flip move with the same result. In other words, having multiple flip moves in a shuffle sequence does not help to make the board more shuffled. Therefore, when developing a shuffling sequence, it only makes sense to do one flip move ('none', 'horizontal', 'vertical', or 'horizontal and vertical'). The same goes for board rotations. 

* Theorem: The board's values (1-9) can be "consistently switched" without affecting the board's validity. What does "consistently switched" mean? Here's an example: We can replace the values [1, 2, 3, 4, 5, 6, 7, 8, 9] with the values [2, 6, 4, 7, 8, 1, 9, 3, 5]. This means that every 1 on the original board, becomes a 2 on the new board; and that every 7 on the original board, becomes a 9 on the new board.

* Postulate: Given a valid board, any other valid board can be achieved, given a correct sequence of the above shuffles.

* Postulate: Any board mask can be shuffled by the above rules, without affecting the difficulty or solvability of the mask.

* Postulate: If a puzzle consists of a completed board and a mask, then the board and mask can be shuffled independently, without affecting the validity, difficulty or solvability of the puzzle.

* Postulate: Every n-blank board mask will have a certain level of difficulty associated with it. The difficulty will be determined by both the number of blanks (n) and the arrangement of those blanks. Given a n-blank board mask, any n-blank board mask of the same n and difficulty can be achieved by applying a sequence of the above shuffles to the board mask.

## Ideas for algorithms for generating a random valid complete board or puzzle ##

* Shuffle your way to a new board: 

	1. Start with a blank board.
	2. Fill it in with any solver. Ordered solver or random solver will both do the job.
	3. Perform a large number of shuffling moves on the board.

* Generate mask of appropriate difficulty:

	1. Save a few different masks for each difficulty level (these can be called mask seeds).
	2. Choose one mask of the correct difficulty level.
	3. Apply a sequence of shuffling operations to the mask.
	
* Combine a complete board and mask to create a puzzle:

	1. Create a new board and new board mask.
	2. If needed save the new board to use as a solution key to the puzzle.
	3. Using the 1 and 0 values on a board mask, whichever cell has a 0 as its value, change the value in the board to a zero also. 
	
## Ideas for algorithms for solving a sudoku puzzle ##

* Brute force solver (recursive):

	1. ...
	
* Rule based solver:

	This solver will behave more like a human solver would. It uses various rules that it can exploit in order to solve a puzzle. Solvers of various intelligence levels / efficiency levels can be created by controlling which rules that solver can use, and in what order they are applied. Here are some sample rules:
	1. Look for rows, columns, or 3x3 boxes that have only 1 missing value. Determine which single value has not yet been represented in that row, column or 3x3 box. Then place that value in the missing-value-cell.
	2. Look for rows, columns, or 3x3 boxes that have 2 missing values. Find out what those missing values are. Go to each missing-value-cell and try to eliminate one of the potential missing values by looking if that value already exists in one of the other dimensions. Can also be done for more than

