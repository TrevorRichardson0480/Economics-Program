import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;

import static java.lang.Integer.parseInt;

public class MoneyIn {
    private String fileNameDaily;
    private String fileNameLog;
    private ReplaceLine replaceLine;

    public MoneyIn(String fileNameDaily, String fileNameLog) throws FileNotFoundException {
        this.fileNameDaily = fileNameDaily;
        this.fileNameLog = fileNameLog;
        this.replaceLine = new ReplaceLine(fileNameDaily);

    }

    void addDataToLog(String date, int day, double amount, String description) throws IOException {
            String newLine = date + "     $" + amount + "   -   " + description;

            List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(fileNameLog), StandardCharsets.UTF_8));
            fileContent.add(fileContent.size(), newLine);
            Files.write(Paths.get(fileNameLog), fileContent, StandardCharsets.UTF_8);

    }


    private double returnData(int day) throws FileNotFoundException {
        String currLine = "";
        int dataAt = 0;
        int exponent = 0;
        int thisDay = 0;
        double data = 0;
        boolean dayExists = false;


        File dailyFilePath = new File(fileNameDaily);
        File dailyFile = new File(dailyFilePath.getAbsolutePath());
        Scanner readDaily = new Scanner(dailyFile);

        readDaily.nextLine();
        readDaily.nextLine();
        readDaily.nextLine();
        readDaily.nextLine();

        while (readDaily.hasNextLine()) {
            currLine = readDaily.nextLine();

            if (currLine.length() > 0) {
                if (currLine.charAt(1) == '.') {
                    thisDay = parseInt(currLine.substring(0, 1));

                } else if (currLine.charAt(2) == '.') {
                    thisDay = parseInt(currLine.substring(0, 2));

                } else {
                    break;

                }

            } else {
                break;

            }

            if (thisDay == day) {

                if (currLine.length() < 3) {
                    return 0;

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

        if (dayExists) {

            for (int i = currLine.length() - 1; dataAt <= i; i--) {
                if (currLine.charAt(i) == '-') {
                    data *= -1;

                } else if (currLine.lastIndexOf('.') - i == -2) {
                    data += ((((double) currLine.charAt(i)) - 48) * 0.01);

                } else if (currLine.lastIndexOf('.') - i == -1) {
                    data += ((((double) currLine.charAt(i)) - 48) * 0.1);

                } else if (currLine.lastIndexOf('.') - i == 0) {
                    continue;

                } else {
                    data += ((((double) currLine.charAt(i)) - 48) * Math.pow(10, exponent));
                    exponent++;

                }
            }

            return data;

        } else {
            return -1;

        }
    }

    public void addData(int day, double amount, double currData) throws IOException {
        String newLine;
        String oldLine;


        double newData = amount + currData;

        newLine = day + ".    $" + newData;
        oldLine = day + ".    $" + currData;

        replaceLine.replaceData(oldLine, newLine);

    }

    public boolean addByDay(int day, double amount, Scanner scnr) throws IOException {
        double data = returnData(day);

        if (data != 0 && data != -1) {

            System.out.println("\nWarning! Data already exists for this day!");
            System.out.println("Would you like to [1] subtract expense from current data, [2] replace data, [3] abort.");
            System.out.print("Please select a number: ");

            switch (scnr.next()) {

                case "1":
                    addData(day, amount, data);
                    System.out.println("\nData has been updated!");
                    break;

                case "2":
                    replaceLine.replaceData(day + ".    $" + data, day + ".    $" + amount);
                    System.out.println("\nData has been replaced!");
                    break;

                case "3":
                    System.out.println("\nCancelled!");
                    break;

                default:
                    System.out.println("\nInvalid Selection! Abort!");
                    break;
            }

            return true;

        } else if (data == 0) {
            replaceLine.replaceData(day + ".", day + ".    $" + amount);
            System.out.println("Data has been updated!");
            return true;


        } else if (data == -1) {
            return false;

        }

        return false;
    }
}
