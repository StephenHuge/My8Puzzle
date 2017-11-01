package my.puzzle.state;
import my.puzzle.model.PuzzleBoard;

public class GamingState extends State {

    public GamingState(PuzzleBoard board) {
        super(board);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void change() {
        if (board.getManhattan() == 0) {
            board.setCurrent(board.getGoal());
            System.out.println("You won!!! Your score is " + board.getScore());
        }
        board.setScore(board.getScore() + 1);
    }
}
