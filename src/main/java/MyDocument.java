import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

public class MyDocument extends Document {
    private int indent;

    public MyDocument(PdfDocument pdfDoc) {
        super(pdfDoc);
    }

    public void setIndent(int indent) {
        this.indent += indent;
    }

    public int getIndent() {
        return indent;
    }
}
