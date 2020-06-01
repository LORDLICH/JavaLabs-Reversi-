import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Reversi game = Reversi.getThisGame();
        //MiniMax opponent = new MiniMax();
        Reversi game = new Reversi();
        Stage mainStage = new Stage();

        Scene mainScene = new Scene(game.createContent());
        mainStage.setTitle("Reversi");
        mainStage.setScene(mainScene);
        mainStage.showAndWait();
    }


    public static void main(String[] args) { launch(args); }
}