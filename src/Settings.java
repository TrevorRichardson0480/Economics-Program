import java.io.*;
import java.util.Scanner;

public class Settings {
    public File settingsFile;
    public ReplaceLine replaceLineSettings;

    public Settings() throws FileNotFoundException {
        File settingsPath = new File("settings.txt");
        settingsFile = new File(settingsPath.getAbsolutePath());
        replaceLineSettings = new ReplaceLine("settings.txt");

    }

    public String getSetMonth() throws FileNotFoundException {
        Scanner settings = new Scanner(settingsFile);

        while(settings.hasNextLine()) {
            if (settings.next().startsWith("CURRENTMONTH")) {
                settings.next();
                return settings.next();
            }
        }

        return null;
    }

    public void setDifMonth(String newMonth) throws IOException {
        replaceLineSettings.replaceData("CURRENTMONTH = " + getSetMonth(), "CURRENTMONTH = " + newMonth);

    }

    public String getSetGoal() throws FileNotFoundException {
        Scanner settings = new Scanner(settingsFile);

        while(settings.hasNextLine()) {
            if (settings.next().startsWith("CURRENTGOAL")) {
                settings.next();
                return settings.next();
            }
        }

        return null;
    }

    public void setDifGoal(String newGoal) throws IOException {
        String oldGoal = getSetGoal();
        replaceLineSettings.replaceData("CURRENTGOAL = " + oldGoal, "CURRENTGOAL = " + newGoal);

        ReplaceLine replaceLineMonth = new ReplaceLine(getSetMonth() + "DailyChange.txt");
        replaceLineMonth.replaceData("MONTHLY GOAL: " + oldGoal, "MONTHLY GOAL: " + newGoal);

    }
}
