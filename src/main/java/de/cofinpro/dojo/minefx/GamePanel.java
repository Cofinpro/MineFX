package de.cofinpro.dojo.minefx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;

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

    public GamePanel(int height, int width, int numberOfMines) {
        this.height = height;
        this.width = width;
        this.numberOfMines = numberOfMines;
        this.start();
    }

    public void start() {
        drawField();
        placeMines();
    }

    private void placeMines() {
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
                gameField.addEventHandler(ActionEvent.ACTION, revealEmptyFields);
                field[i][j] = gameField;
                this.add(gameField, i, j);
            }
        }
    }

    public EventHandler<ActionEvent> uncoverHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Arrays.stream(field).forEach(row -> Arrays.stream(row).forEach(GameField::uncover));
        }
    };

    private EventHandler<ActionEvent> revealEmptyFields = event -> {
        GameField gameField = (GameField) event.getSource();
        revealEmptyFields(gameField);
    };

    private void revealEmptyFields(GameField field) {
        if (!field.isUncovered()) {
            field.uncover();
            if (field.getMineCount() == 0) {
                walkNeighbours(field, this::revealEmptyFields);
            }
        }
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
