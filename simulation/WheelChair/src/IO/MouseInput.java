package IO;

import Structures.Coordinate;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{


    public static boolean buttons[] = new boolean[5];
    public static boolean oldbuttons[] = new boolean[5];
    private static int mx=0;
    private static int my=0;
    private static int oldmx=0;
    private static int oldmy=0;
    private static boolean ismoved=false;

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        super.mousePressed(e);
        buttons[e.getButton()]=true;
        System.out.println(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        super.mouseReleased(e);
        buttons[e.getButton()]=false;
    }

    static int mousex;
    static int mousey;
    @Override
    public void mouseClicked(MouseEvent e) {
         mousex=e.getX();
         mousey=e.getY()+40;
//        System.out.println(mousex+","+mousey);//these co-ords are relative to the component
    }


    public static Coordinate getclickcr(){

            Coordinate c = new Coordinate(mousex,mousey);
            System.out.println("mouse clicker cordinates "+mousex+" "+mousey);
            return c;

    }


    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        super.mouseMoved(e);
        mx = e.getX();
        my = e.getY();
    }

    public static void update()
    {
        for(int i=0;i<5;i++)
        {
            oldbuttons[i]=buttons[i];
        }
        if(oldmx==mx && oldmy==my)
        {
            ismoved=false;
        }
        else
        {
            ismoved=true;
        }
        oldmx=mx;
        oldmy=my;
    }

    public static boolean isdown(int key)
    {
        return buttons[key];
    }

    public static boolean waspressed(int key)
    {
        return isdown(key)&&!oldbuttons[key];
    }
    public static boolean wasreleased(int key)
    {
        return !isdown(key)&&oldbuttons[key];
    }
    public static boolean wasmoved()
    {
        return ismoved;
    }

    public static int getmx()
    {
        return mx;
    }

    public static int getmy()
    {
        return my;
    }

}
