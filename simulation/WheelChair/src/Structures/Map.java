package Structures;

import Main.StateManager;
import javafx.util.Pair;

import java.awt.Graphics2D;
import java.util.HashMap;

/**
 * Map class stores the currently discovered map and provides methods to
 * add block,change a block to safe,change a block to unsafe
 */

public class Map
{
    /**
     * Stores the current discovered map in form of collection of blocks
     */
    private HashMap<Pair<Integer, Integer>, Block> Data = new HashMap<Pair<Integer, Integer>, Block>();
    /**
     * Breadth of the currently discovered map
     */
    private int dimension_x = 0;
    /**
     * Height of the currently discovered map
     */
    private int dimension_y = 0;


    public boolean Contains(Pair<Integer,Integer> p1)
    {
        return Data.containsKey(p1);
    }

    /**
     * Returns the breadth of the currently discovered map
     *
     * @return dimension_x
     */
    public int getDimension_x()
    {
        return dimension_x;
    }

    /**
     * Returns the height of the currently discovered map
     *
     * @return dimension_y
     */
    public int getDimension_y()
    {
        return dimension_y;
    }

    /**
     * Adds the current block to the Hashmap of blocks
     *
     * @param b current block on which the wheelchair is standing
     */
    public void addBlock(Block b)
    {
        Integer a1 = b.getX_block();
        Integer a2 = b.getY_block();
        this.dimension_x = Math.max(a1, dimension_x);
        this.dimension_y = Math.max(a2, dimension_y);
        Pair<Integer, Integer> p1 = new Pair<Integer, Integer>(a1, a2);
        Data.putIfAbsent(p1, b);
    }

    /**
     * Marks the currentblock as safe
     *
     * @param p1 current block on which the wheelchair is standing
     */
    public void setSafe(Pair<Integer, Integer> p1)
    {
        if (Data.containsKey(p1))
        {
            Block c = Data.get(p1);
            c.setSafe();
        }
    }

    /**
     * Marks the currentblock as unsafe
     *
     * @param p1 current block on which the wheelchair is standing
     */
    public void setUnsafe(Pair<Integer, Integer> p1)
    {
        if (Data.containsKey(p1))
        {
            Block c = Data.get(p1);
            c.setUnsafe();
        }
    }


    public boolean isSafe(Pair<Integer,Integer> p1)
    {
        Block c = Data.get(p1);
        return c.isSafe();
    }

    public void render(StateManager sm, Graphics2D g2d)
    {
        HashMap<Block,Boolean> path = sm.getAutoPath();

            Data.forEach((k, v) -> v.render(g2d, false));
//        if(path!=null)
//        {
//            path.forEach((k, v) -> k.render(g2d, true));
//        }
    }
}
