/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW2
 */

/**
 * Tester Class to ensure that the following works as is expected: <br>
 * Trie data structure creation, wordsPrefixedBy, findWord, insertString, printSorted
 */
public class Tester {
    /**
     * Main method that creates a new instance of a Trie <br>
     * Initializes and inserts words from a wordlist <br>
     * Then traverses Trie to search for words starting with the passed in prefix
     * @param args String array representing the values passed in
     */
    public static void main(String[] args) {
        Trie2 myTrie = new Trie2(); // Create a new instance of a Trie

        String[] wordList = {"apple", "bike", "bake", "pen", "did", "ape", "child", "cat", "file", "hello", "he", "hell"}; // Example word list

        for (String word : wordList) { // Loop through each word in the wordList
            myTrie.insertString(word); // Insert each word into the Trie
        }

        // Print out all words beginning with 'ap' in the Trie
        System.out.println("Words starting with 'ap':");
        myTrie.wordsPrefixedBy("ap");

        System.out.println();

        // Print out all words beginning with 'he' in the Trie
        System.out.println("Words starting with 'he':");
        myTrie.wordsPrefixedBy("he");
    }
}