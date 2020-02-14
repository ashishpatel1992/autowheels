package Main;

import javax.swing.JFrame;
import java.io.IOException;

/**
 * Main class which runs the entire system
 * @author autowheels
 */
public class Main
{

    /**
     * Calculations per second - number of times tick() is called in 1 second
     */


    /**
     * Main method instantiates the StateManager.Using time measurements
     * we make sure that tick() and render() methods are called certain number of time
     * every second.
     *
     * @param args
     */

        public static void main(String[] args) throws IOException
        {
            Screen screen = new Screen();
            JFrame frame = new JFrame(screen.TITLE);
            frame.add(screen);
            frame.setSize(screen.WIDTH, screen.HEIGHT);  //setsize before visible
            frame.setResizable(true);
            frame.setFocusable(true);
            frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            screen.start();
        }


}
