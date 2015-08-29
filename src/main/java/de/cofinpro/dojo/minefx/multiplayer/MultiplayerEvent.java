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

    private static final int clientId = new Random().nextInt();

    public static int getClientId() {
        return clientId;
    }

    public void execute(GamePanel panel) {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                executeMove(panel);
                return null;
            }
        };
        Platform.runLater(task);
    }


}
