package de.cofinpro.dojo.minefx.multiplayer;

import de.cofinpro.dojo.minefx.GamePanel;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class ClickEvent extends MultiplayerEvent {
    private int x;
    private int y;

    public ClickEvent(String gameId, int x, int y) {
        super(gameId);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "ClickEvent{" +
                "gameId=" + getGameId() +
                ", sourceClientId=" + sourceClientId +
                ", x=" + x +
                ", y=" + y +
                "} ";
    }

    @Override
    public void executeMove(GamePanel gamePanel) {
        gamePanel.revealField(sourceClientId, x,y);
    }
}
