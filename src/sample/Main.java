package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Minesweeper");
        final Scene value = new Scene(new GamePanel(10,10, 10), 300, 275);

        primaryStage.setScene(value);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
