package de.cofinpro.dojo.minefx;

import de.cofinpro.dojo.minefx.multiplayer.MulticastReceiver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class GamePanel extends GridPane {

    private GameField[][] field;
    int height;
    int width;
    int numberOfMines;
    private GameMediaLoader gameMediaLoader;
    private Timeline timerTimeline;
    private Stage primaryStage;
    private boolean shakedAway;

    public GamePanel(int height, int width, int numberOfMines, Timeline timerTimeline, Stage primaryStage) throws IOException {
        this.height = height;
        this.width = width;
        this.numberOfMines = numberOfMines;
        this.timerTimeline = timerTimeline;
        this.primaryStage = primaryStage;
        gameMediaLoader = new GameMediaLoader();
        MulticastReceiver multicastReceiver = new MulticastReceiver(this);
        new Thread(multicastReceiver).start();
        this.start();
    }

    public void start() {
        this.getChildren().clear();
        drawField();
        placeMines();
    }

    private void placeMines() {
        this.numberOfMines = Math.min(numberOfMines, width*height);
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numberOfMines) {
            final int x = random.nextInt(width);
            final int y = random.nextInt(height);

            final GameField gameField = field[x][y];

            if (gameField.isMine()) {
                // skip and try another field
                continue;
            }
            gameField.setMine(true);
            gameField.addEventHandler(ActionEvent.ACTION, uncoverHandler);
            incrementMineCount(x, y);
            minesPlaced++;
        }
    }

    private void incrementMineCount(int x, int y) {
        walkNeighbours(this.field[x][y], GameField::incrementMineCount);
    }

    private void drawField() {
        field = new GameField[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final GameField gameField = new GameField(i, j);
                gameField.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                gameField.addEventHandler(ActionEvent.ACTION, revealEmptyFields);
                gameField.addEventHandler(ActionEvent.ACTION, checkWinCondition);
                field[i][j] = gameField;
                this.add(gameField, i, j);
            }
        }
    }

    public void revealField(int x, int y) {
        GameField gameField = field[x][y];
        revealEmptyFields(gameField);
        handleWinning();
    }

    public EventHandler<ActionEvent> uncoverHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Arrays.stream(field).forEach(row -> Arrays.stream(row).forEach(GameField::uncover));
            timerTimeline.pause();

            Timeline shakerTimeline = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    double xDelta = Math.random() * 10;
                    double yDelta = Math.random() * 10;

                    if (shakedAway) {
                        primaryStage.setX(primaryStage.getX() + xDelta);
                        primaryStage.setY(primaryStage.getY() + yDelta);
                        shakedAway = false;
                    } else {
                        primaryStage.setX(primaryStage.getX() - xDelta);
                        primaryStage.setY(primaryStage.getY() - yDelta);
                        shakedAway = true;
                    }
                }
            }));

            shakerTimeline.setCycleCount(50);
            shakerTimeline.setAutoReverse(false);
            shakerTimeline.play();

            MediaPlayer mediaPlayer = new MediaPlayer(gameMediaLoader.getLooseSound());
            mediaPlayer.play();
        }
    };

    private EventHandler<ActionEvent> revealEmptyFields = event -> {
        GameField gameField = (GameField) event.getSource();
        revealEmptyFields(gameField);
    };

    private void revealEmptyFields(GameField field) {
        if (field.isCovered()) {
            field.uncover();
            if (field.getMineCount() == 0) {
                walkNeighbours(field, this::revealEmptyFields);
            }
        }
    }

    private EventHandler<ActionEvent> checkWinCondition = event -> {
            handleWinning();
    };

    private void handleWinning() {
        if (isWinConditionFulfilled()) {
            timerTimeline.pause();
            MediaPlayer mediaPlayer = new MediaPlayer(gameMediaLoader.getWinSound());
            mediaPlayer.play();
        }
    }

    private boolean isWinConditionFulfilled() {
        int totalFields = width * height;
        int totalFieldsToUncover = totalFields - numberOfMines;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                GameField gameField = field[x][y];

                // uncover all non-mine fields to win
                if (gameField.isRevealed()) {
                    totalFieldsToUncover--;
                }
            }
        }

        return totalFieldsToUncover == 0;
    }

    private void walkNeighbours(GameField field, java.util.function.Consumer<GameField> op) {
        int x = field.getxCoordinate();
        int y = field.getyCoordinate();
        for (int i = Math.max(x - 1, 0); i <= Math.min(x + 1, width - 1); i++) {
            for (int j = Math.max(y - 1, 0); j <= Math.min(y + 1, height - 1); j++) {
                GameField gameField = this.field[i][j];
                op.accept(gameField);
            }
        }
    }


}
