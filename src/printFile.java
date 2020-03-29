import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class printFile {

    public printFile(String fileName) throws FileNotFoundException {

        File filePath = new File(fileName);
        File file = new File(filePath.getAbsolutePath());
        Scanner readData = new Scanner(file);

        while (readData.hasNextLine()) {
            System.out.println(readData.nextLine());

        }
    }
}