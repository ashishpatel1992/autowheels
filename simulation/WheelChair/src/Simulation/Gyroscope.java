package Simulation;

public class Gyroscope
{
    private float currentangle;

    public Gyroscope(float a)
    {
        this.currentangle=a;
    }

    public void incrementcurrentangle(float inc)
    {
        this.currentangle+=inc;
        this.currentangle = (this.currentangle)%360;
    }
    public void decrementcurrentangle(float dec)
    {
        this.currentangle-=dec;
        this.currentangle = (this.currentangle+360)%360;
    }
    public float getCurrentangle()
    {
        return this.currentangle;
    }
}
