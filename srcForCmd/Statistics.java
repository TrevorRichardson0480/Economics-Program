import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.*;

/* The Statistics class will:
 * - Construct the file path and prepare to replace a line
 * - Update the statistics
 * - Print the statistics
 * - NOTE: The statistics are locates at the bottom of the set Daily Changes file.
 */

public class Statistics {
    private File file;
    private ReplaceLine replaceLine;

    // Set the file path and prepare to replace a line
    public Statistics(String fileName) {
        File filePath = new File("../saved_data/" + fileName);
        this.file = new File(filePath.getAbsolutePath());
        this.replaceLine = new ReplaceLine("../saved_data/" + fileName);
    }

    // Update the statistics
    public void updateStats() throws IOException {
        int numDays = 0;
        int numDaysWithData = 0;
        double totalData = 0;
        double monthlyGoal;
        double dailyGoal;
        double dailyAverage;
        double percentToGoal;
        String currLine;
        String oldDailyGoal;
        String oldDailyAverage;
        String oldPercentage;
        String oldTotal;

        Scanner stats = new Scanner(file);

        // Skips line so we start counting where the data start in the file
        stats.nextLine();
        stats.nextLine();
        stats.nextLine();
        stats.nextLine();

        while (stats.hasNextLine()) {
            currLine = stats.nextLine();

            // If the current line is empty, we know we have hit the end of the data, break
            if (currLine.equals("")) {
                break;

            // If the current line contains a $, we know we have data to accumulate
            } else if (currLine.contains("$")) {
                // If the data contains a -, we know this change with be negative
                if (currLine.contains("-")) {
                    // Subtract the data after the -
                    totalData -= Double.parseDouble(currLine.substring(currLine.indexOf("-") + 1));

                } else {
                    // Add the data after the +
                    totalData += Double.parseDouble(currLine.substring(currLine.indexOf("$") + 1));

                }

                numDays++;
                numDaysWithData++;

            } else {
                numDays++;

            }
        }

        // Get monthly goal and old lines.
        currLine = stats.nextLine();

        // Get old lines
        oldDailyGoal = stats.nextLine();
        oldDailyAverage = stats.nextLine();
        oldPercentage = stats.nextLine();
        oldTotal = stats.nextLine();

        // if the monthly goal exists
        if (!currLine.contains("None")) {
            // Determine monthly goal
            monthlyGoal = Double.parseDouble(currLine.substring(currLine.indexOf("$") + 1));

            // Do math: get daily goal, get percentage
            dailyGoal = monthlyGoal / numDays;
            percentToGoal = (totalData / monthlyGoal) * 100;

            // Update lines involving a monthly goal
            replaceLine.replaceData(oldDailyGoal, "DAILY GOAL: $" + dailyGoal);
            replaceLine.replaceData(oldPercentage, "PERCENTAGE TO GOAL: " + String.format("%.2f", percentToGoal) + "%");

        }

        stats.close();

        // Do math: Get daily average spent
        dailyAverage = totalData / numDaysWithData;

        // Update the Daily Changes file with the new stats
        replaceLine.replaceData(oldDailyAverage, "DAILY AVERAGE: $" + String.format("%.2f", dailyAverage));
        replaceLine.replaceData(oldTotal, "TOTAL SPENT: $" + totalData);
    }

    // Print the status
    public void getStats() throws FileNotFoundException {
        String currLine;

        Scanner stats = new Scanner(file);

        while (stats.hasNextLine()) {
            currLine = stats.nextLine();

            // Once we have found our keyword "MONTHLY", print through the rest of the Daily Changes file
            if (currLine.startsWith("MONTHLY")) {
                System.out.println(currLine);

                while (stats.hasNextLine()) {
                    System.out.println(stats.nextLine());

                }
            }
        }
    }
}
