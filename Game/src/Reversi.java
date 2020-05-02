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


public class Reversi implements BoardGame{

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public static int TURN_COUNTER = 0;
    public static int FREE_TILES = 5;

    private Board mainBoard = new Board();

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
        gameMenu.getChildren().addAll(exitButton1, scoreLabel);

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
                    Piece newPiece = null;
                    int mouseX = (int)(event.getSceneX() / TILE_SIZE);
                    int mouseY = (int)((event.getSceneY() - 30) / TILE_SIZE);
                    if(!board[mouseX][mouseY].hasPiece()) {
                        newPiece = makePiece(TURN_COUNTER % 2 == 0 ? PieceType.BLACK : PieceType.WHITE, mouseX, mouseY);
                        if(TURN_COUNTER % 2  == 0) mainBoard.incScoreBlack();
                        else mainBoard.incScoreWhite();
                        scoreLabel.setText("Black: " + mainBoard.getScoreBlack() + "/" + "White: " + mainBoard.getScoreWhite());
                        board[mouseX][mouseY].setPiece(newPiece);
                        TURN_COUNTER++;
                        FREE_TILES--;
                    }
                    if(newPiece != null){
                        pieceGroup.getChildren().add(newPiece);
                    }
                });
            }
        }

        mainBoard.setBoard(board);

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

    public void makeMove(int X, int Y){
        return;
    }

    public void undoMove(){
        return;
    }

    public boolean gameOver(){
        return true;
    }

    public boolean isDraw(){
        return true;
    }

    public double getBoardValue(){
        double value = 0.0;
        value = mainBoard.getScoreBlack()- mainBoard.getScoreWhite();
        return value;
    }

    public boolean legalMove(int X, int Y){
        return true;
    }

    public int checkMove(int X, int Y){
        return 1;
    }


    private Piece makePiece(PieceType type, int x, int y){
        Piece piece = new Piece(type, x, y);
        return piece;
    }

}
