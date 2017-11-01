/******************************************************************************
 *  Compilation:  javac PercolationVisualizer.java
 *  Execution:    java PercolationVisualizer input.txt
 *  Dependencies: Percolation.java
 *
 *  This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size n of the percolation system.
 *    - Creates an n-by-n grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black,
 *  with with site (1, 1) in the upper left-hand corner.
 *
 ******************************************************************************/

import java.awt.Font;
import java.io.File;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class Ground {

    // delay in miliseconds (controls animation speed)
    private static final int DELAY = 100;

    // draw n-by-n percolation system
    public static void draw(Board board, int n) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05*n, 1.05*n);
        StdDraw.setYscale(-0.05*n, 1.05*n);   // leave a border to write text
        StdDraw.filledSquare(n/2.0, n/2.0, n/2.0);

        // draw n-by-n grid
        int opened = 0;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                int num = board.blockz[(row - 1) * n + (col - 1)] - 48;
                if (num == 0)   StdDraw.setPenColor(StdDraw.BLACK);
                else            StdDraw.setPenColor(StdDraw.WHITE);
                
                StdDraw.filledSquare(col - 0.5, n - row + 0.5, 0.48);
                StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 60));
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text(col - 0.5, n - row + 0.5, num +"");
                System.out.println(String.format("(%d, %d) --> %d", row, col, num));
            }
        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.25*n, -0.025*n, opened + " open sites");
//        if (perc.percolates()) StdDraw.text(0.75*n, -0.025*n, "percolates");
//        else                   StdDraw.text(0.75*n, -0.025*n, "does not percolate");

    }
    public void move() {
        
    }
    public static void main(String[] args) {

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
        Board board = new Board(blocks);
        draw(board, n);
        StdDraw.show();
        
        while (true) {
            if (StdDraw.isKeyPressed(0x25)) {
                
            }
        }
    }
    /**
     * Constant for the non-numpad <b>left</b> arrow key.
     * @see #VK_KP_LEFT
     */
    public static final int VK_LEFT           = 0x25;

    /**
     * Constant for the non-numpad <b>up</b> arrow key.
     * @see #VK_KP_UP
     */
    public static final int VK_UP             = 0x26;

    /**
     * Constant for the non-numpad <b>right</b> arrow key.
     * @see #VK_KP_RIGHT
     */
    public static final int VK_RIGHT          = 0x27;

    /**
     * Constant for the non-numpad <b>down</b> arrow key.
     * @see #VK_KP_DOWN
     */
    public static final int VK_DOWN           = 0x28;

}
