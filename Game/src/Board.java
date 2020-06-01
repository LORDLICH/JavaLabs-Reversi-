public class Board {

    private Tile[][] board = new Tile[Reversi.WIDTH][Reversi.HEIGHT];

    private int scoreBlack = 0;

    private int scoreWhite = 0;

    private int turn = 0;
    private boolean noMovesLeft = false;
    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public int getScoreBlack() {
        return scoreBlack;
    }

    public void setScoreBlack(int scoreBlack) {
        this.scoreBlack = scoreBlack;
    }

    public void incScoreBlack() {
        scoreBlack++;
    }

    public int getScoreWhite() {
        return scoreWhite;
    }

    public void setScoreWhite(int scoreWhite) {
        this.scoreWhite = scoreWhite;
    }

    public void incScoreWhite() {
        scoreWhite++;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public boolean isNoMovesLeft() {
        return noMovesLeft;
    }

    public void setNoMovesLeft(boolean noMovesLeft) {
        this.noMovesLeft = noMovesLeft;
    }


}
