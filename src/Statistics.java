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

        stats.nextLine();
        stats.nextLine();
        stats.nextLine();
        stats.nextLine();

        while (stats.hasNextLine()) {
            currLine = stats.nextLine();

            if (currLine.equals("")) {
                break;

            } else if (currLine.contains("$")) {
                if (currLine.contains("-")) {
                    totalData -= Double.parseDouble(currLine.substring(currLine.indexOf("-") + 1));

                } else {
                    totalData += Double.parseDouble(currLine.substring(currLine.indexOf("$") + 1));
                }

                numDays++;
                numDaysWithData++;

            } else {
                numDays++;

            }
        }

        currLine = stats.nextLine();
        monthlyGoal = Double.parseDouble(currLine.substring(currLine.indexOf("$") + 1));
        oldDailyGoal = stats.nextLine();
        oldDailyAverage = stats.nextLine();
        oldPercentage = stats.nextLine();
        oldTotal = stats.nextLine();

        stats.close();

        dailyGoal = monthlyGoal / numDays;
        dailyAverage = totalData / numDaysWithData;
        percentToGoal = (totalData / monthlyGoal) * 100;

        replaceLine.replaceData(oldDailyGoal, "DAILY GOAL: $" + dailyGoal);
        replaceLine.replaceData(oldDailyAverage, "DAILY AVERAGE: $" + String.format("%.2f", dailyAverage));
        replaceLine.replaceData(oldPercentage, "PERCENTAGE TO GOAL: " + String.format("%.2f", percentToGoal) + "%");
        replaceLine.replaceData(oldTotal, "TOTAL SPENT: $" + totalData);
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
