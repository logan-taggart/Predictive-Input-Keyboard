/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW1
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MyLinkedList class contains a list of WordItem objects. <br><br>
 * This Data Structure holds the data that we extract from the provided text file. <br>
 * In WordProcessor Class, we add a new WordItem object to a MyLinkedList object each time we find a new English word. <br>
 * Each English word is represented by a WordItem Object. English word is stored as a String in the data field of the ListNode object. <br>
 */
public class MyLinkedList implements Iterable<Object> {
    /**
     * Private inner Node class representing a node in our LinkedList <br>
     * Each node contains an object as data and reference to the next node.
     */
    private class Node {
        private Object data; // The data being stored in the node
        private Node next; // The stored reference to the next Node

        /**
         * Private constructor the creates new Node that sets the data field and sets the reference to the next node
         * @param data Object representing the data being stored in the node
         * @param next Node representing the reference to the next node in the LinkedList
         */
        private Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }

        /**
         * Private constructor that sets the data field and sets the reference to the next node to be null
         * @param data Object representing the data the node will store
         */
        private Node(Object data) {
            this(data, null);
        }
    }

    private Node head; // The first Node in the LinkedList
    private int size; // The size/length of the LinkedList

    /**
     * Public default constructor that initializes an empty LinkedList with a dummy head node and sets size to be 0
     */
    public MyLinkedList() {
        this.head = new Node(null); // with Dummy Node
        this.size = 0;
    }

    /**
     * This method adds the passed in object to the end of the LinkedList
     * @param e Object representing the data we are wanting to add to the LinkedList
     * @return boolean representing whether the add operation was successful
     */
    public boolean add(Object e) {
        Node cur;

        // Traverse to end of LinkedList
        for (cur = head; cur.next != null; cur = cur.next){
            // Empty loop body
        }

        // Add data into a new node and set as last node in LinkedList then increment size by 1
        cur.next = new Node(e);
        this.size++;

        return true;
    }

    /**
     * Method to sort the LinkedList based on the implemented comparators <br> <br>
     * Either sorts based solely on alphabetical order,
     * or sorts based on descending word count then if there is entries with matching counts it sorts that subset based on alphabetical order <br> <br>
     * Form of insertion sort
     * @param comparator WordItem Comparator representing either our Alphabetical or Count Comparator
     */
    public void sort(Comparator<WordItem> comparator) {
        Node cur;
        MyLinkedList newList = new MyLinkedList();

        // Traverses through LinkedList, skips dummy head node
        for (cur = this.head.next; cur != null; cur = cur.next) {
            // Cast cur.data object into WordItem
            WordItem item = (WordItem) cur.data;

            // Initialize nodes to help with insertion
            Node insertCur = newList.head.next;
            Node insertPrev = newList.head;

            // Find correct position to insert current item (insertion sort logic)
            while (insertCur != null) {
                WordItem currentItem = (WordItem) insertCur.data;

                // If compare between currentItem and item we are inserting is > 0
                // Then we know currentItem in sorted list comes after item, so we have found the correct insertion position
                if (comparator.compare(currentItem, item) > 0) {
                    break;
                }

                // Else update references to continue traversal
                insertPrev = insertCur;
                insertCur = insertCur.next;
            }

            // Inserts sorted Node into correct position and increments LinkedList size by 1
            insertPrev.next = new Node(item, insertCur);
            newList.size++;
        }

        // Replace unsorted LinkedList with new sorted LinkedList
        this.head.next = newList.head.next;
    }

    /**
     * This method checks whether the passed-in parameter "word" exists or not in
     * this list. If it exists, the method update number occurrence and its show-up locations
     * (at which line(s) the word appear in the original text file) as well,
     * then it returns true, Otherwise, it returns false.
     * @param word String representing the word that our LinkedList will be checked for
     * @param atLine int representing the line number that the word was extracted from that our LinkedList will be checked for
     * @return boolean representing whether or not the word was found in the LinkedList
     */
    public boolean containWord(String word, int atLine) {
        // Loop through LinkedList starting after dummy head node
        for (Node cur = this.head.next; cur != null; cur = cur.next) {
            // Cast cur.data object into a WordItem
            WordItem wordItem = (WordItem) cur.data;

            // If passed in word is equal to newly cast WordItem (case-insensitively)
            // This means we've already seen and stored this word in the LinkedList
            if (wordItem.getWord().equalsIgnoreCase(word)) {
                wordItem.incrementCount(); // Increment the word's occurrence count by 1

                // Add line number to stored numbers if the word has not already been seen at that line
                if (!wordItem.getAtLines().contains(atLine)) {
                    wordItem.getAtLines().add(atLine);
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Override method builds a string of the data stored at current position in the LinkedList
     * @return String representing information of all the cur.data objects
     */
    @Override
    public String toString() {
        String result = "";

        // Loops through LinkedList after dummy head node
        for (Node cur = this.head.next; cur != null; cur = cur.next) {
            // Adds cur.data to line in resulting string
            result += cur.data + "\n";
        }

        return result;
    }


    /**
     * Override method that creates a new iterator for MyLinkedList class <br>
     * Starts from this.head.next so that it begins iteration at first actual Node and not the dummy head node
     * @return Iterator object that begins after dummy head node
     */
    @Override
    public Iterator<Object> iterator() {
        return new MyLinkedListIterator(this.head.next);
    }

    /**
     * Inner Public class implementing Iterator to allow iteration of the MyLinkedList
     */
    public class MyLinkedListIterator implements Iterator<Object> {
        private Node cur; // Current node in traversal process
        private int index; // Index to track the current position
        private Node prev; // Reference to previous node in traversal

        /**
         * Private constructor that creates a new iterator and sets the cur value to be the passed in Node <br>
         * We do this so that we can construct it with head.next so that we can bypass the dummy head
         * @param start Node representing the Node that we want to start the iteration of our LinkedList from
         */
        private MyLinkedListIterator(Node start) {
            this.cur = start; // with Dummy Node
            this.index = 0;
            prev = start;
        }

        /**
         * This method checks for the existence of a Node at the next position in the LinkedList
         * @return boolean representing whether or not there is a Node at the next spot in our LinkedList
         */
        public boolean hasNext() {
            return cur != null;
        }

        /**
         * Method that retrieves data of next Node if it exists and returns the corresponding data
         * @return Object representing the next Node in the LinkedList
         */
        public Object next() {
            // Checks if next position holds a node, if it does reassign cur and prev references and returns the data from the next node
            if (hasNext()) {
                Object data = cur.data;
                prev = cur;
                cur = cur.next;

                return data;
            }

            // Otherwise throws NoSuchElementException
            throw new NoSuchElementException();
        }

        /**
         * This method blocks the removal of Nodes from the LinkedList iterator <br>
         * Instead of performing the operation, it throws an UnsupportedOperationException error.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}