import com.itextpdf.layout.properties.TextAlignment;
import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class MyParagraphTest extends TestCase {
    public void testCopyProperties() {
        MyParagraph paragraph = new MyParagraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.setMarginLeft(20);
        paragraph.add("Test");

        MyParagraph paragraph2 = paragraph.copyProperties();

        assertEquals(paragraph2.getOwnProperty(70).toString(), paragraph.getOwnProperty(70).toString()); // Font
        assertEquals(paragraph2.getOwnProperty(44).toString(), paragraph.getOwnProperty(44).toString()); // Size
        assertThat(paragraph2.getChildren(), is(not(paragraph.getChildren()))); // Text should not be copied
    }
}
