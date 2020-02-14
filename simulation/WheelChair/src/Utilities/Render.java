package Utilities;

import Structures.Map;

import java.awt.image.BufferedImage;

/**
 * Converts Map data to Image
 * @author autowheels
 *
 */
public class Render
{
    /**
     * This method converts the Map to image format using Modifications of pixel data
     * in a Buffered Image.
     *
     * @param m1 Takes the Map as input
     * @return returns the map in BufferedImage format
     */
    public static BufferedImage render(Map m1)
    {
        BufferedImage img = new BufferedImage(m1.getDimension_x(), m1.getDimension_y(), BufferedImage.TYPE_INT_RGB);
        //do something to the image
        return img;
    }
}
