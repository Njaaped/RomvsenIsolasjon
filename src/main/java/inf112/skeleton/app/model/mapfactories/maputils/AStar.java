package inf112.skeleton.app.model.mapfactories.maputils;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;


public class AStar {
    
    /**
     * A node class to represent a position in the grid 
     *  with x, y coordinates and f, g, h values for A* algorithm
     *  where h is the heuristic value, g is the cost to move from the start node to a given node
     */
    public static class Node {
        int row, col;
        double f, g, h;
        Node parent;
        
        Node(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }

        @Override 
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Node node)) {
                return false;
            }
            return col == node.col && row == node.row;
        }

        @Override
        public int hashCode() {
            return 31 * col + row;
        }
    }

    /**
     * Find a path from the start node to the end node in the grid
     * Input grid, start and end coordinates
     * Output a list of AStar.Node representing the path
     * @param grid grid
     * @param startCol starting column
     * @param startRow starting row
     * @param endCol end column
     * @param endRow end row
     * @return List of nodes
     */

    public static List<Node> findPath(int[][] grid, int startRow, int startCol, int endRow, int endCol, boolean ingame) {
        Node start = new Node(startRow, startCol);
        Node end = new Node(endRow, endCol);
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        
        openList.add(start);
        
        while (!openList.isEmpty()) {
            Node current = openList.stream().min(Comparator.comparingDouble(node -> node.f)).get();
            
            openList.remove(current);
            closedList.add(current);
            
            if (current.getCol() == end.getCol() && current.getRow() == end.getRow()) {
                List<Node> path = new ArrayList<>();
                Node node = current;
                while (node != null) {
                    path.add(node);
                    node = node.parent;
                }

                Collections.reverse(path);
                return path;
            }
            
        
            List<Node> neighbors = generateNeighbors(current, grid);
            for (Node neighbor : neighbors) {
                if (closedList.contains(neighbor)) {
                    continue;
                }
                
                double tentativeG = current.g + calculateCost(neighbor, grid); 

                boolean betterPath = false;

                if (!openList.contains(neighbor)) {
                    openList.add(neighbor);
                    betterPath = true;
                } else if (tentativeG < neighbor.g) {
                    betterPath = true;
                }
                
                if (betterPath) {
                    neighbor.parent = current;
                    neighbor.g = tentativeG;
                  neighbor.h = calculateHeuristic(neighbor, end) * 20;
                  if (!ingame) {
                        neighbor.f = neighbor.g + neighbor.h;
                    } else {
                        neighbor.f = neighbor.g + neighbor.h + current.f;
                    }
                    
                }
            }
        }
        
        return null; 
    }
    
    // Helper method to generate the neighbors of a node
    private static List<Node> generateNeighbors(Node node, int[][] grid) {
        List<Node> neighbors = new ArrayList<>();
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] dir : dirs) {
            int row = node.getRow() + dir[0];
            int col = node.getCol() + dir[1];
            if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
                continue;
            }
            if (grid[row][col] != 2) { // Ignore obstacles
                neighbors.add(new Node(row, col));
            }
        }
        return neighbors;
    }
    
    // Helper method to calculate the heuristic (e.g., Manhattan distance) from a node to the end node
    private static int calculateHeuristic(Node node, Node end) {
        return Math.abs(node.getCol() - end.getCol()) + Math.abs(node.getRow() - end.getRow());
    }
    
    // Helper method to calculate the cost of moving to a neighbor based on the grid value
    private static int calculateCost(Node node, int[][] grid) {
        int value = grid[node.getRow()][node.getCol()];
        if (value == 1) {
            return 1;
        } else if (value == 2) {
            return Integer.MAX_VALUE; 
        } else if (value == 4) {
            return 5;
        } else {
            return 5;
        }
    }

    private static boolean addOrUpdateNode(PriorityQueue<Node> openSet, Node neighborNode) {
        for (Node node : openSet) {
            if (node.equals(neighborNode)) {
                if (node.g > neighborNode.g) {
                    openSet.remove(node);
                    openSet.add(neighborNode);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * algorithm to find a path from integer positions to player to spawn point 
     * This is also A-Star but with different implementation for closed floor with walls around.
     * this is not supposed to be used to find paths between islands.
     * 
     * @param grid grid
     * @param startCol starting column
     * @param startRow starting row
     * @param endCol end column
     * @param endRow end row
     * @return list of nodes
     */
    
    public static List<Node> algorithm(int[][] grid, int startRow, int startCol, int endRow, int endCol) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f));
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> gScore = new HashMap<>();
    
        Node startNode = new Node(startRow, startCol);

        startNode.h = heuristic(startRow, startCol, endRow, endCol);
        startNode.f = startNode.h;

        openSet.add(startNode);
        gScore.put(startNode, 0.0);
    
        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
    
            if (currentNode.col == endCol && currentNode.row == endRow) {
                return reconstructPath(cameFrom, currentNode);
            }
    
            for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                int neighborRow = currentNode.row + direction[0];
                int neighborCol = currentNode.col + direction[1];
    
                if (!isWalkable(grid, neighborRow, neighborCol)) {
                    continue;
                }
    
                Node neighborNode = new Node(neighborRow, neighborCol);
     
                double tentativeGScore = gScore.getOrDefault(currentNode, Double.MAX_VALUE) + 1;
    
                if (tentativeGScore < gScore.getOrDefault(neighborNode, Double.MAX_VALUE)) {
                    cameFrom.put(neighborNode, currentNode);
                    gScore.put(neighborNode, tentativeGScore);
                    double h = heuristic(neighborRow, neighborCol, endRow, endCol);
                    neighborNode.g = tentativeGScore;
                    neighborNode.h = h;
                    neighborNode.f = tentativeGScore + h;
    
                    if (addOrUpdateNode(openSet, neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }
    
        return Collections.emptyList(); // No path found
    }
    
    private static List<Node> reconstructPath(Map<Node, Node> cameFrom, Node currentNode) {
        List<Node> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = cameFrom.get(currentNode);
        }
        Collections.reverse(path);
        return path;
    }
    private static boolean isWalkable(int[][] grid, int row, int col) {
        return col >= 0 && row >= 0 && row < grid.length && col < grid[0].length && grid[row][col] == 1;
    }

    private static double heuristic(int row1, int col1, int row2, int col2) {
        // Using Manhattan distance as heuristic
        return Math.abs(col1 - col2) + Math.abs(row1 - row2);
    }


}


