package States;

import Main.StateManager;
import Structures.Block;
import Utilities.BFS;

import java.awt.Graphics2D;
import java.io.IOException;

/**
 * Auto state class used in auto mode of wheelchair
 * @author autowheels
 */
public class Auto extends State
{
    /**
     * Destination block of the wheel chair
     */
    private Block Destination;
    /**
     * AutoHandler gives the required directions to the chair according to
     * the shortest path
     */
    private AutoHandler autohandler;


    /**
     * Default constructor
     *
     * @param name
     * @param sm
     */
    public Auto(String name, StateManager sm)
    {
        super(name);
    }


    public void initAuto(StateManager sm, Block Destination) throws IOException
    {

        this.Destination = Destination;
        if(Destination!=null)
        {
            this.autohandler = new AutoHandler(BFS.getPath(sm.getCurrentBlock(), Destination, sm.map));

        }
    }

    /**
     * Moves the wheelchair according to the instructions from AutoHandler
     *
     * @param sm
     */
    public void move(StateManager sm)
    {
        autohandler.getNextInstruction(sm);
    }

    @Override
    /**
     * Checks if destination is reached.If reached it switches to manual mode
     */
    public void tick(StateManager sm)
    {
        if(Destination!=null)
        {
            if(autohandler.nopath(sm))
            {
                sm.setState("manual");
            }
            else
            {
                if (!autohandler.checkDest(sm))
                {
                    super.tick(sm);
                }
                else
                {
                    sm.setState("manual");
                }
            }
        }
    }

    @Override
    public void render(StateManager sm, Graphics2D g2d)
    {
        super.render(sm,g2d);
        Block[] torender = autohandler.Path;
        for(int i=0;i<torender.length;i++)
        {
            torender[i].render(g2d,true);
        }
    }

    public Block[] getPath()
    {
        return autohandler.Path;
    }
}
