import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;

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
        if (!(returnData(day) == -1)) {
            String newLine = date + " $" + amount + " - " + description;

            List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(fileNameLog), StandardCharsets.UTF_8));
            fileContent.set(fileContent.size(), newLine);
            Files.write(Paths.get(fileNameLog), fileContent, StandardCharsets.UTF_8);
        }

    }


    private double returnData(int day) throws FileNotFoundException {
        String currLine = "";
        int dataAt = 0;
        int exponent = 0;
        int currDay = 0;
        double data = 0;
        boolean dayExists = false;


        File dailyFilePath = new File(fileNameDaily);
        File dailyFile = new File(dailyFilePath.getAbsolutePath());
        Scanner readDaily = new Scanner(dailyFile);

        while (readDaily.hasNextLine()) {
            currLine = readDaily.nextLine();
            currDay++;

            if (currLine.length() < 3) {
                return 0;
            }

            if (currDay == day) {
                if (currLine.charAt(3) == '$') {
                    dataAt = 4;
                    dayExists = true;
                    break;

                } else if (currLine.charAt(4) == '$') {
                    dataAt = 5;
                    dayExists = true;
                    break;

                } else if (currLine.charAt(5) == '$') {
                    dataAt = 6;
                    dayExists = true;
                    break;

                } else {
                    return 0;

                }
            }
        }

        if (dayExists) {
            int stopHere = currLine.indexOf("\n") - 1;

            for (int i = stopHere; dataAt <= i; i--) {
                if (i == stopHere) {
                    data += ((((double) currLine.charAt(i)) - 48) * 0.01);

                } else if (i == stopHere - 1) {
                    data += ((((double) currLine.charAt(i)) - 48) * 0.1);

                } else if (i == stopHere - 2) {
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

        newLine = day + ". $" + newData + "\n";
        oldLine = day + ". $" + currData + "\n";

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
                    replaceLine.replaceData(day + ". $" + data + "\n", day + ". $" + amount + "\n");
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
            replaceLine.replaceData(day + ".", day + ". $" + amount + "\n");
            System.out.println("Data has been updated!");
            return true;


        } else if (data == -1) {
            return false;

        }

        return false;
    }
}
