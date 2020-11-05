
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;



public class PercolationStats {
    
    private static final double  CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double mean;
    private final double stddev;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        
        
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        
        
        double[] threshold = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {

            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {

                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                perc.open(row, col);

            }
            
            threshold[i] = (double) perc.numberOfOpenSites()/(double) (n*n);
            
           
            
        }
        
        
        mean = StdStats.mean(threshold);
        stddev = StdStats.stddev(threshold);

    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double lo = mean - ((CONFIDENCE_95 * stddev) / Math.sqrt(trials));
        return lo;

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double hi = mean + ((CONFIDENCE_95 * stddev) / Math.sqrt(trials));
        return hi;
    }

    // test client (see below)
    public static void main(String[] args) {

//        int n = Integer.parseInt(args[0]);
//        int trials = Integer.parseInt(args[1]);
        int n = 20;
        int trials = 10;
        
        if (args.length >= 2) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        PercolationStats p = new PercolationStats(n, trials);
        
        System.out.println(String.format("%-20s= %s", "mean", p.mean()));
        System.out.println(String.format("%-20s= %s", "stddev", p.stddev()));
        System.out.println(String.format("%-20s= [%s, %s]", "95% confidence interval", p.confidenceLo(), p.confidenceHi()));
    }

}