package States;

import IO.InputFromGyro;
import IO.OutputToPhone;
import Main.StateManager;
import Structures.Block;
import Structures.Coordinate;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.HashMap;

import static java.lang.StrictMath.floor;
import static java.lang.StrictMath.toRadians;

/**
 * Abstract state class containing basic state methods
 * @author autowheels
 */
public abstract class State
{
    /**
     * hash map of all the possible states. Keys are the names of the states
     * and values are the states
     */
    public static HashMap<String, State> States = new HashMap<>();
    /**
     * Name of the state
     */
    protected String name;

    /**
     * Initializes the name of the state. Adds the state to the HashMap of states
     *
     * @param name Name of the state
     */
    public State(String name)
    {
        this.name = name;
        States.put(this.name.toUpperCase(), this);
        System.out.println("this is called");
    }

    /**
     * Updates the position of wheelchair.
     * The distance travelled by wheelchair is calculated by :
     * distance = WHEEL_RADIUS*(changeofWheelAngle/360) where change of wheel angle
     * is difference between new and old wheel angle.
     * <p>
     * The current coordinates of the wheelchair are calculated using
     * newx = oldx + distance * cos(wheelchairangle)
     * newy = oldy + distance * sin(wheelchairangle)
     * <p>
     * New block coordinates are calculated using:
     * xblock = newx/BLOCK_SIZE+1
     * yblock = newy/BLOCK_SIZE+1
     *
     * @param sm StateManager
     */
    public void update(StateManager sm)
    {
        float newChairAngle = sm.chairgyro.getCurrentangle();
        float newWheelAngle = sm.wheelgyro.getCurrentangle();
        float oldChairAngle = sm.getCurrentChairAngle();
        float oldWheelAngle = sm.getCurrentWheelAngle();
        float changeofWheelAngle = newWheelAngle - oldWheelAngle;
        if (changeofWheelAngle < 0)
        {
            changeofWheelAngle = newWheelAngle + 360 - oldWheelAngle;
        }
        float distance = sm.WHEEL_RADIUS * (changeofWheelAngle / 360);
        Coordinate c = sm.getCurrentCoordinate();
        double newx = c.getX_coordinate() + distance * Math.cos(toRadians(newChairAngle));
        double newy = c.getY_cooordinate() - (distance * Math.sin(toRadians(newChairAngle)));
        c = new Coordinate(newx, newy);
        sm.setCurrentCoordinate(c);
        int blockx = (int) floor(newx / (double) sm.BLOCK_SIZE) ;
        int blocky = (int) floor(newy / (double) sm.BLOCK_SIZE) ;
        try
        {
            sm.setCurrentBlock(new Block(blockx, blocky));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        sm.setCurrentChairAngle(newChairAngle);
        sm.setCurrentWheelAngle(newWheelAngle);
//        System.out.println("chair angle is "+ sm.getCurrentChairAngle());
//        System.out.println(sm.getCurrentWheelAngle());
    }

    public abstract void move(StateManager sm);

    /**
     * Updates the map and adds the currently discovered block to the map
     *
     * @param sm
     */
    public void updateMap(StateManager sm)
    {
        sm.map.addBlock(sm.getCurrentBlock());
    }

    /**
     * Moves the wheelchair, updates the wheelchair coordinates,updates the map
     *
     * @param sm
     */
    public void tick(StateManager sm)
    {
        move(sm);
        update(sm);
        updateMap(sm);
    }

    /**
     * Sends the map and numerical data to the phone.
     *
     * @param sm
     */
    public void render(StateManager sm, Graphics2D g2d)
    {
        sm.map.render(sm,g2d);

//        OutputToPhone.sendData();
    }

}
