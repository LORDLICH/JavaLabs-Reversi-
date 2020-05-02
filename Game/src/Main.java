import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Reversi game = new Reversi();
        Scene mainScene = new Scene(game.createContent());
        primaryStage.setTitle("Reversi");
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }

    public static void main(String[] args) { launch(args); }
}