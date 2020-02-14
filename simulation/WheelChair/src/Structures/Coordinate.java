package Structures;

/**
 * Structure for storing the accurate position of wheelchair
 * @author autowheels
 */
public class Coordinate
{
    /**
     * current x coordinate of chair in cm
     */
    private double x_coordinate;
    /**
     * current y coordinate of chair in cm
     */
    private double y_cooordinate;

    /**
     * Constructor
     *
     * @param x_coordinate  x coordinate
     * @param y_cooordinate y coordinate
     */
    public Coordinate(double x_coordinate, double y_cooordinate)
    {
        this.x_coordinate = x_coordinate;
        this.y_cooordinate = y_cooordinate;
    }

    /**
     * Returns the x coordinate
     *
     * @return x coordinate
     */
    public double getX_coordinate()
    {
        return x_coordinate;
    }

    /**
     * Sets the x coordinate
     *
     * @param x_coordinate required x coordinate
     */
    public void setX_coordinate(float x_coordinate)
    {
        this.x_coordinate = x_coordinate;
    }

    /**
     * Returns the y coordinate
     *
     * @return y coordinate
     */
    public double getY_cooordinate()
    {
        return y_cooordinate;
    }

    /**
     * Sets the y coordinate
     *
     * @param y_coordinate required y coordinate
     */
    public void setY_cooordinate(float y_coordinate)
    {
        this.y_cooordinate = y_cooordinate;
    }
 public boolean equals(Object o)
 {
     if(o instanceof Coordinate)
     {
         return ((Coordinate) o).x_coordinate==this.x_coordinate && ((Coordinate) o).y_cooordinate==this.y_cooordinate;
     }
     else
     {
         return false;
     }
 }
}
