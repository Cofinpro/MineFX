package de.cofinpro.dojo.minefx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class Main extends Application {

    private static final DecimalFormat DF = new DecimalFormat("00");
    private GamePanel gamePanel;
    private long startMillis;
    private Timeline timerTimeline;

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane statusBar = createStatusBar();
        gamePanel = new GamePanel(10, 10, 10, timerTimeline);

        VBox root = new VBox();
        root.getChildren().add(createMenu());
        root.getChildren().add(gamePanel);
        root.getChildren().add(statusBar);

        primaryStage.setTitle("Shitsweeper");
        final Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);

        primaryStage.show();
    }


    private MenuBar createMenu() {
        Menu menu = new Menu();
        menu.setText("Shit");
        MenuItem menuItem = new MenuItem("Shit again");
        menuItem.setOnAction(event -> { this.restart(); });
        menu.getItems().add(menuItem);
        MenuBar bar = new MenuBar();
        bar.getMenus().add(menu);

        return bar;
    }

    private GridPane createStatusBar() {

        startMillis = System.currentTimeMillis();

        Label time = new Label();
        GridPane pane = new GridPane();
        pane.add(time, 0, 0);

        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Duration duration = new Duration(System.currentTimeMillis() - startMillis);
                String text = DF.format(duration.toHours()) + ":" + DF.format(duration.toMinutes()) + ":" + DF.format(duration.toSeconds());
                time.setText(text);
            }
        }));

        timerTimeline.setCycleCount(Animation.INDEFINITE);
        timerTimeline.play();

        return pane;
    }

    public void restart() {
        startMillis = System.currentTimeMillis();
        timerTimeline.play();
        this.gamePanel.start();
    }
}
