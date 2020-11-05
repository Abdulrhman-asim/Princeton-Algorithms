import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    private int dim;
    private int[][] tiles;
    private int blankRow;
    private int blankCol;

    public Board(int[][] tiles) {

        dim = tiles.length;
        this.tiles = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) {
                this.tiles[i][j] = tiles[i][j];
                if(tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    
                }
            }
        //                this.tiles[i] = Arrays.copyOf(tiles[i], dim);



    }

    // string representation of this board
    public String toString() {

        StringBuffer rep = new StringBuffer(dim + "\n");

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j <dim; j++)
                rep.append(" " + tiles[i][j]);
            rep.append("\n");
        }

        String result = new String(rep);
        return result;
    }

    // board dimension n
    public int dimension() {
        return dim;
    }

    // number of tiles out of place
    public int hamming() {

        int ham = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j <dim; j++) {

                if(tiles[i][j] == 0)
                    continue;

                int loc = (i * dim + j + 1);
                loc = loc % (dim * dim);

                if(tiles[i][j] != loc) {
                    ham++;
                }
            }

        }
        return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int man = 0;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j <dim; j++) {

                if(tiles[i][j] == 0)
                    continue;
                int actualRow = (tiles[i][j] - 1) / dim;
                int actualCol = (tiles[i][j] - 1) % dim;

                man += (Math.abs(actualRow - i));
                man += (Math.abs(actualCol - j));

            }

        }

        return man;

    }

    // is this board the goal board?
    public boolean isGoal() {
        int check = manhattan();
        if (check == 0)
            return true;

        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (y == this) 
            return true;


        if (!(y instanceof Board)) 
            return false;

        Board toCompare = (Board) y;

        if (toCompare.dimension() != dim)
            return false;

        for (int i = 0; i < dim; i++) 
            for (int j = 0; j <dim; j++) 
                if (tiles[i][j] != toCompare.tiles[i][j])
                    return false;

        return true;

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        ArrayList<Board> neighbors = new ArrayList<Board>();

        if (blankRow > 0) {
            tiles[blankRow][blankCol] = tiles[blankRow - 1][blankCol];
            tiles[blankRow - 1][blankCol] = 0;
            neighbors.add(new Board(tiles));
            tiles[blankRow - 1][blankCol] = tiles[blankRow][blankCol];
            tiles[blankRow][blankCol] = 0;


        }

        if (blankRow < (dim - 1)) {
            tiles[blankRow][blankCol] = tiles[blankRow + 1][blankCol];
            tiles[blankRow + 1][blankCol] = 0;
            neighbors.add(new Board(tiles));
            tiles[blankRow + 1][blankCol] = tiles[blankRow][blankCol];
            tiles[blankRow][blankCol] = 0;
        }

        if (blankCol > 0) {
            tiles[blankRow][blankCol] = tiles[blankRow][blankCol - 1];
            tiles[blankRow][blankCol - 1] = 0;
            neighbors.add(new Board(tiles));
            tiles[blankRow][blankCol - 1] = tiles[blankRow][blankCol];
            tiles[blankRow][blankCol] = 0;
        }

        if (blankCol < (dim - 1)) {
            tiles[blankRow][blankCol] = tiles[blankRow][blankCol + 1];
            tiles[blankRow][blankCol + 1] = 0;
            neighbors.add(new Board(tiles));
            tiles[blankRow][blankCol + 1] = tiles[blankRow][blankCol];
            tiles[blankRow][blankCol] = 0;
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        Board twinn;

        if(blankRow > 0) {
            int tmp = tiles[blankRow - 1][0];
            tiles[blankRow - 1][0] = tiles[blankRow - 1][1];
            tiles[blankRow - 1][1] = tmp;
            twinn = new Board(tiles);
            tiles[blankRow - 1][1] = tiles[blankRow - 1][0];
            tiles[blankRow - 1][0] = tmp;

        }

        else {
            int tmp = tiles[blankRow + 1][0];
            tiles[blankRow + 1][0] = tiles[blankRow + 1][1];
            tiles[blankRow + 1][1] = tmp;
            twinn = new Board(tiles);
            tiles[blankRow + 1][1] = tiles[blankRow + 1][0];
            tiles[blankRow + 1][0] = tmp;

        }
        
        

        return twinn;
    }   

    // unit testing (not graded)
    public static void main(String[] args) {

        int [][] tiles = {{2, 0, 3, 4}, {1, 10, 6, 8}, {5, 9, 7, 12}, {13, 14, 11, 15}};

        Board b = new Board(tiles);

        
        
        for (Board x : b.neighbors()) {

            StdOut.println(x);
        }

    }

}