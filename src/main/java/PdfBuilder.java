import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;

public class PdfBuilder {
    String[] items;
    MyDocument document;
    MyParagraph currentParagraph;
    MyText currentText;

    // CONSTRUCTOR
    public PdfBuilder(String outputFile, String[] elements) throws IOException {
        this.items = elements;
        PdfWriter writer = new PdfWriter(outputFile);
        PdfDocument pdf = new PdfDocument(writer);
        this.document = new MyDocument(pdf);
        this.currentParagraph = new MyParagraph();
        this.currentText = new MyText("");
    }

    // Main logic for building the PDF from list of text and commands
    public void processText() throws IOException {
        for (String item : items) {
            if (isCommand(item)) {
                formatOnCommand(item);
            } else {
                currentText.setText(item);
                currentParagraph.add(currentText);
                currentText = (MyText) currentText.copyProperties();
            }
        }
        document.add(currentParagraph);
        currentText = new MyText("");
        currentParagraph = new MyParagraph();
        document.resetIndent();
    }

    private Boolean isCommand(String item) {
        return item.charAt(0) == '.';
    }

    private void formatOnCommand(String command) {
        if (command.equals(".large")) { currentText.setFontSize(30f);
        } else if (command.equals(".normal")) { currentText.setFontSize(12f);
        } else if (command.equals(".regular")) { currentText.makeFontRegular();
        } else if (command.equals(".fill")) { currentParagraph.setTextAlignment(TextAlignment.JUSTIFIED);
        } else if (command.equals(".nofill")) { currentParagraph.setTextAlignment(TextAlignment.LEFT);
        } else if (command.split(" ")[0].equals(".indent")) { setIndent(command);
        } else if (command.equals(".bold")) { currentText.makeFontBold();
        } else if (command.equals(".italics")) { currentText.makeFontItalic();
            // Add current paragraph to document if not empty, then clear paragraph
        } else if (command.equals(".paragraph")) {
            if (!currentParagraph.equals(new MyParagraph())) { document.add(currentParagraph); }
            currentParagraph = currentParagraph.copyProperties();
        }
    }

    private void setIndent(String command) {
        String indentAmt = (command.split(" "))[1];
        document.setIndent(Integer.parseInt(indentAmt));
        currentParagraph.setIndent(document.getIndent());
    }

    public void closeDocument() {
        document.close();
    }
}
