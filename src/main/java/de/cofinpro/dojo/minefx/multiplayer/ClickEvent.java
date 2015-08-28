package de.cofinpro.dojo.minefx.multiplayer;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class ClickEvent extends  MultiplayerEvent {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ClickEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
