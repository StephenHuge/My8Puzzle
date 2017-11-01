import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class Board {

    private final int n;

    private final int hamming;

    private final int manhattan;

    final char[] blockz;    // 1 - dimension char array for improving performance

    private Queue<Board> neighbors;

    private final int vacancy;

    //    private int lastMoveDirection = 0;    // this is useless by assignent's requirements

    private static final int TRANS = 48;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        vacancy = validate(blocks);     // validate
        n = blocks.length;              // set length
        blockz = copy(blocks);          // allocate array

        hamming = getHamming();
        manhattan = getManhattan();
    }
    public int dimension()                 // board dimension n
    {
        return n;
    }
    public int hamming()                   // number of blocks out of place
    {
        return hamming;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return manhattan;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        return manhattan() == 0;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int p1 = 0;
        int p2 = n * n - 1;
        if (blockz[p1] == '0') p1++;
        if (blockz[p2] == '0') p2--;

        exch(p1, p2);       // swap entry by indexes
        Board twin = new Board(getArray(blockz));
        exch(p2, p1);   // swap back for another use    
        return twin;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this)  return true;
        if (y == null)  return false;
        if (y.getClass() != this.getClass())    return false;

        Board b = (Board) y;
        return isArrayEqual(this.blockz, b.blockz);
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        if (neighbors == null) {
            neighbors = generateNeighbors();
        }
        return neighbors;
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        int len = n * n;
        for (int i = 0; i < len; i++) {
            s.append(String.format("%2d ", blockz[i] - TRANS));
            if ((i + 1) % n == 0)   s.append("\n");
        }
        return s.toString();
    }
    /**
     * validate whether array blocks is legal, also get vacancy for this board
     *  
     * @param blocks
     * @return
     */
    private int validate(int[][] blocks) {
        int vacant = -1;  // vacancy for this method
        if (blocks == null || blocks.length == 0 || blocks[0].length == 0)
            throw new IllegalArgumentException();

        int len = blocks.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (blocks[i][j] < 0)   throw new IllegalArgumentException();
                else if (blocks[i][j] == 0) {
                    vacant = i * len + j;
                }   
            }
        }

        if (vacant == -1)       throw new IllegalArgumentException();
        return vacant;
    }
    private int getManhattan() {
        int mManhattan  = 0;
        int len = blockz.length;
        for (int i = 0; i < len; i++) {
            if ((blockz[i] != '0') && (blockz[i] != (char) (i  + 1 + TRANS))) {

                // e.g.: 8 is in index 1 -->(0, 1) : first row, second column  
                // 8 - 1 = 7, x = 7 / 3 - 0 = 2, y = 7 % 3 - 1 = 0
                // so manhattan distance of 7 is 2 + 0 = 2
                int node = blockz[i] - TRANS - 1;
                int x = node / n - i / n;        
                int y = node % n - i % n;

                x = x > 0 ? x : -x;     // make sure x distance is positive
                y = y > 0 ? y : -y;     // make sure y distance is positive
                mManhattan += (x + y);
                //                System.out.println(String.format("manhattan of %d is %d", node + 1, (x + y)) );
            }
        }
        return mManhattan;
    }
    private int getHamming() {
        int mHamming = 0;
        int len = blockz.length;
        for (int i = 0; i < len; i++) {
            if ((blockz[i] != '0') && (blockz[i] != (char) (i  + 1 + TRANS))) {
                //                System.out.println("hamming " + blockz[i] + " : " + (char) (i  + 1 + TRANS));
                mHamming++;
            } 
        }
        return mHamming;
    }
    private boolean isArrayEqual(char[] c1, char[] c2) {
        if (c1 == null || c2 == null || c1.length != c2.length) return false;
        int len = c1.length;
        for (int i = 0; i < len; i++) {
            if (c1[i] - c2[i] != 0)     return false;
        }
        return true;
    }
    private Queue<Board> generateNeighbors() {
        Queue<Board> mNeighbors = new Queue<>();
        int x = vacancy / n;    // axis of vacancy block, like n = 3, 5 --> (1, 2)
        int y = vacancy % n;

        if (x != 0) {      
            exch(vacancy, vacancy - n);   
            mNeighbors.enqueue(new Board(getArray(blockz)));
            exch(vacancy - n, vacancy);   
        }
        if (x != n - 1) {       
            exch(vacancy, vacancy + n);       // swap entry in vacancy and its upper
            mNeighbors.enqueue(new Board(getArray(blockz)));
            exch(vacancy + n, vacancy);   // swap back for another use    
        }
        if (y != 0) {       
            exch(vacancy, vacancy - 1);       
            mNeighbors.enqueue(new Board(getArray(blockz)));
            exch(vacancy - 1, vacancy);   
        }
        if (y != n - 1) {
            exch(vacancy, vacancy + 1);   
            mNeighbors.enqueue(new Board(getArray(blockz)));
            exch(vacancy + 1, vacancy);   
        }
        return mNeighbors;   
    }
    private int[][] getArray(char[] mBlockz) {
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] =  mBlockz[i * n + j] - TRANS;
            }
        }
        return blocks;
    }
    private void exch(int a, int b) {
        char t = blockz[a];
        blockz[a] = blockz[b];
        blockz[b] = t;
    }
    private char[] copy(int[][] blocks) {
        char[] copy = new char[n * n];
        int len = blocks.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                copy[i * len + j] = (char) (blocks[i][j] + TRANS);
            }
        }
        return copy;
    }
    public static void main(String[] args) // unit tests (not graded)
    {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board board = new Board(blocks);
        System.out.println(board);
        /* ***********test validate()***************** */
        //        System.out.println("board.vacancy: " + board.vacancy);  
        /* ***********test copy()***************** */
        //        char[] blockz = board.blockz;
        //        System.out.print("char array: ");
        //        for (int i = 0; i < blockz.length; i++) {
        //            System.out.print(blockz[i] + " ");
        //        }
        //        System.out.println();
        //        /* ***********test hamming()***************** */
        //        System.out.println("hamming :銆�" + board.hamming());
        //        /* ***********test manhattan()***************** */
        //        System.out.println("manhattan :銆�" + board.manhattan());
        /* ***********test toString()***************** */
        //                System.out.println(board.toString());
        /* ***********test isArrayEqual()******************/
        //        System.out.println("isArrayEqual : " + board.isArrayEqual(board.blockz, board.blockz));
        /* ***********test neighbor()***************** */
        //        Iterable<Board> it = board.neighbors();
        //        System.out.println("neighbors: ");
        //        for (Board b : it) {
        //            System.out.println(b);
        //        }
        /* ***********test twin()***************** */
        Board twin = board.twin();
        System.out.println(twin);

    }
}
