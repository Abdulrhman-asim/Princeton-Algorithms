import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {


    public static void main(String args[]) {

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> que = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String inpt = StdIn.readString();
            que.enqueue(inpt);
        }

        Iterator<String> i = que.iterator();
        while (k != 0) {
            System.out.println(i.next());
            k--;
        }

    }


}
