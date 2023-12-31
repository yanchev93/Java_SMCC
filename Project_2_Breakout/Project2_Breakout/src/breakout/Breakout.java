/**
 *  Name of the program: Breakout
 *  Project: No:2
 *  Author:  Teodor Yanchev
 *  Due Date: September 30 at 11:59 PM
 *  Pledged: I wrote this code. Help was found by www.geeksforgeeks.org, googling, www.stackoverflow.com, youtube.com, trigonometry class.
 *
 */
package breakout;

import acm.graphics.*;
import acm.program.*;
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
     * Minimum velocity of the ball
     */
    private static final double MIN_VELOCITY = 3.5;
    /**
     * Pause between each step in the animation
     */
    private static final int PAUSE_TIME = 16;

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
     * The X coordinate of the paddle when moving the mouse
     */
    private double paddleX;

    /**
     * The collider object
     */
    private GObject collider;

    /**
     * The life points of the player
     */
    private int lifePoints = NTURNS;

    /**
     * The visualization of the life points that the player have, and win/lose
     * scenario
     */
    private final String TOP_MESSAGE = "Life points left: ";

    /**
     * The label on which we will write a message
     */
    private GLabel topLabel;

    /**
     * Positioning the label X coordinate
     */
    private double labelX;

    /**
     * Positioning the label Y coordinate
     */
    private double labelY;

    private int bricksLeft;

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

        addMouseListeners();

        Random randGen = new Random();
        double vX = randGen.nextDouble() * 3 + 1;
        double vY = 3;

        SetScreenLabel(lifePoints, TOP_MESSAGE);

        bricksLeft = NBRICKS_PER_ROW * NBRICK_ROWS;

        while (!isDone) {
            ball.move(vX, vY);

            // Add minimum velocity threshold to ensures that if the velocity in
            // either the x or y direction falls below MIN_VELOCITY, it will be set to MIN_VELOCITY
            if (Math.abs(vX) < MIN_VELOCITY) {
                vX = Math.signum(vX) * MIN_VELOCITY;
            }
            if (Math.abs(vY) < MIN_VELOCITY) {
                vY = Math.signum(vY) * MIN_VELOCITY;
            }

            double xBall = ball.getX();
            double yBall = ball.getY();

            if (xBall < 0 || xBall > WIDTH - BALL_WIDTH) {
                vX = -vX;
            }

            if (yBall < 0 || yBall > HEIGHT - BALL_HEIGHT) {
                vY = -vY;
            }

            // PART 2
            // Gets the element position
            GObject collisionUpLeft = getElementAt(ball.getX(), ball.getY());
            GObject collisionUpRight = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
            GObject collisionDownLeft = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
            GObject collisionDownRight = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);

            // Checking if collision occur.
            // If collision occured with paddle or label ignores it, otherwise removes the object(the brick/s)
            if (collisionUpLeft != null && collisionUpLeft != paddle && collisionUpLeft != topLabel) {
                collider = collisionUpLeft;

                // Makes the ball bouce of the object it hits, collider equals the brick.
                if (ball.getBounds().intersects(collider.getBounds())) {
                    vY = -vY * 0.8;
                    vX = vX * 0.8;
                }

                // Removes the collided object and remove 1 brick
                remove(collider);
                bricksLeft -= 1;
            } else if (collisionUpRight != null && collisionUpRight != paddle && collisionUpRight != topLabel) {
                collider = collisionUpRight;

                // Makes the ball bouce of the object it hits, collider equals the brick.
                if (ball.getBounds().intersects(collider.getBounds())) {
                    vY = -vY * 0.8;
                    vX = vX * 0.8;
                }
                
                // Removes the collided object and remove 1 brick
                remove(collider);
                bricksLeft -= 1;
            } else if (collisionDownLeft != null && collisionDownLeft != paddle && collisionDownLeft != topLabel) {
                collider = collisionDownLeft;

                // Makes the ball bouce of the object it hits, collider equals the brick.
                if (ball.getBounds().intersects(collider.getBounds())) {
                    vY = -vY * 0.8;
                    vX = vX * 0.8;
                }
                
                // Removes the collided object and remove 1 brick
                remove(collider);
                bricksLeft -= 1;
            } else if (collisionDownRight != null && collisionDownRight != paddle && collisionDownRight != topLabel) {
                collider = collisionDownRight;

                // Makes the ball bouce of the object it hits, collider equals the brick.
                if (ball.getBounds().intersects(collider.getBounds())) {
                    vY = -vY * 0.8;
                    vX = vX * 0.8;
                }

                // Removes the collided object and remove 1 brick
                remove(collider);
                bricksLeft -= 1;
            }

            // Makes the ball bouce of the paddle when collided
            if (ball.getBounds().intersects(paddle.getBounds())) {
                vY = -vY * 0.8;
                vX = vX * 0.8;
            }

            // Ball goes under paddle -> lifePoints -= 1; 
            // if (ball.getY() > paddle.getY() + 20) => this is how i'll do it to be more like a real game
            // Ball touches the bottom line -> lifePoints -= 1;
            if (ball.getY() >= HEIGHT - 30) {
                lifePoints -= 1;

                // Remove the ball and the topLabel from the screen
                remove(ball);
                remove(topLabel);

                // Sets the top label again with updated values
                SetScreenLabel(lifePoints, TOP_MESSAGE);

                // Set the position of the ball in the center again for the next round
                ball = new GOval(ballX, ballY, BALL_WIDTH, BALL_HEIGHT);
                ball.setFilled(true);

                // Add the ball again at the set position
                add(ball);
            }

            // This code checks if the game is over
            if (lifePoints == 0) {
                isDone = true;
            } else if (bricksLeft <= 0) {
                isDone = true;
            }

            pause(PAUSE_TIME);
        }

        // This code checks if the player lost all life points or there is any life points left
        // Prints the appopriate text on screen.
        if (lifePoints > 0) {
            remove(topLabel);
            GameDoneLabel("You are the WINNER!");
        } else {
            remove(topLabel);
            GameDoneLabel("You Lose!");
        }
    }

    private void SetScreenLabel(int lifePoints, String message) {
        topLabel = new GLabel(message + lifePoints);
        topLabel.setFont("Times-36");

        labelY = 0;
        labelX = 0;

        labelY = topLabel.getHeight();

        labelX = (WIDTH - topLabel.getWidth()) / 2;

        topLabel.setLocation(labelX, labelY);
        add(topLabel);
    }

    private void GameDoneLabel(String message) {
        topLabel = new GLabel(message);
        topLabel.setFont("Times-36");

        labelY = 0;
        labelX = 0;

        labelY = topLabel.getHeight();

        labelX = (WIDTH - topLabel.getWidth()) / 2;

        topLabel.setLocation(labelX, labelY);
        add(topLabel);
    }
}
