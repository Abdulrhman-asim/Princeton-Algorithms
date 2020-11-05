

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF sites;
    private boolean[]  siteStatus;
    private final int num;
    private final int arrLen;
    private int openedNum;


    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        openedNum = 0;
        num = n;
        arrLen = num*num;

        sites = new WeightedQuickUnionUF(arrLen + 2);
        siteStatus = new boolean[arrLen];


        for (int i = 0; i < arrLen; i++) {
            siteStatus[i] = false;
        }
        

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        assertInRange(row, col);


        if (isOpen(row, col)) {
            return;
        }

        int index = mapper(row, col);

        siteStatus[index] = true;
        openedNum++;
        
        if (row == 1)
            sites.union(arrLen, index);
        
        if (row == num)
            sites.union(arrLen + 1, index);
        

        if (col < num)
            if (isOpen(row, col + 1))
                sites.union(index, index + 1);

        if (col > 1)
            if (isOpen(row, col - 1))
                sites.union(index, index - 1);

        if (row > 1)
            if (isOpen(row - 1, col))
                sites.union(index, index - num);

        if (row < num)
            if (isOpen(row + 1, col))
                sites.union(index, index + num);


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        assertInRange(row, col);

        int index = mapper(row, col);

        if (siteStatus[index])
            return true;

        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        assertInRange(row, col);

        if (!isOpen(row, col))
            return false;

        int index = mapper(row, col);

        int siteParent = sites.find(index);
        int imaginaryTopParent = sites.find(arrLen);



        if (siteParent == imaginaryTopParent)
            return true;

        return false;

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedNum;
    }

    // does the system percolate?
    public boolean percolates() {

        int imaginaryBotParent = sites.find(arrLen + 1);
        int imaginaryTopParent = sites.find(arrLen);
        
        if (imaginaryBotParent == imaginaryTopParent)
            return true;

        return false;


    }

    private void assertInRange(int row, int col) {
        if (row < 1 || row > num || col < 1 || col > num) {
            throw new IllegalArgumentException();
        }
    }

    private int mapper(int row, int col) {

        int index = (row - 1) * num;
        index = index + (col - 1);
        
        
        return index;
    }

}
