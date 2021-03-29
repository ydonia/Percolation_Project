public class Percolation {

    private int numOpenSites = 0;

    private boolean[][] grid;

    // array with values: 0 if it is not full, 1 if isFull was called and turned out to be false, 2 if full
    private int[][] fullStatus;

    private int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        // set variable n
        this.n = n;

        // create 2-dimensional array of size n
        grid = new boolean[n][n];

        // initialize every element to false (true is open, false is closed)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        // create another 2D array to mark the full sites on the grid and also initialize to false
        fullStatus = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                fullStatus[i][j] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        // set all the 1s in the fullStatus array to 0s
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (fullStatus[i][j] == 1)
                    fullStatus[i][j] = 0;
            }
        }
        // we must generate the ints row and col randomly in the main function
        grid[row][col] = true;
        StdOut.println("Site row " + row + ", col " + col + " has been opened");

        // incremement number of open sites
        numOpenSites++;
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        // check the array to see if the square is true
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        if (!isOpen(row, col)) {
            return false;
        }
        else {
            if (row == 0) {
                // StdOut.println("row = " + row + ", col = " + col + " is in first row");
                fullStatus[row][col] = 2;
                return true;
            }
            else {
                if (fullStatus[row][col] == 2) { // if the site is already marked as full
                    // StdOut.println("row = " + row + ", col = " + col + " is already full");
                    return true;
                }
                else if (fullStatus[row][col] == 1) { // has already been called by isFull
                    return false;
                }
                else {
                    fullStatus[row][col] = 1;
                    if (row > 0 && isFull(row - 1, col)) {
                        fullStatus[row - 1][col] = 2;
                        return true;
                    }
                    else if (col > 0 && isFull(row, col - 1)) {
                        fullStatus[row][col - 1] = 2;
                        return true;
                    }
                    else if (col < n - 1 && isFull(row, col + 1)) {
                        fullStatus[row][col + 1] = 2;
                        return true;
                    }
                    else if (row < n - 1 && isFull(row + 1, col)) {
                        fullStatus[row + 1][col] = 2;
                        return true;
                    }
                    else {
                        // StdOut.println("row = " + row + ", col = " + col + " is not full");
                        return false;
                    }
                }
            }
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {

        // percolates only if bool isFull is true for a square in the bottom row of the grid
        for (int j = 0; j < n; j++) {
            if (isFull(n - 1, j)) {
                StdOut.println("Percolates!");
                StdOut.println(numOpenSites);
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {

        // create object of type Percolation
        StdOut.println("Enter a number for the size of your grid: ");
        int n = StdIn.readInt();

        Percolation p = new Percolation(n);


        // generate random numbers for the row and col variables
        int row = 0;
        int col = 0;

        // Random random = new Random();

        // loop while the grid doesn't percolate
        boolean percolates = false;
        while (!percolates) {

            // generate random numbers for the row and col variables
            row = StdRandom.uniform(0, n);
            col = StdRandom.uniform(0, n);
            // THESE ARE OTHER OPTIONS TO RANDOMIZE NUMBERS
            // row = (int) (Math.random() * (n - 1) + 0);
            // col = (int) (Math.random() * (n - 1) + 0);
            // row = random.nextInt(n - 1);
            // col = random.nextInt(n - 1);
            // randomly open a site (if already open, do not increment numOpenSites)
            if (!p.isOpen(row, col)) {
                p.open(row, col);
                if (p.percolates()) {
                    percolates = true; // to exit the loop
                }
            }
        }
    }
}

