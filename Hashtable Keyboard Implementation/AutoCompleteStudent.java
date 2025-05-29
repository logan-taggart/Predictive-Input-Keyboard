/** Author: Yun Tian, created March 1st 2014, all rights are reserved
 * This only serves as a startup GUI. Students have to add more instance variables
 * and more code to fulfill the requirements of this project.
 * ------------------------------------------------------------------------------------
 * Logan Taggart
 * CSCD 499 - Predictive Input HW3 (Hashtable)
 */

import java.awt.*;
import java.awt.event.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;

import java.util.ArrayList;

import javax.swing.*;

/**
 * Public AutoCompleteStudent class implements a GUI-based predictive input application <br>
 * Builds GUI, Initializes Hashtable, Handles User Input, and Updates GUI Output <br> <br>
 * This app shows word predictions as you type. You can choose a prediction by pressing its corresponding key
 */
class AutoCompleteStudent extends JFrame implements KeyListener { 

	@Serial
	private static final long serialVersionUID = 1L;
	
	JTextArea output = new JTextArea();
	JTextArea input = new JTextArea();

	String partialWord = ""; // This is the prefix we are currently typing
	String current = ""; // This is the sentence/string of words we have typed/created so far

	boolean inWord = false; // Stores whether we are in the middle of typing a word

	Hashtable hashtable;
	ArrayList<String> mostFrequentlyUsedWords; // This is the current list of the most frequently used words based on the partialWord


	/**
	 * Sets up the GUI and loads the dictionary of WordItems into a Hashtable
	 * @param worditemDictionary WordItem array representing the WordItems we are initializing the predictive input application with
	 */
	public AutoCompleteStudent(WordItem[] worditemDictionary) {
		JFrame frame = new JFrame("Predictive Input Application (Hashtable)"); // Setup application GUI
		frame.setSize(640,640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(2,1));
		
		JPanel inputPanel = new JPanel();
		JPanel outPanel = new JPanel();
		
		outPanel.setBackground(Color.LIGHT_GRAY);
		inputPanel.setLayout(new GridLayout(1,1));
		outPanel.setLayout(new GridLayout(1,1));
		
		outPanel.add(output);
		inputPanel.add(input);
		output.setEditable(true);
		output.addKeyListener(this);
		input.setEditable(false);
		input.addKeyListener(this);
		input.setLineWrap(true);
		output.setLineWrap(true);

		// Change the font and the color in the input textArea
		Font font = new Font("Verdana", Font.BOLD, 16);
		input.setFont(font);
		input.setForeground(Color.BLUE);	

		frame.add(outPanel);
		frame.add(inputPanel);
		frame.setVisible(true);

		partialWord = "";  // This is the prefix that you currently have

		hashtable = new Hashtable(worditemDictionary); // Initialize a new hashtable

        for (WordItem wordItem : worditemDictionary) { // Insert each word from the dictionary into the hashtable
            hashtable.insertString(wordItem.getWord());
        }
	}


	/**
	 * Override function handles the event when a key is pressed <br> <br>
	 * Handles text input, selecting predictions, and punctuation <br>
	 * Based on the key pressed, it updates the prefix and as a result, changes the predicted words matching that prefix
	 * @param e the event to be processed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (!inWord) { // If we press key, and we are not currently in the middle of a word set inWord to true
			inWord = true;
		}	
		
		int keyCode = e.getKeyCode(); // Get the integer key code for the pressed key
		char ch = e.getKeyChar(); // Get the actual character of the pressed key
		int index = parseKeyCode(keyCode); // Convert key code into our simplified integer system
		
		// Handle regular alphabetic letter keys (A-Z, a-z)
		if (index < 0) {
			output.setEditable(true); // Echo what we input
			
			if (Character.isAlphabetic(ch) && inWord) {
				partialWord += Character.toLowerCase(ch); // Append the lowercase version of the typed character to the current prefix
				System.out.println("Current Prefix: \"" + partialWord + "\""); // Print the current prefix we are searching for to the console
				mostFrequentlyUsedWords = hashtable.insertString(partialWord); // Retrieve up to the 9 of the most frequent words associated with the prefix we typed
				input.setText(arrToString(mostFrequentlyUsedWords)); // Show the predictions that we have available for the user to choose from
			}
		}
		else if (index <= 9) { // Handles if the key pressed is either an entered, space, or numbers
			output.setEditable(false);

			// If there are predictions and the selected index is valid, append/autocomplete the chosen word
			if (mostFrequentlyUsedWords != null && index < mostFrequentlyUsedWords.size()) {
				current += mostFrequentlyUsedWords.get(index) + " ";
			}

			output.setText(current);

			// Reset variables to prepare for the next word
			inWord = false; // Recognize we are not currently inside a word
			partialWord = ""; // Reset prefix we are searching for
		}
		else if (index == 10 || index == 11) { // Handles punctuation (commas and periods)
			output.setEditable(false);
			current = current.substring(0, current.length() - 1); // Remove ending space

			if (index == 10) { // Add a comma to the sentence
				current += ", ";
			}
			else { // Add a period to the sentence
				current += ". "; // Period
			}

			output.setText(current);
		}
	}


	/**
	 * Override function handles the event when a key is typed
	 * @param e the event to be processed
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}


	/**
	 * Override function handles the event where a key is released
	 * @param e the event to be processed
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}


	/**
	 * Maps key events into key codes so they can be used for processing <br>
	 * Maps ENTER, SPACES, COMMAS, PERIODS, and NUMBERS (1-9)
	 * @param code int representing the key event value
	 * @return int representing the key code the event was mapped to
	 */
	private int parseKeyCode(int code) {
		return switch (code) {
			case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> 0; // Map Enter and Space to 0 (used to complete most predicted word and/or reset prefix)
			case KeyEvent.VK_1 -> 1; // Map number 1 key to value 1 (used to select prediction 1)
			case KeyEvent.VK_2 -> 2; // Map number 2 key to value 2 (used to select prediction 2)
			case KeyEvent.VK_3 -> 3; // Map number 3 key to value 3 (used to select prediction 3)
			case KeyEvent.VK_4 -> 4; // Map number 4 key to value 4 (used to select prediction 4)
			case KeyEvent.VK_5 -> 5; // Map number 5 key to value 5 (used to select prediction 5)
			case KeyEvent.VK_6 -> 6; // Map number 6 key to value 6 (used to select prediction 6)
			case KeyEvent.VK_7 -> 7; // Map number 7 key to value 7 (used to select prediction 7)
			case KeyEvent.VK_8 -> 8; // Map number 8 key to value 8 (used to select prediction 8)
			case KeyEvent.VK_9 -> 9; // Map number 9 key to value 9 (used to select prediction 9)
			case KeyEvent.VK_COMMA -> 10; // Map comma key to 10 (used to add a comma to the current text)
			case KeyEvent.VK_PERIOD -> 11; // Map period key to 11 (used to add a period to the current text)
			default -> -1; // Maps all other inputs to -1 (these won't do anything)
		};
	}


	/**
	 * Private function to create a String of up to 9 of the most frequently used words for a given prefix
	 * Formats String by adding 5 most common words to the first line, and the next 4 most common to the second line
	 * @param predictedWords ArrayList of Strings containing the list of up to 9 words matching the typed prefix
	 * @return String representing the list of most frequently used words
	 */
	private String arrToString(ArrayList<String> predictedWords) {
		String predictions = ""; // Initialize an empty string

		if (predictedWords == null || predictedWords.isEmpty())  // This is important, sometimes the prefix is not in the tree.
			return predictions;

		predictions += " --> " + predictedWords.get(0) + "  "; // Print highest probability word

		for (int i = 1; i < predictedWords.size(); i++) { // Print and format up to 8 more predicted words matching the prefix
			predictions += i + ": " + predictedWords.get(i) + "  ";

			if (i == 4) {
				predictions += "\n        ";
			}
		}

		return predictions;
	}


	/**
	 * Private method to read in a dictionary file <br>
	 * Reads one line from the dictionary at a time <br>
	 * Splits the word and frequency in the line, creates a new WordItem using them, and adds them to an ArrayList <br>
	 * Converts ArrayList to WordItem array and returns it
	 * @param fileName String representing the filepath to the dictionary we are reading from
	 * @return WordItem array representing the words and their frequencies that were read in from the dictionary
	 * @throws IOException if there is an error reading from the input file
	 */
	private static WordItem[] extractAll(String fileName) throws IOException {
		FileReader fr = new FileReader(fileName); // Initialize readers
		BufferedReader br = new BufferedReader(fr);

		ArrayList<WordItem> wordItems = new ArrayList<>(); // Create a new empty ArrayList that will hold the WordItems retrieved from the dictionary file
		String line;

		while ((line = br.readLine()) != null) { // Read in a single line while there are lines in the file
			line = line.trim();

			if (!line.isEmpty()) { // For non-empty lines split line to know the word and its frequency, then make WordItem out of it and add to the ArrayList
				String[] item = line.split(",");
				wordItems.add(new WordItem((item[0]), Integer.parseInt(item[1])));
			}
		}

		fr.close(); // Close readers
		br.close();

		WordItem[] dict = wordItems.toArray(new WordItem[0]); // Convert ArrayList to WordItem array

		return dict;
	}


	/**
	 * The main method that creates a new instance of AutoCompleteStudent <br>
	 * Initializes the prefix hashtable and launches the GUI <br>
	 * Times and prints the number of seconds that initialization takes (< 3 seconds)
	 * @param args String array representing the values passed in
	 * @throws IOException if there is an error reading from the input file
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Initializing with Hashtable...");

		long startTime = System.currentTimeMillis(); // Start initialization timer

		WordItem[] dict = AutoCompleteStudent.extractAll("./files/dictionary.txt"); // Read in WordItems from the "dictionary.txt" file
		new AutoCompleteStudent(dict); // Initialize Hashtable and program with the extracted WordItems

		long endTime = System.currentTimeMillis(); // End initialization timer
		
		System.out.println("Initialization is complete and is ready to use");
		System.out.println("Initialization Time: " + ((endTime - startTime) / 1000.0) + " seconds"); // Display time took to initialize
	}
}