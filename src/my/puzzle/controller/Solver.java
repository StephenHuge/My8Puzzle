package my.puzzle.controller;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import my.puzzle.model.PuzzleBoard;

public class Solver {

    // delay in miliseconds (controls animation speed)
    private static final int DELAY = 200;
    
    public static final int VK_SPACE = 0x20;
    
    private int moves = 0;

    private boolean solvable = false;

    private final Stack<PuzzleBoard> solution;

    public Solver(PuzzleBoard initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) throw new java.lang.IllegalArgumentException();

        moves = 0;
        Priority min = new Priority(initial, null);
        Priority twin = new Priority(initial.twin(), null);

        MinPQ<Priority> minPQ = new MinPQ<>();
        MinPQ<Priority> twinPQ = new MinPQ<>();

        minPQ.insert(min);      // insert min
        twinPQ.insert(twin);      
        Priority sol = solve(minPQ, twinPQ);

        solution = new Stack<>();
        while (sol != null) {       // get solution
            solution.push(sol.board);
            sol = sol.father;
        }
    }
    private Priority solve(MinPQ<Priority> minPQ, MinPQ<Priority> twinPQ) {
        Priority min;
        Priority twin;
        while (true) {
            min = minPQ.delMin();   // find the smallest one
            if (min.board.getManhattan() == 0) {   // solvable
                solvable = true;
                moves = min.moves;
                break;
            } 
            twin = twinPQ.delMin();   // find the smallest one
            if (twin.board.getManhattan() == 0) {   // unsolvable
                moves = -1;
                break;
            } 
            insertNeighbors(minPQ, min);
            insertNeighbors(twinPQ, twin);
        }
        if (solvable)   return min;
        return null;
    }
    private Iterable<PuzzleBoard> insertNeighbors(MinPQ<Priority> minPQ, Priority min) {
        Iterable<PuzzleBoard> it = min.board.neighbors(); // get smallest one's neighbors
        for (PuzzleBoard b : it) {
            if (min.father == null || !b.equals(min.father.board))   minPQ.insert(new Priority(b, min));
        }
        return it;
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return solvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (!solvable)  return -1;
        return moves;
    }
    public Iterable<PuzzleBoard> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!solvable)  return null;
        return solution;
    }
    private class Priority implements Comparable<Priority> {
        PuzzleBoard board;
        int moves;
        int manhattan;
        Priority father;

        Priority(PuzzleBoard mBoard, Priority mFather) {
            this.board = mBoard;
            if (mFather == null)    this.moves = 0;
            else                    this.moves = mFather.moves + 1;
            this.manhattan = board.getManhattan(); 
            this.father = mFather;
        }
        int getPriority() {
            return manhattan + moves; 
        }
        @Override
        public int compareTo(Priority p) {
            return Integer.compare(getPriority(), p.getPriority());
        }
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // turn on animation mode
        StdDraw.enableDoubleBuffering();

        In in = new In(args[0]);    // input file
        int n = in.readInt();
        int[][] blocks = new int[n][n];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        PuzzleBoard board = new PuzzleBoard(blocks);
        Game.draw(board, n);
        StdDraw.show();

//        while (true) {
//            if (StdDraw.isKeyPressed(VK_SPACE))     break;
//        }
        Iterable<PuzzleBoard> ans = new Solver(board).solution();

        for (PuzzleBoard pb : ans) {
            StdDraw.pause(DELAY);
            Game.draw(pb, n);
            StdDraw.show();
        }
    }
}
