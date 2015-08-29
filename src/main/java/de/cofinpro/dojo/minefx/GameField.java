package de.cofinpro.dojo.minefx;

import de.cofinpro.dojo.minefx.multiplayer.MultiplayerClickHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class GameField extends Button {

    private FieldStatus status = FieldStatus.COVERED;

    private int mineCountHint = 0;

    private int xCoordinate;
    private int yCoordinate;

    private boolean editable = true;

    private static final MineClickHandler clickHandler = new MineClickHandler();
    private static final MultiplayerClickHandler multiplayerHandler = new MultiplayerClickHandler();

    public GameField(int x, int y) {
        super(" ");
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.setOnMouseClicked(clickHandler);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, multiplayerHandler);
        this.setMinSize(32, 32);
        this.setMaxSize(32, 32);
    }

    public void setHiddenMine() {
        this.status = FieldStatus.HIDDEN_MINE;
        this.updateText();
    }

    public boolean isHiddenMine() {
        return this.status == FieldStatus.HIDDEN_MINE;
    }

    public void incrementMineCount() {
        mineCountHint++;
    }

    public void uncover() {
        if (isEditable()) {
            if (isHiddenMine()) {
                this.status = FieldStatus.REVEALED_MINE;
            } else {
                this.status = FieldStatus.HINT;
            }
            this.setStyle(status.getStyle());
            this.updateText();
            this.setEditable(false);
        }
    }

    public void mark() {
        if (isMarked()) {
            this.status = FieldStatus.COVERED;
        } else {
            this.status = FieldStatus.MARKED;
        }
        this.setStyle(status.getStyle());
        this.updateText();
        this.toggleEditable();
    }

    public boolean isMarked() {
        return (FieldStatus.MARKED == this.status);
    }

    public boolean isNotYetRevealed() {
        return (FieldStatus.COVERED == this.status) || FieldStatus.HIDDEN_MINE == this.status;
    }

    public boolean isHint() {
        return (FieldStatus.HINT == this.status);
    }

    private void toggleEditable() {
        this.setEditable(!this.isEditable());
    }

    private void updateText() {
        if (status == FieldStatus.HINT) {
            this.setText(mineCountHint == 0 ? null : String.valueOf(mineCountHint));
        } else {
            if (status.getImageUrl() == null) {
                this.setText(status.getSymbol());
                this.setGraphic(null);
            } else {
                this.setText(null);
                this.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream(status.getImageUrl()))));
            }
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

    public boolean isRevealed() {
        return isRevealedMine() || isHint();
    }

    public boolean isRevealedMine() {
        return this.status == FieldStatus.REVEALED_MINE;
    }

    public FieldStatus getStatus() {
        return status;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
