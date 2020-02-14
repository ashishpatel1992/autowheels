package IO;

import Structures.Map;
import Utilities.Render;

import java.awt.image.BufferedImage;
/**
 * Manages giving output to phone
 * @author autowheels
 */
public class OutputToPhone
{
    /**
     * Sends the current map to the phone
     *
     * @param roomMap Current map of the room
     * @return true if successfully sent
     */
    public static boolean SendMap(Map roomMap)
    {
        BufferedImage img = Render.render(roomMap);
        //send
        return true;
    }

    /**
     * Sends data in numerical form to the phone
     */
    public static void sendData()
    {

    }
}
