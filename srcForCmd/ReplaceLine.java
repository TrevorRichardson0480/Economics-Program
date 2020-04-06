import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* The Replace Line class will:
 * - Use the constructor to set the name of the file that needs a line to be replaced
 * - Use the replaceData method to replace the line
 */

public class ReplaceLine {
    private String fileName;

    public ReplaceLine(String fileName) {
        this.fileName =  fileName;

    }

    // replaceData method will replace the given old line with a given new line
    public void replaceData(String oldLine, String newLine) throws IOException {
        // Create a new list of the file contents, get all content
        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));

        // for loop will look for the online. Once found, set the old line to the new line
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(oldLine)) {
                fileContent.set(i, newLine);
                break;

            }
        }

        // Replace the file contents with the new contents (which has the new line)
        Files.write(Paths.get(fileName), fileContent, StandardCharsets.UTF_8);

    }
}
