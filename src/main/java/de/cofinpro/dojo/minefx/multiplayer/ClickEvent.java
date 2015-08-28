package de.cofinpro.dojo.minefx.multiplayer;

import java.io.Serializable;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class ClickEvent implements Serializable {
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
