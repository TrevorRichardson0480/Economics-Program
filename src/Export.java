import java.io.*;
import java.util.Scanner;

/* The Export class will:
 * - Use the constructor to prepare an export
 * - Export current set files to given location
 */

public class Export {
    private String fileNameDaily;
    private String fileNameLog;
    private Scanner daily;
    private Scanner log;

    // Construct the export. This is where we setup the file names and scanner for the files.
    public Export(String fileNameDaily, String fileNameLog) throws FileNotFoundException {
        this.fileNameDaily = fileNameDaily;
        this.fileNameLog = fileNameLog;

        File fileDailyPath = new File(fileNameDaily);
        File fileLogPath = new File(fileNameLog);

        File fileDaily = new File(fileDailyPath.getAbsolutePath());
        File fileLog = new File(fileLogPath.getAbsolutePath());

        this.daily = new Scanner(fileDaily);
        this.log = new Scanner(fileLog);

    }

    // The exportTo method will export using the given path and with the scanner passed in
    public void exportTo(String path, Scanner scnr) throws IOException {
        String warningOption;

        // Set up temporary files to test if the location already has files
        File fileTestExistsDaily = new File(path + fileNameDaily);
        File fileTestExistsLog = new File(path + fileNameLog);

        // if the files exist, prompt the user if they wish to overwrite for each file.
        // If the user does not wish to overwrite the Daily changes file, we assume that they do not wish to overwrite the log file as well and return.
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

        // Copy file contents
        while (daily.hasNextLine()) {
            fileContents = fileContents.concat(daily.nextLine() + "\n");

        }

        // Write file contents to new file
        FileWriter writer = new FileWriter(path + fileNameDaily);
        writer.write(fileContents);
        writer.close();

        // Repeat process for the log file
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
