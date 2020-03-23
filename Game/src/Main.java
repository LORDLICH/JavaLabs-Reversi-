import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public static int TURN_COUNTER = 0;
    public static int FREE_TILES = 5;

    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Parent createGameScene() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup,pieceGroup);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

       for (int y = 0; y < HEIGHT; y++) {
           for (int x = 0; x < WIDTH; x++) {
               Tile tile = new Tile(x, y);
               board[x][y] = tile;
               tileGroup.getChildren().add(tile);



               Piece piece = null;


               if((x == 3) && (y == 3) || (x == 4) && (y == 4)){
                   piece = makePiece(PieceType.WHITE, x, y);
               }

               if((x == 3) && (y == 4) || (x == 4) && (y == 3)){
                   piece = makePiece(PieceType.BLACK, x, y);
               }

               if(piece != null){
                   tile.setPiece(piece);
                   pieceGroup.getChildren().add(piece);
               }

               board[x][y].setOnMouseClicked(event -> {
                   Piece newPiece = null;
                   int mouseX = (int)(event.getSceneX() / TILE_SIZE);
                   int mouseY = (int)(event.getSceneY() / TILE_SIZE);
                   if(!board[mouseX][mouseY].hasPiece()) {
                       newPiece = makePiece(TURN_COUNTER % 2 == 0 ? PieceType.BLACK : PieceType.WHITE, mouseX, mouseY);
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
       return root;
    }

    private Parent createMenuScene() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        return root;
    }



    private Piece makePiece(PieceType type, int x, int y){
        Piece piece = new Piece(type, x, y);


        return piece;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene mainScene = new Scene(createGameScene());
        primaryStage.setTitle("Reversi");
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }

    public static void main(String[] args) { launch(args); }
}