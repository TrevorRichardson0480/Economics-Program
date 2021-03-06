/* ======================================================================================================
 * Expenses.java
 * ======================================================================================================
 * Author:           Trevor Richardson
 * Date completed:   4-5-2020
 * Purpose :         To make it easier to keep track of your expenses and income, with a goal.
 * ======================================================================================================
 */


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

/* Expenses.java is the main file with the main program loop
 * - Setup settings, the modifying class (MoneyIn), and file names
 * - Prompt user with options
 * - Execute until user input == "11"
 */

public class Expenses {

    public static void main(String[] args) throws IOException {
        String month;
        String fileNameDaily;
        String fileNameLog;
        String date;
        String description;
        String option = "";
        String setChoice;
        String setMonth;
        int day;
        double limit;
        double amount;


        // Setup scanner, settings, file names, and the data modifier
        Scanner scnr = new Scanner(System.in);
        Settings setting = new Settings();

        fileNameDaily = setting.getSetMonth() + "DailyChanges.txt";
        fileNameLog = "LogFileFor_" + setting.getSetMonth() + ".txt";

        MoneyIn inMoney = new MoneyIn(fileNameDaily, fileNameLog);

        System.out.println("\n    Welcome to Trevor's money management thing");
        System.out.println("---------------------------------------------------");
        System.out.println("Keep track of what's coming in and what's going out");
        System.out.println("---------------------------------------------------");

        // main loop
        while (option.compareTo("11") != 0) {
            System.out.print( "\n------------------------" +
                                "\nPlease select an option" +
                                "\n------------------------" +
                                "\n1  - Set month and monthly goal" +
                                "\n2  - View daily changes" +
                                "\n3  - View expenses and income log" +
                                "\n4  - Add an expense by day" +
                                "\n5  - Add an expense by transaction name" +
                                "\n6  - Add income by day" +
                                "\n7  - Add income by transaction name" +
                                "\n8  - Update and view statistics" +
                                "\n9  - Start a new month" +
                                "\n10 - Export data" +
                                "\n11 - Exit" +
                                "\n------------------------" +
                                "\nOption: ");

            option = scnr.next();

            switch (option) {
                // Case 1 will handles settings
                case "1":
                    System.out.println("\nThe current month is set to \"" + setting.getSetMonth() + "\".");
                    System.out.print("Would you like to set a different month? [Y/N] ");
                    setChoice = scnr.next();

                    // if the user wishes to change the month
                    if (setChoice.compareTo("Y") == 0 || setChoice.compareTo("y") == 0) {
                        System.out.print("What is the month? ");
                        setMonth = scnr.next();
                        File newMonth = new File("../Economics-Program/saved_data/" + setMonth + "DailyChanges.txt");

                        // if this month could not be found, we inform the user
                        if (!newMonth.exists()) {
                            System.out.println("\nThis month file does not exist! If you are trying to start a new month, please use option '9'.");
                            break;
                        }

                        // Update settings, the monthly goal from the month file that was just loaded will automatically replace the monthly goal in the settings file with the getAndSetGoalFromMonth() method
                        setting.setDifMonth(setMonth);
                        System.out.println("\nThe current month is now " + setting.getSetMonth() + " with a monthly goal of $" + setting.getAndSetGoalFromMonth(setMonth));

                        // Set the new file names and initialize inMoney with those files
                        fileNameDaily = setting.getSetMonth() + "DailyChanges.txt";
                        fileNameLog = "LogFileFor_" + setting.getSetMonth() + ".txt";
                        inMoney = new MoneyIn(fileNameDaily, fileNameLog);
                    }

                    System.out.println("\nThe current monthly goal is set to \"$" + setting.getSetGoal() + "\"");
                    System.out.print("Would you like to set a different goal? [Y/N] ");
                    setChoice = scnr.next();

                    // If the user wishes to change the goal
                    if (setChoice.compareTo("Y") == 0 || setChoice.compareTo("y") == 0) {
                        System.out.print("What is the goal? (exclude \"$\") ");
                        // Update the settings file and the DailyChanges file to reflect this new goal
                        setting.setDifGoal(scnr.nextDouble());
                        System.out.println("\nThe current goal is now $" + setting.getSetGoal() + ".");

                    }

                    break;

                // Case 2 Handles printing the DailyChanges file
                case "2":
                    System.out.println();
                    PrintFile.printFile(fileNameDaily);
                    break;

                // Case 3 Handles printing the Log file
                case "3":
                    System.out.println();
                    PrintFile.printFile(fileNameLog);
                    break;

                // Case 4 handles adding a daily expense
                case "4":
                    System.out.print("\nWhat is the amount of the expense? (exclude \"$\" and \"-\") ");
                    amount = -scnr.nextDouble();
                    System.out.print("What is the date of this expense? (mm/dd/yy) ");
                    date = scnr.next();

                    // determine day from date
                    day = parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));

                    // if the change was not successful, print an error, otherwise add the change to the log
                    if (!inMoney.addByDay(day, amount, date, scnr)) {
                        System.out.println("\nERROR! That day was not found!");

                    } else {
                        inMoney.addDataToLog(date, amount, "Generic expense transaction for day: " + day);

                    }

                    break;

                // Case 5 handles adding a specific transaction expense, with a description
                case "5":
                    System.out.print("\nWhat is the amount of the expense? (exclude \"$\") ");
                    amount = -scnr.nextDouble();
                    System.out.print("What is the date of this expense? (mm/dd/yy) ");
                    date = scnr.next();
                    System.out.print("Please add a description of the transaction: ");
                    description = scnr.next();

                    // determine day from date
                    day = parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));

                    // if the change was not successful, print an error, otherwise add the change to the log
                    if (!inMoney.addByTransaction(day, amount)) {
                        System.out.println("\nERROR! That day was not found!");

                    } else {
                        inMoney.addDataToLog(date, amount, description);

                    }

                    break;

                // Case 6 handles adding a daily income
                case "6":
                    System.out.print("\nWhat is the amount of the income? (exclude \"$\") ");
                    amount = scnr.nextDouble();
                    System.out.print("What is the date of this income? (mm/dd/yy) ");
                    date = scnr.next();

                    // determine day from date
                    day = parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));

                    // if the change was not successful, print an error, otherwise add the change to the log
                    if (!inMoney.addByDay(day, amount, date, scnr)) {
                        System.out.println("\nERROR! That day was not found!");

                    } else {
                        inMoney.addDataToLog(date, amount, "Generic income transaction for day: " + day);

                    }
                    break;

                // Case 7 handles adding a specific transaction income, with a description
                case "7":
                    System.out.print("\nWhat is the amount of the income? (exclude \"$\") ");
                    amount = scnr.nextDouble();
                    System.out.print("What is the date of this income? (mm/dd/yy) ");
                    date = scnr.next();
                    System.out.print("Please add a description of the transaction: ");
                    description = scnr.next();

                    // determine day from date
                    day = parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));

                    // if the change was not successful, print an error, otherwise add the change to the log
                    if (!inMoney.addByTransaction(day, amount)) {
                        System.out.println("\nERROR! That day was not found!");

                    } else {
                        inMoney.addDataToLog(date, amount, description);

                    }

                    break;

                // Case 8 handles updating, then viewing the updated statistics
                case "8":
                    Statistics stats = new Statistics(fileNameDaily);
                    stats.updateStats();
                    System.out.println("\nStatistics have been updated:\n");
                    stats.getStats();

                    break;

                // Case 9 handles creating new files for a new month set of data
                case "9":
                    System.out.print("\nName of new month: ");
                    month = scnr.next();

                    // Create a path of given month
                    Path path = Paths.get("../Economics-Program/saved_data/" + month + "DailyChanges.txt");

                    // Use path to determine if the month file exists. If so, prompt user if they want to write over said file.
                    if (Files.exists(path)) {
                        System.out.println("\nWARNING! This month file already exists!");
                        System.out.println("Remember, you can call this file anything, not just names of months.");
                        System.out.print("Do you wish to write over this data? [Y/N] ");
                        String writeOver = scnr.next();
                        System.out.println();

                        // If the user does not wish to write over, break.
                        if (writeOver.compareTo("y") != 0 && writeOver.compareTo("Y") != 0) {
                            break;

                        }
                    }

                    System.out.print("How many days are in this month? ");
                    day = scnr.nextInt();
                    System.out.print("What is the goal spending limit of this month? (No \"$\". If none, type 0) ");
                    limit = scnr.nextInt();

                    // create files
                    new SetupNewFiles(month, day, limit);

                    System.out.println("\nNew month files have been created!");

                    // Update settings, file names, and modifier to the new month file and goal. Print to confirm the change.

                    fileNameDaily = month + "DailyChanges.txt";
                    fileNameLog = "LogFileFor_" + month + ".txt";
                    setting.setDifMonth(month);
                    setting.setDifGoal(limit);
                    inMoney = new MoneyIn(fileNameDaily, fileNameLog);
                    System.out.println("The current month file is now set to " + setting.getSetMonth() + " with a monthly goal of $" + setting.getSetGoal());

                    break;


                // Case 10 handles exporting.
                case "10":
                    System.out.println("\nData will export in two files. Where would you like to export?");
                    System.out.println("1 - Documents");
                    System.out.println("2 - Desktop");
                    System.out.println("3 - Somewhere else (specify a directory)");
                    System.out.print("Type anything else to exit this section: ");

                    Export export = new Export(fileNameDaily, fileNameLog);

                    // Files will export to desired location
                    switch (scnr.next()) {
                        case "1":
                            export.exportTo("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\", scnr);
                            break;

                        case "2":
                            export.exportTo("C:\\Users\\" + System.getProperty("user.name") + "\\OneDrive\\Desktop\\", scnr);
                            break;

                        case "3":
                            System.out.println("\nWhere would you like to export?");
                            export.exportTo(scnr.next(), scnr);
                            break;

                        default:
                            System.out.println("Unrecognised option! Abort!");
                            break;

                    }
                    break;

                // Case 11 handles exiting
                case "11":
                    System.out.println("\nGoodbye!");
                    break;

                default:
                    System.out.println("Is that what you meant to say!");

            }
        }
    }
}
