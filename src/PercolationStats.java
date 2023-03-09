import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private final double xBar;
    private final double s;
    private final int trials;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.trials = trials;
        double[] fracs = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if (!p.isOpen(row, col)) p.open(row, col);
            }
            fracs[i] = (double) p.numberOfOpenSites() / (double) (n*n);
        }
        xBar = StdStats.mean(fracs);
        s = StdStats.stddev(fracs);
    }

    // sample mean of percolation threshold
    public double mean() {
        return xBar;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return xBar - (1.96*s) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return xBar + (1.96*s) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.printf("mean \t\t\t\t = %f\n", ps.mean());
        StdOut.printf("stddev \t\t\t\t = %f\n", ps.stddev());
        StdOut.print("95%");
        StdOut.printf(" confidence interval \t  = [%f, %f]", ps.confidenceLo(), ps.confidenceHi());
    }

}