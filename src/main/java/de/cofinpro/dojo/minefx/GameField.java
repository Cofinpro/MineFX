package de.cofinpro.dojo.minefx;

import javafx.scene.control.ToggleButton;

import java.awt.*;


/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class GameField extends ToggleButton {

    private FieldStatus status = FieldStatus.COVERED;

    private int mineCountHint = 0;

    private boolean mine = false;
    private int xCoordinate;
    private int yCoordinate;

    private static final MineClickHandler clickHandler = new MineClickHandler();

    public GameField(int x, int y) {
        super(" ");
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.setOnMouseClicked(clickHandler);
        this.setPrefSize(30, 30);
        this.setMaxSize(30, 30);
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
        this.updateText();
    }

    public void incrementMineCount() {
        mineCountHint++;
    }

    public void uncover() {
        this.setSelected(true);
        if (mine) {
            this.status = FieldStatus.MINE;
        } else {
            this.status = FieldStatus.HINT;
        }
        this.setStyle(status.getStyle());
        this.updateText();
        this.setDisabled(true);
    }

    public void mark() {
        if (isMarked()) {
            this.status = FieldStatus.COVERED;
        } else {
            this.status = FieldStatus.MARKED;
        }
        this.setStyle(status.getStyle());
        this.updateText();
        this.toggleEnabled();
    }

    public boolean isMarked() {
        return (FieldStatus.MARKED == this.status);
    }

    private void toggleEnabled() {
        this.setDisabled(!this.isDisabled());
    }

    private void updateText() {
        if (status != FieldStatus.HINT) {
            this.setText(status.getSymbol());
        } else {
            this.setText(mineCountHint == 0 ? null : String.valueOf(mineCountHint));
        }
    }

    public int getMineCount() {
        return mineCountHint;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public boolean isUncovered() {
        return status != FieldStatus.COVERED && status != FieldStatus.MARKED;
    }

    public FieldStatus getStatus() {
        return status;
    }
}
