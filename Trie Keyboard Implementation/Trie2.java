/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW3 (Trie)
 *
 * Quick Sort Used
 */

import java.util.*;

/**
 * Public Trie2 class that implements a Trie (prefix tree) data structure <br>
 * Uses a TreeMap to store the Trie and to keep it in an alphabetically sorted order <br> <br>
 * Class supports inserting words and retrieving or printing all words with a given prefix
 */
public class Trie2 {
	/**
	 * Private TrieNode class implementing a singular Node in the Trie <br>
	 * Each node contains a map of its children nodes <br>
	 * And a boolean value indicating whether this node completes a valid word
	 */
	private class TrieNode {
		// TreeMap is a Java built-in data structure
		// Basically it acts like a Hashtable or Hashmap, establishing a mapping between a Key and a Value
		// Unlike a hashtable; the keys in TreeMap are sorted
		Map<Character, TrieNode> children = new TreeMap<>();
		boolean aword = false; // Stores whether the current node represents the end of a complete word or if it is just a prefix in the Trie
		ArrayList<String> mostFreqUsed; // Stores the most frequently used words
	}

	private TrieNode root; // The root node of the Trie
	private WordItem[] dict; // The dictionary associated with the prefix Trie

	/**
	 * Public Constructor for Trie2 class <br>
	 * Initializes the empty root node of the Trie and the passed in WordItem array dictionary
	 */
	public Trie2(WordItem[] dictionary) {
		this.root = new TrieNode();
		this.dict = dictionary;
	}


	/**
	 * Public method inserts a string into the Trie, building nodes as needed <br>
	 * As each character is added, the method updates the most frequently used (MFU) word list for the current prefix
	 * @param s String representing the string we want to insert into the Trie
	 * @return ArrayList of Strings representing up to 9 of the most frequently used words associated with the current prefix
	 */
	public ArrayList<String> insertString(String s) {
		String prefix = "";

		char[] characters = s.toCharArray(); // Convert string into an array of characters

		TrieNode cur = root; // Start insertion/traversal from the root node of Trie

		for (char character : characters) { // Loop through each character in the string being inserted
			prefix += character;
			TrieNode next = cur.children.get(character); // Get the children node associated with the current character

			if (next == null) { // If the current character is not currently in the Trie, add it as a child of the current node
				cur.children.put(character, next = new TrieNode());
				next.mostFreqUsed = computeMFU(dict, prefix);
			}

			cur = next; // Move to the next node in the Trie
		}

		cur.aword = true; // Set the current node (last character in the string) as the end of a complete word

		return cur.mostFreqUsed;
	}


	/**
	 * Private method to compute up to 9 words that match the given prefix in the dictionary <br> <br>
	 * Sorts the found words by frequency (from high to low) <br>
	 * And breaks ties in frequency using reverse alphabetical order
	 * @param dict WordItem array representing the WordItems we will associate with the passed in prefix
	 * @param prefix String representing the prefix we are finding the most frequently associated/used words for
	 * @return ArrayList of Strings representing up to 9 of the most frequently used words associated with a specific TrieNode
	 */
	private ArrayList<String> computeMFU(WordItem[] dict, String prefix) {
		ArrayList<String> mfu = new ArrayList<>();

		int first = binarySearch(dict, prefix, "first"); // Binary Search finds first occurrence of word with matching prefix in the dictionary
		int last = binarySearch(dict, prefix, "last"); // Binary Search finds last occurrence of word with matching prefix in the dictionary

		if (first == -1 || last == -1) {
			return mfu;
		}

		WordItem[] sortedMFU = Arrays.copyOfRange(dict, first, last + 1); // Grab the specific segment of the array containing the prefix we are looking for
		quickSort(sortedMFU, 0, sortedMFU.length - 1); // Sort the array using quick sort

		for (int i = 0; i < Math.min(9, sortedMFU.length); i++) { // Only add up to 9 of the most frequently used words to the results
			mfu.add(sortedMFU[i].getWord());
		}

		return mfu;
	}


	/**
	 * Private method to perform a binary search on a WordItem array <br> <br>
	 * Finds either the index of the first or last occurrence of a word with a certain prefix
	 * @param dict WordItem array representing the dictionary we are searching for a specific prefix through
	 * @param prefix String representing the prefix we are searching for
	 * @param order String representing whether we want to find the first or last occurrence of a word with the specified prefix
	 * @return int representing the index of either the first or last occurrence of a word with a certain prefix
	 */
	private int binarySearch(WordItem[] dict, String prefix, String order) {
		int left = 0; // Start of the search range
		int right = dict.length - 1; // End of the search range
		int idx = -1; // Index of the word we are searching for, -1 if not found

		while (left <= right) {
			int midpoint = left + (right - left) / 2; // Calculate the midpoint to avoid overflow
			String word = dict[midpoint].getWord(); // Get the word at the midpoint

			if (word.startsWith(prefix)) { // Check if the word at midpoint starts with the prefix
				idx = midpoint;

				if (order.equals("first")) {
					right = midpoint - 1; // Continue searching towards the left to find the first occurrence
				} else {
					left = midpoint + 1; // Continue searching towards the right to find the last occurrence
				}
			} else if (prefix.compareTo(word) < 0) { // If the prefix is less than the word, search in the left half
				right = midpoint - 1;
			} else { // If the prefix is greater than the word, search in the right half
				left = midpoint + 1;
			}
		}

		return idx;
	}


	/**
	 * Private method to recursively call a quick sort algorithm on the most frequently used words <br> <br>
	 * In place sorting method that partitions the array around a pivot and sorts the subarrays to the left and right of the pivot
	 * @param mfuArray WordItem array representing the array of WordItems to sort using the quick sort algorithm
	 * @param low int representing the starting index of the subarray to sort
	 * @param high int representing the ending index of the subarray to sort
	 */
	private void quickSort(WordItem[] mfuArray, int low, int high) {
		// Only sort if there is more than one element in this part of the array/subarray
		if (low < high) {
			int partition = partition(mfuArray, low, high); // Partition the array around the pivot point

			quickSort(mfuArray, low, partition - 1); // Sort the left side of the pivot
			quickSort(mfuArray, partition + 1, high); // Sort the right side of the pivot
		}
	}


	/**
	 * Private method to rearrange elements in an array so that
	 * all WordItems with a higher count than the pivot are moved to the left side, and all lower or equal ones to the right <br>
	 * If counts are equal, words that come later alphabetically are treated as higher (reverse alphabetical order)
	 * @param mfuArray WordItem array representing the array to rearrange
	 * @param start int representing the starting index of the section to partition
	 * @param end int rpresenting the ending index of the section to partition (pivot is here)
	 * @return int representing the index where the pivot was placed (its correct sorted position)
	 */
	private int partition(WordItem[] mfuArray, int start, int end) {
		int pivotCount = mfuArray[end].getCount();  // Count of the pivot
		String pivotWord = mfuArray[end].getWord(); // Word of the pivot
		int greaterIndex = start - 1; // Last position where a higher count item was placed

		// Go through all items before the pivot
		for (int current = start; current < end; current++) {
			int currentCount = mfuArray[current].getCount();
			String currentWord = mfuArray[current].getWord();

			// If the current item should go before the pivot
			if (currentCount > pivotCount || (currentCount == pivotCount && currentWord.compareTo(pivotWord) > 0)) {
				greaterIndex++;

				// Swap the current item with the one at greaterIndex
				WordItem temp = mfuArray[greaterIndex];
				mfuArray[greaterIndex] = mfuArray[current];
				mfuArray[current] = temp;
			}
		}

		// Move the pivot into the correct spot
		WordItem temp = mfuArray[greaterIndex + 1];
		mfuArray[greaterIndex + 1] = mfuArray[end];
		mfuArray[end] = temp;

		return greaterIndex + 1;
	}
}