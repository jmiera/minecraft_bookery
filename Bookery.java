import IBookery;

class Bookery implements IBookery {
    private StringBuilder[] book;
    private int numPages = 0;
    private int[] charLengths = new int[] { 3, 1, 3, 5, 5, 5, 5, 1, 3, 3, 3, 5, 1, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
            5, 1, 1, 4, 5, 4, 5, 6, 5, 5, 5, 5, 5, 5, 5, 5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 3, 5, 3,
            5, 5, 2, 5, 5, 5, 5, 5, 4, 5, 5, 1, 5, 4, 2, 5, 5, 5, 5, 5, 5, 5, 3, 5, 5, 5, 5, 5, 5, 3, 1, 3, 6 };

    public void loadBook(String text) {
        book = new StringBuilder[100];
        book[0] = new StringBuilder();
        int i;
        int line_number = 0; // There are 14 possible rows or lines. This is the index of the line.
        int line_pxl_length = 0; // This is the pixel length of the current line
        int page_char_count = 0; // This is the number of characters on the current page
        int word_pxl_length = 0; // This is the number of pixels so far in the current word
        int page_number = 0;
        StringBuilder current_word = new StringBuilder("");
        for (i = 0; i < text.length(); i++) {
            if (text[i] != '\n' && (text[i] < 32 || text[i] > 126)) {
                continue;
            }
            if (text[i] == '\n') {
                if (line_number < 14) {
                    line_number++;
                    book[page_number].append(text[i]);
                    page_char_count += 2;
                } else {
                    page_number++;
                    line_number = 0;
                    page_char_count = 0;
                }
                line_pxl_length = 0;
                word_pxl_length = 0;
                if (current_word.length() > 0) {
                    current_word = new StringBuilder("");
                }
                continue;
            }
            if (text[i] == ' ') {
                word_pxl_length = 0;
                if (current_word.length() > 0) {
                    current_word = new StringBuilder("");
                }
                if (page_char_count >= 255
                        || (line_number == 14 && line_pxl_length + getCharPixelLength(text[i]) > 113)) {
                    page_number++;
                    line_number = 0;
                    page_char_count = 0;
                    line_pxl_length = 0;
                    continue;
                }
                if (line_number < 14 && line_pxl_length + getCharPixelLength(text[i]) > 113) {
                    line_pxl_length = 0;
                    line_number++;
                }
                line_pxl_length += getCharPixelLength(text[i]);
                book[page_number].append(text[i]);
            } else {
                // if (word_pxl_length + getCharPixelLength(text[i]) > 113)
                word_pxl_length += getCharPixelLength(text[i]);
                current_word.append(text[i]);
            }
        }
    }

    public int getNumPages() {
        return numPages;
    }

    public String getPage(int page_number) {
        if (numPages != 0 && page_number > 0) {
            return book[page_number - 1];
        } else {
            return "";
        }
    }

    private int getCharPixelLength(char c) {
        int i = c - 32;
        if (i >= 0 && i < 95) {
            return charLengths[i];
        }
        return 0;
    }
}