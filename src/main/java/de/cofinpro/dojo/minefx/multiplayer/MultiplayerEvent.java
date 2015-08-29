package de.cofinpro.dojo.minefx.multiplayer;

import de.cofinpro.dojo.minefx.GamePanel;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.Serializable;
import java.util.Random;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public abstract class MultiplayerEvent implements Serializable {


    public abstract void executeMove(GamePanel panel);

    private static final String userId = UserIdProvider.getInstance().getUserId();
    protected final String sourceClientId = userId;
    private String gameId;

    public MultiplayerEvent(String gameId) {
        this.gameId = gameId;
    }

    public void execute(GamePanel panel) {
        if (userId.equals(sourceClientId)) {
            // do not execute your own events
            return;
        }
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                executeMove(panel);
                return null;
            }
        };
        Platform.runLater(task);
    }

    public String getGameId() {
        return gameId;
    }
}
