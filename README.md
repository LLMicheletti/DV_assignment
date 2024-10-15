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
My program's workflow in Java:
* read from the .txt maze file;
* turn the grid of char into a graph using a Map of nodes;
* call a recursive DFS from the starting node "S" to set for every reachable node except "#":
  * the pointer `parent` to the last node from which DFS is called, i.e. the visitor node;
  * the flag `visited` to true so that DFS won't visit it again;
  * the string `direction` to "UP", "DOWN", "LEFT" or "RIGHT" based on the point from wich the node is visited.
* iterate through the grid to get the node that holds the char "E" and go parent-by-parent until reaching "S", saving the strings `direction` in a `solution` list, which is returned unless the process finds a null pointes, which means the DFS algorithm didn't find an available path, therefore no solution exists.
## How to use the program
In order to start solving maze in a blink of an eye follow these easy steps:
* download the file [MazeSolver.java](code/MazeSolver.java);
* create a maze from scratch or copy one of the examples from the directory [maze_examples](code/maze_examples);
* run the code and the 
