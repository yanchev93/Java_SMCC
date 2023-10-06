package breakout;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author vgreen
 */
public class Breakout extends GraphicsProgram {

    /**
     * Width of the game display (all coordinates are in pixels)
     */
    private static final int WIDTH = 400;
    /**
     * Height of the game display
     */
    private static final int HEIGHT = 600;
    /**
     * Width of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    /**
     * Height of the paddle
     */
    private static final int PADDLE_HEIGHT = 10;
    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;
    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;
    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;
    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;
    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH
            = WIDTH / NBRICKS_PER_ROW - BRICK_SEP;
    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;
    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;
    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;
    /**
     * Number of turns
     */
    private static final int NTURNS = 3;
    /**
     * Pause between each step in the animation
     */
    private static final int PAUSE_TIME = 10;

    /**
     * The paddle. You are responsible for drawing this in run(). When you
     * create the GRect for the paddle, store it in this variable (you don't
     * have to make a new paddle variable). Because it is created here, in the
     * class and not inside a method, both the mouseMoved() method and the run()
     * method can access it.
     */
    private GRect paddle;

    /**
     * Runs the program as an application. This method differs from the simplest
     * possible boilerplate in that it passes parameters to specify the
     * dimensions of the court. You do not have to add any code to this method.
     */
    public static void main(String[] args) {
        String[] sizeArgs = {"width=" + WIDTH, "height=" + HEIGHT};
        new Breakout().start(sizeArgs);
    }

    /**
     * Method: mouseMoved() is called whenever the mouse moves inside the app
     * window. Don't worry about how this happens for now, just know that this
     * is the place where you can put code that will happen every time the mouse
     * moves. Note: in the run() method you will see an addMouseListeners()
     * line. That line is what makes mouseMoved() work here.
     */
    @Override
    public void mouseMoved(MouseEvent me) {
        // The paddle does not move in the y direction, so you only need to 
        // pay attention to the mouse's x coordinate.
        // Change the variable name if you wish.
        int mouseX = me.getX();

        // the paddle's y coordinate 
        int paddleY = paddle.getY();

        // TODO: you write this code
        // A GObject (and also a GRect, which is a GObject) have a
        // setLocation() method that takes 2 doubles for x and y.
        // Use paddle.setLocation() here to set the paddle's x,y location.
    }

    /**
     * Method: run() Runs the Breakout program.
     */
    public void run() {
        boolean isDone = false;
        // TODO: add variables here as necessary.

        // TODO: write any necessary setup code.
        
        // This sets up the program so that mouseMoved() is called when the
        // mouse moves.
        addMouseListeners();

        // this is the "run loop", which runs until isDone becomes true.
        // You figure out the logic for isDone.
        while (!isDone) {

            // TODO: write any code that happens over and over as the game plays.
            // adjust the value of PAUSE_TIME to change the animation speed.
            pause(PAUSE_TIME);
        }

        // TODO: write any code that happens when the game is over.
    }

}
