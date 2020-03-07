import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private Piece piece;

    public boolean hasPiece(){
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }


    public Tile(boolean condition, int x, int y){
        setWidth(condition ? Main.TILE_SIZE : Main.TILE_SIZE - 2);
        setHeight(condition ? Main.TILE_SIZE : Main.TILE_SIZE - 2);

        relocate(condition ? x * Main.TILE_SIZE : x * Main.TILE_SIZE + 1, condition ?  y * Main.TILE_SIZE : y * Main.TILE_SIZE + 1);

        setFill(condition ? Color.valueOf("#000000") : Color.valueOf("#006400"));
    }

}
