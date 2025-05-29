/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW1
 */

import java.util.ArrayList;

/**
 *  Each WordItem object represents a word extracted from the text file. <br>
 *  It has three fields. String word stores the literal English word. <br>
 *  int count records the number of occurrence for that word in text file. <br>
 *  ArrayList atLines records a list of line numbers at which the word appears in the original text file. <br>
 *  NOTE: line number in this Arraylist 'atLines' should be unique, no duplicates.
 */
public class WordItem {
    private String word; // The unique word extracted from the text
    private int count; // The number of times the word has been extracted from the text
    private ArrayList<Integer> atLines; // The line numbers that the word was extracted from (NOTE: No repeats if multiple occurrences on same line)

    /**
     * Public constructor for a new WordItem for a new word that was extracted from our text file
     * @param word String representing the unique word that was extracted
     * @param c int representing the number of times the word has been found
     * @param atLine int representing the line number that the word was extracted from
     */
    public WordItem(String word, int c, int atLine) {
        this.word = word;
        this.count = c;
        this.atLines = new ArrayList<Integer>();

        this.atLines.add(atLine); // Adds line number to the ArrayList for the given word
    }

    /**
     * Getter that returns the word
     * @return String representing the word stored in the WordItem
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Getter that returns the count occurrence of the word
     * @return int representing the count occurrence of the word
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Setter that increments the count occurrence of the word by 1
     */
    public void incrementCount() {
        this.count++;
    }

    /**
     * Getter that returns the line numbers that the word was extracted from
     * @return ArrayList of Integers representing the line numbers the word was extracted from
     */
    public ArrayList<Integer> getAtLines() {
        return this.atLines;
    }

    /**
     * Override method builds a string containing a word, then the number of occurrences for it <br>
     * It will appear something like the following; from:2
     * @return String representing the word, the number of occurrences for it, and the lines that it was extracted from
     */
    @Override
    public String toString() {
        String ret = "";
        ret += word + ":" + this.count;

        return ret;
    }
}