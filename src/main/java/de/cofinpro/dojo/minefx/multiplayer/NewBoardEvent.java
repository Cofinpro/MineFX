package de.cofinpro.dojo.minefx.multiplayer;

import de.cofinpro.dojo.minefx.FieldStatus;
import de.cofinpro.dojo.minefx.GamePanel;

/**
 * Created by mheck on 29.08.2015.
 */
public class NewBoardEvent extends MultiplayerEvent {

    private FieldStatus[][] boardField;

    public FieldStatus[][] getBoardField() {
        return boardField;
    }

    public void setBoardField(FieldStatus[][] boardField) {
        this.boardField = boardField;
    }

    @Override
    public void executeMove(GamePanel panel) {
        panel.setNewBoard(boardField);
    }
}
