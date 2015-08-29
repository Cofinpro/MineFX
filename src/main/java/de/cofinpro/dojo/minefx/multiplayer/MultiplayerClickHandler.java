package de.cofinpro.dojo.minefx.multiplayer;

import de.cofinpro.dojo.minefx.GameField;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class MultiplayerClickHandler implements EventHandler<MouseEvent> {



    @Override
    public void handle(MouseEvent event) {
        GameField gameField = (GameField) event.getSource();

        final ClickEvent clickEvent;
        if (event.getButton() == MouseButton.PRIMARY) {
            clickEvent = new ClickEvent(gameField.getGameId(), gameField.getxCoordinate(), gameField.getyCoordinate());
        } else if (event.getButton() == MouseButton.SECONDARY) {
            clickEvent = new MarkEvent(gameField.getGameId(), gameField.getxCoordinate(), gameField.getyCoordinate());
        } else {
            return;
        }

        try {
            MulticastTransmitter.getInstance().send(clickEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
