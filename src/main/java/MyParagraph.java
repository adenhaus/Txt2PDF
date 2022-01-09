import com.itextpdf.layout.element.Paragraph;

public class MyParagraph extends Paragraph {

    public void setIndent(int indent) {
        this.setMarginLeft((float)(indent * 20));
    }

    public Paragraph copyProperties() {
        MyParagraph newParagraph = new MyParagraph();
        if (this.hasOwnProperty(70)) {
            newParagraph.setProperty(70, this.getOwnProperty(70)); // Text alignment (fill)
        }
        if (this.hasOwnProperty(44)) {
            newParagraph.setProperty(44, this.getOwnProperty(44)); // Left margin (indent)
        }
        return newParagraph;
    }
}
