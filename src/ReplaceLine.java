import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReplaceLine {
    private String fileName;

    public ReplaceLine(String fileName) {
        this.fileName =  fileName;

    }

    public void replaceData(String oldLine, String newLine) throws IOException {
        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(oldLine)) {
                fileContent.set(i, newLine);
                break;

            }
        }

        Files.write(Paths.get(fileName), fileContent, StandardCharsets.UTF_8);

    }
}
