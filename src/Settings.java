import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Settings {
    public File settingsPath;

    public Settings() {
        settingsPath = new File("settings.txt");
    }

    public String getSetMonth() throws FileNotFoundException {
        String currLine;

        File settings = new File(settingsPath.getAbsolutePath());
        Scanner settingsFile = new Scanner(settings);

        while(settingsFile.hasNextLine()) {
            if (settingsFile.next().startsWith("CURRENTMONTH")) {
                settingsFile.next();
                return settingsFile.next();
            }

        }

        return null;
    }

    public void setDifMonth(String newMonth) throws IOException {
        String currMonth = getSetMonth();

        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(settingsPath.getAbsolutePath()), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals("CURRENTMONTH = " + currMonth)) {
                fileContent.set(i, "CURRENTMONTH = " + newMonth);
                break;
            }
        }

        Files.write(Paths.get(settingsPath.getAbsolutePath()), fileContent, StandardCharsets.UTF_8);

    }
}
