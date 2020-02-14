

package States;

import IO.OutputtoMotor;
import Main.StateManager;
import Structures.Block;
import Structures.Coordinate;

/**
 * Helps in getting the next instruction for the motor in Auto Mode
 * @author autowheels
 *
 */
public class AutoHandler
{
    /**
     * Calculated Shortest path is stored as an array of blocks in Path
     */
    Block Path[];
    /**
     * The index of block in Path array on which wheelchair is currently standing
     */
    int BlockIndex = 0;


    public boolean nopath(StateManager sm)
    {
        if(Path==null)
            return true;
        return false;
    }

    public AutoHandler(Block[] path)
    {
        this.Path = path;
        this.BlockIndex = 0;
        if(path==null)
        {

        }
    }

    /**
     * Updates the BlockIndex if the wheelchair moves to the next block
     *
     * @param sm StateManager
     */
    boolean special=false;

    private double geodist(Coordinate a,Coordinate b)
    {
        return Math.sqrt(Math.pow(a.getX_coordinate()-b.getX_coordinate(),2)+Math.pow(a.getY_cooordinate()-b.getY_cooordinate(),2));
    }

    public void updateIndex(StateManager sm)
    {
        Coordinate check =  new Coordinate(Path[BlockIndex+1].getX_block()*StateManager.BLOCK_SIZE+16,Path[BlockIndex+1].getY_block()*StateManager.BLOCK_SIZE+16);
        Coordinate current = sm.getCurrentCoordinate();
        System.out.println(geodist(check,current));
        if (geodist(check,current)<=16)
        {
//            System.out.println("current block is" + sm.getCurrentBlock().getX_block()+" "+sm.getCurrentBlock().getY_block());
            BlockIndex++;
        }
        if(BlockIndex==Path.length-1)
        {
            special=true;
        }
    }

    /**
     * Checks if the destination block is reached
     *
     * @param sm Statemanager
     * @return true if destination is reached, false otherwise
     */
    public boolean checkDest(StateManager sm)
    {
        if (sm.getCurrentBlock().equals(Path[Path.length - 1]))
        {
            //checks if currentblock is equal to last block
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Decides the next instruction for the method.Based on the currentblock,
     * current orientation and next block on the shortest path
     *
     * @param sm StateManager
     */
    public void getNextInstruction(StateManager sm)
    {
        updateIndex(sm);
        if(!special)
        {
            System.out.println("currently on " + Path[BlockIndex].getX_block()+" "+Path[BlockIndex].getY_block());
            if (Path[BlockIndex + 1].isRightOf(Path[BlockIndex]))
            {
                if (sm.getCurrentChairAngle() == 0)
                {
                    sm.wheelgyro.incrementcurrentangle(1);
                }
                else if (sm.getCurrentChairAngle() < 180)
                {
                    sm.chairgyro.decrementcurrentangle(1);
                }
                else if (sm.getCurrentChairAngle() >= 180)
                {
                    sm.chairgyro.incrementcurrentangle(1);
                }
                return;
            }
            if (Path[BlockIndex + 1].isLeftOf(Path[BlockIndex]))
            {
                if (sm.getCurrentChairAngle() == 180)
                {
                    sm.wheelgyro.incrementcurrentangle(1);
                }
                else if (sm.getCurrentChairAngle() < 180)
                {
                    sm.chairgyro.incrementcurrentangle(1);
                }
                else if (sm.getCurrentChairAngle() > 180)
                {
                    sm.chairgyro.decrementcurrentangle(1);
                }
                return;
            }
            if (Path[BlockIndex + 1].isAbove(Path[BlockIndex]))
            {
                System.out.println(" is above");
                if (sm.getCurrentChairAngle() == 90)
                {
                    sm.wheelgyro.incrementcurrentangle(1);
                }
                else if (sm.getCurrentChairAngle() < 90 && sm.getCurrentChairAngle()>=0)
                {
                   sm.chairgyro.incrementcurrentangle(1);
                }
                else if (sm.getCurrentChairAngle() <270)
                {
                    sm.chairgyro.decrementcurrentangle(1);
                }
                else
                {
                    sm.chairgyro.incrementcurrentangle(1);
                }
                return;
            }
            if (Path[BlockIndex + 1].isBelow(Path[BlockIndex]))
            {
                if (sm.getCurrentChairAngle() == 270)
                {
                    sm.wheelgyro.incrementcurrentangle(1);
                }
                else if (sm.getCurrentChairAngle() < 90 && sm.getCurrentChairAngle()>=0)
                {
                    sm.chairgyro.decrementcurrentangle(1);
                }
                else if (sm.getCurrentChairAngle() >270 && sm.getCurrentChairAngle()<360)
                {
                    sm.chairgyro.decrementcurrentangle(1);
                }
                else
                {
                    sm.chairgyro.incrementcurrentangle(1);
                }
                return;
            }
        }
        else
        {
            sm.wheelgyro.incrementcurrentangle(1);
        }
    }
}
