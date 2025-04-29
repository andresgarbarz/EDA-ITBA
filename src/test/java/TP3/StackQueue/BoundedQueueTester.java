package TP3.StackQueue;

import itba.andy.TP3.StackQueue.BoundedQueue;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BoundedQueueTester {

    @Test
    public void testQueueEnqueueDequeue() {
        BoundedQueue<Integer> myQueue = new BoundedQueue<>(10);

        myQueue.enqueue(10);
        myQueue.enqueue(20);
        myQueue.enqueue(30);
        myQueue.enqueue(40);

        assertEquals(10, myQueue.dequeue(), "Dequeue should return 10");
        assertEquals(20, myQueue.dequeue(), "Dequeue should return 20");
    }

    @Test
    public void testQueueEnqueueAfterDequeue() {
        BoundedQueue<Integer> myQueue = new BoundedQueue<>(10);

        myQueue.enqueue(10);
        myQueue.enqueue(20);
        myQueue.enqueue(30);
        myQueue.enqueue(40);

        // Dequeue 2 elements
        myQueue.dequeue();
        myQueue.dequeue();

        // Enqueue more elements
        myQueue.enqueue(50);
        myQueue.enqueue(60);
        myQueue.enqueue(70);

        // After dequeuing 2 elements, check the queue content using dump()
        String expectedQueueContent = "30 40 50 60 70";
        // Capture the output of dump() using ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);  // Redirect System.out to the ByteArrayOutputStream

        myQueue.dump();  // This will print to the outputStream

        // Convert the captured output to a string and verify it
        String dump = outputStream.toString().trim();
        assertEquals(expectedQueueContent, dump, "Queue content after enqueueing should match expected output");
    }

    @Test
    public void testQueueSizeLimit() {
        BoundedQueue<Integer> myQueue = new BoundedQueue<>(3);

        myQueue.enqueue(1);
        myQueue.enqueue(2);
        myQueue.enqueue(3);

        // At this point the queue is full
        assertThrows(IllegalStateException.class, () -> {
            myQueue.enqueue(4); // This should throw an exception since the queue size is 3
        });
    }

    @Test
    public void testQueueEmpty() {
        BoundedQueue<Integer> myQueue = new BoundedQueue<>(10);

        // Initially, the queue is empty
        assertTrue(myQueue.isEmpty(), "Queue should be empty initially");

        myQueue.enqueue(10);
        assertFalse(myQueue.isEmpty(), "Queue should not be empty after enqueue");
    }

    @Test
    public void testQueueDequeueEmpty() {
        BoundedQueue<Integer> myQueue = new BoundedQueue<>(10);

        // Trying to dequeue from an empty queue should throw an exception
        assertThrows(IllegalStateException.class, myQueue::dequeue, "Dequeueing from an empty queue should throw an exception");
    }
}
