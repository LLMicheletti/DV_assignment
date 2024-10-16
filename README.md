# DV_assignment
This repository shows my solution for the Exercise 1 of the assignment given by the Race Up Team.
## Task Description
It is given a grid of characters representing a maze, where:
* "S" is the starting point;
* "." is an open passage;
* "#" is a wall;
* "E" is the ending point.

An example of a given maze is:
```
S.... 
.###.
..E..
.#.#.
.....
```

The solution must be a series of commands ("UP", "DOWN", "LEFT", "RIGHT") to give to an hypothetical adventurer starting from "S" to get him to "E" going excusively trough "."
## The idea
My idea is that of using DFS, a graph traversal learning algorithm specifically created for solving mazes. This recursive algorithm firstly visits a neighbour node and then the neighbour of the neighbour, i.e. the function calls itself recursively from a visited node as long as it matches some "visiting condition", otherwise it goes back to a previously visited node until it finds one that matches the condition. This behaviour is perfect to explore paths throughout a maze, since we can set the "visiting condition" to be "don't visit the walls and don't exit the labirinth" and the algorithm will explore every possible path, backtracking if it comes to a dead end and trying a different way, just like a human adventurer would do, but with a better memory!
## The implementation
The idea is translated in Java by:
* reading process with `Bufferedreader` from the .txt maze file;
* turning the car grid into a graph using a Map, made of entries where:
  * the key is a list of coordinates ( (0, 0) corresponds to "S" at top left);
  * the value is a `Node` object, which possesses fields like the coordinates, the corresponding char in the grid and some features for DFS, like a flag `visited`, a pointer `parent` and a string `direction`.
* call a recursive DFS from the starting node "S" to set for every reachable node except "#":
  * the pointer `parent` to the last node from which DFS is called, i.e. the visitor node;
  * the flag `visited` to true so that DFS won't visit it again;
  * the string `direction` to "UP", "DOWN", "LEFT" or "RIGHT" based on the point from wich the node is visited.
* iterate through the grid to get the node that holds the char "E" and go parent-by-parent until reaching "S", saving the strings `direction` in a `solution` list, which is returned unless the process finds a null pointer, which means the DFS algorithm didn't find an available path, therefore no solution exists.
## How to use the program
The program is a standard .java file, so you just need to create a new directory on your pc, download the program from [MazeSolver.java](code/MazeSolver.java) and create in the same directory a "maze.txt" file similar to the ones in [maze_examples](code/maze_examples), or just use one of them. 
## Attention
Remember that the program will only read a file named "maze.txt", which must be a grid of char exclusively made of a single "S" located at top left, a single "E", "." and "#". Obviously you need a correctly running JDK to compile and execute the code.
