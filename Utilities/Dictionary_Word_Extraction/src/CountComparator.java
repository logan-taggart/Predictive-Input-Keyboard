/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW1
 */

import java.util.Comparator;

/**
 * Comparator Class to sort WordItems <br>
 * Prioritizes sorting by occurrence count of WordItem <br>
 * For subsets with matching occurrence counts sorts by alphabetical order
 */
public class CountComparator implements Comparator<WordItem> {
    /**
     * Override method compares WordItems by count descending, then if matching, by alphabetical order
     * @param firstWordItem the first WordItem object to be compared.
     * @param secondWordItem the second WordItem object to be compared.
     * @return int representing the numerical count comparisons of the WordItems,
     * or alternatively if the counts are the same the alphabetical comparison of the wordItems
     */
    @Override
    public int compare(WordItem firstWordItem, WordItem secondWordItem) {
        // Comparison will be either -1, 0, 1
        // We flip order of normal comparison so that count occurrence order is descending
        // -1 means that secondWordItem will come before firstWordItem
        // 0 means that secondWordItem is the same as firstWordItem
        // 1 means that secondWordItem will come after firstWordItem
        int countComparison = Integer.compare(secondWordItem.getCount(), firstWordItem.getCount());

        if (countComparison != 0) { // If word count occurrences are not equal to each other return result of count comparison
            return countComparison;
        }

        // Comparison will be either -1, 0, 1
        // -1 means that firstWordItem will come before secondWordItem
        // 0 means that firstWordItem is the same as secondWordItem -This SHOULD NOT happen in our case since we are storing unique words
        // 1 means that firstWordItem will come after secondWordItem
        int alphabeticalComparison = firstWordItem.getWord().compareToIgnoreCase(secondWordItem.getWord());

        return alphabeticalComparison;
    }
}