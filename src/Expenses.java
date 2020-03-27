import java.io.IOException;
import java.util.Scanner;

public class Expenses {

    public static void main(String[] args) throws IOException {
        int option = 0;
        int day;
        double amount;
        Scanner scnr = new Scanner(System.in);
        MoneyIn inMoney = new MoneyIn();

        System.out.println("\n    Welcome to Trevor's money management thing");
        System.out.println("---------------------------------------------------");
        System.out.println("Keep track of what's coming in and what's going out");
        System.out.println("---------------------------------------------------");

        while (option != 8) {
            System.out.println( "\n------------------------" +
                                "\nPlease select an option" +
                                "\n------------------------" +
                                "\n1 - View daily change" +
                                "\n2 - View expenses and income log" +
                                "\n3 - Add an expense by day" +
                                "\n4 - Add an expense by transaction name" +
                                "\n5 - Add income by day" +
                                "\n6 - Add income by transaction name" +
                                "\n7 - Settings" +
                                "\n8 - Exit" +
                                "\n------------------------" +
                                "\nPlease input desired action!");

            option = scnr.nextInt();

            switch (option) {
                case 1:
                    break;

                case 2:
                    break;

                case 3:
                    System.out.print("\n What is the day of the expense? ");
                    day = scnr.nextInt();
                    System.out.print("\n What is the amount of the expense? (exclude \"$\"");
                    amount = scnr.nextDouble();
                    inMoney.addByDay(day, amount, scnr);
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    break;

                case 7:
                    break;

                case 8:
                    break;


                default:
                    System.out.println("Is that what you meant to say!");

            }


        }
    }
}
