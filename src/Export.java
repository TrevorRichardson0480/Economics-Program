import java.io.*;
import java.util.Scanner;

public class Export {
    private String fileNameDaily;
    private String fileNameLog;
    private File fileDaily;
    private File fileLog;
    private Scanner daily;
    private Scanner log;

    public Export(String fileNameDaily, String fileNameLog) {
        this.fileNameDaily = fileNameDaily;
        this.fileNameLog = fileNameLog;

        File fileDailyPath = new File(fileNameDaily);
        File fileLogPath = new File(fileNameLog);

        this.fileDaily = new File(fileDailyPath.getAbsolutePath());
        this.fileLog = new File(fileLogPath.getAbsolutePath());

        this.daily = new Scanner(fileNameDaily);
        this.log = new Scanner(fileNameLog);

    }

    public void exportTo(String path) throws IOException {
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

    }
}
