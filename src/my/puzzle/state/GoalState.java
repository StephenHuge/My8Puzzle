package my.puzzle.state;
import my.puzzle.model.PuzzleBoard;

public class GoalState extends State {

    public GoalState(PuzzleBoard board) {
        super(board);
    }
    @Override
    public void change() {
        System.out.println("You won!!! Your score is " + board.getScore());
    }
}
