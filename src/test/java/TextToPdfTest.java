import junit.framework.TestCase;

import java.io.FileNotFoundException;

public class TextToPdfTest extends TestCase {

    public void testGetFileElements() throws FileNotFoundException {
        String[] items = {".large", "My PDF Document", ".normal", ".paragraph", "This is my", ".italics", "very first",
                ".regular", "pdf document, and the output is formatted really well. " +
                "While this paragraph is not filled, the following one has automatic filling set:", ".paragraph",
                ".indent +2", ".fill", "\"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
                "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
                "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"",
                ".paragraph", ".nofill", ".indent -2", "Well that was", ".bold", "exciting", ".regular",
                ", good luck!"};

        for (int i = 0; i < items.length; i++) {
            assertEquals(items[i], TextToPdf.getFileElements("input.txt")[i]);
        }
    }

    public void testHandleCommas() {
        String[] itemsBefore = {".large", "My PDF Document", ".normal", ".paragraph", "This is my", ".italics", "very first",
                ".regular", "pdf document, and the output is formatted really well. " +
                "While this paragraph is not filled, the following one has automatic filling set:", ".paragraph",
                ".indent +2", ".fill", "\"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
                "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
                "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"",
                ".paragraph", ".nofill", ".indent -2", "Well that was", ".bold", "exciting", ".regular",
                ", good luck!"};

        String[] itemsAfterExpected = {".large", "My PDF Document ", ".normal", ".paragraph", "This is my ", ".italics", "very first ",
                ".regular", "pdf document, and the output is formatted really well. " +
                "While this paragraph is not filled, the following one has automatic filling set: ", ".paragraph",
                ".indent +2", ".fill", "\"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
                "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
                "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\" ",
                ".paragraph", ".nofill", ".indent -2", "Well that was ", ".bold", "exciting", ".regular",
                ", good luck!"};

        String[] itemsAfter = TextToPdf.handleCommas(itemsBefore);

        for (int i = 0; i < itemsBefore.length; i++) {
            assertEquals(itemsAfter[i], itemsAfterExpected[i]);
        }
    }
}