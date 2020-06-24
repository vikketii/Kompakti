package kompakti.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PriorityQueueTest {
    PriorityQueue priorityQueue;

    @Before
    public void setUp() {
        priorityQueue = new PriorityQueue();
    }

    @Test
    public void addAndPollSingleValueIsCorrect() {
        priorityQueue.add(new BinaryNode((byte) 0, 0));
        assertEquals(0, priorityQueue.poll().getFrequency());
    }

    @Test
    public void addSingleValueIsCorrect() {
        priorityQueue.add(new BinaryNode((byte) 0, 0));
        assertEquals("[0]", priorityQueue.toString());
    }

    @Test
    public void addMultipleValuesIsCorrect() {
        priorityQueue.add(new BinaryNode((byte) 0, 1));
        priorityQueue.add(new BinaryNode((byte) 0, 3));
        priorityQueue.add(new BinaryNode((byte) 0, 4));
        priorityQueue.add(new BinaryNode((byte) 0, 2));
        assertEquals("[1,2,4,3]", priorityQueue.toString());
    }

    @Test
    public void toStringWithEmptyQueueIsCorrect() {
        assertEquals("[]", priorityQueue.toString());
    }

    @Test
    public void queueOrderIsCorrect() {
        priorityQueue.add(new BinaryNode((byte) 0, 7));
        priorityQueue.add(new BinaryNode((byte) 0, 8));
        priorityQueue.add(new BinaryNode((byte) 0, 1));
        priorityQueue.add(new BinaryNode((byte) 0, 5));
        priorityQueue.add(new BinaryNode((byte) 0, 3));
        priorityQueue.add(new BinaryNode((byte) 0, 4));
        priorityQueue.add(new BinaryNode((byte) 0, 9));
        priorityQueue.add(new BinaryNode((byte) 0, 5));
        priorityQueue.add(new BinaryNode((byte) 0, 5));
        priorityQueue.add(new BinaryNode((byte) 0, 8));
        int[] correct = {1,3,4,5,5,5,7,8,8,9};
        assertEquals(10, priorityQueue.size());
        for (int i = 0; i < priorityQueue.size(); i++) {
            assertEquals(correct[i], priorityQueue.poll().getFrequency());
        }
    }

}