/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW1
 */

import java.io.IOException;
import java.util.Scanner;

/**
 * Tester Class to ensure that the following works as is expected: Streaming Text Processing,
 * Tokenization, Counting Word Occurrence, List Sorting, Comparator, Comparable and file I/O.
 */
public class Tester {

    /**
     * Main method that specifies a filepath <br>
     * Extracts the words in the file into our LinkedList <br>
     * Writes the results of the LinkedList to a file (This should be based alone on alphabetical order) <br>
     * Then sorts the LinkedList based off upon word occurrence first (descending) then by alphabetical if there are matching occurrence numbers <br>
     * @param args String array representing the values passed in
     * @throws IOException if there is an error reading from the input file or writing to the output file
     */
    public static void main(String[] args) throws IOException {
        // Text File Paths
        String fileName1="files/testfile1"; // Smaller File
        String fileName2="files/testfile2"; // Larger File

        // Initialize WordProcessor
        WordProcessor wp = new WordProcessor();

        // Initialize Comparators
        AlphabeticalComparator alphaSort = new AlphabeticalComparator();
        CountComparator countSort = new CountComparator();

        // Get file choice input from the user
        Scanner scanner = new Scanner(System.in);
        String fileChoice = "";

        // Take in and validate file choice number
        while (!fileChoice.equals("1") && !fileChoice.equals("2")) {
            System.out.print("Would you like to extract from file 1 or file 2 (Enter 1 or 2): ");
            fileChoice = scanner.nextLine().trim();

            if (!fileChoice.equals("1") && !fileChoice.equals("2")) {
                System.out.println("Invalid input. Enter 1 or 2.");
            }
        }

        // Extract from file based on user input we collected
        if (fileChoice.equals("1")) {
            // Extract words from file 1
            MyLinkedList allWords = wp.extractAll(fileName1);

            // Alphabetical Comparator sort and write to output file
            allWords.sort(alphaSort);
            wp.writeToFile(allWords, "outSort1");

            // Count Comparator sort and write to output file
            allWords.sort(countSort);
            wp.writeToFile(allWords, "outSort2");

            System.out.println(allWords);
        } else {
            // Extract words from file 2
            MyLinkedList allWords2 = wp.extractAll(fileName2);

            // Alphabetical Comparator sort and write to output file
            allWords2.sort(alphaSort);
            wp.writeToFile(allWords2, "out2Sort1");

            // Count Comparator sort and write to output file
            allWords2.sort(countSort);
            wp.writeToFile(allWords2, "out2Sort2");

            System.out.println(allWords2);
        }
    }
}