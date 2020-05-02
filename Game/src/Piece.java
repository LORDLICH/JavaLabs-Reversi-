import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {

    private PieceType type;

    public PieceType getType() {
        return type;
    }

    public Piece(PieceType type, int x, int y){
        this.type = type;

        relocate(x * Reversi.TILE_SIZE, y * Reversi.TILE_SIZE);

        Ellipse bg = new Ellipse(Reversi.TILE_SIZE * 0.3125, Reversi.TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);
        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(Reversi.TILE_SIZE * 0.03);

        bg.setTranslateX((Reversi.TILE_SIZE - Reversi.TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((Reversi.TILE_SIZE - Reversi.TILE_SIZE * 0.26 * 2) / 2 + Reversi.TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(Reversi.TILE_SIZE * 0.3125, Reversi.TILE_SIZE * 0.26);
        ellipse.setFill(type == PieceType.BLACK ? Color.valueOf("2E2D2D") : Color.WHITE);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(Reversi.TILE_SIZE * 0.03);

        ellipse.setTranslateX((Reversi.TILE_SIZE - Reversi.TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((Reversi.TILE_SIZE - Reversi.TILE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);
    }
}
