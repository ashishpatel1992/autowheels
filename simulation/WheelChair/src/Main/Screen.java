package Main;
import IO.KeyInput;
import IO.MouseInput;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Screen extends Canvas implements Runnable {

    public static final String TITLE = "Simulation";
    public static final int WIDTH = 1472/2;
    public static final int HEIGHT = 1088/2;
    private static boolean running;
    private BufferedImage background;
    private StateManager statemanager;
    public static final int CPS = 60;

    public Screen() throws IOException
    {
        MouseInput mi = new MouseInput();
        addMouseListener(mi);
        addMouseMotionListener(mi);
        addKeyListener(new KeyInput());
        statemanager = new StateManager();
        try {
            background = ImageIO.read(new File("./resources/textures/background.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void start()
    {
        if(running) return;
        running=true;
        new Thread(this,"Main").start();

    }
    public static void stop()
    {
        if(!running)return;
        running = false;
    }

    private void tick() throws IOException
    {

        statemanager.tick();

    }

    private void render()
    {
//        System.out.println("screen render is called");
        BufferStrategy bs = getBufferStrategy(); //get buffer comes from canvas
        if(bs==null)
        {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();  //need to send graphics context
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(0,-40);
        g2d.setColor(Color.BLACK);
        g2d.drawImage(background,0,0,null);
        statemanager.render(g2d);

        g2d.dispose();
        bs.show();
    }


    @Override
    public void run() {

        double target=CPS;
        double nsptick = 1000000000/target;
        long lastTime = System.nanoTime();
        boolean canRender = false;
        long elapsedtime=0;
        while(running)
        {


            long now = System.nanoTime();
            elapsedtime+=now-lastTime;
            lastTime=now;

            if(elapsedtime >= nsptick)
            {

                try
                {
                    tick();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                KeyInput.update();
                MouseInput.update();
                canRender=true;
                elapsedtime=0;
            }
            else
            {
                canRender=false;
            }

            if(canRender)
            {
                render();
            }

//			if(System.currentTimeMillis()-lastTimeMilli>1000)
//			{
//				System.out.println(fps);
//				System.out.println(tps);
//				fps=0;
//				tps=0;
//				lastTimeMilli = System.currentTimeMillis();
//			}

        }
        System.exit(0);

    }








}
