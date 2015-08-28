package de.cofinpro.dojo.minefx;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class MineClickHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        GameField gameField = (GameField) event.getSource();
        switch (event.getButton()) {
            case SECONDARY:
                gameField.mark();
                break;
            case PRIMARY:
                if (FieldStatus.MARKED == gameField.getStatus()) {
                    return;
                }
                if (gameField.isMine()) {
                    new Alert(Alert.AlertType.ERROR, "BANG!").show();
                } else {
                    if (gameField.getStatus() == FieldStatus.COVERED) {
                        gameField.uncover();
                    }
                }

        }
    }
}
