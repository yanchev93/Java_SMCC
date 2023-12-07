/**
 *  Description: description of program
 *  Author:  your name
 *  Due Date:
 *  Pledged:
 *
 */
package stargazer;

import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author
 */
public class StarGazer extends GraphicsProgram {

    /**
     * Width/height of the display (all coordinates are in pixels)
     */
    public static final int WINDOW_SIZE = 800;

    /**
     * ArrayList of the stars in the data file
     */
    // TODO: uncomment this once you have written the Star class.
    private ArrayList<Star> stars;
    /**
     * ArrayList of the constellations in the various data files
     */
    private ArrayList<Constellation> constellations;

    /**
     * Leave this as it is; you will work in the run() method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] sizeArgs = {"width=" + WINDOW_SIZE, "height=" + WINDOW_SIZE};
        new StarGazer().start(sizeArgs);
    }

    // TODO: write your helper methods here
    public GPoint coordsToPixel(double xCoordinate, double yCoordinate, double pixelPicture) {

        double pixelX = (xCoordinate + 0.5) * pixelPicture / 2.0;
        double pixelY = (0.5 - yCoordinate) * pixelPicture / 2.0;

        return new GPoint(pixelX, pixelY);
    }

    public void plotSquare(GPoint gPoint, int size, Color color) {
        double x = gPoint.getX() - size / 2.0;  // Calculate top-left x-coordinate
        double y = gPoint.getY() - size / 2.0;  // Calculate top-left y-coordinate

        GRect square = new GRect(x, y, size, size);
        square.setFilled(true);
        square.setColor(color);

        // add the square to the screen
    }
    
    public void readStars(String starstxt, ArrayList<Star> stars) {
        try {
            // create the new file input stream and attach a Scanner to it.
            FileInputStream fis = new FileInputStream(starstxt);
            Scanner s = new Scanner(fis);
            // make an ArrayList to hold the Strings with the star pairs (see data file)
            // We have to make an ArrayList here because the Constellation class
            // has an ArrayList as a property, and requires that we pass it
            // to the constructor when creating a new Constellation.

            // keep reading until we get to the end of the file
            while (s.hasNext()) {
                String[] inputFromFile = s.nextLine().split(" ");

                double xCoordinate = Double.parseDouble(inputFromFile[0]);
                double yCoordinate = Double.parseDouble(inputFromFile[1]);
                int henryDraperId = Integer.parseInt(inputFromFile[3]);
                double magnitude = Double.parseDouble(inputFromFile[4]);

                Star currentStar;
                if (inputFromFile.length < 7) {
                    currentStar = new Star(xCoordinate,
                            yCoordinate,
                            henryDraperId,
                            magnitude);
                } else {
                    StringBuilder sb = new StringBuilder(); 
                    for (int i = 6; i < inputFromFile.length; i++) {
                        sb.append(" ");
                        sb.append(inputFromFile[i]);
                    }
                    
                    String starName = sb.toString();
                    currentStar = new Star(xCoordinate,
                            yCoordinate,
                            henryDraperId,
                            magnitude,
                            starName);
                }
                
                stars.add(currentStar);
                
            }

            // close the file stream now that we're done
            fis.close();

        } catch (IOException ex) {
            Logger.getLogger(StarGazer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Read a constellation's data and add a new Constellation to the ArrayList.
     * The name of the constellation is not part of the data file, so it is
     * given here as a parameter.
     *
     * @param filename - file with star pairs, such as "Cas_lines.txt"
     * @param constellationName - for example, "Cassiopeia"
     * @param constellations - ArrayList to add the Constellation object to
     */
    public void readConstellation(String filename, String constellationName, ArrayList<Constellation> constellations) {
        try {
            // create the new file input stream and attach a Scanner to it.
            FileInputStream fis = new FileInputStream(filename);
            Scanner s = new Scanner(fis);
            // make an ArrayList to hold the Strings with the star pairs (see data file)
            // We have to make an ArrayList here because the Constellation class
            // has an ArrayList as a property, and requires that we pass it
            // to the constructor when creating a new Constellation.
            ArrayList<String> starPairs = new ArrayList();
            // keep reading until we get to the end of the file
            while (s.hasNext()) {
                String oneLine = s.nextLine();
                // trim off whitespace and newlines
                oneLine = oneLine.trim();
                // add to the list of star pairs
                starPairs.add(oneLine);
            }
            // make a Constellation and add it to the list of constellations
            // passed in as a parameter.
            Constellation c = new Constellation(constellationName, starPairs);
            constellations.add(c);

            // close the file stream now that we're done
            fis.close();

        } catch (IOException ex) {
            Logger.getLogger(StarGazer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Called by the system once the app is started.
     */
    @Override
    public void run() {
        // black background
        setBackground(Color.BLACK);

        // initialize the ArrayLists for Star and Constellation objects
        // TODO: uncomment this when you have implemented the Star class.
        // stars = new ArrayList();
        constellations = new ArrayList();

        // read constellations
        readConstellation("BigDipper_lines.txt", "Big Dipper", constellations);
        readConstellation("Bootes_lines.txt", "Bootes", constellations);
        readConstellation("Cas_lines.txt", "Cassiopeia", constellations);
        readConstellation("Cyg_lines.txt", "Cygnus", constellations);
        readConstellation("Gemini_lines.txt", "Gemini", constellations);
        readConstellation("Hydra_lines.txt", "Hydra", constellations);
        readConstellation("UrsaMajor_lines.txt", "Ursa Major", constellations);
        readConstellation("UrsaMinor_lines.txt", "Ursa Minor", constellations);

        // TODO: call your methods here
    }

    
    
}
