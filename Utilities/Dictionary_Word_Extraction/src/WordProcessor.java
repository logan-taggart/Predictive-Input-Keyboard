/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW1
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The WordProcessor class would extract words from the raw text file (a.k.a. tokenization). <br> <br>
 * After extracted one word, it either creates a new WordItem object and insert the object into LinkedList at a proper location,
 * or it calls a method in MyLinkedList to increment
 * the word occurrence and to update line-number list if a word has already been existing. <br> <br>
 *
 * The class also provides File I/O methods. Write the resultant string or list back to a file.
 */
public class WordProcessor {
    /**
     * Method reads in lines from the specified file and adds each of them to an ArrayList
     * @param name String representing the name of the file to be read from
     * @return ArrayList of Strings representing each of the trimmed lines from the file
     * @throws IOException if there is an error reading the input file
     */
    public ArrayList<String> fileRead(String name) throws IOException {
        // Initialize readers
        FileReader fileReader = new FileReader(name);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        ArrayList<String> lines = new ArrayList<String>();
        String aline = null;

        // Read in and add lines into ArrayList until end of file
        while ((aline = bufferedReader.readLine()) != null) {
            aline = aline.trim();

            if (!aline.isEmpty()) {
                lines.add(aline);
            }
        }

        fileReader.close();

        return lines;
    }

    /**
     * Method that extracts words from line of text,
     * and stores the word, number of occurrences, and the line it was found on in a LinkedList <br> <br>
     * If a word has not been found yet then we create a new entry into the LinkedList <br>
     * If a word has already been found then corresponding information is added/updated for it <br> <br>
     * Words are valid if they are either more than one character long or are "a"/"i" case-insensitive.
     * @param aline String representing the line of the text that we are extracting words from
     * @param lineNumber int representing the number of the line we are currently extracting from
     * @return MyLinkedList representing the WordItems extracted/generated from the given line of text
     */
    public MyLinkedList extractLine(String aline, int lineNumber) {
        boolean inWord = false; // Initialize boolean to track if we are positioned inside a word
        int i = 0; // Index of character positioned at in the line
        int start = 0; // Index of first character in the current word
        int wordLen = 0; // Length of current word positioned in

        MyLinkedList wordList = new MyLinkedList();

        // Traverse character by character through the line of text
        while (i < aline.length()) {
            if (Character.isLetter(aline.charAt(i))) { // If character is a letter
                if (!inWord) { // If we are not already positioned inside a word (meaning this is the first letter of the word)
                    start = i; // Set start of word to be the current index
                    inWord = true; // We are now in a word
                }
                wordLen++; // Increase the length of the word so far by 1
            }
            else if (inWord) { // If we are already in a word but current character is not a letter (Meaning we have hit the end of the word)
                String newWord = aline.substring(start, start + wordLen); // Retrieves the word substring

                // Check to make sure that word is either greater than the length of 1 or if it is 1, then it must be either "A" or "I" case-insensitive
                if (newWord.length() > 1 || newWord.equalsIgnoreCase("a") || newWord.equalsIgnoreCase("i")) {
                    // Call containWord method on extracted word, if it doesn't currently exist returns false, if it does updates information and returns true
                    if (!wordList.containWord(newWord, lineNumber)) { // If word wasn't found
                        WordItem item = new WordItem(newWord, 1, lineNumber); // Create and add new wordItem
                        wordList.add(item);
                    }
                }

                wordLen = 0; // Now set length back to 0 and reset inWord boolean to false
                inWord = false;
            }

            i++; // Increment loop index by 1
        }

        // Same logic as previous but handles case where word is at the end of a line since we are now outside of loop
        if (inWord) {
            String newWord = aline.substring(start, start + wordLen);

            if (newWord.length() > 1 || newWord.equalsIgnoreCase("a") || newWord.equalsIgnoreCase("i")) {
                if (!wordList.containWord(newWord, lineNumber)) {
                    WordItem item = new WordItem(newWord, 1, lineNumber);
                    wordList.add(item);
                }
            }
        }

        return wordList;
    }

    /**
     * Method to extract one line at a time and combine their extraction results into a LinkedList
     * @param fileName String representing the name of the file we are reading/extracting from
     * @return MyLinkedList representing all the extracted words, their occurrence counts, and the line numbers they were found on from the text file
     * @throws IOException if there is an error reading the input file
     */
    public MyLinkedList extractAll(String fileName) throws IOException {
        MyLinkedList wordList = new MyLinkedList();

        ArrayList<String> lines = fileRead(fileName);

        // Loops through each of the lines and extracts words into
        for (int i = 0; i < lines.size(); i++) {
            MyLinkedList line = extractLine(lines.get(i), i); // Extract words from singular line into LinkedList

            // Using iterator loop through LinkedList
            for (Object word : line) {
                // Cast object to be a WordItem
                WordItem item = (WordItem) word;

                // Add/Update wordList LinkedList using containWord method
                if (!wordList.containWord(item.getWord(), i)) {
                    wordList.add(item);
                }
            }
        }

        return wordList;
    }

    /**
     * Method to write LinkedList data out to a file
     * @param alist MyLinkedList representing the LinkedList of words and their corresponding data that we want to write to a file
     * @param fileName String representing the name of the file we want to write the LinkedList data to
     */
    public void writeToFile(MyLinkedList alist, String fileName) {
        FileWriter fileWriter = null;

        try {
            String content = alist.toString(); // Print LinkedList into string utilizing toString method

            // Initialize and write to specified file
            File newTextFile = new File(fileName);
            fileWriter = new FileWriter(newTextFile);
            fileWriter.write(content);
            fileWriter.close();
        }
        catch (IOException ex) { // Catches/handles any IOException errors that occurred during writing
            ex.printStackTrace();
        }
        finally {
            try {
                fileWriter.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}