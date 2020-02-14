package IO;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private static final boolean[] keys = new boolean[256];
    private static final boolean[] oldkeys = new boolean[256];

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        keys[e.getKeyCode()]=true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        super.keyReleased(e);
        keys[e.getKeyCode()]=false;
    }

    public static void update()
    {
        for(int i=0;i<256;i++)
        {
            oldkeys[i]=keys[i];
        }
    }

    public static boolean iskeydown(int key)
    {
        return keys[key];
    }

    public static boolean wasPressed(int key)
    {
        return iskeydown(key)&&!oldkeys[key];
    }
    public static boolean wasReleased(int key)
    {
        return !iskeydown(key)&&oldkeys[key];
    }

}
