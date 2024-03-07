/**
 *                Revision History (newest first)
 * <br> 02/04/2024 Implemented printAveragesByDecade; - Teodor Yanchev </br>
 * <br> 01/31/2024 Implemented methods as follow: fAveraging by decade, Analysis
 * of the results - Teodor Yanchev </br>
 * <br> 01/29/2024 Implemented methods as follow: firstFallFrost;
 * printFirstFallFrostDates; - Teodor Yanchev  </br>
 * <br> 01/27/2024 Implemented methods as follow: printHighestTemperature;
 * printLowestTemperature; printAverageHighTemp; printAverageLowTemp;
 * printStartingIndexForEachYear; findStartingIndexes; - Teodor Yanchev </br>
 * <br> 01/26/2024 Implemented methods as follow: findMaxValueIndex;
 * findMinValueIndex; calculateAverage; findFirstIndex - Teodor Yanchev </br>
 * <br> 01/25/2024 Created the project and worked on the run() method - Teodor
 * Yanchev </br>
 */
package climatechange;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author Teodor Yanchev
 */
public class WeatherProcessor {

    // 
    private int dataRecords;
    private int[] month;
    private int[] day;
    private int[] year;
    private int[] tMax;
    private int[] tMin;

    // Constructor
    public WeatherProcessor() {
    }

    /**
     * Calculates the day of the year for a given date using LocalDate.
     *
     * @param year The year.
     * @param month The month (1-12).
     * @param day The day of the month.
     * @return The day of the year for the given date.
     */
    public int getDayOfYear(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        int dayOfYear = date.getDayOfYear();

        return dayOfYear;
    }

    /**
     * Calculates the Julian Day Number for a given date.
     *
     * @param day The day of the month.
     * @param month The month (1-12).
     * @param year The year.
     * @return The Julian Day Number for the given date.
     */
    public double calcDayNumber(int day, int month, int year) {
        double a, b, c, e, f;
        a = year / 100;
        b = a / 4;
        c = 2 - a + b;
        e = 365.25 * (year + 4716);
        f = 30.6001 * (month + 1);
        return c + day + e + f - 1524.5;
    }

    /**
     * Prints average day of year of the first frost by decade.
     *
     * @param startingIndexes An array containing the starting indexes of
     * different "years."
     */
    public void printAveragesByDecade(int[] startingIndexes) {
        int currentDecade = year[0] / 10 * 10;
        //int startYearIndex = 0;
        int sumFrostDays = 0;
        int countYears = 0;
        double dayNum = 0;

        System.out.println("Decade   Average DoY    Approx\n"
                + "       of First Frost    Date");

        for (int i = 0; i < startingIndexes.length; i++) {
            int yearStartIndex = startingIndexes[i];
            int yearEndIndex
                    = (i < startingIndexes.length - 1)
                            ? startingIndexes[i + 1] - 1
                            : year.length - 1;

            int decade = year[yearStartIndex] / 10 * 10;

            if (currentDecade != decade) {
                double averageFrostDays = Math.ceil((double) sumFrostDays / countYears);
                int avgFrostDays = (int) averageFrostDays;

                System.out.printf("%d's      %d", currentDecade, avgFrostDays);
                String printMonth
                        = (month[avgFrostDays] < 10)
                                ? "0" + month[avgFrostDays]
                                : Integer.toString(month[avgFrostDays]);
                String printDay
                        = (day[avgFrostDays] < 10)
                                ? "0" + day[avgFrostDays]
                                : Integer.toString(day[avgFrostDays]);
                System.out.printf("%12s/%s\n", printMonth,
                        printDay);

                currentDecade = decade;
                sumFrostDays = 0;
                countYears = 0;

                int firstFrostIndex = firstFallFrost(tMin, yearStartIndex, yearEndIndex);
                dayNum += calcDayNumber(day[i], month[i], year[i]) - calcDayNumber(1, 1, year[i]);
                int currentYear = year[yearStartIndex];
                int currentMonth = month[firstFrostIndex];
                int currentDay = day[firstFrostIndex];

                int dayOfYear = getDayOfYear(currentYear, currentMonth, currentDay);
                // Accumulate data for the current decade
                sumFrostDays += dayOfYear;
                countYears++;
            } else {

                // Call firstFallFrost to find the index of the first fall frost
                int firstFrostIndex = firstFallFrost(tMin, yearStartIndex, yearEndIndex);

                int currentYear = year[yearStartIndex];
                int currentMonth = month[firstFrostIndex];
                int currentDay = day[firstFrostIndex];

                dayNum += calcDayNumber(currentYear, currentMonth, currentDay)
                        - calcDayNumber(1, 1, year[i]);

                int dayOfYear = getDayOfYear(currentYear, currentMonth, currentDay);
                // Accumulate data for the current decade
                sumFrostDays += dayOfYear;
                countYears++;
            }

        }

        // Print the average for the last decade
        if (countYears > 0) {
//            int averageFrostDays = (int) (sumFrostDays / countYears);
//            System.out.printf("%d's      %d%n", currentDecade, averageFrostDays);
        }
    }

    /**
     * Prints the first fall frost dates for each year.
     *
     * @param startingIndexes An array containing the starting indexes of
     * different "years."
     */
    public void printFirstFallFrostDates(int[] startingIndexes) {
        System.out.println("Year     First Fall Frost");

        // Iterate through each year
        for (int i = 0; i < startingIndexes.length; i++) {
            int yearStartIndex = startingIndexes[i];
            int yearEndIndex
                    = (i < startingIndexes.length - 1)
                            ? startingIndexes[i + 1] - 1
                            : year.length - 1;

            // Call firstFallFrost to find the index of the first fall frost
            int firstFrostIndex = firstFallFrost(tMin, yearStartIndex, yearEndIndex);

            // Print the result
            String printMonth
                    = (month[firstFrostIndex] < 10)
                            ? "0" + month[firstFrostIndex]
                            : Integer.toString(month[firstFrostIndex]);
            String printDay
                    = (day[firstFrostIndex] < 10)
                            ? "0" + day[firstFrostIndex]
                            : Integer.toString(day[firstFrostIndex]);
            System.out.printf("%-8d %6s/%s\n", year[yearStartIndex], printMonth,
                    printDay);

        }

        System.out.println();
    }

    /**
     * Prints the starting index for each year along with the corresponding
     * year.
     *
     * @param startingIndexes An array containing the starting indexes of
     * different "years."
     */
    public void printStartingIndexForEachYear(int[] startingIndexes) {
        System.out.println("Year Starting Index");
        System.out.println("Number of years: " + startingIndexes.length);
        System.out.printf("%-14d%3d\n", year[0], startingIndexes[0]);
        for (int i = 1; i < startingIndexes.length; i++) {
            System.out.printf("%-12d%5d\n", year[i + startingIndexes[i] - 1], startingIndexes[i]);
        }

        System.out.println();
    }

    /**
     * Prints information about the average low temperature.
     *
     * @param averageTmin The average low temperature to be printed.
     */
    public void printAverageLowTemp(double averageTmin) {
        System.out.printf("The average low temperature was %.2f degrees F.\n", averageTmin);
    }

    /**
     * Prints information about the average high temperature.
     *
     * @param averageTmax The average high temperature to be printed.
     */
    public void printAverageHighTemp(double averageTmax) {
        System.out.printf("The average high temperature was %.2f degrees F.\n", averageTmax);
    }

    /**
     * Prints information about the lowest temperature in the data at a specific
     * index.
     *
     * @param minTminIndex The index of the lowest temperature in the tMin
     * array.
     */
    public void printLowestTemperature(int minTminIndex) {
        System.out.println("The lowest temperature in the data is: " + tMin[minTminIndex] + " degrees F which occurred on " + month[minTminIndex] + "/" + day[minTminIndex] + "/" + year[minTminIndex]);
    }

    /**
     * Prints information about the highest temperature in the data at a
     * specific index.
     *
     * @param maxTmaxIndex The index of the highest temperature in the tMax
     * array.
     */
    public void printHighestTemperature(int maxTmaxIndex) {
        System.out.println("The highest temperature in the data is: " + tMax[maxTmaxIndex] + " degrees F which occurred on " + month[maxTmaxIndex] + "/" + day[maxTmaxIndex] + "/" + year[maxTmaxIndex]);
    }

    /**
     * Finds the index of the first occurrence of fall frost in a specified
     * range of an array.
     *
     * @param inputArray The input array representing temperature data.
     * @param yearStartIndex The starting index of the range to search for fall
     * frost.
     * @param yearEndIndex The ending index of the range to search for fall
     * frost.
     * @return The index of the first occurrence of fall frost, or -1 if the
     * range is invalid or fall frost is not found.
     */
    private int firstFallFrost(int[] inputArray, int yearStartIndex, int yearEndIndex) {
        // Check if the range is valid
        if (yearStartIndex < 0 || yearEndIndex >= inputArray.length || yearStartIndex > yearEndIndex) {
            return -1; // Invalid range
        }

        int startIndex = yearStartIndex;
        int endIndex = yearEndIndex;

        // Start searching from the middle of the year
        int middleIndex = (startIndex + endIndex) / 2;

        int firstFallFrostIndex = middleIndex;
        for (int i = middleIndex; i < endIndex; i++) {
            if (inputArray[i] <= 32) {
                firstFallFrostIndex = i;
                return firstFallFrostIndex;
            }
        }

        return firstFallFrostIndex;

    }

    /**
     * Finds the starting indexes of different "years" in an array.
     *
     * @param inputArray The input array representing a sequence of years.
     * @return An array containing the starting indexes of different "years."
     */
    private int[] findStartingIndexes(int[] inputArray) {
        int arrLength = inputArray.length / 365;
        int itteration = 0;
        int nextIndex = 0;

        int[] indexesOfYears = new int[arrLength];

        indexesOfYears[nextIndex] = itteration;
        for (int i = 1; i < inputArray.length; i++) {
            itteration += 1;
            if (inputArray[i - 1] != inputArray[i]) {
                nextIndex += 1;
                indexesOfYears[nextIndex] += itteration;
            }
        }

        return indexesOfYears;
    }

    /**
     * Finds the first occurrence index of a specific value in a portion of an
     * array. The search is limited to the specified range between startIndex
     * and endIndex (inclusive).
     *
     * @param inputArray The array to search within.
     * @param value The value to find.
     * @param startIndex The starting index for the search.
     * @param endIndex The ending index for the search.
     * @return The index of the first occurrence of the value within the
     * specified range, or -1 if the value is not found.
     */
    private int findFirstIndex(int[] inputArray, int value, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            if (inputArray[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds and returns the average (a double) of the numbers in a 1-D array
     * that is passed to it.
     *
     * @param inputArray
     * @return The average number
     */
    private double calculateAverage(int[] inputArray) {
        if (inputArray.length == 0) {
            return 0.0;
        }

        int sum = 0;
        for (int currNum : inputArray) {
            sum += currNum;
        }

        double result = (double) sum / inputArray.length;

        return result;
    }

    /**
     * Finds and returns the index of the minimum value in a 1-D array that is
     * passed to it.
     *
     * @param inputArray
     * @return The index of Highest Temperature
     */
    private int findMinValueIndex(int[] inputArray) throws Exception {
        if (inputArray == null || inputArray.length == 0) {
            throw new Exception("Array is empty or null");
        }

        int minIndex = 0;
        int minValue = inputArray[0];

        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] < minValue) {
                minValue = inputArray[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    /**
     * Finds and returns the index of the maximum value in a 1-D array that is
     * passed to it.
     *
     * @param inputArray
     * @return The index of Highest Temperature
     */
    private int findMaxValueIndex(int[] inputArray) throws Exception {
        if (inputArray == null || inputArray.length == 0) {
            throw new Exception("Array is empty or null");
        }
        int maxIndex = 0;
        int maxValue = inputArray[0];

        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    /**
     * This method is the main routine for processing weather data. It reads
     * weather records from a file, calculates various statistics related to
     * temperature, and prints the results. If any issues occur, it catches
     * exceptions and prints error messages.
     */
    public void run() {
        File fileInput;
        Scanner fileScnr;

        try {
            fileInput = new File("PortlandWeather1941to2020.txt");
            fileScnr = new Scanner(fileInput);
            fileScnr.useDelimiter("[/ \t\n\r]+");

            dataRecords = fileScnr.nextInt();

            fileScnr.nextLine();
            fileScnr.nextLine();
            fileScnr.nextLine();

            month = new int[dataRecords];
            day = new int[dataRecords];
            year = new int[dataRecords];
            tMax = new int[dataRecords];
            tMin = new int[dataRecords];

            for (int i = 0; i < dataRecords; i++) {
                month[i] = fileScnr.nextInt();
                day[i] = fileScnr.nextInt();
                year[i] = fileScnr.nextInt();
                tMax[i] = fileScnr.nextInt();
                tMin[i] = fileScnr.nextInt();
            }

            int maxTmaxIndex = findMaxValueIndex(tMax);
            int minTminIndex = findMinValueIndex(tMin);
            double averageTmax = calculateAverage(tMax);
            double averageTmin = calculateAverage(tMin);

            System.out.println("run:");
            printHighestTemperature(maxTmaxIndex);
            printLowestTemperature(minTminIndex);
            printAverageHighTemp(averageTmax);
            printAverageLowTemp(averageTmin);

            int[] startingIndexes = findStartingIndexes(year);

            printStartingIndexForEachYear(startingIndexes);

            printFirstFallFrostDates(startingIndexes);

            printAveragesByDecade(startingIndexes);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
