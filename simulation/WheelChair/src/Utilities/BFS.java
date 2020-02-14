package Utilities;

import Structures.Block;
import Structures.Map;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Used for calculating the shortest Path using standard Breadth first search.
 * @author autowheels
 *
 */

public class BFS
{
    /**
     * Calculates the shortest path between destination and the source. Standard Breadth
     * first search is used for the calculations as the input map is an unweighted,
     * undirected,connected graph.All the discovered blocks to right,left,up,down are considered
     * adjacent nodes.
     * BFS:
     * A visited map is maintained which checks if a block is already visited
     * by the algorithm or not. Initially the source block is pushed in a queue(FIFO).
     * A while loop runs until the queue is empty.During each iteration of the loop, a block is popped from
     * the queue and its adjacent blocks are pushed in the queue if they are not visited.
     * When they are pushed in the queue, they are marked as visited.Along with this a
     * parent map is maintained which stores the parent block of each block.A parent block
     * is the block after which the currentblock was visited.
     * Pathfound :
     * If the popped block is equal to destination block then the path is found.The loop breaks
     * and the path is generated using backward tracking in ParentMap.
     * PathNotFound:
     * If the queue becomes empty then there is no path.
     *
     * @param current     Current position of wheelchair
     * @param destination Required destination of wheelchair
     * @param m1          Map of the room
     * @return The method returns array of blocks in the shortest path to destination.It returns null if path is not found
     */
    public static Block[] getPath(Block current, Block destination, Map m1) throws IOException
    {
        if(destination==null)
            return null;
        HashMap<Pair<Integer,Integer>, Boolean> visited = new HashMap<>();
        HashMap<Block, Block> parent = new HashMap<>();
        ArrayList<Block> Path = new ArrayList<>();
        Queue<Block> q = new LinkedList<Block>();
        boolean pathfound = false;
        q.add(current);
        Pair<Integer,Integer> p1 = new Pair<>(current.getX_block(),current.getY_block());
        visited.putIfAbsent(p1, true);
        while (!q.isEmpty())
        {
            Block p = q.remove();
            if (p.equals(destination))
            {
                pathfound = true;
                break;
            }
            Pair<Integer, Integer> left = new Pair<Integer, Integer>(p.getX_block()-1,p.getY_block());
            Pair<Integer, Integer> right = new Pair<Integer, Integer>(p.getX_block()+1,p.getY_block());
            Pair<Integer, Integer> up = new Pair<Integer, Integer>(p.getX_block(),p.getY_block()-1);
            Pair<Integer, Integer> down = new Pair<Integer, Integer>(p.getX_block(),p.getY_block()+1);
            if (m1.Contains(left) && !(visited.containsKey(left)))
            {
                Block leftb = new Block(left.getKey(),left.getValue());
                if (m1.isSafe(left))
                {
                    ((LinkedList<Block>) q).add(leftb);
                    parent.put(leftb, p);
                }
                visited.putIfAbsent(left, true);
            }
            if (m1.Contains(right) && !(visited.containsKey(right)))
            {
                Block rightb = new Block(right.getKey(),right.getValue());
                if (m1.isSafe(right))
                {
                    ((LinkedList<Block>) q).add(rightb);
                    parent.put(rightb, p);
                }
                visited.putIfAbsent(right, true);
            }
            if (m1.Contains(up) && !(visited.containsKey(up)))
            {
                Block upb = new Block(up.getKey(),up.getValue());
                if (m1.isSafe(up))
                {
                    ((LinkedList<Block>) q).add(upb);
                    parent.put(upb, p);
                }
                visited.putIfAbsent(up, true);
            }
            if (m1.Contains(down) && !(visited.containsKey(down)))
            {
                Block downb = new Block(down.getKey(),down.getValue());
                if (m1.isSafe(down))
                {
                    ((LinkedList<Block>) q).add(downb);
                    parent.put(downb, p);
                }
                visited.putIfAbsent(down, true);
            }
        }
        if (pathfound)
        {
            parent.put(current, current);
            Block c = destination;
            while (!parent.get(c).equals(c))
            {
                Path.add(c);
                Block p = parent.get(c);
                c = p;
            }
            Path.add(current);
            Block[] answer = Path.toArray(new Block[Path.size()]);
            Collections.reverse(Arrays.asList(answer));
            return answer;
        }
        else
        {
            return null;
        }
    }
}
