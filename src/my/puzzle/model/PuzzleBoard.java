package my.puzzle.model;
import my.puzzle.state.GamingState;
import my.puzzle.state.GoalState;
import my.puzzle.state.InitialState;
import my.puzzle.state.State;

public class PuzzleBoard {

    private static final int TRANS = 48;

    /**
     * Constant for the non-numpad <b>left</b> arrow key.
     */
    public static final int VK_LEFT           = 0x25;

    /**
     * Constant for the non-numpad <b>up</b> arrow key.
     */
    public static final int VK_UP             = 0x26;

    /**
     * Constant for the non-numpad <b>right</b> arrow key.
     */
    public static final int VK_RIGHT          = 0x27;

    /**
     * Constant for the non-numpad <b>down</b> arrow key.
     */
    public static final int VK_DOWN           = 0x28;


    private State initial;
    private State gaming;
    private State goal;

    private State current;

    public final char[] blockz;
    private int vacancy;
    private final int n;
    @SuppressWarnings("unused")
    private int manhattan;

    private int score = 0;

    public PuzzleBoard(int[][] blocks) {
        initial = new InitialState(this);
        gaming = new GamingState(this);
        goal = new GoalState(this);
        current = initial;

        vacancy = validate(blocks);     // validate
        n = blocks.length;              // set length
        blockz = copy(blocks);          // allocate array

        manhattan = getManhattan();
    }

    public void move(int key) {
        getNeighbor(key);
        System.out.println(getManhattan());
        current.change();
    }

    /* ************************ private methods **************************************** */
    private void getNeighbor(int key) {
        int x = vacancy / n;    // axis of vacancy block, like n = 3, 5 --> (1, 2)
        int y = vacancy % n;
        switch(key) {
        case VK_LEFT:
            if (y != n - 1) {
                exch(vacancy, vacancy + 1);  
                vacancy = vacancy + 1;
            }
            break;
        case VK_RIGHT: 
            if (y != 0) {
                exch(vacancy, vacancy - 1);  
                vacancy = vacancy - 1;
            }
            break;
        case VK_UP: 
            if (x != n - 1) {
                exch(vacancy, vacancy + n);  
                vacancy = vacancy + n;
            }
            break;
        case VK_DOWN: 
            if (x != 0) {
                exch(vacancy, vacancy - n);  
                vacancy = vacancy - n;
            }
            break;
        default :
            break;
        }
    }

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
    private int manhattan() {
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
    /* ************************ getter and setter methods ********************** */
    public int getManhattan() {
        return manhattan();
    }
    public int getVacancy() {
        return vacancy;
    }
    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }
    public State getCurrent() {
        return current;
    }

    public void setCurrent(State current) {
        this.current = current;
    }

    public State getGaming() {
        return gaming;
    }

    public void setGaming(State gaming) {
        this.gaming = gaming;
    }

    public State getGoal() {
        return goal;
    }

    public void setGoal(State goal) {
        this.goal = goal;
    }

    public int getScore() {
        // TODO Auto-generated method stub
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static void main(String[] args) {

    }
}
