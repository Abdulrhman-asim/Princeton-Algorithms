
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 10;

    private int sz;
    private Item[] elements;


    // construct an empty randomized queue
    public RandomizedQueue() {

        elements = (Item[]) new Object[INIT_CAPACITY];
        sz = 0;

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return sz == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return sz;
    }

    private void resize(int capacity) {
        assert capacity >= sz;

        // textbook implementation
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < sz; i++) {
            copy[i] = elements[i];
        }
        elements = copy;


    }

    // add the item
    public void enqueue(Item item) {

        if (item == null) 
            throw new IllegalArgumentException();

        if (sz == elements.length)
            resize(2*elements.length); 

        elements[sz++] = item;
    }

    // remove and return a random item
    public Item dequeue() {

        if (sz == 0)
            throw new NoSuchElementException();

        int rand = StdRandom.uniform(sz);
        Item released = elements[rand];
        elements[rand] = elements[--sz];

        if (sz > 0 && sz == elements.length/4)
            resize(elements.length/2);

        return released;

    }

    // return a random item (but do not remove it)
    public Item sample() {

        if (sz == 0)
            throw new NoSuchElementException();

        int rand = StdRandom.uniform(sz);
        return elements[rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        
        private int i;
        Item [] a;

        public ArrayIterator() {
            i = sz;
            a = (Item[]) new Object[i];
            Item [] b = elements.clone();
            for(int j = 0; j < sz; j++) {
                int rand = 0;
                if (i != 0)
                    rand = StdRandom.uniform(i);
                a[j] = b[rand];
                b[rand] = b[--i];
                
            }
            i = 0;
            
        }

        public boolean hasNext() {
            return (i) < sz ;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) 
                throw new NoSuchElementException();

            return a[i++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(4);
        StdOut.println(test.dequeue());
        StdOut.println(test.isEmpty());
        StdOut.println(test.size());
        StdOut.println(test.sample());
        StdOut.println(test.size());
        
        test.enqueue(8);
        
        
        for (int a : test) {
            for (int b : test)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }

}