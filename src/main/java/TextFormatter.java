public class TextFormatter {
    // Iterates over list of input strings, formats them and adds them to pdf
    public static void processText(String[] items, PDF pdf) {
        for (String item : items) {
            if (isCommand(item)) {
                pdf.formatOnCommand(item);
            } else {
                if (item.charAt(0) == ',') {
                    pdf.setText(item);
                } else {
                    pdf.setText(" " + item);
                }
                pdf.setIndent();
                pdf.setFontSize();
                pdf.setFontType();
                pdf.setFill();
                pdf.addTextToParagraph();
            }
        }

        // For text added to a paragraph but not the document, when end of list is reached
        pdf.addParagraphToDocument();
        pdf.resetFormatter();
    }

    // IDENTIFY COMMAND METHODS

    public static Boolean isCommand(String item) {
        return item.charAt(0) == '.';
    }
}
