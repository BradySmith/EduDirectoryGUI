/*
 * Authors      Nsid    ID
 * Max Gooding, mag501, 11087688
 * Brady Smith, bas453, 11135973
 */
package personDatabase;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/*
 * NumbersOnlyDocumentFilter is a filter for document mutation methods for 
 * a text field that will replace any non numbers with a blank string or insert a blank
 * string when there is any non numbers
 */
public class NumbersOnlyDocumentFilter extends DocumentFilter {

    public void insertString(DocumentFilter.FilterBypass fb, int offset,
            String text, AttributeSet attr)
            throws BadLocationException {

        fb.insertString(offset, text.replaceAll("[^0-9-.]", ""), attr);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
            String text, AttributeSet attrs)
            throws BadLocationException {

        fb.replace(offset, length, text.replaceAll("[^0-9-.]", ""), attrs);
    }
}
