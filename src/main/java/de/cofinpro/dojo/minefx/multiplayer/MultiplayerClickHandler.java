package de.cofinpro.dojo.minefx.multiplayer;

import de.cofinpro.dojo.minefx.GameField;
import javafx.event.EventHandler;
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
        switch (event.getButton()) {
            case PRIMARY:
                clickEvent = new ClickEvent(gameField.getGameId(), gameField.getxCoordinate(), gameField.getyCoordinate());
                break;
            case SECONDARY:
                clickEvent = new MarkEvent(gameField.getGameId(), gameField.getxCoordinate(), gameField.getyCoordinate());
                break;
            default:
                return;
        }

        try {
            MulticastTransmitter.getInstance().send(clickEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
