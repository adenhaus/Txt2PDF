import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;

public class PdfBuilder {
    String[] lines;
    MyDocument document;
    MyParagraph currentParagraph;
    MyText currentText;

    // CONSTRUCTOR
    public PdfBuilder(String outputFile, String[] elements) throws IOException {
        PdfWriter writer = new PdfWriter(outputFile);
        PdfDocument pdf = new PdfDocument(writer);
        this.lines = elements;
        this.document = new MyDocument(pdf);
        this.currentParagraph = new MyParagraph();
        this.currentText = new MyText("");
    }

    // Main logic for building the PDF from list of text and commands
    public void processText() throws IOException {
        for (String item : lines) {
            if (isCommand(item)) {
                formatOnCommand(item);
            } else {
                currentText.setText(item);
                currentParagraph.add(currentText);
                currentText = currentText.copyProperties();
            }
        }
        // Once loop completes, current paragraph hasn't been added to document yet
        document.add(currentParagraph);
        // Reset document elements
        currentText = new MyText("");
        currentParagraph = new MyParagraph();
        document.setIndent(0);
    }

    // Formats current text and paragraph based on command
    private void formatOnCommand(String command) {
        if (command.equals(".large")) { currentText.setFontSize(30f);
        } else if (command.equals(".normal")) { currentText.setFontSize(12f);
        } else if (command.equals(".regular")) { currentText.makeFontRegular();
        } else if (command.equals(".fill")) { currentParagraph.setTextAlignment(TextAlignment.JUSTIFIED);
        } else if (command.equals(".nofill")) { currentParagraph.setTextAlignment(TextAlignment.LEFT);
        } else if (command.split(" ")[0].equals(".indent")) {
            int newIndent = calcIndent(command);
            document.setIndent(newIndent);
            currentParagraph.setIndent(newIndent);
        } else if (command.equals(".bold")) { currentText.makeFontBold();
        } else if (command.equals(".italics")) { currentText.makeFontItalic();
            // Add current paragraph to document if not empty, then clear paragraph
        } else if (command.equals(".paragraph")) {
            if (!currentParagraph.equals(new MyParagraph())) { document.add(currentParagraph); }
            currentParagraph = currentParagraph.copyProperties();
        }
    }

    private int calcIndent(String command) {
        int indentAmt = Integer.parseInt((command.split(" "))[1]);
        int currentIndent = document.getIndent();
        return indentAmt + currentIndent;
    }

    private Boolean isCommand(String item) {
        return item.charAt(0) == '.';
    }

    public void closeDocument() {
        document.close();
    }
}
