import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/* The PrintFile class:
 * - Iterates through a given file and prints its contents, line by line
 */

public class PrintFile {

    // Print the given file name
    public static void printFile(String fileName) throws FileNotFoundException {

        File filePath = new File("../saved_data/" + fileName);
        File file = new File(filePath.getAbsolutePath());
        Scanner readData = new Scanner(file);

        while (readData.hasNextLine()) {
            System.out.println(readData.nextLine());

        }
    }
}