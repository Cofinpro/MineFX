package de.cofinpro.dojo.minefx.multiplayer;

import de.cofinpro.dojo.minefx.GamePanel;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class MarkEvent extends ClickEvent {
    public MarkEvent(String gameId, int x, int y) {
        super(gameId, x, y);
    }

    @Override
    public void executeMove(GamePanel gamePanel) {
        if (!getGameId().equals(gamePanel.getGameId())) {
            return;
        }
        gamePanel.getGameField(getX(),getY()).mark(sourceClientId);
    }

    @Override
    public String toString() {
        return "MarkEvent{" +
                "gameId=" + getGameId() +
                ", sourceClientId=" + sourceClientId +
                ", x=" + getX() +
                ", y=" + getY() +
                "} ";
    }
}
