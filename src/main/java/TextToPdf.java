import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TextToPdf {
    public static void main(String[] args) throws IOException {
        String[] items = handleCommas(getFileElements("input.txt"));
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

    // Add " " to end of text provided following text does not begin with comma
    public static String[] handleCommas(String[] items) {
        for (int i = 0; i < items.length-1; i++) {
            // Skip commands
            if (items[i].charAt(0) != '.') {
                int j = i+1;
                while (j < items.length) {
                    // Scan ahead until text (non-command) is found
                    if (items[j].charAt(0) == '.') {
                        j++;
                    } else {
                        // Only append " " if next text doesn't begin with comma
                        if (items[j].charAt(0) != ',') {
                            items[i] += " ";
                        }
                        break;
                    }
                }
            }
        }
        return items;
    }
}
