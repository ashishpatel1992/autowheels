package Main;

import IO.InputFromPhone;
import IO.KeyInput;
import IO.MouseInput;
import Simulation.Chair;
import Simulation.Gyroscope;
import States.Auto;
import States.Manual;
import States.State;
import Structures.Block;
import Structures.Coordinate;
import Structures.Map;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;

/**
 * Central class for the entire system.Manages transition between states,
 * and exchanges data between phone,gyroscope and motor.
 * @author autowheels
 */
public class StateManager
{
    /**
     * Dimension of one block in cm
     */
    public static final int BLOCK_SIZE = 32;
    /**
     * Radius of the wheel in cm
     */
    public static final float WHEEL_RADIUS = 1000;
    public Auto auto;
    public Manual manual;
    public Gyroscope wheelgyro;
    public Gyroscope chairgyro;
    public Chair chair;
    /**
     * Map of the room
     */
    public Map map;
    /**
     * Current coordinate of the Wheelchair
     */
    Coordinate currentCoordinate;
    /**
     * Current block of the Wheelchair
     */
    Block currentBlock;
    /**
     * Current state of the wheelchair (Auto/Manual)
     */
    State CurrentState;
    /**
     * Wheel angle in degrees
     */
    float currentWheelAngle;
    /**
     * Chair angle in degrees
     */
    float currentChairAngle;
     Block[] Autopath;
    /**
     * Constructor sets manual as default state and initializes other fields
     */
    public StateManager() throws IOException
    {
        currentCoordinate = new Coordinate(16, 480-16);
        currentBlock = new Block(0, 0);
        manual = new Manual("manual", this);
        auto = new Auto("auto", this);
        map = new Map();
        wheelgyro = new Gyroscope(0);
        chairgyro = new Gyroscope(0);
        chair = new Chair();
        CurrentState = manual;
    }

    /**
     * Checks inputs from Phone and updates the block,coordinates,state of the chair
     */
    public void tick() throws IOException
    {
        if (KeyInput.wasReleased(KeyEvent.VK_M))
        {
            System.out.println("swicthed to manual");
            setState("manual");
            return;
        }
        if (KeyInput.wasReleased(KeyEvent.VK_A))
        {
            System.out.println("swicthed to atuo");
            Coordinate c = MouseInput.getclickcr();
            System.out.println("current coordinates "+getCurrentCoordinate().getX_coordinate()+" "+getCurrentCoordinate().getY_cooordinate());
            Block destination;
            if(c!=null)
            {
                destination = new Block((int) (c.getX_coordinate() / BLOCK_SIZE), (int) (c.getY_cooordinate() / BLOCK_SIZE));
                System.out.println("destination is " + destination.getX_block()+" "+ destination.getY_block());
            }
            else
            {
                destination=null;
            }
            if(destination!=null)
            {
                System.out.println("this is called");
                auto.initAuto(this, destination);
            }
            setState("Auto");
            Autopath = ((Auto)CurrentState).getPath();
            return;
        }
        CurrentState.tick(this);
    }

    public HashMap<Block,Boolean> getAutoPath()
    {
        HashMap<Block,Boolean> m1 = new HashMap<>();
        if(Autopath!=null)
        {
            for(int i=0;i<Autopath.length;i++)
            {
                m1.put(Autopath[i],true);

            }
            return m1;
        }
        else
            return null;
    }
    /**
     * Converts Map to Image from
     */
    public void render(Graphics2D g2d)
    {
//        System.out.println(CurrentState);
        CurrentState.render(this,g2d);
        chair.render(this,g2d);
//        System.out.println(this.getCurrentCoordinate().getX_coordinate());
//        System.out.println(this.getCurrentCoordinate().getY_cooordinate());
    }

    /**
     * Getter method for current Wheel angle
     *
     * @return current wheel angle
     */
    public float getCurrentWheelAngle()
    {
        return currentWheelAngle;
    }

    /**
     * Setter method for current Wheel angle
     *
     * @param currentWheelAngle Wheel angle
     */
    public void setCurrentWheelAngle(float currentWheelAngle)
    {
        this.currentWheelAngle = currentWheelAngle;
    }

    /**
     * Getter method for current Chair angle
     *
     * @return current chair angle
     */
    public float getCurrentChairAngle()
    {
        return currentChairAngle;
    }

    /**
     * Setter method for current Chair angle
     *
     * @param currentChairAngle chair angle
     */
    public void setCurrentChairAngle(float currentChairAngle)
    {
        this.currentChairAngle = currentChairAngle;
    }

    /**
     * Getter method for current coordinate
     *
     * @return currentCoordinate
     */
    public Coordinate getCurrentCoordinate()
    {
        return currentCoordinate;
    }

    /**
     * Setter method for current coordinate
     *
     * @param currentCoordinate
     */
    public void setCurrentCoordinate(Coordinate currentCoordinate)
    {
        this.currentCoordinate = currentCoordinate;
    }

    /**
     * Getter method for current block
     *
     * @return currentBlock
     */
    public Block getCurrentBlock()
    {
        return currentBlock;
    }

    /**
     * Setter method for current block
     *
     * @param currentBlock
     */
    public void setCurrentBlock(Block currentBlock)
    {
        this.currentBlock = currentBlock;
    }

    /**
     * Sets the current state to the input state if the input is valid
     *
     * @param name Name of the required state
     */
    public void setState(String name)
    {
        if (State.States.containsKey(name.toUpperCase()))
        {
            this.CurrentState = State.States.get(name.toUpperCase());
        }
    }
}
