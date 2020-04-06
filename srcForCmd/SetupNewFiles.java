import java.io.*;

/* The Setup New File Class creates a new file prepared for receiving data.
 * - This class is designed to only be called once, with the constructor
 * - The constructor will call the methods that will create the new Daily Changes file and the new Log file
 */

public class SetupNewFiles {

    public SetupNewFiles(String month, int days, double limit) throws IOException {
        newMonthlyFile(month, days, limit);
        newLogFile(month);

    }

    // Create a new monthly file (ie. the Daily Changes file)
    public void newMonthlyFile(String month, int days, double limit) throws IOException {
        FileWriter writer = new FileWriter("../saved_data/" + month + "DailyChanges.txt");

        // Write the header
        writer.write("Expenses for " + month + "\n");
        writer.write("--------------------------\n");
        writer.write("\n");
        writer.write("Day:  Change:\n");

        // Write the days as a list
        for (int i = 1; i <= days; i++) {
            writer.write((i + ".\n").toString());

        }

        writer.write("\n");

        // If the goal was given, initialize it to the given
        if (limit > 0) {
            writer.write("MONTHLY GOAL: $" + limit + "\n");
            writer.write("DAILY GOAL: $" + (limit / (double) days) + "\n");

        } else {
            writer.write("MONTHLY GOAL: $" + limit + "\n");
            writer.write("DAILY GOAL: None\n");

        }

        // Setup the statistics, to be changed later
        writer.write("DAILY AVERAGE: null\n");
        writer.write("PERCENTAGE TO GOAL: 0%\n");
        writer.write("TOTAL SPENT: $0\n");

        writer.close();

    }

    // Create a new log file, to keep track of every change to the Daily Changes file
    public void newLogFile(String month) throws IOException {
        FileWriter writer = new FileWriter("../saved_data/LogFileFor_" + month + ".txt");

        // Write the header
        writer.write("Log for " + month + "\n");
        writer.write("--------------------------\n");
        writer.write("\n");
        writer.write("Date:        Change:      Transaction Name:\n");
        writer.write("\n");

        writer.close();

    }
}
