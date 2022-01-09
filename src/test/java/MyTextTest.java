import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import junit.framework.TestCase;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class MyTextTest extends TestCase {
    private final PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    private final PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
    private final PdfFont italicsFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
    private final PdfFont boldAndItalicsFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLDOBLIQUE);

    public MyTextTest() throws IOException {
    }

    // FONTS
    public void testBoldToBoldAndItalics() throws IOException {
        MyText txt = new MyText("");
        txt.makeFontBold();
        txt.makeFontItalic();

        MyText txt2 = new MyText("");
        txt2.setFont(boldAndItalicsFont);

        assertEquals(txt2.getOwnProperty(20).toString(), txt.getOwnProperty(20).toString());
    }

    public void testItalicsToBoldAndItalics() throws IOException {
        MyText txt = new MyText("");
        txt.makeFontItalic();
        txt.makeFontBold();

        MyText txt2 = new MyText("");
        txt2.setFont(boldAndItalicsFont);

        assertEquals(txt2.getOwnProperty(20).toString(), txt.getOwnProperty(20).toString());
    }

    public void testBoldAndItalicsToRegular() throws IOException {
        MyText txt = new MyText("");
        txt.makeFontItalic();
        txt.makeFontBold();
        txt.makeFontRegular();

        MyText txt2 = new MyText("");
        txt2.setFont(regularFont);

        assertEquals(txt2.getOwnProperty(20).toString(), txt.getOwnProperty(20).toString());
    }

    public void testBoldToRegular() throws IOException {
        MyText txt = new MyText("");
        txt.makeFontBold();
        txt.makeFontRegular();

        MyText txt2 = new MyText("");
        txt2.setFont(regularFont);

        assertEquals(txt2.getOwnProperty(20).toString(), txt.getOwnProperty(20).toString());
    }

    public void testItalicsToRegular() throws IOException {
        MyText txt = new MyText("");
        txt.makeFontItalic();
        txt.makeFontRegular();

        MyText txt2 = new MyText("");
        txt2.setFont(regularFont);

        assertEquals(txt2.getOwnProperty(20).toString(), txt.getOwnProperty(20).toString());
    }

    public void testToRegularItalics() throws IOException {
        MyText txt = new MyText("");
        txt.makeFontItalic();

        MyText txt2 = new MyText("");
        txt2.setFont(italicsFont);

        assertEquals(txt2.getOwnProperty(20).toString(), txt.getOwnProperty(20).toString());
    }

    public void testToRegularBold() throws IOException {
        MyText txt = new MyText("");
        txt.makeFontBold();

        MyText txt2 = new MyText("");
        txt2.setFont(boldFont);

        assertEquals(txt2.getOwnProperty(20).toString(), txt.getOwnProperty(20).toString());
    }

    // COPY
    public void testCopyProperties() throws IOException {
        MyText txt = new MyText("Test");
        txt.makeFontBold();
        txt.setFontSize(20);

        MyText txt2 = (MyText) txt.copyProperties();

        assertEquals(txt2.getOwnProperty(20).toString(), txt.getOwnProperty(20).toString()); // Font
        assertEquals(txt2.getOwnProperty(24).toString(), txt.getOwnProperty(24).toString()); // Size
        assertThat(txt2.getText(), is(not(txt.getText()))); // Text should not be copied
    }
}
