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

public class PDF {
    private int indent = 0;
    private Paragraph currentParagraph = new Paragraph();
    private Text text;

    private enum format {
        BOLD,
        ITALICS,
        REGULAR,
        BOLD_AND_ITALICS
    }
    private enum fill {
        FILL,
        NO_FILL
    }
    private enum textSize {
        NORMAL,
        LARGE
    }

    private format formatType;
    private fill fillType;
    private textSize fontSize;

    private final Document document;

    private final PdfFont defaultFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    private final PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
    private final PdfFont italicsFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
    private final PdfFont boldAndItalicsFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLDOBLIQUE);

    // CONSTRUCTOR
    public PDF(String outputFile) throws IOException {
        PdfWriter writer = new PdfWriter(outputFile);
        PdfDocument pdf = new PdfDocument(writer);
        this.document = new Document(pdf);
        this.formatType = format.BOLD;
        this.fillType = fill.NO_FILL;
        this.fontSize = textSize.NORMAL;
    }

    // METHODS
    public void setText(String inputString) {
        this.text = new Text(inputString);
    }

    public void addTextToParagraph() {
        currentParagraph.add(text);
    }

    public void addParagraphToDocument() {
        document.add(currentParagraph);
    }

    // Changes formatting rules based on command received
    public void formatOnCommand(String command) {
        if (command.equals(".large")) { fontSize = textSize.LARGE;
        } else if (command.equals(".normal")) { fontSize = textSize.NORMAL;
        } else if (command.equals(".regular")) { formatType = format.REGULAR;
        } else if (command.equals(".fill")) { fillType = fill.FILL;
        } else if (command.equals(".nofill")) { fillType = fill.NO_FILL;
        } else if (command.split(" ")[0].equals(".indent")) {
            String indentAmt = (command.split(" "))[1];
            indent += Integer.parseInt(indentAmt);
        // Allow text to be both bold an italicised at the same time
        } else if (command.equals(".bold")) {
            if (formatType == format.ITALICS) { formatType = format.BOLD_AND_ITALICS;
            } else { formatType = format.BOLD; }
        } else if (command.equals(".italics")) {
            if (formatType == format.BOLD) { formatType = format.BOLD_AND_ITALICS;
            } else { formatType = format.ITALICS; }
        // Add current paragraph to document if not empty, then clear paragraph
        } else if (command.equals(".paragraph")) {
            if (!currentParagraph.equals(new Paragraph())) { document.add(currentParagraph); }
            currentParagraph = new Paragraph();
        }
    }

    // FORMAT TEXT AND PARAGRAPH METHODS

    public void setIndent() {
        currentParagraph.setMarginLeft((float)(indent * 20));
    }

    public void setFontSize() {
        if (fontSize == textSize.LARGE) {
            text.setFontSize(30f);
        } else if (fontSize == textSize.NORMAL) {
            text.setFontSize(12f);
        }
    }

    public void setFontType() {
        switch (formatType) {
            case BOLD_AND_ITALICS:
                text.setFont(boldAndItalicsFont);
                break;
            case REGULAR:
                text.setFont(defaultFont);
                break;
            case BOLD:
                text.setFont(boldFont);
                break;
            case ITALICS:
                text.setFont(italicsFont);
                break;
        }
    }

    public void setFill() {
        if (fillType == fill.FILL) {
            currentParagraph.setTextAlignment(TextAlignment.JUSTIFIED);
        } else if (fillType == fill.NO_FILL) {
            currentParagraph.setTextAlignment(TextAlignment.LEFT);
        }
    }

    // OTHER METHODS

    public void closeDocument() {
        document.close();
    }

    public void resetFormatter() {
        fillType = fill.NO_FILL;
        formatType = format.REGULAR;
        fontSize = textSize.NORMAL;
        indent = 0;
        currentParagraph = new Paragraph();
    }
}
