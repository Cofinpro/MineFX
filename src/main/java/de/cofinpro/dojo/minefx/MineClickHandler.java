package de.cofinpro.dojo.minefx;

import javafx.event.EventHandler;
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
                if (gameField.isRevealed()) {
                    // bereits aufgedeckte Felder werden nicht markiert
                    return;
                }
                gameField.mark(System.getProperty("user.name"));
                break;
            case PRIMARY:
                if (gameField.isMarked() || gameField.isHiddenMine()) {
                    return;
                }
                if (gameField.isNotYetRevealed()) {
                    gameField.uncover(System.getProperty("user.name"));
                }
        }
    }
}
