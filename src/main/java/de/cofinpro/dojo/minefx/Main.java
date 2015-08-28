package de.cofinpro.dojo.minefx;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane(createMenu(), new GamePanel(10, 10, 10), null, null, null);

        primaryStage.setTitle("Minesweeper");
        final Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);

        primaryStage.show();
    }


    private Node createMenu() {
        javafx.scene.control.Menu menu = new javafx.scene.control.Menu();
        menu.setText("Poo");
        menu.getItems().add(new MenuItem("Neustart"));
        javafx.scene.control.MenuBar bar = new javafx.scene.control.MenuBar();
        bar.getMenus().add(menu);

        return (Node) bar;
    }
}
