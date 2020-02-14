package IO;

/**
 * Manages taking input from Gyroscope
 * @author autowheels
 */
public class InputFromGyro
{
    /**
     *
     * Records the current ChairAngle(Angle of chair wrt room)
     */
    static float chairAngle;
    /**
     * Records the current WheelAngle (Angle rotated by wheel)
     */

    static float wheelAngle;

    /**
     * Getter method for chair angle
     *
     * @return chairAngle
     */
    public static float getChairAngle()
    {
        return chairAngle;
    }

    /**
     * Getter method for wheel angle
     *
     * @return wheelAngle
     */
    public static float getWheelAngle()
    {
        return wheelAngle;
    }

    /**
     * Gets the input from Gyroscopes
     */
    public void getInput()
    {
        //getsinput
    }


}
