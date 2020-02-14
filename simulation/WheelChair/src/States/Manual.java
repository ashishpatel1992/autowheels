package States;

import IO.InputFromPhone;
import IO.KeyInput;
import IO.MouseInput;
import IO.OutputtoMotor;
import Main.StateManager;
import Structures.Coordinate;

import java.awt.event.KeyEvent;

/**
 * Manual State class used in manual mode
 * @author autowheels
 */
public class Manual extends State
{
    /**
     * Default constructor
     *
     * @param name name
     * @param sm   StateManager
     */
    public Manual(String name, StateManager sm)
    {
        super(name);
    }

    /**
     * Decides the next instruction for the motor based on the key
     * pressed on the phone
     *
     * @param sm
     */
    public void move(StateManager sm)
    {

//        if(c!=null)
//        {
//            System.out.println(c.getY_cooordinate());
//            System.out.println(c.getX_coordinate());
//        }
        if (KeyInput.iskeydown(KeyEvent.VK_UP))
        {
            sm.wheelgyro.incrementcurrentangle((float)0.5);
        }
        if (KeyInput.iskeydown(KeyEvent.VK_LEFT))
        {
            sm.chairgyro.incrementcurrentangle(2);
        }
        if (KeyInput.iskeydown(KeyEvent.VK_RIGHT))
        {
            sm.chairgyro.decrementcurrentangle(2);
        }
    }

}
