import com.itextpdf.layout.element.Paragraph;

public class MyParagraph extends Paragraph {
    final int indentFactor = 20; // Arbitrary constant to make indent visibly larger

    public void setIndent(int indent) {
        this.setMarginLeft((float)(indent * indentFactor));
    }

    // Copies properties (fill and indent), but not text, to new paragraph object
    public MyParagraph copyProperties() {
        MyParagraph newParagraph = new MyParagraph();
        // The integers below correspond to particular properties as per the Itext7 docs
        if (this.hasOwnProperty(70)) {
            newParagraph.setProperty(70, this.getOwnProperty(70)); // Text alignment (fill)
        }
        if (this.hasOwnProperty(44)) {
            newParagraph.setProperty(44, this.getOwnProperty(44)); // Left margin (indent)
        }
        return newParagraph;
    }
}
