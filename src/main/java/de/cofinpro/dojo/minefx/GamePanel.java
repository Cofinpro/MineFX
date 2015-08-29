package de.cofinpro.dojo.minefx;

import de.cofinpro.dojo.minefx.model.ConfigFx;
import de.cofinpro.dojo.minefx.multiplayer.MulticastTransmitter;
import de.cofinpro.dojo.minefx.multiplayer.NewBoardEvent;
import de.cofinpro.dojo.minefx.multiplayer.UserIdProvider;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class GamePanel extends GridPane {

    private GameField[][] field;
    private String gameId;
    int height;
    int width;
    int numberOfMines;
    private boolean useBigBadPoo = false;
    private GameMediaLoader gameMediaLoader;
    private Timeline timerTimeline;
    private Stage primaryStage;
    private ConfigFx configFx;
    private FieldColorTable colorTable = new FieldColorTable();
    private String localUserId = UserIdProvider.getInstance().getUserId();
    private ObservableList<UserScoreEntry> userScoreData;


    public GamePanel(ConfigFx configFx, Timeline timerTimeline, Stage primaryStage, ObservableList<UserScoreEntry> userScoreData) throws IOException {
        this.configFx = configFx;
        this.height = configFx.getRows();
        this.width = configFx.getColumns();
        this.numberOfMines = configFx.getPoos();
        this.timerTimeline = timerTimeline;
        this.primaryStage = primaryStage;
        this.useBigBadPoo = configFx.getDoBigBadPoo();
        this.gameMediaLoader = new GameMediaLoader();
        this.userScoreData = userScoreData;
        this.start();
    }

    public void start() {
        this.userScoreData.clear();
        this.gameId = String.valueOf(new Random().nextInt(1000));
        this.getChildren().clear();
        this.height = configFx.getRows();
        this.width = configFx.getColumns();
        this.numberOfMines = configFx.getPoos();
        this.useBigBadPoo = configFx.getDoBigBadPoo();
        drawField();
        placeMines();
    }

    private void placeMines() {
        this.numberOfMines = Math.min(numberOfMines, width * height - 1);
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numberOfMines) {
            final GameField gameField = getRandomNonMineField(random);
            gameField.setHiddenMine();
            incrementMineCount(gameField.getxCoordinate(), gameField.getyCoordinate());
            minesPlaced++;
        }

        if (useBigBadPoo) {
            final GameField gameField = getRandomNonMineField(random);
            gameField.setHiddenBigBadPoo();
            incrementForBigBadPoo(gameField.getxCoordinate(), gameField.getyCoordinate());
        }
    }

    private GameField getRandomNonMineField(Random random) {
        while (true) {
            final int x = random.nextInt(width);
            final int y = random.nextInt(height);

            final GameField gameField = field[x][y];

            if (gameField.isHiddenMine() || gameField.isHiddenBigBadPoo()) {
                // skip and try another field
                continue;
            }
            return gameField;
        }
    }

    private void incrementMineCount(int x, int y) {
        walkNeighbours(this.field[x][y], GameField::incrementMineCount);
    }

    private void incrementForBigBadPoo(int x, int y) {
        walkNeighbours(this.field[x][y], GameField::incrementForBigBadPoo);
    }

    private void drawField() {
        field = new GameField[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final GameField gameField = new GameField(gameId, i, j, colorTable);
                gameField.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                gameField.addEventHandler(ActionEvent.ACTION, revealEmptyFields);
                gameField.addEventHandler(ActionEvent.ACTION, checkWinCondition);
                field[i][j] = gameField;
                this.add(gameField, i, j);
            }
        }
    }

    public GameField getGameField(int x, int y) {
        return field[x][y];
    }

    public void revealField(String actor, int x, int y) {
        GameField gameField = field[x][y];
        revealField(gameField, actor);
        handleWinning();
    }

    public void handleMine() {
        uncoverAllFields();
        timerTimeline.pause();

        new ShitHitsTheFanAnimation(primaryStage).run();

        MediaPlayer mediaPlayer = new MediaPlayer(gameMediaLoader.getLooseSound());
        mediaPlayer.play();
    }


    public void handleBigBadPoo() {
        uncoverAllFields();
        timerTimeline.pause();

        new ShitHitsTheFanAnimation(primaryStage).run();

        MediaPlayer mediaPlayer = new MediaPlayer(gameMediaLoader.getLooseBigPooSound());
        mediaPlayer.play();
    }

    private void uncoverAllFields() {
        Arrays.stream(field).forEach(row -> Arrays.stream(row).forEach(g -> {
            // uncovering all fields due to a loss attributes no user
            g.uncover(null);
            g.setDisable(true);
        }));
    }

    private EventHandler<ActionEvent> revealEmptyFields = event -> {
        GameField gameField = (GameField) event.getSource();
        revealField(gameField, localUserId);
        calculateScoreBoard();
    };

    private void revealField(GameField field, String actor) {
        if (field.isNotYetRevealed()) {
            field.uncover(actor);
            if (field.getMineCount() == 0) {
                walkNeighbours(field, gameField -> revealField(gameField, actor));
            }

            // only handle mines if the local user is the cause of the uncovering
            if (localUserId.equals(actor)) {
                if (field.isRevealedMine()) {
                    handleMine();
                }
                if (field.isRevealdBigBadPoo()) {
                    handleBigBadPoo();
                }
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

        if (useBigBadPoo) {
            totalFieldsToUncover--;
        }

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

    public void broadcastGameboard() throws IOException {
        FieldStatus[][] fieldBoard = new FieldStatus[width][height];
        GameFieldModification[][] fieldModifications = new GameFieldModification[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                fieldBoard[x][y] = field[x][y].getStatus();
                fieldModifications[x][y] = field[x][y].getModification();
            }
        }
        MulticastTransmitter.getInstance().send(new NewBoardEvent(gameId, fieldBoard, fieldModifications));
    }

    public void setNewBoard(String gameId, FieldStatus[][] newBoard, GameFieldModification[][] modificationField) {
        this.gameId = gameId;
        this.width = newBoard[0].length;
        this.height = newBoard.length;
        this.numberOfMines = 0;
        this.getChildren().clear();
        drawField();
        userScoreData.clear();
        primaryStage.sizeToScene();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                field[x][y].setModifiaction(modificationField[x][y]);

                switch (newBoard[x][y]) {
                    case HIDDEN_BIG_BAD_POO:
                        field[x][y].setHiddenBigBadPoo();
                        numberOfMines++;
                        useBigBadPoo = true;
                        incrementForBigBadPoo(x, y);
                        break;
                    case HIDDEN_MINE:
                        field[x][y].setHiddenMine();
                        numberOfMines++;
                        incrementMineCount(x, y);
                        break;
                    case REVEALED_BIG_BAD_POO:
                        useBigBadPoo = true;
                    case REVEALED_MINE:
                        numberOfMines++;
                        incrementMineCount(x, y);
                        //fallthrough
                    case HINT:
                        field[x][y].uncover(modificationField[x][y].getModifiedBy());
                        break;
                    case COVERED:
                    default: //do nothing
                }
            }
        }
    }

    public void calculateScoreBoard() {
        System.out.println("calculate Scoreboard");

        Map<String, UserScoreEntry> userScoreEntryMap = new HashMap<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                GameField gameField = field[x][y];

                GameFieldModification modification = gameField.getModification();
                String user = modification.getModifiedBy();
                if (user != null) {
                    if (!userScoreEntryMap.containsKey(user)) {
                        UserScoreEntry userScoreEntry = new UserScoreEntry();
                        userScoreEntry.setColor(colorTable.getColor(user));
                        userScoreEntry.setUserName(user);
                        userScoreEntry.setPoints(0);
                        userScoreEntryMap.put(user, userScoreEntry);
                    }

                    UserScoreEntry userScoreEntry = userScoreEntryMap.get(user);
                    if (gameField.isHint() && !gameField.isEditable()) {
                        userScoreEntry.addPoints(gameField.getMineCount());
                    }
                }
            }
        }

        userScoreData.clear();
        for (UserScoreEntry userScoreEntry : userScoreEntryMap.values()) {
            System.out.println(userScoreEntry.toString());
            userScoreData.add(userScoreEntry);
        }
    }

    public String getGameId() {
        return gameId;
    }
}
