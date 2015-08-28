package de.cofinpro.dojo.minefx.multiplayer;

import de.cofinpro.dojo.minefx.GamePanel;
import javafx.concurrent.Task;

import java.io.Serializable;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public abstract class MultiplayerEvent implements Serializable {

    public abstract void executeMove(GamePanel panel);

    public void execute(GamePanel panel) {
       Task task = new Task<Void>() {
           @Override
           protected Void call() throws Exception {
               executeMove(panel);
               return null;
           }
       };
        new Thread(task).start();
    }

}
