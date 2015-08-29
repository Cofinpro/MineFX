package de.cofinpro.dojo.minefx.multiplayer;

import de.cofinpro.dojo.minefx.FieldStatus;
import de.cofinpro.dojo.minefx.GamePanel;

/**
 * Created by mheck on 29.08.2015.
 */
public class NewBoardEvent extends MultiplayerEvent {

    private FieldStatus[][] boardField;

    public NewBoardEvent(String gameId, FieldStatus[][] boardField) {
        super(gameId);
        this.boardField = boardField;
    }

    public FieldStatus[][] getBoardField() {
        return boardField;
    }

    @Override
    public void executeMove(GamePanel panel) {
        panel.setNewBoard(getGameId(), boardField);
    }

    @Override
    public String toString() {
        return "NewBoardEvent{" +
                "gameId=" + getGameId() +
                '}';
    }
}
