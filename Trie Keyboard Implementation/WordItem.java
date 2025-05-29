/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW3 (Trie)
 */

/**
 *  Each WordItem object represents a word extracted from the text file. <br>
 *  It has two fields. String word stores the literal English word. <br>
 *  int count records the amount of occurrence for that word in a text file.
 */
public class WordItem {
	private String word; // The unique word extracted from the text
	private int count; // The number of times the word has been extracted from the text

	/**
	 * Public constructor for a new WordItem for a new word that was extracted from our text file
	 * @param word String representing the unique word that was extracted
	 * @param c int representing the number of times the word has been found
	 */
	public WordItem(String word, int c) {
		this.word = word;
		this.count = c;
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
}