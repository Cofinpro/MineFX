package de.cofinpro.dojo.minefx;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Minesweeper");
        final Scene scene = new Scene(new GamePanel(10,10, 10), bounds.getWidth(), bounds.getHeight());

        primaryStage.setScene(scene);

        primaryStage.show();
    }


}
