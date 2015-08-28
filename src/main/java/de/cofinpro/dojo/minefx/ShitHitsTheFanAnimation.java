package de.cofinpro.dojo.minefx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class ShitHitsTheFanAnimation {

    private static boolean shakedAway;
    private final Stage stage;

    public ShitHitsTheFanAnimation(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void run() {
        Timeline shakerTimeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
            double xDelta = Math.random() * 10;
            double yDelta = Math.random() * 10;

            if (shakedAway) {
                stage.setX(stage.getX() + xDelta);
                stage.setY(stage.getY() + yDelta);
                shakedAway = false;
            } else {
                stage.setX(stage.getX() - xDelta);
                stage.setY(stage.getY() - yDelta);
                shakedAway = true;
            }
        }));

        shakerTimeline.setCycleCount(50);
        shakerTimeline.setAutoReverse(false);
        shakerTimeline.play();
    }
}
