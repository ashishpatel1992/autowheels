package Structures;

import Main.StateManager;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Structure for storing the current block of wheel chair
 * @author autowheels
 */
public class Block
{
    /**
     * x co-ordinate of block
     */
    static BufferedImage Bluetile;
    static BufferedImage Redtile;

    static
    {
        try
        {
            Redtile = ImageIO.read(new File("./resources/textures/tile2.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    ;

    static
    {
        try
        {
            Bluetile = ImageIO.read(new File("./resources/textures/tile.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private int x_block;
    /**
     * y co-ordinate of block
     */
    private int y_block;
    /**
     * Whether the block is safe or not;
     */
    private boolean safe;

    /**
     * Constructor -- initializes each block as safe by default
     *
     * @param x x block coordinate
     * @param y y block coordinate
     */
    public Block(int x, int y) throws IOException
    {
        this.x_block = x;
        this.y_block = y;
        this.safe = true;

    }

    /**
     * returns x coordinate of block
     *
     * @return
     */
    public int getX_block()
    {
        return x_block;
    }

    /**
     * sets x coordinate of block
     *
     * @param x_block
     */
    public void setX_block(int x_block)
    {
        this.x_block = x_block;
    }

    /**
     * returns y coordinate of block
     *
     * @return
     */
    public int getY_block()
    {
        return y_block;
    }

    /**
     * sets y coordinate of block
     *
     * @param y_block
     */

    public void setY_block(int y_block)
    {
        this.y_block = y_block;
    }

    /**
     * Returns true if the block is marked safe
     *
     * @return true/false
     */
    public boolean isSafe()
    {
        return safe;
    }

    /**
     * marks the block safe
     */
    public void setSafe()
    {
        this.safe = true;
    }

    /**
     * marks the block unsafe
     */
    public void setUnsafe()
    {
        this.safe = false;
    }

    /**
     * Returns true if current block is at immediate right of b1
     *
     * @param b1
     * @return
     */
    public boolean isRightOf(Block b1)
    {
        return (this.x_block - b1.x_block == 1) && (this.y_block == b1.y_block);
    }

    /**
     * Returns true if current block is at immediate left of b1
     *
     * @param b1
     * @return
     */
    public boolean isLeftOf(Block b1)
    {
        return (b1.x_block - this.x_block == 1) && (this.y_block == b1.y_block);
    }

    /**
     * Returns true if current block is above  b1
     *
     * @param b1
     * @return
     */
    public boolean isAbove(Block b1)
    {
        return (b1.y_block-this.y_block== 1) && (this.x_block == b1.x_block);
    }

    /**
     * Returns true if current block is below b1
     *
     * @param b1
     * @return
     */
    public boolean isBelow(Block b1)
    {
        return (this.y_block-b1.y_block == 1) && (this.x_block == b1.x_block);
    }

    /**
     * Returns true if current block is equal to b1
     *
     * @param b1
     * @return
     */
    public boolean isEqual(Block b1)
    {
        return (b1.y_block == this.y_block) && (this.x_block == b1.x_block);
    }

    @Override
    public boolean equals(Object b1)
    {
        if (!(b1 instanceof Block))
        {
            return false;
        }
        return (((Block) b1).y_block == this.y_block) && (this.x_block == ((Block) b1).x_block);
    }
     public int hashCode()
     {

             int hash = 7;
             Integer key = ((Block) this).getX_block();
             Integer value = ((Block) this).getY_block();
             hash = 31 * hash + (key != null ? key.hashCode() : 0);
             hash = 31 * hash + (value != null ? value.hashCode() : 0);
             return hash;

     }

    int size = StateManager.BLOCK_SIZE;
    public void render(Graphics2D g2d,boolean ispath)
    {
//        System.out.println(this.getX_block());
//        System.out.println(this.getY_block());
        g2d.setColor(Color.blue);

//        g2d.fillRect(this.getX_block()*StateManager.BLOCK_SIZE+20,this.getY_block()*StateManager.BLOCK_SIZE-30, StateManager.BLOCK_SIZE,StateManager.BLOCK_SIZE);
        if(!ispath)
        {
            g2d.drawImage(Bluetile, size * this.getX_block(), size * this.y_block, StateManager.BLOCK_SIZE, StateManager.BLOCK_SIZE, null);
        }
        else
        {
            g2d.drawImage(Redtile, size * this.getX_block(), size * this.y_block, StateManager.BLOCK_SIZE, StateManager.BLOCK_SIZE, null);
        }
//        g2d.fillRect(size*this.getX_block(),size*this.y_block, StateManager.BLOCK_SIZE,StateManager.BLOCK_SIZE);
    }

}
