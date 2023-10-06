/**
 *  Name of the program: Breakout
 *  Project: No:2
 *  Author:  Teodor Yanchev
 *  Due Date: September 30 at 11:59 PM
 *  Pledged: I wrote this code. Help was found by googling, stackoverflow.com, youtube.com, trigonometry class.
 *
 */
package breakout;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 *
 * @author Teodor Yanchev
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
     * Height of the ball
     */
    private static final int BALL_HEIGHT = 2 * BALL_RADIUS;
    /**
     * Width of the ball
     */
    private static final int BALL_WIDTH = 2 * BALL_RADIUS;
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
     * The ball. Used for controlling the ball object in the game.
     */
    private GOval ball;

    /**
     * The X coordinate of the paddle when moving the mouse;
     */
    private double paddleX;

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
        // Mouse X coordinate
        double mouseX = me.getX();

        // The paddle's y coordinate 
        double paddleY = paddle.getY();

        // This line calculates the x-coordinate of the paddle based on the mouse position.
        // Positions the paddle so that it's centered around the mouse cursor.
        paddleX = mouseX - PADDLE_WIDTH / 2;

        // This block of code checks and adjusts the paddle going out of boundries.
        // Making sure it doesn't go off the right or left edge
        if (paddleX < 0) {
            paddleX = 0;
        } else if (paddleX + PADDLE_WIDTH > WIDTH) {
            paddleX = WIDTH - PADDLE_WIDTH;
        }

        // This line sets the new position of the paddle
        paddle.setLocation(paddleX, paddleY);

    }

    /**
     * Method: run() Runs the Breakout program.
     */
    public void run() {
        boolean isDone = false;

        // Setting up the bricks
        for (int row = 0; row < NBRICK_ROWS; row++) {
            for (int column = 0; column < NBRICKS_PER_ROW; column++) {
                /**
                 * To get the x coordinate for the starting width: start at the
                 * center width, subtract half of the bricks (width) in the row,
                 * subtract half of the separations (width) between the bricks
                 * in the row, now you're at where the first brick should be, so
                 * for the starting point of the next bricks in the column, you
                 * need to: add a brick width add a separation width
                 */
                double x = getWidth() / 2 - (NBRICKS_PER_ROW * BRICK_WIDTH) / 2 - ((NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2 + column * BRICK_WIDTH + column * BRICK_SEP;

                /**
                 * To get the y coordinate of the starting height: start at the
                 * given length from the top for the first row, then add a brick
                 * height and a brick separation for each of the following rows
                 */
                double y = BRICK_Y_OFFSET + row * BRICK_HEIGHT + row * BRICK_SEP;

                GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
                add(brick);
                brick.setFilled(true);
                brick.setFillColor(Color.RED);
            }
        }

        // Theis line of codes is setting up the paddle coordinates,
        // filling the paddle and adding it to the screen
        int paddleXCoordinate = (WIDTH - PADDLE_WIDTH) / 2;
        int paddleYCoordinate = (HEIGHT - PADDLE_Y_OFFSET * 2);
        paddle = new GRect(paddleXCoordinate, paddleYCoordinate, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFilled(true);
        add(paddle);

        // Create the ball and position it in the center
        double ballX = WIDTH / 2 - BALL_RADIUS;
        double ballY = HEIGHT / 2 - BALL_RADIUS;

        ball = new GOval(ballX, ballY, BALL_WIDTH, BALL_HEIGHT);
        ball.setFilled(true);
        add(ball);
        // This sets up the program so that mouseMoved() is called when the mouse moves.
        addMouseListeners();

        // this is the "run loop", which runs until isDone becomes true.
        // You figure out the logic for isDone.
        Random randGen = new Random();
        double vX = randGen.nextDouble() * 3 + 1;
        double vY = 3;

        while (!isDone) {
            ball.move(vX, vY);

            double xBall = ball.getX();
            double yBall = ball.getY();

            if (xBall < 0 || xBall > WIDTH - BALL_WIDTH) {
                vX = -vX;
            }

            if (yBall < 0 || yBall > HEIGHT - BALL_HEIGHT) {
                vY = -vY;
            }

            pause(PAUSE_TIME);
        }

        // TODO: write any code that happens when the game is over.
    }

}
