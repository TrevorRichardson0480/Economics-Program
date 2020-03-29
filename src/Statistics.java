import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.*;

public class Statistics {
    private File file;
    private ReplaceLine replaceLine;

    public Statistics(String fileName) {
        File filePath = new File(fileName);
        this.file = new File(filePath.getAbsolutePath());
        this.replaceLine = new ReplaceLine(fileName);
    }

    public void updateStats() throws IOException {
        int numDays = 0;
        int numDaysWithData = 0;
        double totalData = 0;
        double monthlyGoal = 0;
        double dailyGoal;
        double dailyAverage;
        String currLine;
        String nextLine;
        String percentToGoal;
        String oldDailyGoal = null;
        String oldDailyAverage = null;
        String oldPercentage = null;
        String oldTotal = null;

        Scanner stats = new Scanner(file);

        while (stats.hasNextLine()) {
            currLine = stats.nextLine();
            nextLine = stats.nextLine();

            if (currLine.endsWith(".")) {
                numDays++;

            }

            if (nextLine.endsWith(".")) {
                numDays++;

            }

            while (currLine.charAt(0) > 47 && currLine.charAt(0) < 58 && !nextLine.endsWith(".")) {
                totalData += Double.parseDouble(nextLine.substring(1));
                numDaysWithData++;

                currLine = stats.nextLine();
                nextLine = stats.nextLine();

            }


            if (currLine.charAt(0) == 77) {
                stats.nextLine();
                monthlyGoal = Double.parseDouble(stats.nextLine().substring(1));
                oldDailyGoal = stats.nextLine() + " " + stats.nextLine() + " " + stats.nextLine();
                oldDailyAverage = stats.nextLine() + " " + stats.nextLine() + " " + stats.nextLine();
                oldPercentage = stats.nextLine() + " " + stats.nextLine() + " " + stats.nextLine() + " " + stats.nextLine();
                oldTotal = stats.nextLine() + " " + stats.nextLine() + " " + stats.nextLine();
                break;

            }
        }

        stats.close();

        dailyGoal = monthlyGoal / numDays;
        dailyAverage = totalData / numDaysWithData;
        percentToGoal = (totalData / monthlyGoal) + "%";

        replaceLine.replaceData("DAILY GOAL: $" + dailyGoal, oldDailyGoal);
        replaceLine.replaceData("DAILY AVERAGE: $" + dailyAverage, oldDailyAverage);
        replaceLine.replaceData("PERCENTAGE TO GOAL: " + percentToGoal, oldPercentage);
        replaceLine.replaceData("TOTAL SPENT: $" + totalData, oldTotal);
    }

    public void getStats() throws FileNotFoundException {
        Scanner stats = new Scanner(file);

        while (stats.hasNextLine()) {
            if (stats.nextLine().startsWith("MONTHLY")) {
                System.out.print("MONTHLY ");

                while (stats.hasNextLine()) {
                    System.out.println(stats.nextLine() + " ");

                }
            }
        }
    }
}
