/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW1
 */

import java.util.Comparator;

/**
 * Comparator Class to sort WordItems <br>
 * Sorts by alphabetical order
 */
public class AlphabeticalComparator implements Comparator<WordItem> {
    /**
     * Override method case-insensitively compares WordItem words by alphabetical order
     * @param firstWordItem the first WordItem object to be compared.
     * @param secondWordItem the second WordItem object to be compared.
     * @return int representing the value returned from the alphabetical comparison of the wordItem words
     */
    @Override
    public int compare(WordItem firstWordItem, WordItem secondWordItem) {
        // Comparison will be either -1, 0, 1
        // -1 means that firstWordItem will come before secondWordItem
        // 0 means that firstWordItem is the same as secondWordItem -> This SHOULD NOT happen in our case since we are storing unique words
        // 1 means that firstWordItem will come after secondWordItem
        int alphabeticalComparison = firstWordItem.getWord().compareToIgnoreCase(secondWordItem.getWord());

        return alphabeticalComparison;
    }
}