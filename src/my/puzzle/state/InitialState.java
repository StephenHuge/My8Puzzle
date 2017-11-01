package my.puzzle.state;
import my.puzzle.model.PuzzleBoard;

public class InitialState extends State {

    public InitialState(PuzzleBoard board) {
        super(board);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void change() {
        if (board.getCurrent().getClass() == InitialState.class) {
            board.setCurrent(board.getGaming());
            board.setScore(board.getScore() + 1);
            System.out.println(board.getScore());
        }
    }
    
}
