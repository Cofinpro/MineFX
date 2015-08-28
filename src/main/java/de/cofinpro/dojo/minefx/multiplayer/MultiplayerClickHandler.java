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
        if (event.getButton() != MouseButton.PRIMARY) {
            return;
        }
        GameField gameField = (GameField) event.getSource();
        try {
            final ClickEvent clickEvent = new ClickEvent(gameField.getxCoordinate(), gameField.getyCoordinate());
            MulticastSender.getInstance().send(clickEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
