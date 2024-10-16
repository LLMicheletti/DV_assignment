import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MazeSolver {
    public static List<char[]> readMazeFile(String fileName) {
        /* A method that saves every line of the fileName.txt as a char[] in the List mazeList */

        List<char[]> mazeList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                mazeList.add(line.toCharArray());
            }
        }

        //Handle errors
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            System.err.println("Error: " + e);
        }
        catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
            System.err.println("Error: " + e);
        }

        return mazeList;
    }

    public static Map<List<Integer>, Node> convertToMap(List<char[]> mazeList) {
        /* A method that creates a Map with key=List of coordinates[row][col]
         * and value=Node object with default values
         * starting from the List of arrays char[] mazeList */
        int numRows = mazeList.size();
        int numCols = mazeList.get(0).length;

        Map<List<Integer>, Node> map = new HashMap<>();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                List<Integer> coordinates = new ArrayList<>();
                coordinates.add(i);
                coordinates.add(j);
                Node node = new Node();
                node.pos = coordinates;
                node.c = mazeList.get(i)[j];
                node.visited = false;
                node.direction = "";
                node.parent = null;
                map.put(coordinates, node);
            }
        }
        return map;
    }

    public static class Node {
        //Coordinates of the matrix to locate the Node
        List<Integer> pos;

        //Pointer to the visitor node (the one from which DFS is running)
        Node parent;

        //Char value related ("S", "." or "#")
        char c;

        //Flag for DFS
        boolean visited;

        //UP, DOWN, RIGHT or LEFT based on the direction from where it is visited
        String direction;
    }
    
    public static void recursiveDFS(Map<List<Integer>, Node> map, Node n, int numRows, int numCols) {
        //Basic case, the maze is solved
        if (n.c == "E".charAt(0)) return;

        //Search for neighbour nodes
        for (int[] dir : new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}) {

            //Calculate new coordinates
            List<Integer> newCoordinates = new ArrayList<>();
            newCoordinates.add(n.pos.get(0) + dir[0]);
            newCoordinates.add(n.pos.get(1) + dir[1]);
            
            //Check if the newCoordinates don't point to a null node outside of the map
            if(0 <= newCoordinates.get(0) && newCoordinates.get(0) < numRows 
            && 0 <= newCoordinates.get(1) && newCoordinates.get(1) < numCols) {
                
                //Declare and assign a var newNode to the visited node
                Node newNode = map.get(newCoordinates);

                //Check if the node hasn't already been visited or is a wall
                if(!newNode.visited && newNode.c != "#".charAt(0)) {
                    //Save the parent node
                    newNode.parent = n;

                    //Update the direction
                    if (dir[0] == 0 && dir[1] == 1) {
                        newNode.direction = "RIGHT";
                    }
                    else if (dir[0] == 0 && dir[1] == -1) {
                        newNode.direction = "LEFT";
                    }
                    else if (dir[0] == 1 && dir[1] == 0) {
                        newNode.direction = "DOWN";
                    }
                    else if (dir[0] == -1 && dir[1] == 0) {
                        newNode.direction = "UP";
                    }
                    
                    //Update the visited flag
                    newNode.visited = true;

                    //Start a new DFS from the node
                    recursiveDFS(map, newNode, numRows, numCols);
                }
            }
        }
    }

    public static void printCheck(List<char[]> mazeList) {
        /* A method to print the read maze */
        if (mazeList.isEmpty()) {
            System.out.println("The list is Empty!");
        }
        else {
            int numRows = mazeList.size();
            int numCols = mazeList.get(0).length;
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    System.out.print(mazeList.get(i)[j]);
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        //Read the maze file and create a list of char arrays
        List<char[]> mazeList = readMazeFile("maze2.txt");

        //Check if the reading got the maze right
        printCheck(mazeList);

        //Turn the mazeList into map implementing a graph
        Map<List<Integer>, Node> map = convertToMap(mazeList);

        //Run the DFS traversal to update the fields of the nodes, starting from the S node
        int numRows = mazeList.size();
        int numCols = mazeList.get(0).length;
        Node sNode = map.get(List.of(0, 0));
        sNode.visited = true;
        recursiveDFS(map, sNode, numRows, numCols);

        //Search for the node E
        Node currNode = map.get(List.of(0,0));
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                currNode = map.get(List.of(i, j));
                if (currNode.c == "E".charAt(0)) break;
            }
            if (currNode.c == "E".charAt(0)) break;
        }
        
        //Follow the path given by the field parent to return to S
        Deque<String> solution= new ArrayDeque<>();
        while (currNode.c != "S".charAt(0)) {
            //Insert the direction at the beginning to get the right order (we are iterating through the reverse path)
            solution.offerFirst(currNode.direction);
            currNode = currNode.parent;

            //If the parent field points to null, it means that there is no solution
            if (currNode == null) {
                System.out.println("There is no solution!");
                break;
            }
        }
        //If the parent field doesn't points to null, print the solution
        if (currNode != null) System.out.println(solution);
    }
}