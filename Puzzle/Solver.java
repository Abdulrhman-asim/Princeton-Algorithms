import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private int moves;
    private ArrayList<Board> solutionSeq;

    private boolean solvable;

    private class Node {

        int manhattan;
        int moves;
        Board current;
        Node prev;
        int priority;

        public Node(int man, int mov, Board initial, Node p) {
            manhattan = man;
            moves = mov;
            current = initial;
            prev = p;
            priority = moves + manhattan;
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null)
            throw new IllegalArgumentException();

        MinPQ<Node> pq;
        MinPQ<Node> pqTwin;


        solutionSeq = null;
        moves = -1;
        solvable = true;

        pq = new MinPQ<Node>(comparator());
        pqTwin = new MinPQ<Node>(comparator());

        pq.insert(new Node(initial.manhattan(), 0, initial, null));

        pqTwin.insert(new Node(initial.manhattan(), 0, initial.twin(), null));

        
        
        int flag = 0;

        Node dqed = null;
        
        solutionSeq = new ArrayList<Board>();
        
        solutionSeq.add(initial);
        
        if(initial.isGoal()) {
            moves = 0;
            return;
        }
        

        while(flag == 0) {

            dqed = pq.delMin();
            
            if(dqed.current.isGoal()) {
                flag = 1;
                moves = dqed.moves;
                break;
            }

            for (Board b : dqed.current.neighbors()) {
                
                if(dqed.prev != null)
                    if (b.equals(dqed.prev.current))
                        continue;

                pq.insert(new Node(b.manhattan(), dqed.moves + 1, b, dqed)); 
            }


            dqed = pqTwin.delMin();

            if(dqed.current.isGoal()) {
                flag = 2;
                break;
            }

            for (Board b : dqed.current.neighbors()) {
                
                if(dqed.prev != null)
                    if (b.equals(dqed.prev.current))
                        continue;

                pqTwin.insert(new Node(b.manhattan(), dqed.moves + 1, b, dqed)); 
            }

            

        }

        if (flag == 2) {
            solvable = false;
            solutionSeq = null;
            return;
        }
        
        Node tmp = dqed;
        
        
        ArrayList<Board> tmpSeq = new ArrayList<Board>();
        
        while (tmp != null) {
            tmpSeq.add(tmp.current);
            tmp = tmp.prev;
        }
        
        for (int i = tmpSeq.size() - 2; i >= 0 ; i--) {
            solutionSeq.add(tmpSeq.get(i));
        }
                

        

    }


    private Comparator<Node> comparator() {

        return new Comparator<Node>() {

            public int compare(Node a, Node b) {
                if (a.priority > b.priority) return 1;
                if (a.priority < b.priority) return -1;

                return 0;
            }


        };
    }
    // is the initial board solvable? (see below)
    public boolean isSolvable() {

        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solutionSeq == null)
            return null;

        return new ArrayList<Board>(solutionSeq);
    }

    // test client (see below) 
    public static void main(String[] args) {


        //        int [][] tiles = {{2, 0, 3, 4}, {1, 10, 6, 8}, {5, 9, 7, 12}, {13, 14, 11, 15}};
        int [][] tiles = {{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};

        Board b = new Board(tiles);
        Solver s = new Solver(b);
        
        
        for (Board board : s.solution())
            StdOut.println(board);

        StdOut.println(s.moves());

        //        In in = new In(args[0]);
        //        int n = in.readInt();
        //        int[][] tiles = new int[n][n];
        //        for (int i = 0; i < n; i++)
        //            for (int j = 0; j < n; j++)
        //                tiles[i][j] = in.readInt();
        //
        //        Board initial = new Board(tiles);
        //
        //        // solve the puzzle
        //        Solver solver = new Solver(initial);
        //
        //        // print solution to standard output
        //        if (!solver.isSolvable())
        //            StdOut.println("No solution possible");
        //        else {
        //            StdOut.println("Minimum number of moves = " + solver.moves());
        //            for (Board board : solver.solution())
        //                StdOut.println(board);
        //        }

    }

}