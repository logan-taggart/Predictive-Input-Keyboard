/**
 * Logan Taggart
 * CSCD 499 - Predictive Input HW1
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnitTests {

    @Nested
    @DisplayName("AlphabeticalComparator Tests")
    public class AlphabeticalComparatorTests {
        private AlphabeticalComparator comparator;

        @BeforeEach
        public void setUp() {
            comparator = new AlphabeticalComparator();
        }

        @Test
        @DisplayName("AlphabeticalComparator successfully constructs")
        public void AlphabeticalComparatorConstructs() {
            assertInstanceOf(AlphabeticalComparator.class, comparator);
        }

        @Test
        @DisplayName("Compare smaller word to bigger word alphabetically")
        public void testAlphabeticalSmaller() {
            WordItem w1 = new WordItem("Around", 1, 1);
            WordItem w2 = new WordItem("Beach", 1, 2);

            assertEquals(-1, comparator.compare(w1, w2));
        }

        @Test
        @DisplayName("Compare bigger word to smaller word alphabetically")
        public void testAlphabeticalBigger() {
            WordItem w1 = new WordItem("Around", 1, 1);
            WordItem w2 = new WordItem("Beach", 1, 2);

            assertEquals(1, comparator.compare(w2, w1));
        }

        @Test
        @DisplayName("Compare two identical words alphabetically")
        public void testAlphabeticalSame() {
            WordItem w1 = new WordItem("Hello", 1, 1);
            WordItem w2 = new WordItem("hello", 1, 2);

            assertEquals(0, comparator.compare(w1, w2));
        }
    }

    // ----------------------------------------------------------------------------------

    @Nested
    @DisplayName("CountComparator Tests")
    public class CountComparatorTests {
        private CountComparator comparator;

        @BeforeEach
        public void setUp() {
            comparator = new CountComparator();
        }

        @Test
        @DisplayName("CountComparator successfully constructs")
        public void CountComparatorConstructs() {
            assertInstanceOf(CountComparator.class, comparator);
        }

        @Test
        @DisplayName("Compare smaller count to bigger count (DESC)")
        public void testCountSmaller() {
            WordItem w1 = new WordItem("Same", 1, 1);
            WordItem w2 = new WordItem("Same", 2, 2);

            assertEquals(1, comparator.compare(w1, w2));
        }

        @Test
        @DisplayName("Compare bigger count to smaller count (DESC)")
        public void testCountBigger() {
            WordItem w1 = new WordItem("Same", 1, 1);
            WordItem w2 = new WordItem("Same", 2, 2);

            assertEquals(-1, comparator.compare(w2, w1));
        }

        @Test
        @DisplayName("Compare two identical counts then alternatively by alphabetical")
        public void testCountSame() {
            WordItem w1 = new WordItem("New", 1, 1);
            WordItem w2 = new WordItem("Old", 1, 2);

            assertEquals(-1, comparator.compare(w1, w2));
        }
    }

    // ----------------------------------------------------------------------------------

    @Nested
    @DisplayName("MyLinkedList Tests")
    public class MyLinkedListTests {
        private MyLinkedList LL;

        @BeforeEach
        public void setUp() {
            LL = new MyLinkedList();
        }

        @Test
        @DisplayName("Linked List successfully constructs")
        public void LinkedListConstructs() {
            assertInstanceOf(MyLinkedList.class, LL);
        }

        @Test
        @DisplayName("Linked List Iterator successfully constructs")
        public void LinkedListIteratorConstructs() {
            Iterator<Object> LLIterator = new MyLinkedList().iterator();

            assertInstanceOf(MyLinkedList.MyLinkedListIterator.class, LLIterator);
        }

        @Test
        @DisplayName("LinkedList add works #1")
        public void LinkedListAdd1() {
            WordItem w1 = new WordItem("word", 1, 1);
            LL.add(w1);

            assertEquals("word:" + 1 + "\n", LL.toString());
        }

        @Test
        @DisplayName("LinkedList add works #2")
        public void LinkedListAdd2() {
            WordItem w1 = new WordItem("one", 3, 1);
            WordItem w2 = new WordItem("two", 2, 1);
            WordItem w3 = new WordItem("three", 1, 1);
            LL.add(w1);
            LL.add(w2);
            LL.add(w3);

            assertEquals("one:" + 3 + "\n" + "two:" + 2 + "\n" + "three:" + 1 + "\n", LL.toString());
        }

        @Test
        @DisplayName("LinkedList add works #3")
        public void LinkedListAdd3() {
            WordItem w1 = new WordItem("word", 1, 1);

            assertTrue(LL.add(w1));
        }

        @Test
        @DisplayName("LinkedList Sort works (alphabetical comparator)")
        public void LinkedListSort1() {
            WordItem w1 = new WordItem("word", 1, 1);
            WordItem w2 = new WordItem("item", 3, 1);
            WordItem w3 = new WordItem("test", 2, 1);
            LL.add(w1);
            LL.add(w2);
            LL.add(w3);
            LL.sort(new AlphabeticalComparator());

            assertEquals("item:" + 3 + "\n" + "test:" + 2 + "\n" + "word:" + 1 + "\n", LL.toString());
        }

        @Test
        @DisplayName("LinkedList Sort works (count comparator)")
        public void LinkedListSort2() {
            WordItem w1 = new WordItem("word", 1, 1);
            WordItem w2 = new WordItem("item", 3, 1);
            WordItem w3 = new WordItem("test", 4, 1);
            LL.add(w1);
            LL.add(w2);
            LL.add(w3);
            LL.sort(new CountComparator());

            assertEquals("test:" + 4 + "\n" + "item:" + 3 + "\n" + "word:" + 1 + "\n", LL.toString());
        }

        @Test
        @DisplayName("LinkedList Sort works (count comparator with match frequency count)")
        public void LinkedListSort3() {
            WordItem w1 = new WordItem("word", 4, 1);
            WordItem w2 = new WordItem("item", 2, 1);
            WordItem w3 = new WordItem("test", 2, 1);
            LL.add(w1);
            LL.add(w2);
            LL.add(w3);
            LL.sort(new CountComparator());

            assertEquals("word:" + 4 + "\n" + "item:" + 2 + "\n" + "test:" + 2 + "\n", LL.toString());
        }

        @Test
        @DisplayName("LinkedList ContainWord works #1")
        public void LinkedListContainWord1() {
            WordItem w1 = new WordItem("word", 4, 1);
            LL.add(w1);

            assertTrue(LL.containWord("word", 3));
        }

        @Test
        @DisplayName("LinkedList ContainWord works #2")
        public void LinkedListContainWord2() {
            WordItem w1 = new WordItem("word", 4, 1);
            LL.add(w1);

            assertFalse(LL.containWord("mystery", 3));
        }

        @Test
        @DisplayName("LinkedList ContainWord works #3")
        public void LinkedListContainWord3() {
            WordItem w1 = new WordItem("word", 4, 1);
            LL.add(w1);

            assertTrue(LL.containWord("WORD", 3));
        }

        @Test
        @DisplayName("LinkedList ToString works (empty list)")
        public void LinkedListToString1() {
            assertEquals("", LL.toString());
        }

        @Test
        @DisplayName("LinkedList ToString works (singular item)")
        public void LinkedListToString2() {
            WordItem w1 = new WordItem("word", 4, 1);
            LL.add(w1);

            assertEquals("word:" + 4 + "\n", LL.toString());
        }

        @Test
        @DisplayName("LinkedList ToString works (multiple items)")
        public void LinkedListToString3() {
            WordItem w1 = new WordItem("word", 4, 1);
            WordItem w2 = new WordItem("words", 10, 1);
            LL.add(w1);
            LL.add(w2);

            assertEquals("word:" + 4 + "\n" + "words:" + 10 + "\n", LL.toString());
        }

        @Test
        @DisplayName("Iterator Next Works #1")
        public void IteratorNext1() {
            WordItem w1 = new WordItem("first", 1, 1);
            LL.add(w1);
            Iterator<Object> LLIterator = LL.iterator();

            assertEquals(w1, LLIterator.next());
        }

        @Test
        @DisplayName("Iterator Next Works #2")
        public void IteratorNext2() {
            WordItem w1 = new WordItem("first", 1, 1);
            WordItem w2 = new WordItem("second", 1, 2);
            WordItem w3 = new WordItem("third", 1, 3);
            LL.add(w1);
            LL.add(w2);
            LL.add(w3);
            Iterator<Object> LLIterator = LL.iterator();

            assertEquals(w1, LLIterator.next());
            assertEquals(w2, LLIterator.next());
            assertEquals(w3, LLIterator.next());
        }

        @Test
        @DisplayName("Iterator Next Works (exception)")
        public void IteratorNext3() {
            Iterator<Object> LLIterator = LL.iterator();

            assertThrows(NoSuchElementException.class, () -> LLIterator.next());
        }

        @Test
        @DisplayName("Iterator HasNext Works #1")
        public void IteratorHasNext1() {
            WordItem w1 = new WordItem("first", 1, 1);
            LL.add(w1);
            Iterator<Object> LLIterator = LL.iterator();

            assertTrue(LLIterator.hasNext());
        }

        @Test
        @DisplayName("Iterator HasNext Works #2")
        public void IteratorHasNext2() {
            WordItem w1 = new WordItem("first", 1, 1);
            WordItem w2 = new WordItem("next", 1, 1);
            LL.add(w1);
            LL.add(w2);
            Iterator<Object> LLIterator = LL.iterator();

            assertTrue(LLIterator.hasNext());
            LLIterator.next();
            assertTrue(LLIterator.hasNext());
        }

        @Test
        @DisplayName("Iterator HasNext Works #3")
        public void IteratorHasNext3() {
            Iterator<Object> LLIterator = LL.iterator();

            assertFalse(LLIterator.hasNext());
        }

        @Test
        @DisplayName("Iterator Remove Does Not Work")
        public void IteratorRemove() {
            Iterator<Object> LLIterator = LL.iterator();

            assertThrows(UnsupportedOperationException.class, () -> LLIterator.remove());
        }
    }

    // ----------------------------------------------------------------------------------

    @Nested
    @DisplayName("WordItem Tests")
    public class WordItemTests {
        @Test
        @DisplayName("WordItem successfully constructs")
        public void WordItemConstructs() {
            WordItem item = new WordItem("Test", 1, 1);

            assertInstanceOf(WordItem.class, item);
        }

        @Test
        @DisplayName("WordItem getWord getter successfully retrieves #1")
        public void WordItemGetWord1() {
            WordItem item = new WordItem("Test", 1, 1);

            assertEquals("Test", item.getWord());
        }

        @Test
        @DisplayName("WordItem getWord getter successfully retrieves #2")
        public void WordItemGetWord2() {
            WordItem item = new WordItem("Word", 2, 1);

            assertEquals("Word", item.getWord());
        }

        @Test
        @DisplayName("WordItem getWord getter successfully retrieves #3")
        public void WordItemGetWord3() {
            WordItem item = new WordItem("Hello", 5, 5);

            assertEquals("Hello", item.getWord());
        }

        @Test
        @DisplayName("WordItem getCount getter successfully retrieves #1")
        public void WordItemGetCount1() {
            WordItem item = new WordItem("Test", 1, 1);

            assertEquals(1, item.getCount());
        }

        @Test
        @DisplayName("WordItem getCount getter successfully retrieves #2")
        public void WordItemGetCount2() {
            WordItem item = new WordItem("Word", 2, 1);

            assertEquals(2, item.getCount());
        }

        @Test
        @DisplayName("WordItem getCount getter successfully retrieves #3")
        public void WordItemGetCount3() {
            WordItem item = new WordItem("Hello", 5, 5);

            assertEquals(5, item.getCount());
        }

        @Test
        @DisplayName("WordItem incrementCount setter successfully sets #1")
        public void WordItemIncrementCount1() {
            WordItem item = new WordItem("Hello", 5, 5);
            item.incrementCount();

            assertEquals(6, item.getCount());
        }

        @Test
        @DisplayName("WordItem incrementCount setter successfully sets #2")
        public void WordItemIncrementCount2() {
            WordItem item = new WordItem("Hello", 2, 5);
            item.incrementCount();
            item.incrementCount();

            assertEquals(4, item.getCount());
        }

        @Test
        @DisplayName("WordItem incrementCount setter successfully sets #3")
        public void WordItemIncrementCount3() {
            WordItem item = new WordItem("Hello", 10, 5);
            item.incrementCount();
            item.incrementCount();
            item.incrementCount();

            assertEquals(13, item.getCount());
        }

        @Test
        @DisplayName("WordItem getAtLines getter successfully retrieves and addLine successfully sets #1")
        public void WordItemGetSetAtLines1() {
            WordItem item = new WordItem("Hello", 5, 5);
            ArrayList<Integer> lines = new ArrayList<Integer>();
            lines.add(5);

            assertEquals(lines, item.getAtLines());
        }

        @Test
        @DisplayName("WordItem getAtLines getter successfully retrieves and addLine successfully sets #2")
        public void WordItemGetSetAtLines2() {
            WordItem item = new WordItem("Hello", 2, 2);
            item.getAtLines().add(7);
            ArrayList<Integer> lines = new ArrayList<Integer>();
            lines.add(2);
            lines.add(7);

            assertEquals(lines, item.getAtLines());
        }

        @Test
        @DisplayName("WordItem getAtLines getter successfully retrieves and addLine successfully sets #3")
        public void WordItemGetSetAtLines3() {
            WordItem item = new WordItem("Hello", 1, 0);
            item.getAtLines().add(12);
            item.getAtLines().add(101);
            ArrayList<Integer> lines = new ArrayList<Integer>();
            lines.add(0);
            lines.add(12);
            lines.add(101);

            assertEquals(lines, item.getAtLines());
        }

        @Test
        @DisplayName("ToString prints properly #1")
        public void ToString1() {
            WordItem item = new WordItem("Test", 1, 1);

            assertEquals("Test:1", item.toString());
        }

        @Test
        @DisplayName("ToString prints properly #2")
        public void ToString2() {
            WordItem item = new WordItem("eastern", 12, 1);

            assertEquals("eastern:12", item.toString());
        }

        @Test
        @DisplayName("ToString prints properly #3")
        public void ToString3() {
            WordItem item = new WordItem("computer", 100, 1);

            assertEquals("computer:100", item.toString());
        }
    }

    // ----------------------------------------------------------------------------------

    @Nested
    @DisplayName("WordProcessor Tests")
    public class WordProcessorTests {
        private WordProcessor wp;

        @BeforeEach
        public void setUp() {
            wp = new WordProcessor();
        }

        @Test
        @DisplayName("FileRead works (exception)")
        public void FileRead() {
            assertThrows(IOException.class, () -> wp.fileRead("BADFILEPATH.txt"));
        }

        @Test
        @DisplayName("ExtractLine works")
        public void ExtractLine() {
            assertEquals("This:1" + "\n" + "is:1" + "\n" + "a:1" + "\n" + "test:1" + "\n",
                    wp.extractLine("This is a test.", 0).toString());
        }

        @Test
        @DisplayName("ExtractLine works (multiple of word)")
        public void ExtractLine2() {
            assertEquals("This:1" + "\n" + "test:2" + "\n" + "is:1" + "\n" + "a:1" + "\n",
                    wp.extractLine("This test is a test.", 0).toString());
        }

        @Test
        @DisplayName("ExtractLine works (singular letters)")
        public void ExtractLine3() {
            assertEquals("I:1" + "\n" + "a:1" + "\n",
                    wp.extractLine("I a t x", 0).toString());
        }

        @Test
        @DisplayName("ExtractLine works (case insensitive)")
        public void ExtractLine4() {
            assertEquals("HeLLo:2" + "\n",
                    wp.extractLine("HeLLo hElLo", 0).toString());
        }
    }
}