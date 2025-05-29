/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW3 (Hashtable)
 *
 * Quick Sort Used
 */

import java.util.*;

/**
 * Public Hashtable class implements custom hashtable that maps string prefixes to lists of suggested words <br>
 * Used for prefix-based autocomplete/MFU (most frequently used) word lookup
 */
public class Hashtable {
	/**
	 * Private inner class representing each entry (key-value pair) in the hashtable
	 */
	private class HashEntry {
		private String key; // The unique prefix extracted from the text
		private ArrayList<String> value; // The words associated with that prefix
		private HashEntry next; // Reference to the next HashEntry in case of a hash collision

		/**
		 * Private constructor for a new HashEntry
		 * @param key String representing the unique prefix
		 * @param value ArrayList of Strings representing the words associated with the prefix
		 */
		private HashEntry(String key, ArrayList<String> value) {
			this.key = key;
			this.value = value;
			this.next = null;
		}
	}

	private static final int INITIAL_CAPACITY = 1024; // The initial capacity of the hashtable (number of buckets)
	private HashEntry[] table; // The array representing the buckets of entries
	private WordItem[] dict; // The dictionary of words used to compute predictions

	/**
	 * Public constructor for a new Hashtable
	 * @param dictionary WordItem array representing the array of WordItem objects used for MFU lookups
	 */
	public Hashtable(WordItem[] dictionary) {
		table = new HashEntry[INITIAL_CAPACITY];
		this.dict = dictionary;
	}


	/**
	 * Private method to hash a key to a bucket index
	 * @param key String representing the key to hash to a bucket index
	 * @return int representing the bucket index where the key is hashed to
	 */
	private int hash(String key) {
		return Math.abs(key.hashCode() % INITIAL_CAPACITY);
	}


	/**
	 * Private method that inserts a key-value pair into the hashtable, or updates it if the key exists
	 * @param key String representing the key to insert or update the value of in the hashtable
	 * @param words
	 */
	private void set(String key, ArrayList<String> words) {
		int index = hash(key); // Hash the key to a bucket index to find the correct bucket to insert the key-value pair into
		HashEntry current = table[index]; // Get the first HashEntry in the bucket at the hashed index, or null if the bucket is empty

		// Check if the key already exists by traversing the linked list
		while (current != null) {
			if (current.key.equals(key)) { // If key is found, update the existing value and exit
				current.value = words;
				return;
			}

			current = current.next; // Move to next entry
		}

		HashEntry newEntry = new HashEntry(key, words); // Create a new entry in the Hashtable and link properly to chain
		newEntry.next = table[index];
		table[index] = newEntry;
	}


	/**
	 * Private method that retrieves a key-value pair from the hashtable if it exists, or null if it does not
	 * @param key String representing the key to retrieve the value for from the hashtable, or null if it does not exist in the hashtable
	 * @return ArrayList of Strings representing the words associated with the key in the hashtable, or null if the key does not exist in the hashtable
	 */
	private ArrayList<String> get(String key) {
		int index = hash(key); // Hash the key to a bucket index to find the correct bucket to insert the key-value pair into
		HashEntry current = table[index]; // Get the first HashEntry in the bucket at the hashed index, or null if the bucket is empty

		// Check if the key already exists by traversing the linked list, and return the value if it exists, or null if it does not exist in the hashtable
		while (current != null) {
			if (current.key.equals(key)) {
				return current.value;
			}

			current = current.next; // Move to next entry
		}

		return null; // If the key does not exist in the hashtable return null
	}

	
	/**
	 * Public method inserts all prefixes of the given word into the hashtable and gets their associated words <br>
	 * If a prefix is not already in the table, it computes and stores it
	 * @param s String representing the prefix to insert
	 * @return ArrayList of Strings representing the list of words associated with the prefix
	 */
	public ArrayList<String> insertString(String s) {
		ArrayList<String> associatedWords = new ArrayList<>();

		for (int i = 1; i <= s.length(); i++) { // Iterate through each character in the prefix string, from 1 to the end of the string, builds up map
			String prefix = s.substring(0, i); // Define substring
			associatedWords = get(prefix); // Fetches the words associated with the given prefix

			// If prefix not found, compute and store it
			if (associatedWords == null) {
				associatedWords = computeMFU(dict, prefix); // Compute the most frequently used words associated with the prefix
				set(prefix, associatedWords); // Save the computed words to the hashtable
			}
		}

		return associatedWords;
	}


	/**
	 * Private method to compute up to 9 words that match the given prefix in the dictionary <br>
	 * Sorts the found words by frequency (from high to low) <br>
	 * And breaks ties in frequency using reverse alphabetical order
	 * @param dict WordItem array representing the WordItems we will associate with the passed in prefix
	 * @param prefix String representing the prefix we are finding the most frequently associated/used words for
	 * @return ArrayList of Strings representing up to 9 of the most frequently used words associated with a specific entry
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
	 * @param mfuArr the array of WordItems to sort using the quick sort algorithm
	 * @param low the starting index of the subarray to sort
	 * @param high the ending index of the subarray to sort
	 */
	private void quickSort(WordItem[] mfuArr, int low, int high) {
		// Only sort if there is more than one element in this part of the array/subarray
		if (low < high) {
			int partition = partition(mfuArr, low, high); // Partition the array around the pivot point

			quickSort(mfuArr, low, partition - 1); // Sort the left side of the pivot
			quickSort(mfuArr, partition + 1, high); // Sort the right side of the pivot
		}
	}


	/**
	 * Private method to rearrange elements in an array so that
	 * all WordItems with a higher count than the pivot are moved to the left side, and all lower or equal ones to the right <br>
	 * If counts are equal, words that come later alphabetically are treated as higher (reverse alphabetical order)
	 * @param wordArray the array to rearrange
	 * @param start the starting index of the section to partition
	 * @param end the ending index of the section to partition (pivot is here)
	 * @return the index where the pivot was placed (its correct sorted position)
	 */
	private int partition(WordItem[] wordArray, int start, int end) {
		int pivotCount = wordArray[end].getCount();  // Count of the pivot
		String pivotWord = wordArray[end].getWord(); // Word of the pivot
		int greaterIndex = start - 1; // Last position where a higher count item was placed

		// Go through all items before the pivot
		for (int current = start; current < end; current++) {
			int currentCount = wordArray[current].getCount();
			String currentWord = wordArray[current].getWord();

			// If the current item should go before the pivot
			if (currentCount > pivotCount || (currentCount == pivotCount && currentWord.compareTo(pivotWord) > 0)) {
				greaterIndex++;

				// Swap the current item with the one at greaterIndex
				WordItem temp = wordArray[greaterIndex];
				wordArray[greaterIndex] = wordArray[current];
				wordArray[current] = temp;
			}
		}

		// Move the pivot into the correct spot
		WordItem temp = wordArray[greaterIndex + 1];
		wordArray[greaterIndex + 1] = wordArray[end];
		wordArray[end] = temp;

		return greaterIndex + 1;
	}
}