import java.io.IOException;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

public class Formatter {
    String[] items;

    private int indent = 0;
    private Paragraph currentParagraph;
    private Text currentText;

    private final Document document;

    private final PdfFont defaultFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    private final PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
    private final PdfFont italicsFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
    private final PdfFont boldAndItalicsFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLDOBLIQUE);

    // CONSTRUCTOR
    public Formatter(String outputFile, String[] elements) throws IOException {
        this.items = elements;
        PdfWriter writer = new PdfWriter(outputFile);
        PdfDocument pdf = new PdfDocument(writer);
        this.document = new Document(pdf);
        this.currentParagraph = new Paragraph();
        this.currentText = new Text("");
    }

    public void processText() {
        for (String item : items) {
            if (isCommand(item)) {
                formatOnCommand(item);
            } else {
                currentText.setText(item + " ");
                currentParagraph.add(currentText);
                currentText = copyTextProperties();
            }
        }
        document.add(currentParagraph);
    }

    private Boolean isCommand(String item) {
        return item.charAt(0) == '.';
    }

    private void formatOnCommand(String command) {
        if (command.equals(".large")) { currentText.setFontSize(30f);
        } else if (command.equals(".normal")) { currentText.setFontSize(12f);
        } else if (command.equals(".regular")) { currentText.setFont(defaultFont);
        } else if (command.equals(".fill")) { currentParagraph.setTextAlignment(TextAlignment.JUSTIFIED);
        } else if (command.equals(".nofill")) { currentParagraph.setTextAlignment(TextAlignment.LEFT);
        } else if (command.split(" ")[0].equals(".indent")) { setIndent(command);
            // Allow text to be both bold an italicised at the same time
        } else if (command.equals(".bold")) { makeBold();
        } else if (command.equals(".italics")) { makeItalic();
            // Add current paragraph to document if not empty, then clear paragraph
        } else if (command.equals(".paragraph")) {
            if (!currentParagraph.equals(new Paragraph())) { document.add(currentParagraph); }
            currentParagraph = copyParagraphProperties();
        }
    }

    private void makeBold() {
        if (currentText.getOwnProperty(20) == currentText.setFont(italicsFont).getOwnProperty(20)) {
            currentText.setFont(boldAndItalicsFont);
        } else { currentText.setFont(boldFont); }
    }

    private void makeItalic() {
        if (currentText.getOwnProperty(20) == currentText.setFont(boldFont).getOwnProperty(20)) {
            currentText.setFont(boldAndItalicsFont);
        } else { currentText.setFont(italicsFont); }
    }

    private void setIndent(String command) {
        String indentAmt = (command.split(" "))[1];
        indent += Integer.parseInt(indentAmt);
        currentParagraph.setMarginLeft((float)(indent * 20));
    }

    private Paragraph copyParagraphProperties() {
        Paragraph newParagraph = new Paragraph();
        if (currentParagraph.hasOwnProperty(70)) {
            newParagraph.setProperty(70, currentParagraph.getOwnProperty(70)); // Text alignment (fill)
        }
        if (currentParagraph.hasOwnProperty(44)) {
            newParagraph.setProperty(44, currentParagraph.getOwnProperty(44)); // Left margin (indent)
        }
        return newParagraph;
    }

    private Text copyTextProperties() {
        Text newText = new Text("");
        if (currentText.hasOwnProperty(20)) {
            newText.setProperty(20, currentText.getOwnProperty(20)); // Font
        }
        if (currentText.hasOwnProperty(24)) {
            newText.setProperty(24, currentText.getOwnProperty(24)); // Font size
        }
        return newText;
    }

    public void closeDocument() {
        document.close();
    }
}
