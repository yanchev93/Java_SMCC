/**
 *  Description: Put a brief description of the program here.
 *  Author:  Teodor Yanchev
 *  Due Date: September 16 at 11:59 PM
 *  Pledged: I wrote this code. Help was found by googling, stackoverflow.com, youtube.com, trigonometry class.
 *
 */
package stampit;

import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Teodor Yanchev
 */
public class StampIt extends GraphicsProgram {

    final int CIRCLE_SIZE = 25;
    private int windowWidth;
    private int windowHeight;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new StampIt().start();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        double mouseX = me.getX() - 12.5; // g
        double mouseY = me.getY() - 12.5;

        windowWidth = this.getWidth(); // getting the width of the window that we are working
        windowHeight = this.getHeight(); // getting the height of the window that we are working

        double mouseXqdrt = (me.getX() / (double) this.getWidth()) * 2 - 1; // determing the X-axis for the quadrants, so we can make changes
        double mouseYqdrt = (me.getY() / (double) this.getHeight()) * -2 + 1; // determing the X-axis for the quadrants, so we can make changes

        GOval shape = new GOval(mouseX, mouseY, CIRCLE_SIZE, CIRCLE_SIZE); // initializing the shape

        // Finding the First Quadrant and beggining work with it - First Quadrant -> (1,0)
        if (mouseXqdrt > 0 && mouseYqdrt > 0) {

            // We are doing calcualtions if shape is out of window bounds
            if (mouseX < 0 || mouseY < 0 || shape.getX() + shape.getWidth() > windowWidth || shape.getY() + shape.getHeight() > windowHeight) {

                // Calculating new position within the window bounds
                double newX = Math.max(0, Math.min(windowWidth - shape.getWidth(), shape.getX()));
                double newY = Math.max(0, Math.min(windowHeight - shape.getHeight(), shape.getY()));

                // Set the shape's new position
                shape.setLocation(newX, newY);
            } else {
                // If the shape is within window bounds, create a new shape at mouse coordinates
                shape = new GOval(mouseX, mouseY, CIRCLE_SIZE, CIRCLE_SIZE);
            }

            // Set the Color and Fill it
            shape.setFillColor(Color.PINK);
            shape.setFilled(true);
        } // Finding the Second Quadrant and beggining work with it - Second Quadrant -> (0,1)
        else if (mouseXqdrt < 0 && mouseYqdrt > 0) {

            // We are doing calcualtions if shape is out of window bounds
            if (mouseX < 0 || mouseY < 0 || shape.getX() + shape.getWidth() > windowWidth || shape.getY() + shape.getHeight() > windowHeight) {

                // Calculating new position within the window bounds
                double newX = Math.max(0, Math.min(windowWidth - shape.getWidth(), shape.getX()));
                double newY = Math.max(0, Math.min(windowHeight - shape.getHeight(), shape.getY()));
                shape.setLocation(newX, newY);
            } else {
                // If the shape is within window bounds, create a new shape at mouse coordinates
                shape = new GOval(mouseX, mouseY, CIRCLE_SIZE, CIRCLE_SIZE);
            }

            // Set the Color and Fill it
            shape.setFillColor(Color.RED);
            shape.setFilled(true);
        } // Finding the Third Quadrant and beggining work with it - Third Quadrant -> (-1,0)
        else if (mouseXqdrt < 0 && mouseYqdrt < 0) {

            // We are doing calcualtions if shape is out of window bounds
            if (mouseX < 0 || mouseY < 0 || shape.getX() + shape.getWidth() > windowWidth || shape.getY() + shape.getHeight() > windowHeight) {

                // Calculating new position within the window bounds
                double newX = Math.max(0, Math.min(windowWidth - shape.getWidth(), shape.getX()));
                double newY = Math.max(0, Math.min(windowHeight - shape.getHeight(), shape.getY()));
                shape.setLocation(newX, newY);
            } else {
                // If the shape is within window bounds, create a new shape at mouse coordinates
                shape = new GOval(mouseX, mouseY, CIRCLE_SIZE, CIRCLE_SIZE);
            }

            // Set the Color and Fill it
            shape.setFillColor(Color.BLUE);
            shape.setFilled(true);
        } // Finding the Fourth Quadrant and beggining work with it - Fourth Quadrant -> (0,-1)
        else if (mouseXqdrt > 0 && mouseYqdrt < 0) {

            // We are doing calcualtions if shape is out of window bounds
            if (mouseX < 0 || mouseY < 0 || shape.getX() + shape.getWidth() > windowWidth || shape.getY() + shape.getHeight() > windowHeight) {

                // Calculating new position within the window bounds
                double newX = Math.max(0, Math.min(windowWidth - shape.getWidth(), shape.getX()));
                double newY = Math.max(0, Math.min(windowHeight - shape.getHeight(), shape.getY()));
                shape.setLocation(newX, newY);
            } else {
                // If the shape is within window bounds, create a new shape at mouse coordinates
                shape = new GOval(mouseX, mouseY, CIRCLE_SIZE, CIRCLE_SIZE);
            }

            // Set the Color and Fill it
            shape.setFillColor(Color.ORANGE);
            shape.setFilled(true);
        }

        // this adds the GRect to the app window.
        add(shape);
    }

    @Override
    public void run() {
        double labelX = 0;
        double labelY = 0;
        // TODO: Customize this with your own message, or to give the user instructions.
        final String TOP_MESSAGE = "Yanchev Stamp it!";

        // This line is needed so that your mousePressed() method will run.
        addMouseListeners();

        // You do not need to change any of the code below.  However, read through
        // it to see how the centering was achieved.
        GLabel topLabel = new GLabel(TOP_MESSAGE);
        topLabel.setFont("Times-36");
        // put it right at the top.  The y coordinate of a GLabel is the 
        // "baseline", or the bottom of the text.  So if you set its y to 0
        // the text will be off the top of the window.  Here we get the height
        // of the GLabel and set the y to that, so all of it is visible.
        // 0,0 is the top left corner of the app window.
        labelY = topLabel.getHeight();
        // here we center the label by subtracting the difference between the
        // app width and the label width, and then dividing the space evenly
        // on each side.
        labelX = (this.getWidth() - topLabel.getWidth()) / 2;

        // now we actually set the location of the label using the calculated coords.
        topLabel.setLocation(labelX, labelY);
        add(topLabel);

        // This line is needed so that your mousePressed() method will run.
        addMouseListeners();

        // Draw a line down the middle and across the middle for testing purposes.
        GLine midLine = new GLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
        add(midLine);
        midLine = new GLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
        add(midLine);
    }
}
