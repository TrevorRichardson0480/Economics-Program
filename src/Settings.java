import java.io.*;
import java.util.Scanner;
import static java.lang.Double.parseDouble;

/* The Settings class:
 * - Creates a settings.txt file
 * - Updates settings
 * - Gets settings
 * - updates other files with given settings
 */

public class Settings {
    public File settingsFile;
    public ReplaceLine replaceLineSettings;

    public Settings() throws IOException {
        File settingsPath = new File("settings.txt");
        settingsFile = new File(settingsPath.getAbsolutePath());

        // If we construct the settings class and see there is no settings file. Then make a settings file.
        if (!settingsFile.exists()) {
            makeNewSettingsFile(settingsPath);

        }

        // Initialize to replace lines in settings.txt later
        replaceLineSettings = new ReplaceLine("settings.txt");
    }

    // Returns the set month in settings.txt
    public String getSetMonth() throws FileNotFoundException {
        Scanner settings = new Scanner(settingsFile);

        // Once we find the month, return it
        while(settings.hasNextLine()) {
            if (settings.next().startsWith("CURRENTMONTH")) {
                settings.next();
                return settings.next();
            }
        }

        return null;
    }

    // Replaces the set month in settings.txt
    public void setDifMonth(String newMonth) throws IOException {
        replaceLineSettings.replaceData("CURRENTMONTH = " + getSetMonth(), "CURRENTMONTH = " + newMonth);

    }

    // Returns the set Goal in settings.txt
    public double getSetGoal() throws FileNotFoundException {
        Scanner settings = new Scanner(settingsFile);

        // Once we find the goal, return it
        while(settings.hasNextLine()) {
            if (settings.next().startsWith("CURRENTGOAL")) {
                settings.next();
                return parseDouble(settings.next());
            }
        }

        return 0;
    }

    // Replace the goal of the settings.txt goal and the current month DailyChanges file's goal
    public void setDifGoal(Double newGoal) throws IOException {
        Double oldGoal = getSetGoal();
        replaceLineSettings.replaceData("CURRENTGOAL = " + oldGoal, "CURRENTGOAL = " + newGoal);

        ReplaceLine replaceLineMonth = new ReplaceLine(getSetMonth() + "DailyChanges.txt");
        replaceLineMonth.replaceData("MONTHLY GOAL: $" + oldGoal, "MONTHLY GOAL: $" + newGoal);

    }

    // Write a new settings file
    public void makeNewSettingsFile(File settingsPath) throws IOException {
        FileWriter writer = new FileWriter(settingsPath);
        writer.write("CURRENTMONTH = null\n");
        writer.write("CURRENTGOAL = 0.0\n");
        writer.close();

    }

    // Once a new month has been set, this method will get the goal from that month, and set it to the settings.txt goal
    public double getAndSetGoalFromMonth(String month) throws IOException {
        Double newGoal = null;
        String thisLine;

        // Initialize scanner for finding the goal
        File newMonth = new File(month + "DailyChanges.txt");
        newMonth = new File(newMonth.getAbsolutePath());
        Scanner findingGoal = new Scanner(newMonth);

        while(findingGoal.hasNextLine()) {
            thisLine = findingGoal.nextLine();

            // if the goal has been found, get the goal, stop looping
            if(thisLine.length() > 14 && thisLine.substring(0, 15).equals("MONTHLY GOAL: $")) {
                newGoal = parseDouble(thisLine.substring(15));
                break;
            }
        }

        // Replace settings goal with new goal
        replaceLineSettings.replaceData("CURRENTGOAL = " + getSetGoal(), "CURRENTGOAL = " + newGoal);

        return getSetGoal();
    }
}
