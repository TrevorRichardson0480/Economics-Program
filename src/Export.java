import java.io.*;
import java.util.Scanner;

public class Export {
    private String fileNameDaily;
    private String fileNameLog;
    private File fileDaily;
    private File fileLog;
    private Scanner daily;
    private Scanner log;

    public Export(String fileNameDaily, String fileNameLog) throws FileNotFoundException {
        this.fileNameDaily = fileNameDaily;
        this.fileNameLog = fileNameLog;

        File fileDailyPath = new File(fileNameDaily);
        File fileLogPath = new File(fileNameLog);

        this.fileDaily = new File(fileDailyPath.getAbsolutePath());
        this.fileLog = new File(fileLogPath.getAbsolutePath());

        this.daily = new Scanner(fileDaily);
        this.log = new Scanner(fileLog);

    }

    public void exportTo(String path) throws IOException {
        Scanner scnr = new Scanner(System.in);
        String warningOption;
        File fileTestExistsDaily = new File(path + fileNameDaily);
        File fileTestExistsLog = new File(path + fileNameLog);

        if (fileTestExistsDaily.exists()) {
            System.out.print("\nWARNING! The specified path already exists:\n" + fileTestExistsDaily + "\nDo you wish to continue? This will overwrite the current file. [Y/N] ");
            warningOption = scnr.next();
            if (!(warningOption.equals("Y") || warningOption.equals("y"))) {
                return;

            }
        }

        if (fileTestExistsLog.exists()) {
            System.out.print("\nWARNING! The specified path already exists:\n" + fileTestExistsLog + "\nDo you wish to continue? This will overwrite the current file. [Y/N] ");
            warningOption = scnr.next();
            if (!(warningOption.equals("Y") || warningOption.equals("y"))) {
                return;

            }
        }

        String fileContents = "";

        while (daily.hasNextLine()) {
            fileContents = fileContents.concat(daily.nextLine() + "\n");

        }

        FileWriter writer = new FileWriter(path + fileNameDaily);
        writer.write(fileContents);
        writer.close();

        fileContents = "";

        while (log.hasNextLine()) {
            fileContents = fileContents.concat(log.nextLine() + "\n");

        }

        writer = new FileWriter(path + fileNameLog);
        writer.write(fileContents);
        writer.close();

        System.out.println("\nData successfully exported.");

    }
}
