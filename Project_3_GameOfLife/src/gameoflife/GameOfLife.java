/**
 *  Description: “Game of Life” simulation.
 *                Picture a fictitious alien ghost town with 10 houses in a circle.
 *                They are labeled from 0 to 9.
 *
 *  Author:  Teodor Yanchev
 *  Due Date: 11-11-2023
 *  Pledged: I wrote this code. Help was found by googling, stackoverflow.com, youtube.com,
 *  geeksforgeeks.org, docs.oracle.com, trigonometry class.
 *
 */
package gameoflife;

import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author
 */
public class GameOfLife extends GraphicsProgram {

    /**
     * Number of villagers
     */
    private static final int NUM_VILLAGERS = 10;

    /**
     * Width of the game display (all coordinates are in pixels)
     */
    private static final int WIDTH = 600;
    /**
     * Height of the game display
     */
    private static final int HEIGHT = 600;
    /**
     * Size of each cell
     */
    private static final int CELL_SIZE = 20;
    /**
     * X-coordinate on the screen
     */
    private static final int START_X = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] sizeArgs = {"width=" + WIDTH, "height=" + HEIGHT};
        new GameOfLife().start(sizeArgs);
    }

    // TODO: write your 4 methods here
    // Fill the array with values so that all elements except for the middle one are false,
    // and the middle element is true.
    public static void initializeGame(boolean[] array) {

        //for (int i = 0; i < array.length; i++) {
        //  array[i] = false;
        //}
        // This method assigns the specified data type value
        // to each element of the specified range of the specified array.
        Arrays.fill(array, false);
        array[array.length / 2] = true;
    }

    public static void calculateNextGeneration(boolean[] currentGeneration) {
        boolean[] nextGeneration = new boolean[currentGeneration.length];

        for (int i = 0; i < currentGeneration.length; i++) {
            int neighborsAlive = countNeighborsAlive(currentGeneration, i);

            // If the current villager is alive
            if (currentGeneration[i]) {
                if (neighborsAlive == 1) {
                    nextGeneration[i] = false; // The current villager is dead in the next generation.
                } else {
                    nextGeneration[i] = true; // The current villager is alive in the next generation.
                }
            } else {
            // If the current villager is dead
                if (neighborsAlive == 1) {
                    nextGeneration[i] = true; // The current villager is alive in the next generation.
                } else {
                    nextGeneration[i] = false; // The current villager is dead in the next generation.
                }
            }
        }

        // This was suggested from NeatBeans, and as I looked into the documentation it is simplified
        // fucntion of the code down below.
        // The only thing is that we are giving first the array we want to copy,
        // then we are giving the aray on which we want to copy it with the length.
        
        // 'nextGeneration' array  is copied back to 'currentGeneration' array updating the state for
        // the next generation.
        System.arraycopy(nextGeneration, 0, currentGeneration, 0, currentGeneration.length);

        //for (int i = 0; i < currentGeneration.length; i++) {
        //    currentGeneration[i] = nextGeneration[i];
        //} 
    }
    
    // This method is responsive for visuallizing the game on the screen
    public void drawGame(boolean[] array, int y) {
        for (int i = 0; i < array.length; i++) {
            // Creating a rectangle with widht 20, height 20.
            // The position is determined by START_X and CELL_SIZE.
            // The index 'i' plays a crucial role in that.

            GRect cell = new GRect(START_X + i * CELL_SIZE, y, CELL_SIZE, CELL_SIZE); 
            
            if (array[i]) {
                cell.setFilled(true);
                cell.setFillColor(Color.RED); // Filling the color, if alive

            } else {
                cell.setFilled(false);
                cell.setFillColor(Color.BLACK); // Filling the side lines, if dead
            }

            add(cell);
        }
    }

    // This method runs the Game of Life for the specified number of generations.
    public void runGameOfLife(boolean[] array, int generations) {
        for (int i = 0; i < generations; i++) {
            drawGame(array, i * CELL_SIZE);
            calculateNextGeneration(array);
        }
    }

    // This method count the number of neighbors alive for a given index in the array.
    private static int countNeighborsAlive(boolean[] array, int index) {
        int neighborsAlive = 0;

        if (array[(index - 1 + array.length) % array.length]) {
            neighborsAlive++; // Check for the LEFT NEIGHBOR
        }
        if (array[(index + 1) % array.length]) {
            neighborsAlive++; // Check for the RIGHT NEIGHBOR
        }

        return neighborsAlive;
    }

    /**
     * Called by the system once the app is started.
     */
    @Override
    public void run() {
        boolean[] villagers = new boolean[NUM_VILLAGERS];

        initializeGame(villagers);
        runGameOfLife(villagers, 5);
    }
}
