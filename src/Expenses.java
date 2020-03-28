import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Expenses {

    public static void main(String[] args) throws IOException {
        String month;
        String fileName;
        String setMonth = "";
        int option = 0;
        int day;
        double limit = 0;
        double amount;


        Scanner scnr = new Scanner(System.in);
        MoneyIn inMoney = new MoneyIn();

        System.out.println("\n    Welcome to Trevor's money management thing");
        System.out.println("---------------------------------------------------");
        System.out.println("Keep track of what's coming in and what's going out");
        System.out.println("---------------------------------------------------");

        while (option != 8) {
            System.out.print( "\n------------------------" +
                                "\nPlease select an option" +
                                "\n------------------------" +
                                "\n8  - Set month" +
                                "\n1  - View daily change" +
                                "\n2  - View expenses and income log" +
                                "\n3  - Add an expense by day" +
                                "\n4  - Add an expense by transaction name" +
                                "\n5  - Add income by day" +
                                "\n6  - Add income by transaction name" +
                                "\n7  - Update and view statistics" +
                                "\n10 - Start a new month" +
                                "\n11 - Exit" +
                                "\n------------------------" +
                                "\nOption: ");

            option = scnr.nextInt();

            switch (option) {
                case 1:
                    Settings setting = new Settings();
                    System.out.println("\nThe current month is set to \"" + setting.getSetMonth() + "\".");
                    System.out.print("Would you like to set a different month? [Y/N] ");
                    String setChoice = scnr.next();

                    if (setChoice.compareTo("Y") == 0 || setChoice.compareTo("y") == 0) {
                        System.out.print("What is the month? ");
                        setting.setDifMonth(scnr.next());
                        System.out.println("\nThe current month is now " + setting.getSetMonth() + "\n");

                    }

                    break;

                case 2:
                    break;

                case 3:
                    fileName = setMonth + "DailyChanges.txt";
                    System.out.print("\n What is the day of the expense? ");
                    day = scnr.nextInt();
                    System.out.print("\n What is the amount of the expense? (exclude \"$\") ");
                    amount = scnr.nextDouble();
                    inMoney.addByDay(day, amount, scnr, fileName);
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    break;

                case 7:
                    System.out.print("\nName of new month: ");
                    month = scnr.next();

                    Path path = Paths.get(month + "DailyChange.txt");

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

                    SetupNewFiles setupNewFiles = new SetupNewFiles(month, day, limit);

                    System.out.println("\nNew month file has been created!");

                    break;

                case 8:
                    break;


                default:
                    System.out.println("Is that what you meant to say!");

            }


        }
    }
}
