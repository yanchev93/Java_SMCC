package stargazer;

/**
 *
 * @author teoya
 */
public class Star {

    private double xCoordinate;

    private double yCoordinate;

    private double magnitude;

    private int henryDraperId;

    private String starName;

    /**
     * Initializes a new instance of the Star class with the given parameters.
     *
     * @param xCoordinate The x-coordinate of the star in the star catalog.
     * @param yCoordinate The y-coordinate of the star in the star catalog.
     * @param henryDraperId The Henry Draper identification number, a unique identifier for the star.
     * @param magnitude The magnitude or brightness of the star.
     *
     */
    public Star(double xCoordinate, double yCoordinate, int henryDraperId, double magnitude) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.henryDraperId = henryDraperId;
        this.magnitude = magnitude;
        this.starName = "";
    }

    /**
     * Initializes a new instance of the Star class with the given parameters.
     *
     * @param xCoordinate The x-coordinate of the star in the star catalog.
     * @param yCoordinate The y-coordinate of the star in the star catalog.
     * @param henryDraperId The Henry Draper identification number, a unique identifier for the
     * star.
     * @param magnitude The magnitude or brightness of the star.
     * @param starName The name or names of the star (may be empty).
     *
     */
    public Star(double xCoordinate, double yCoordinate, int henryDraperId, double magnitude, String starName) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.henryDraperId = henryDraperId;
        this.magnitude = magnitude;
        this.starName = starName;
    }

    public double getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public int getHenryDraperId() {
        return henryDraperId;
    }

    public void setHenryDraperId(int henryDraperNumber) {
        this.henryDraperId = henryDraperNumber;
    }

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    //How it should look - 0.512379,0.020508,432,2.28,CAPH; CAS BETA
    /**
     * Returns a string representation of the Star object.
     *
     * @return A string containing the star's properties.
     *
     */
    @Override
    public String toString() {
        return String.format("%.6f,%.6f,%d,%.2f,%s",
                xCoordinate, yCoordinate, henryDraperId, magnitude, starName);
    }
}
