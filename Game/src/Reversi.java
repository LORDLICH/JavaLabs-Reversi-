import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Stack;


public class Reversi implements BoardGame{

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;


    //private static Reversi thisGame = new Reversi();

    //static public Reversi getThisGame() {
    //    return thisGame;
    //}


    //private MiniMax opponent = new MiniMax();
    private Board mainBoard = new Board();

    private Stack<Board> stackedBoard = new Stack<>();

    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    public Parent createContent() {
        BorderPane root = new BorderPane();

        HBox gameMenu = new HBox();
        gameMenu.setMaxHeight(30);
        Button exitButton1 = new Button("Exit");
        exitButton1.setMaxHeight(30);
        exitButton1.setOnAction(event -> {
            Node node = (Node) event.getSource();
            final Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        });
        Label scoreLabel = new Label();
        Label turnLabel = new Label();
        gameMenu.getChildren().addAll(exitButton1, scoreLabel, turnLabel);

        Pane game = new Pane();
        game.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        game.getChildren().addAll(tileGroup,pieceGroup);
        game.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));


        root.setTop(gameMenu);
        root.setCenter(game);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile(x, y);
                board[x][y] = tile;
                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if((x == 3) && (y == 3) || (x == 4) && (y == 4)){
                    piece = makePiece(PieceType.WHITE, x, y);
                    mainBoard.incScoreWhite();
                    scoreLabel.setText("Black: " + mainBoard.getScoreBlack() + "/" + "White: " + mainBoard.getScoreWhite());
                }

                if((x == 3) && (y == 4) || (x == 4) && (y == 3)){
                    piece = makePiece(PieceType.BLACK, x, y);
                    mainBoard.incScoreBlack();
                    scoreLabel.setText("Black: " + mainBoard.getScoreBlack() + "/" + "White: " + mainBoard.getScoreWhite());

                }

                if(piece != null){
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }

                board[x][y].setOnMouseClicked(event -> {
                    int mouseX = (int)(event.getSceneX() / TILE_SIZE);
                    int mouseY = (int)((event.getSceneY() - 30) / TILE_SIZE);
                    System.out.println(mouseX);
                    System.out.println(mouseY);
                    if(!board[mouseX][mouseY].hasPiece()) {
                        makeMove(mouseX,mouseY);
                        scoreLabel.setText("Black: " + mainBoard.getScoreBlack() + "/" + "White: " + mainBoard.getScoreWhite());
                        turnLabel.setText("Turn: " + (mainBoard.getTurn() % 2 == 0 ? "Black" : "White"));
                    }
                    /*
                    if(mainBoard.getTurn() % 2 != 0){
                        //play Min for Minimax
                        opponent.makeBestMoveForMin(thisGame, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                    }else if(mainBoard.getTurn() % 2 == 0){
                        //play Max for Minimax
                        opponent.makeBestMoveForMax(thisGame, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                    }*/

                });
            }
        }

        mainBoard.setBoard(board);
        turnLabel.setText("Turn: " + (mainBoard.getTurn() % 2 == 0 ? "Black" : "White"));
        Scene gameScene = new Scene(root);


        VBox mainMenu = new VBox();
        mainMenu.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        mainMenu.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Button playButton = new Button("Play");
        Button exitButton2 = new Button("Exit");

        playButton.setOnAction(event -> {
            Node node = (Node) event.getSource();
            final Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(gameScene);
        });

        exitButton2.setOnAction(event -> {
            Node node = (Node) event.getSource();
            final Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        });

        mainMenu.getChildren().addAll(playButton,exitButton2);

        return mainMenu;
    }

    private void moveInDirection(Position position, Direction direction){
        switch (direction) {
            case NORTH:
                position.setY(position.getY() - 1);
                break;
            case NORTHEAST:
                position.setY(position.getY() - 1);
                position.setX(position.getX() + 1);
                break;
            case EAST:
                position.setX(position.getX() + 1);
                break;
            case SOUTHEAST:
                position.setY(position.getY() + 1);
                position.setX(position.getX() + 1);
                break;
            case SOUTH:
                position.setY(position.getY() + 1);
                break;
            case SOUTHWEST:
                position.setY(position.getY() + 1);
                position.setX(position.getX() - 1);
                break;
            case WEST:
                position.setX(position.getX() - 1);
                break;
            case NORTHWEST:
                position.setY(position.getY() - 1);
                position.setX(position.getX() - 1);
                break;
        }
    }

    private Board copyBoard(){
        Board copiedBoard = new Board();
        copiedBoard.setBoard(mainBoard.getBoard());

        copiedBoard.setScoreBlack(mainBoard.getScoreBlack());
        copiedBoard.setScoreWhite(mainBoard.getScoreWhite());
        copiedBoard.setTurn(mainBoard.getTurn());
        copiedBoard.setNoMovesLeft(mainBoard.isNoMovesLeft());
        return copiedBoard;
    }

    public void makeMove(int x, int y){
        Board stacked = copyBoard();
        stackedBoard.push(stacked);

        Piece newPiece = null;
        ArrayList<Position> toFlip = checkMove(x, y);

        if(!mainBoard.getBoard()[x][y].hasPiece() && toFlip.size() > 0){
            newPiece = makePiece(mainBoard.getTurn()%2 == 0 ? PieceType.BLACK : PieceType.WHITE,x,y);
            mainBoard.getBoard()[x][y].setPiece(newPiece);
            pieceGroup.getChildren().add(newPiece);

            for(int i = 0; i < toFlip.size(); i++){
                int tempX = toFlip.get(i).getX();
                int tempY = toFlip.get(i).getY();
                mainBoard.getBoard()[tempX][tempY].getPiece().setType(mainBoard.getTurn() % 2 == 0
                        ? PieceType.BLACK : PieceType.WHITE, tempX, tempY);
            }

            if(mainBoard.getTurn()%2 == 0){
                mainBoard.setScoreBlack(mainBoard.getScoreBlack()+toFlip.size()+1);
                mainBoard.setScoreWhite(mainBoard.getScoreWhite()-toFlip.size());
            }else{
                mainBoard.setScoreWhite(mainBoard.getScoreWhite()+toFlip.size()+1);
                mainBoard.setScoreBlack(mainBoard.getScoreBlack()-toFlip.size());
            }

            mainBoard.setTurn(mainBoard.getTurn() + 1);

            /*
            if(!legalMovesRemain()){
                mainBoard.setWhoseMove(player);
                //System.out.println("NO MOVES FOUND FOR: " + opponent());
            }

            if(!legalMovesRemain()){
                mainBoard.setNoMovesLeft(1);
                //System.out.println("NO MOVES FOUND FOR ANYONE");
                //System.out.println(toString());
            }

            //System.out.println("Legal moves remain: " + legalMovesRemain());

             */
        }
    }

    public void undoMove() {
        if(!stackedBoard.empty()){
            mainBoard = stackedBoard.pop();
        }
    }

    public boolean gameOver(){
        boolean result = (mainBoard.getScoreBlack() + mainBoard.getScoreWhite() == 64 ||
                mainBoard.getScoreBlack() == 0 || mainBoard.getScoreWhite() == 0);
        return result;
    }

    public String getWinner() {
        String result = "";
        if(gameOver() && mainBoard.getScoreBlack() > mainBoard.getScoreWhite()){
            result = "Congratulations! You win!";
        }else if(mainBoard.getScoreWhite() > mainBoard.getScoreBlack()){
            result = "You're LOSER!";
        }
        return result;
    }

    public boolean isDraw() {
        boolean result = false;
        if(mainBoard.getScoreBlack() == mainBoard.getScoreWhite() &&
                mainBoard.getScoreBlack() + mainBoard.getScoreWhite() == 64){
            result = true;
        }
        return result;
    }

    public double getBoardValue(){
        double value = 0.0;
        value = mainBoard.getScoreBlack()- mainBoard.getScoreWhite();
        return value;
    }

    public boolean legalMove(int x, int y){
        return checkMove(x , y).size() > 0 && !mainBoard.getBoard()[x][y].hasPiece();
    }

    public ArrayList<Position> checkMove(int x, int y){
        ArrayList<Position> toFlip = new ArrayList<>();
        int tempX = x, tempY = y;

        ArrayList<Direction> directionsToLook = new ArrayList<>();
        if(tempY == 0 && tempX >= 1 && tempX <= 6){ //top row when y is 0
            directionsToLook.add(Direction.EAST);
            directionsToLook.add(Direction.SOUTHEAST);
            directionsToLook.add(Direction.SOUTH);
            directionsToLook.add(Direction.SOUTHWEST);
            directionsToLook.add(Direction.WEST);
        }else if(tempY == 0 && tempX == 0){ //top left corner 0,0
            directionsToLook.add(Direction.EAST);
            directionsToLook.add(Direction.SOUTHEAST);
            directionsToLook.add(Direction.SOUTH);
        }else if(tempX == 7 && tempY == 0){ //top right corner 0,7
            directionsToLook.add(Direction.SOUTH);
            directionsToLook.add(Direction.SOUTHWEST);
            directionsToLook.add(Direction.WEST);
        }else if(tempX == 0 && tempY >= 1 && tempY <= 6){ //left column when x is 0
            directionsToLook.add(Direction.NORTH);
            directionsToLook.add(Direction.NORTHEAST);
            directionsToLook.add(Direction.EAST);
            directionsToLook.add(Direction.SOUTHEAST);
            directionsToLook.add(Direction.SOUTH);
        }else if(tempX == 0 && tempY == 7){ // bottom left corner 7,0
            directionsToLook.add(Direction.NORTH);
            directionsToLook.add(Direction.NORTHEAST);
            directionsToLook.add(Direction.EAST);
        }else if(tempY == 7 && tempX >= 1 && tempX <= 6){ // bottom row when y is 7
            directionsToLook.add(Direction.NORTH);
            directionsToLook.add(Direction.NORTHEAST);
            directionsToLook.add(Direction.EAST);
            directionsToLook.add(Direction.WEST);
            directionsToLook.add(Direction.NORTHWEST);
        }else if(tempX == 7 && tempY == 7){ // bottom right corner 7,7
            directionsToLook.add(Direction.NORTH);
            directionsToLook.add(Direction.WEST);
            directionsToLook.add(Direction.NORTHWEST);
        }else if(tempX == 7 && tempY >= 1 && tempY <= 6){ // right column when x is 7
            directionsToLook.add(Direction.NORTH);
            directionsToLook.add(Direction.SOUTH);
            directionsToLook.add(Direction.SOUTHWEST);
            directionsToLook.add(Direction.WEST);
            directionsToLook.add(Direction.NORTHWEST);
        }else{ // check regular directions
            directionsToLook.add(Direction.NORTH);
            directionsToLook.add(Direction.NORTHEAST);
            directionsToLook.add(Direction.EAST);
            directionsToLook.add(Direction.SOUTHEAST);
            directionsToLook.add(Direction.SOUTH);
            directionsToLook.add(Direction.SOUTHWEST);
            directionsToLook.add(Direction.WEST);
            directionsToLook.add(Direction.NORTHWEST);
        }

        for (Direction currentDirection : directionsToLook) {

            Position currentPosition = new Position(tempX, tempY);
            ArrayList<Position> tempToFlip = new ArrayList<>();

            moveInDirection(currentPosition, currentDirection);

            while ((currentPosition.getY()<8) && (currentPosition.getY()>=0) && (currentPosition.getX()<8) && (currentPosition.getX()>=0)) {

                if(mainBoard.getBoard()[currentPosition.getX()][currentPosition.getY()].hasPiece()) {
                    Piece currentPiece = mainBoard.getBoard()[currentPosition.getX()][currentPosition.getY()].getPiece();

                    if(currentPiece.getType() == (mainBoard.getTurn()%2 == 0 ? PieceType.WHITE : PieceType.BLACK)) {

                        Position tempPosition = new Position(currentPosition.getX(),currentPosition.getY());
                        tempToFlip.add(tempPosition);
                        moveInDirection(currentPosition, currentDirection);

                    } else if (tempToFlip.size() != 0){
                        toFlip.addAll(tempToFlip);
                        break;
                    }else{
                        break;
                    }
                } else {
                    break;
                }

            }

        }
        return toFlip;
    }

    private Piece makePiece(PieceType type, int x, int y){
        Piece piece = new Piece(type, x, y);
        return piece;
    }

}
