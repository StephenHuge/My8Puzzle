package my.puzzle.state;

import my.puzzle.model.PuzzleBoard;

/**
 * an abstract class for 8 puzzle by using state design pattern
 *  
 * @author HJS
 * 
 * @date 2017Äê11ÔÂ1ÈÕ
 * 
 */
public abstract class State {
    protected PuzzleBoard board;
    
    public State(PuzzleBoard board) {
        this.setBoard(board);
    }
    
    public void change() {
        System.out.println("You pressed a key!");
    }

    public PuzzleBoard getBoard() {
        return board;
    }

    public void setBoard(PuzzleBoard board) {
        this.board = board;
    }
}
