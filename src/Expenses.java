import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Expenses {

    public static void main(String[] args) throws IOException {
        String month;
        String fileNameDaily;
        String fileNameLog;
        String date;
        String description;
        String option = "";
        int day;
        double limit;
        double amount;


        Scanner scnr = new Scanner(System.in);
        Settings setting = new Settings();

        fileNameDaily = setting.getSetMonth() + "DailyChanges.txt";
        fileNameLog = "LogFileFor_" + setting.getSetMonth() + ".txt";

        MoneyIn inMoney = new MoneyIn(fileNameDaily, fileNameLog);

        System.out.println("\n    Welcome to Trevor's money management thing");
        System.out.println("---------------------------------------------------");
        System.out.println("Keep track of what's coming in and what's going out");
        System.out.println("---------------------------------------------------");

        while (option != "8") {
            System.out.print( "\n------------------------" +
                                "\nPlease select an option" +
                                "\n------------------------" +
                                "\n1  - Set month" +
                                "\n2  - View daily change" +
                                "\n3  - View expenses and income log" +
                                "\n4  - Add an expense by day" +
                                "\n5  - Add an expense by transaction name" +
                                "\n6  - Add income by day" +
                                "\n7  - Add income by transaction name" +
                                "\n8  - Update and view statistics" +
                                "\n9  - Start a new month" +
                                "\n10 - Exit" +
                                "\n------------------------" +
                                "\nOption: ");

            option = scnr.next();

            switch (option) {
                case "1":
                    System.out.println("\nThe current month is set to \"" + setting.getSetMonth() + "\".");
                    System.out.print("Would you like to set a different month? [Y/N] ");
                    String setChoice = scnr.next();

                    if (setChoice.compareTo("Y") == 0 || setChoice.compareTo("y") == 0) {
                        System.out.print("What is the month? ");
                        setting.setDifMonth(scnr.next());
                        System.out.println("\nThe current month is now " + setting.getSetMonth() + ".\n");

                    }

                    fileNameDaily = setting.getSetMonth() + "DailyChanges.txt";
                    fileNameLog = "LogFileFor_" + setting.getSetMonth() + ".txt";
                    inMoney = new MoneyIn(fileNameDaily, fileNameLog);

                    break;

                case "2":
                    System.out.println();
                    new printFile(fileNameDaily);
                    break;

                case "3":
                    System.out.println();
                    new printFile(fileNameLog);
                    break;

                case "4":
                    System.out.print("\nWhat is the day of the month of the expense? ");
                    day = scnr.nextInt();
                    System.out.print("\nWhat is the amount of the expense? (exclude \"$\") ");
                    amount = -scnr.nextDouble();
                    System.out.print("\nWhat is the date of this expense? (mm/dd/yy) ");
                    date = scnr.next();

                    if (!inMoney.addByDay(day, amount, scnr)) {
                        System.out.println("\nERROR! That day was not found!");

                    }

                    inMoney.addDataToLog(date, day, amount, "Generic expense transaction for day: " + day);
                    break;

                case "5":
                    System.out.print("\nWhat is the amount of the expense? (exclude \"$\") ");
                    amount = -scnr.nextDouble();
                    System.out.print("\nWhat is the date of this expense? (mm/dd/yy) ");
                    date = scnr.next();
                    System.out.print("\nPlease add a description of the transaction: ");
                    description = scnr.next();

                    day = ((date.charAt(3) -48 )* 10) + (date.charAt(4) - 48);

                    if (!inMoney.addByDay(day, amount, scnr)) {
                        System.out.println("\nERROR! That day was not found!");

                    }

                    inMoney.addDataToLog(date, day, amount, description);
                    break;

                case "6":
                    System.out.print("\nWhat is the day of the month of the income? ");
                    day = scnr.nextInt();
                    System.out.print("\nWhat is the amount of the income? (exclude \"$\") ");
                    amount = scnr.nextDouble();
                    System.out.print("\nWhat is the date of this income? (mm/dd/yy) ");
                    date = scnr.next();

                    if (!inMoney.addByDay(day, amount, scnr)) {
                        System.out.println("\nERROR! That day was not found!");

                    }

                    inMoney.addDataToLog(date, day, amount, "Generic income transaction for day: " + day);
                    break;

                case "7":
                    System.out.print("\nWhat is the amount of the income? (exclude \"$\") ");
                    amount = scnr.nextDouble();
                    System.out.print("\nWhat is the date of this income? (mm/dd/yy) ");
                    date = scnr.next();
                    System.out.print("\nPlease add a description of the transaction: ");
                    description = scnr.next();

                    day = ((date.charAt(3) -48 )* 10) + (date.charAt(4) - 48);

                    if (!inMoney.addByDay(day, amount, scnr)) {
                        System.out.println("\nERROR! That day was not found!");

                    }

                    inMoney.addDataToLog(date, day, amount, description);
                    break;

                case "8":

                    break;

                case "9":
                    System.out.print("\nName of new month: ");
                    month = scnr.next();

                    Path path = Paths.get(month + "DailyChanges.txt");

                    if (Files.exists(path)) {
                        System.out.println("\nWARNING! This month file already exists!");
                        System.out.println("Remember, you call this file anything, not just names of months.");
                        System.out.print("Do you wish to write over this data? [Y/N] ");
                        String writeOver = scnr.next();
                        System.out.println();

                        if (writeOver.compareTo("y") != 0 && writeOver.compareTo("Y") != 0) {
                            break;
                        }
                    }

                    System.out.print("How many days are in this month? ");
                    day = scnr.nextInt();
                    System.out.print("What is the goal spending limit of this month? (if none, type 0) ");
                    limit = scnr.nextInt();

                    new SetupNewFiles(month, day, limit);

                    System.out.println("\nNew month file has been created!");

                    break;


                case "10":
                    break;

                default:
                    System.out.println("Is that what you meant to say!");

            }
        }
    }
}
