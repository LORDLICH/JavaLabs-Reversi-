

public interface BoardGame {
    public void makeMove(int X, int Y);

    public void undoMove();

    public boolean gameOver();

    public boolean isDraw();

    public double getBoardValue();

    public boolean legalMove(int X, int Y);

    public int checkMove(int X, int Y);

}
