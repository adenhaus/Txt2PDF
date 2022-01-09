import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TextToPdf {
    public static void main(String[] args) throws IOException {
        String[] items = getFileElements("input.txt");
        String outputFile = "output.pdf";
        PdfBuilder pdf = new PdfBuilder(outputFile, items);

        // Arbitrary loop to demonstrate pagination
        int numPrints = 0;
        while (numPrints < 9) {
            pdf.processText();
            numPrints++;
        }

        pdf.closeDocument();
    }
 
    public static String[] getFileElements(String path) throws FileNotFoundException {
        StringBuilder data = new StringBuilder();
        File inputFile = new File(path);
        Scanner sc = new Scanner(inputFile);

        while (sc.hasNext()) {
            data.append(sc.nextLine()).append("\n");
        }
        sc.close();

        return data.toString().split("\n");
    }
}
