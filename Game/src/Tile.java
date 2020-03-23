import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private Piece piece;

    private double mouseX, mouseY;

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public boolean hasPiece(){
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }


    public Tile(int x, int y){
        setWidth(Main.TILE_SIZE - 2);
        setHeight(Main.TILE_SIZE - 2);

        relocate(x * Main.TILE_SIZE + 1, y * Main.TILE_SIZE + 1);

        setFill(Color.DARKGREEN);

        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
    }



}
