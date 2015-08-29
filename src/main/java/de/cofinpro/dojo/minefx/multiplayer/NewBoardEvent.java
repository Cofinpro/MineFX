package de.cofinpro.dojo.minefx.multiplayer;

import de.cofinpro.dojo.minefx.FieldStatus;
import de.cofinpro.dojo.minefx.GameFieldModification;
import de.cofinpro.dojo.minefx.GamePanel;

/**
 * Created by mheck on 29.08.2015.
 */
public class NewBoardEvent extends MultiplayerEvent {

    private FieldStatus[][] boardField;

    private GameFieldModification[][] modificationField;

    public NewBoardEvent(String gameId, FieldStatus[][] boardField, GameFieldModification[][] modificationField) {
        super(gameId);
        this.boardField = boardField;
        this.modificationField = modificationField;
    }

    @Override
    public void executeMove(GamePanel panel) {
        panel.setNewBoard(getGameId(), boardField, modificationField);
    }

    @Override
    public String toString() {
        return "NewBoardEvent{" +
                "gameId=" + getGameId() +
                '}';
    }
}
