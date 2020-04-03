import java.io.*;

public class SetupNewFiles {
    private String month;
    private int days;
    private double limit;

    public SetupNewFiles(String month, int days, double limit) throws IOException {
        this.month = month;
        this.days = days;
        this.limit = limit;

        newMonthlyFile(month, days, limit);
        newLogFile(month);

    }


    public void newMonthlyFile(String month, int days, double limit) throws IOException {
        FileWriter writer = new FileWriter(month + "DailyChanges.txt");
        writer.write("Expenses for " + month + "\n");
        writer.write("--------------------------\n");
        writer.write("\n");
        writer.write("Day:  Change:\n");

        for (int i = 1; i <= days; i++) {
            writer.write((i + ".\n").toString());

        }

        writer.write("\n");

        if (limit > 0) {
            writer.write("MONTHLY GOAL: $" + limit + "\n");
            writer.write("DAILY GOAL: $" + (limit / (double) days) + "\n");

        } else {
            writer.write("MONTHLY GOAL: None\n");
            writer.write("DAILY GOAL: None\n");

        }

        writer.write("DAILY AVERAGE: null\n");
        writer.write("PERCENTAGE TO GOAL: 0%\n");
        writer.write("TOTAL SPENT: $0\n");

        writer.close();

    }

    public void newLogFile(String month) throws IOException {
        FileWriter writer = new FileWriter("LogFileFor_" + month + ".txt");
        writer.write("Log for " + month + "\n");
        writer.write("--------------------------\n");
        writer.write("\n");
        writer.write("Date:          Change:      Transaction Name:\n");
        writer.write("\n");

        writer.close();

    }
}
