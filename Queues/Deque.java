import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int sz;


    private class Node {
        Item value;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        sz= 0;
        first = last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {

        return sz == 0;

    }

    // return the number of items on the deque
    public int size() {
        return sz;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldfirst = first;

        first = new Node();
        first.value = item;
        first.next = oldfirst;
        first.prev = null;

        if (oldfirst != null)
            oldfirst.prev = first;


        if (sz == 0) {
            last = first;
        }
        sz++;


    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldlast = last;

        last = new Node();
        last.value = item;
        last.next = null;
        last.prev = oldlast;

        if (oldlast != null)
            oldlast.next = last;

        if (sz == 0) {
            first = last;
        }
        sz++;


    }

    // remove and return the item from the front
    public Item removeFirst() {

        if(sz == 0) {
            throw new NoSuchElementException();
        }

        Item released = first.value;
        if (first.next != null) {
            first = first.next;
            first.prev = null;
        }
        sz--;

        return released;

    }

    // remove and return the item from the back
    public Item removeLast() {

        if (sz == 0) {
            throw new NoSuchElementException();
        }

        Item released = last.value;

        if (last.prev != null) {
            last = last.prev;
            last.next = null;            
        }

        sz--;

        return released;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.value;
            current = current.next; 
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Integer> test = new Deque<Integer>();
        test.addFirst(2);
//        test.addFirst(4);
//        test.addFirst(6);
//        test.removeLast();
        StdOut.println(test.isEmpty());
        
        test.removeFirst();
        StdOut.println(test.isEmpty());
//        test.removeLast();
//        test.addLast(7);
//        test.addLast(9);
//        test.addFirst(8);
//        test.addFirst(-1);
        StdOut.println("size: " + test.size());
//
//        Iterator<Integer> i = test.iterator();
//        while (i.hasNext()) {
//            StdOut.println(i.next());
//        }
//
//        i.remove();


    }

}