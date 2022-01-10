import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Text;

import java.io.IOException;

public class MyText extends Text {
    private final PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    private final PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
    private final PdfFont italicsFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
    private final PdfFont boldAndItalicsFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLDOBLIQUE);

    // CONSTRUCTOR
    public MyText(String text) throws IOException {
        super(text);
    }

    // Allows text to be both bold and italicised
    public void makeFontBold() {
        if (this.getOwnProperty(20) == this.setFont(italicsFont).getOwnProperty(20)
                || this.getOwnProperty(20) == this.setFont(boldAndItalicsFont).getOwnProperty(20)) {
            this.setFont(boldAndItalicsFont);
        } else { this.setFont(boldFont); }
    }

    // Allows text to be both bold and italicised
    public void makeFontItalic() {
        if (this.getOwnProperty(20) == this.setFont(boldFont).getOwnProperty(20)
                || this.getOwnProperty(20) == this.setFont(boldAndItalicsFont).getOwnProperty(20)) {
            this.setFont(boldAndItalicsFont);
        } else { this.setFont(italicsFont); }
    }

    public void makeFontRegular() {
        this.setFont(regularFont);
    }

    // Copies properties (font and size), but not text itself, to new text object
    public MyText copyProperties() throws IOException {
        MyText newText = new MyText("");
        // The integers below correspond to particular properties as per the Itext7 docs
        if (this.hasOwnProperty(20)) {
            newText.setProperty(20, this.getOwnProperty(20)); // Font
        }
        if (this.hasOwnProperty(24)) {
            newText.setProperty(24, this.getOwnProperty(24)); // Font size
        }
        return newText;
    }
}
