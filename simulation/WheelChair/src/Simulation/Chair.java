package Simulation;

import Main.StateManager;
import Structures.Coordinate;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chair
{
    BufferedImage chairicon;

    {
        try
        {
            chairicon = ImageIO.read(new File("./resources/textures/chair.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void drawRotate(Graphics2D g2d, double x, double y, int angle,BufferedImage im)
    {
        g2d.translate((float)x,(float)y);
        g2d.rotate(-Math.toRadians(angle));
        g2d.drawImage(im,-16,-16,null);
        g2d.rotate(Math.toRadians(angle));
        g2d.translate(-(float)x,-(float)y);
    }
    public void render(StateManager sm, Graphics2D g2)
    {
        Coordinate p = sm.getCurrentCoordinate();
        double x = p.getX_coordinate();
        double y = p.getY_cooordinate();
        float angle = sm.getCurrentChairAngle();
        drawRotate(g2,x,y,(int)angle,chairicon);

    }
}
