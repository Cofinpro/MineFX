package de.cofinpro.dojo.minefx;

import de.cofinpro.dojo.minefx.multiplayer.MulticastReceiver;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

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
        revealField(gameField);
        handleWinning();
    }

    public void handleMine() {
            Arrays.stream(field).forEach(row -> Arrays.stream(row).forEach(GameField::uncover));
            timerTimeline.pause();

            new ShitHitsTheFanAnimation(primaryStage).run();

            MediaPlayer mediaPlayer = new MediaPlayer(gameMediaLoader.getLooseSound());
            mediaPlayer.play();
    }

    private EventHandler<ActionEvent> revealEmptyFields = event -> {
        GameField gameField = (GameField) event.getSource();
        revealField(gameField);
    };

    private void revealField(GameField field) {
        if (field.isCovered()) {
            field.uncover();
            if (field.getMineCount() == 0) {
                walkNeighbours(field, this::revealField);
            }
            if (field.isMine()) {
                handleMine();
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
