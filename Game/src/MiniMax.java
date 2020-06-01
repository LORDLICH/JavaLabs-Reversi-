
public class MiniMax {

    public double minValue(BoardGame game, int depth, double alpha, double beta){
        int legalMoveCounter = 0;
        if(game.gameOver() || depth == 0){
            return game.getBoardValue();
        }else{
            for (int x = 0; x < 8 && x >= 0; x++){
                for(int y = 0; y < 8 && y >= 0; y++){
                    if(game.legalMove(x,y)){
                        legalMoveCounter++;
                        game.makeMove(x, y);
                        double value = maxValue(game, depth - 1, alpha, beta);
                        game.undoMove();
                        if(value <= alpha){
                            return value;
                        }
                        if(value < beta){
                            beta = value;
                        }
                    }
                }
            }
            if(legalMoveCounter == 0){
                alpha = maxValue(game, depth - 1, alpha, beta);
            }
            return beta;
        }
    }

    public double maxValue(BoardGame game, int depth, double alpha, double beta){
        int legalMoveCounter = 0;
        if(game.gameOver() || depth == 0){
            return game.getBoardValue();
        }else{
            for(int x = 0; x < 8 && x >= 0; x++){
                for(int y = 0; y < 8 && y >= 0; y++){
                    if(game.legalMove(x,y)){
                        legalMoveCounter++;
                        game.makeMove(x, y);
                        double v = minValue(game, depth - 1, alpha, beta);
                        game.undoMove();
                        if(v >= beta){
                            return v;
                        }
                        if(v > alpha){
                            alpha = v;
                        }
                    }
                }
            }
            if(legalMoveCounter == 0){
                beta = minValue(game, depth - 1, alpha, beta);
            }
            return alpha;
        }
    }

    public void makeBestMoveForMin(BoardGame game, int depth, double alpha, double beta) {
        int bestRow = -1;
        int bestCol = -1;
        for (int x = 0; x < 8 && x >= 0; x++){
            for(int y = 0; y < 8 && y >= 0; y++){
                if(game.legalMove(x,y)){
                    game.makeMove(x, y);
                    double value = maxValue(game, depth - 1, alpha, beta);
                    game.undoMove();
                    if(value < beta){
                        bestRow = x;
                        bestCol = y;
                    }
                }
            }
        }
        if(!(bestRow == -1 || bestCol == -1)){
            game.makeMove(bestRow, bestCol);
        }
    }

    public void makeBestMoveForMax(BoardGame game, int depth, double alpha, double beta) {
        int bestRow = -1;
        int bestCol = -1;
        for(int x = 0; x < 8 && x >= 0; x++){
            for(int y = 0; y < 8 && y >= 0; y++){
                if(game.legalMove(x,y)){
                    game.makeMove(x, y);
                    double value = minValue(game, depth - 1, alpha, beta);
                    game.undoMove();
                    if(value > alpha){
                        bestRow = x;
                        bestCol = y;
                    }
                }
            }
        }
        if(!(bestRow == -1 || bestCol == -1)){
            game.makeMove(bestRow, bestCol);
        }
    }

}