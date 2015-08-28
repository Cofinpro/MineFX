package de.cofinpro.dojo.minefx;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GamePanel gamePanel = new GamePanel(10, 10, 10);

        VBox root = new VBox();
        root.getChildren().add(createMenu());
        root.getChildren().add(gamePanel);

        primaryStage.setTitle("Minesweeper");
        final Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);

        primaryStage.show();
    }


    private MenuBar createMenu() {
        Menu menu = new Menu();
        menu.setText("Poo");
        MenuItem menuItem = new MenuItem("Neustart");
        menu.getItems().add(menuItem);
        MenuBar bar = new MenuBar();
        bar.getMenus().add(menu);

        return bar;
    }
}
