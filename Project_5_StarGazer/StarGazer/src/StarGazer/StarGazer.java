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
import java.util.Set;
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
    /**
     * Translates star catalog coordinates to pixel coordinates in the app
     * window.
     *
     * @param xCoordinate The x-coordinate of the star in the star catalog.
     * @param yCoordinate The y-coordinate of the star in the star catalog.
     * @param pixelPicture The pixels on the app window.
     * @return A new GPoint containing the x, y location of the star in terms of
     * pixels.
     */
    public GPoint coordsToPixel(double xCoordinate, double yCoordinate, double pixelPicture) {

        double pixelX = (xCoordinate + 1) * pixelPicture / 2.0;
        double pixelY = (1 - yCoordinate) * pixelPicture / 2.0;

        return new GPoint(pixelX, pixelY);
    }

    /**
     * Draws a square of a specified size and color at the given pixel
     * coordinates.The middle of the square should be positioned at the
     * coordinates.
     *
     * @param gPoint The GPoint with (x, y) pixel coordinates.
     * @param starSize The size of a side of the square in pixels.
     * @param color The Color of the square.
     * @return
     */
    public GRect plotSquare(GPoint gPoint, double starSize, Color color) {
        double x = gPoint.getX() - starSize / 2.0;  // Calculate top-left x-coordinate
        double y = gPoint.getY() - starSize / 2.0;  // Calculate top-left y-coordinate

        GRect square = new GRect(x, y, starSize, starSize);
        square.setFilled(true);
        square.setColor(color);

        return square;
    }

    /**
     * Read a star's data from given file and add a new Star to the ArrayList.
     *
     * @param starstxt - file with stars - X and Y coordinates, Henry Draper ID,
     * Magnitude of the star, and name if available
     * @param stars - ArrayList to add the Star object to
     *
     */
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

            // close the file stream
            fis.close();

        } catch (IOException ex) {
            Logger.getLogger(StarGazer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Draws all stars on the display.
     *
     * @param displaySize The size of the display in pixels.
     */
    public void drawStars(int displaySize) {
        if (stars != null) {
            for (Star star : stars) {
                drawStar(star, displaySize);
            }
        }
    }

    /**
     * Draws a single star on the display.
     *
     * @param star The star to be drawn.
     * @param displaySize The size of the display in pixels.
     */
    private void drawStar(Star star, int displaySize) {
        if (star != null) {
            GPoint pixelCoords = coordsToPixel(star.getXCoordinate(), star.getYCoordinate(), displaySize);
            long starSize = Math.round(15.0 / (star.getMagnitude() + 2));

            GRect starRect = plotSquare(pixelCoords, starSize, Color.WHITE);//new GRect(pixelCoords.getX(), pixelCoords.getY(), size, size);

            add(starRect);
        }
    }

    /**
     * Finds a star by its name in the ArrayList of Star objects.
     *
     * @param starName The name of the star to find.
     * @return The Star object with the specified name, or null if not found.
     */
    public Star findStarByName(String starName) {
        if (stars != null && starName != null) {
            for (Star star : stars) {
                if (star.getStarName().contains(starName)) {
                    return star;
                }
            }
        }

        return null;
    }

    /**
     * Plots a constellation by connecting stars with lines.
     *
     * @param displaySize The size of the display in pixels.
     *
     */
    public void plotConstellation(int displaySize) {
        for (Constellation constellation : constellations) {
            ArrayList<String> starPairs = constellation.getStarPairs();

            for (String starPair : starPairs) {
                String[] currStars = starPair.split(",");
                if (currStars.length == 2) {
                    String firstStarName = currStars[0].trim();
                    String secondStarName = currStars[1].trim();

                    Star firstStar = findStarByName(firstStarName);
                    Star secondStar = findStarByName(secondStarName);

                    if (firstStar != null && secondStar != null) {
                        GPoint firstStarPixel = coordsToPixel(firstStar.getXCoordinate(), firstStar.getYCoordinate(), displaySize);
                        GPoint secondStarPixel = coordsToPixel(secondStar.getXCoordinate(), secondStar.getYCoordinate(), displaySize);

                        GLine starConnection = new GLine(firstStarPixel.getX(), firstStarPixel.getY(),
                                secondStarPixel.getX(), secondStarPixel.getY());
                        starConnection.setColor(Color.YELLOW);
                        add(starConnection);
                    }
                }
            }
        }
    }

    /**
     * Finds a constellation by its name
     *
     * @param constellationName The name of the constellation to find.
     * @return The Constellation object with the specified name, or null if not
     * found.
     *
     */
//    public Constellation findConstellationByName(String constellationName) {
//        if (constellations != null && constellationName != null) {
//            for (Constellation constellation : constellations) {
//                if (constellationName.equals(constellation.getName())) {
//                    return constellation;
//                }
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * Plots a line connecting two stars on the display.
//     *
//     * @param firstStar The first star in the pair.
//     * @param secondStar The second star in the pair.
//     * @param displaySize The size of the display in pixels.
//     */
//    private void plotStarConnection(Star firstStar, Star secondStar, int displaySize) {
//        GPoint firstStarPixel = coordsToPixel(firstStar.getXCoordinate(), firstStar.getYCoordinate(), displaySize);
//        GPoint secondStarPixel = coordsToPixel(secondStar.getXCoordinate(), secondStar.getYCoordinate(), displaySize);
//
//        GLine starConnection = new GLine(firstStarPixel.getX(), firstStarPixel.getY(),
//                secondStarPixel.getX(), secondStarPixel.getY());
//        starConnection.setColor(Color.YELLOW);
//        add(starConnection);
//    }

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
        stars = new ArrayList();
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
        int displaySize = WINDOW_SIZE;

        // read stars
        readStars("stars.txt", stars);

        // draw stars
        drawStars(displaySize);

        // plot the constellation
        plotConstellation(displaySize);
    }

}
