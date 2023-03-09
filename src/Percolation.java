import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {

    private final int[][] sites;
    private final WeightedQuickUnionUF wquf;
    private final int size;
    private int numOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        size = n;
        sites = new int[n][n];
        wquf = new WeightedQuickUnionUF(n*n+2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) sites[i][j] = 0;
        }
        numOfOpenSites = 0;
    }

    private void illArg(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) throw new IllegalArgumentException();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        illArg(row, col);
        if (isOpen(row, col)) return;
        sites[row-1][col-1] = 1;
        numOfOpenSites++;
        if (row == 1) wquf.union(0, col);
        if (row == size) wquf.union(size*size+1, (row-1)*size+col);
        if (row < size) {
            if(isOpen(row+1, col)) {
                wquf.union((row-1)*size+col, row*size+col);
            }
        }
        if (row <= size && row > 1) {
            if (isOpen(row-1, col)) {
                wquf.union((row-1)*size+col, (row-2)*size+col);
            }
        }
        if (col <= size && col > 1) {
            if (isOpen(row, col-1)) {
                wquf.union((row-1)*size+col, (row-1)*size+col-1);
            }
        }
        if (col < size) {
            if (isOpen(row, col+1)) {
                wquf.union((row-1)*size+col, (row-1)*size+col+1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        illArg(row, col);
        return sites[row-1][col-1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        illArg(row, col);
        return isOpen(row, col) && wquf.find(0) == wquf.find((row-1)*size+col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquf.find(0) == wquf.find(size*size+1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);
        StdOut.println(p.isFull(2, 3));
        StdOut.println(p.percolates());
        p.open(4, 2);
        StdOut.println(p.percolates());
        StdOut.println(p.numberOfOpenSites());
    }

}