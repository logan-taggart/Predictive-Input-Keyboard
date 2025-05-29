/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW2
 */

import java.util.*;

/**
 * Public Trie2 class that implements a Trie (prefix tree) data structure <br>
 * Uses a TreeMap to store the Trie and to keep it in an alphabetically sorted order <br> <br>
 * Class supports inserting words and retrieving or printing all words with a given prefix <br>
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
	}
	
	private TrieNode root; // The root node of the Trie

	/**
	 * Public Constructor for Trie2 class <br>
	 * Initializes the empty root node of the Trie
	 */
	public Trie2() {
		this.root = new TrieNode();
	}

	/**
	 * Public method to find all the words beginning with a specified prefix <br>
	 * Calls private method wordsPrefixedBy(TrieNode root, String p) <br> <br>
	 * Uses the Trie root node as the starting point for the search <br>
	 * And the passed in string as the prefix that we want to search for
	 * @param p String representing the prefix that we want to search the Trie for
	 * @return LinkedList representing all the words that start with the specified prefix
	 */
	public LinkedList<String> wordsPrefixedBy(String p) {
		// Call private method wordsPrefixedBy with the Trie root node as the starting point for the search
		return wordsPrefixedBy(this.root, p);
	}

	/**
	 * Private method to find all the words that start with the specified prefix <br> <br>
	 * Starting from the root node of the Trie as is called by the public method wordsPrefixedBy <br>
	 * And the passed in string as the prefix of the words that we want to search for and retrieve
	 * @param root TrieNode representing the Node we want to start searching from
	 * @param p String representing the prefix that we want to search the Trie for
	 * @return LinkedList representing all the words that start with the specified prefix
	 */
	private LinkedList<String> wordsPrefixedBy(TrieNode root, String p) {
		LinkedList<String> result = new LinkedList<String>(); // Initialize an empty LinkedList that will contain words with matching prefixes
		char[] characters = p.toCharArray(); // Convert string into an array of characters

		TrieNode cur = root; // Start search/traversal from the root node of Trie

		for (char character : characters) { // Loop through each character in the prefix
			if (!cur.children.containsKey(character)) { // If the character is not in the Trie, return an empty LinkedList
				return result;
			}

			cur = cur.children.get(character); // Move to the child node associated with the current character
		}

		printSorted(cur, p, result); // Call the printSorted(cur, p, result) function to print out words that start with the prefix

		return result;
	}

	/**
	 * Public method to insert a word/string into the proper position in the Trie <br>
	 * Calls private method insertString(TrieNode root, String s) <br> <br>
	 * Uses the Trie root node as the starting point for the insertion <br>
	 * And the passed in string as the word that we want to insert
	 * @param s String representing the string we want to insert into the Trie
	 */
	public void insertString(String s) {
		insertString(this.root, s);
	}

	/**
	 * Private method to insert a word/string into the proper position in the Trie <br> <br>
	 * Starting from the root node of the Trie as is called by the public method insertString <br>
	 * And the passed in string as the word that we want to insert
	 * @param root TrieNode representing the Node we want to start insertion from
	 * @param s String representing the string we want to insert into the Trie
	 */
	private void insertString(TrieNode root, String s) {
		char[] characters = s.toCharArray(); // Convert string into an array of characters

		TrieNode cur = root; // Start insertion/traversal from the root node of Trie

		for (char character : characters) { // Loop through each character in the string being inserted
			TrieNode next = cur.children.get(character); // Get the children node associated with the current character

			if (next == null) // If the current character is not currently in the Trie, add it as a child of the current node
				cur.children.put(character, next = new TrieNode());

			cur = next; // Move to the next node in the Trie
		}

		cur.aword = true; // Set the current node (last character in the string) as the end of a complete word
	}

	/**
	 * Public method to print all the complete words in the Trie in alphabetical order <br>
	 * Calls private method printSorted(TrieNode node, String s) <br> <br>
	 * Starting from the Trie root node as the beginning point for the traversal/printing <br>
	 * And begins the character combination with an empty string
	 */
	public void printSorted() {
		printSorted(this.root, "");
	}

	/**
	 * Private recursive method to print all the complete words in the Trie in alphabetical order <br> <br>
	 * Starting from the Trie root node as is called by printSorted <br>
	 * And the passed in string as the character combination we've traversed through
	 * @param node TrieNode representing the current node being traversed through
	 * @param s String representing the string that has been built up so far combining the characters from traversing the Trie
	 */
	private void printSorted(TrieNode node, String s) {
		// If the current node is the end of a complete word, print it out
		if (node.aword) {
			System.out.println(s);
		}

		// Recursively visits each child node in alphabetical order using depth-first search
		// Calls printSorted(node.children.get(character), s + character)
		for (Character character : node.children.keySet()) {
			printSorted(node.children.get(character), s + character);
		}
	}

	/**
	 * Private recursive method to print all the complete words in the Trie in alphabetical order <br>
	 * And adds them to a LinkedList <br> <br>
	 * Starting from the TrieNode that is passed in <br>
	 * And the passed in string as the character combination we've traversed through <br>
	 * And the passed in LinkedList as the LinkedList that will contain all the complete words
	 * @param node TrieNode representing the current node being traversed through
	 * @param s String representing the string that has been built up so far from combining the characters from traversing the Trie
	 * @param result LinkedList representing all the complete words that have been found so far
	 */
	private void printSorted(TrieNode node, String s, LinkedList<String> result) {
		// If the current node is the end of a complete word, print it out and add it to the LinkedList
		if (node.aword) {
			System.out.println(s);
			result.add(s);
		}

		// Recursively visits each child node in alphabetical order using depth-first search
		// Calls printSorted(node.children.get(character), s + character, result)
		for (Character character : node.children.keySet()) {
			printSorted(node.children.get(character), s + character, result);
		}
	}

	/**
	 * Public method to determine whether a specific prefix/word exists within the Trie <br>
	 * Calls private method findWord(TrieNode node, String s) <br> <br>
	 * Starting from the Trie root node as the starting point for the search <br>
	 * And the passed in string as the prefix/word that we want to search for
	 * @param s String representing the prefix/word that we want to search for
	 * @return boolean representing whether the prefix/word exists within the Trie
	 */
	public boolean findWord(String s) {
		return findWord(this.root, s);
	}

	/**
	 * Private recursive method to determine whether a specific prefix/word exists within the Trie <br> <br>
	 * Starting from the Trie root node as the starting point for the search as is called by the public method findWord <br>
	 * And the passed in string as the prefix/word that we want to search for
	 * @param node TrieNode representing the Node we want to start searching from
	 * @param s String representing the prefix/word we want to search for
	 * @return boolean representing whether the prefix/word exists within the Trie
	 */
	private boolean findWord(TrieNode node, String s) {
		if (s != null) { // Check to make sure the string is not null
			String rest = s.substring(1); // Rest is a substring of s, which excludes the first character in s, and is used for the next recursive call
			char character = s.charAt(0); // Variable character is the first letter of the current string

			TrieNode child = node.children.get(character);	// Return the child node that the character is associated next with.

			// If s currently contains only one letter and the current node has a child associated with that letter, we know we've found the prefix/word in Trie
			if (s.length() == 1 && child != null) {
				return true; // Base Case - We've found the prefix/word, so return true
			}
			if (child == null) {
				return false; // Base Case - We've not found the prefix/ word, so return false
			}
			else {
				// Recursively calls findWord(child, rest) on the child node
				// In this way, we follow the path of the trie with depth-first search from root down towards leaf
				return findWord(child, rest);
			}
		}

		return false; // Returns false if the string is null
	}

	/**
	 * Usage example of the Trie <br> <br>
	 * Main method that creates a new instance of a Trie <br>
	 * Then initializes and inserts words from a wordlist <br>
	 * Then traverses Trie to check if passed in prefixes/words exist in the Trie <br>
	 * And finally prints out all words in the Trie sorted alphabetically
	 * @param args String array representing the values passed in
	 */
	public static void main(String[] args) {
		Trie2 tr = new Trie2();
		
		tr.insertString("hello");
		tr.insertString("world");
		tr.insertString("hi");
		tr.insertString("ant");
		tr.insertString("an");
		
		System.out.println(tr.findWord("ant"));
		System.out.println(tr.findWord("an"));
		System.out.println(tr.findWord("hello"));
		System.out.println(tr.findWord("cant"));
		System.out.println(tr.findWord("hig"));
		System.out.println(tr.findWord("he"));
		
		tr.printSorted();
	}
}