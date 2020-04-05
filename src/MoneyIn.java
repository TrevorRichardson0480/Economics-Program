import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;
import static java.lang.Integer.parseInt;

/* The Money In file takes in data that will be used the update the data of the user's daily changes and log files
 * - The constructor will prepare to replace lines in the daily changes and log files
 * - We will use the data on the line in question to determine actions and update the line in question with new data.
 * - We will keep track of all changes to the log file
 */

public class MoneyIn {
    private String fileNameDaily;
    private String fileNameLog;
    private ReplaceLine replaceLineDaily;
    private ReplaceLine replaceLineLog;

    // Constructor will set file names, to be used later, and prepare to replace lines
    public MoneyIn(String fileNameDaily, String fileNameLog) throws FileNotFoundException {
        this.fileNameDaily = fileNameDaily;
        this.fileNameLog = fileNameLog;
        this.replaceLineDaily = new ReplaceLine(fileNameDaily);
        this.replaceLineLog = new ReplaceLine(fileNameLog);

    }

    // Add a change to the log
    void addDataToLog(String date, double amount, String description) throws IOException {
            String newLine = date + "     $" + amount + "   -   " + description;

            List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(fileNameLog), StandardCharsets.UTF_8));
            fileContent.add(fileContent.size(), newLine);
            Files.write(Paths.get(fileNameLog), fileContent, StandardCharsets.UTF_8);

    }

    // Find if there is data on the given line (day), if so, return the data, if not, return 0, if the line does not exits, return -1
    public double returnData(int day) throws FileNotFoundException {
        String currLine = "";
        int dataAt = 0;
        int exponent = 0;
        int thisDay = 0;
        double data = 0;
        boolean dayExists = false;

        // Setup file to be scanned
        File dailyFilePath = new File(fileNameDaily);
        File dailyFile = new File(dailyFilePath.getAbsolutePath());
        Scanner readDaily = new Scanner(dailyFile);

        // Skip lines so we start where the data starts
        readDaily.nextLine();
        readDaily.nextLine();
        readDaily.nextLine();
        readDaily.nextLine();

        // While loop will find the data of the line in question
        while (readDaily.hasNextLine()) {
            currLine = readDaily.nextLine();

            // if statement will find the day of the current line
            if (currLine.length() > 0) {
                if (currLine.contains(".")) {
                    thisDay = parseInt(currLine.substring(0, currLine.indexOf(".")));

                } else {
                    break;

                }

            // if the length is zero, we have hit the end of the data, and the given day does not exist
            } else {
                break;

            }

            // If the day has been found, find the data with that day
            if (thisDay == day) {

                // if the length is less than 4, we know there is no data, return 0
                if (currLine.length() < 4) {
                    return 0;

                // The rest of the if statement will find the location of the data
                } else if (currLine.charAt(6) == '$') {
                    dataAt = 7;
                    dayExists = true;
                    break;

                } else if (currLine.charAt(7) == '$') {
                    dataAt = 8;
                    dayExists = true;
                    break;

                } else if (currLine.charAt(8) == '$') {
                    dataAt = 9;
                    dayExists = true;
                    break;

                }
            }
        }

        // if the day exists, calculate the data for that day.
        if (dayExists) {

            // for loop will iterate through the data string, starting at the end and working to the dataAt
            for (int i = currLine.length() - 1; dataAt <= i; i--) {
                // if a negative is found, make the data negative
                if (currLine.charAt(i) == '-') {
                    data *= -1;

                // if we are -2 places away from the ".", we know we are in the hundredths place
                } else if (currLine.lastIndexOf('.') - i == -2) {
                    // Convert the value at this place to a double and add to the data
                    data += ((((double) currLine.charAt(i)) - 48) * 0.01);

                // if we are -1 places away from the ".", we know we are in the tens place
                } else if (currLine.lastIndexOf('.') - i == -1) {
                    // Convert the value at this place to a double and add to the data
                    data += ((((double) currLine.charAt(i)) - 48) * 0.1);

                // if we are on the ".", we continue, we cannot add the "."
                } else if (currLine.lastIndexOf('.') - i == 0) {
                    continue;

                // Once all the values after the decimal place have been determined, we begin adding the values before the decimal place
                } else {
                    // Convert the value at this place to a double and add to the data
                    data += ((((double) currLine.charAt(i)) - 48) * Math.pow(10, exponent));
                    // Every time we determine another value, we move over to the left another place. Thus, the exponent increases
                    exponent++;

                }
            }

            // Once we have finished accumulating data, return the data
            return data;

        // if the day does not exist, return -1
        } else {
            return -1;

        }
    }

    // addData replaces the old line with a new line of the old amount and new amount combined
    public void addData(int day, double amount, double currData) throws IOException {
        String newLine;
        String oldLine;

        // Calculate new data
        double newData = amount + currData;

        newLine = day + ".    $" + newData;
        oldLine = day + ".    $" + currData;

        // Replace odd line with new line
        replaceLineDaily.replaceData(oldLine, newLine);

    }

    // addByDay is used to add that data to the Daily Changes file
    public boolean addByDay(int day, double amount, String date, Scanner scnr) throws IOException {
        double data = returnData(day);

        // if data exists, warn the user
        if (data != 0 && data != -1) {

            System.out.println("\nWarning! Data already exists for this day!");
            System.out.println("Would you like to [1] subtract expense or add income from/to current data, [2] replace data, [3] abort.");
            System.out.print("Please select a number: ");

            switch (scnr.next()) {
                // if user wishes to add the data
                case "1":
                    addData(day, amount, data);
                    System.out.println("\nData has been updated!");
                    break;

                // if the user wishes to replace the data
                case "2":
                    replaceLineDaily.replaceData(day + ".    $" + data, day + ".    $" + amount);
                    // The old record of the transaction is marked as void because that data has been replaced in the Daily Changes file
                    replaceLineLog.replaceData(date + "     $" + data + "   -   Generic expense transaction for day: " + day, date + "     $" + data + "   -   Generic expense transaction for day: " + day + " ---!VOID!");
                    System.out.println("\nData has been replaced!");
                    break;

                // if the user wishes to cancel
                case "3":
                    System.out.println("\nCancelled!");
                    break;

                default:
                    System.out.println("\nInvalid Selection! Abort!");
                    break;
            }

            return true;

        // if data does not exist, make a new line with data
        } else if (data == 0) {
            replaceLineDaily.replaceData(day + ".", day + ".    $" + amount);
            System.out.println("Data has been updated!");
            return true;


        // if the line does not exist, return false, indicating a failure
        } else if (data == -1) {
            return false;

        }

        return false;
    }

    // The addByTransaction method adds a new transaction to the Daily Changes file
    public boolean addByTransaction(int day, double amount) throws IOException {
        double data = returnData(day);

        // if data already exists, add data
        if (data != 0 && data != -1) {
            addData(day, amount, data);
            System.out.println("\nData has been updated!");
            return true;

        // if data does not exist, replace line with data
        } else if (data == 0) {
            replaceLineDaily.replaceData(day + ".", day + ".    $" + amount);
            System.out.println("\nData has been updated!");
            return true;

        // if the line does not exist, return false, indicating a failure
        } else if (data == -1) {
            return false;

        }

        return false;
    }
}
