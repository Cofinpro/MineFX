package de.cofinpro.dojo.minefx;

import de.cofinpro.dojo.minefx.multiplayer.MultiplayerClickHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
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

    private FieldColorTable colorTable;

    public GameFieldModification getModification() {
        return modification;
    }

    private GameFieldModification modification = new GameFieldModification();

    private static final MineClickHandler clickHandler = new MineClickHandler();
    private static final MultiplayerClickHandler multiplayerHandler = new MultiplayerClickHandler();
    private final String gameId;

    public GameField(String gameId, int x, int y, FieldColorTable colorTable) {
        this.gameId = gameId;
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.setOnMouseClicked(clickHandler);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, multiplayerHandler);
        this.setMinSize(32, 32);
        this.setMaxSize(32, 32);
        this.colorTable = colorTable;
    }

    public void setHiddenMine() {
        this.status = FieldStatus.HIDDEN_MINE;
        this.updateText();
    }

    public boolean isHiddenMine() {
        return this.status == FieldStatus.HIDDEN_MINE;
    }

    public void setHiddenBigBadPoo() {
        this.status = FieldStatus.HIDDEN_BIG_BAD_POO;
        this.updateText();
    }

    public boolean isHiddenBigBadPoo() {
        return status == FieldStatus.HIDDEN_BIG_BAD_POO;
    }

    public void incrementMineCount() {
        mineCountHint++;
        updateText();
    }

    public void incrementForBigBadPoo() {
        mineCountHint += 2;
        updateText();
    }

    public void uncover() {
        uncover(System.getProperty("user.name"));
    }

    public void uncoverWithoutUser() {
        uncover(null);
    }

    public void uncover(String actor) {
        if (isEditable()) {
            if (actor != null) {
                this.getModification().setModifiedBy(actor);
                this.setTooltip(new Tooltip("Modified by " + actor));
            }
            if (isHiddenMine()) {
                this.status = FieldStatus.REVEALED_MINE;
            } else if (isHiddenBigBadPoo()) {
                this.status = FieldStatus.REVEALED_BIG_BAD_POO;
            } else {
                this.status = FieldStatus.HINT;
            }
            this.setStyle(status.getStyle());
            this.updateText();
            this.setEditable(false);
        }
    }

    public void mark() {
        mark(System.getProperty("user.name"));
    }

    public void mark(String actor) {
        if (actor != null) {
            this.getModification().setModifiedBy(actor);
            this.setTooltip(new Tooltip("Modified by " + actor));
        }

        modification.toggleMarked();

        if(modification.isMarked()) {
            this.setStyle(colorTable.getColor(actor));
        } else {
            this.setStyle(status.getStyle());
        }
        this.updateText();
        this.toggleEditable();
    }

    public boolean isMarked() {
        return modification.isMarked();
    }

    public boolean isNotYetRevealed() {
        return (FieldStatus.COVERED == this.status) || isHiddenMine() || isHiddenBigBadPoo();
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

        if (modification.isMarked()) {
            this.setText(null);
            this.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream(FieldStatus.MARKED.getImageUrl()))));
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
        return isRevealedMine() || isHint() || isRevealdBigBadPoo();
    }

    public boolean isRevealedMine() {
        return this.status == FieldStatus.REVEALED_MINE;
    }

    public boolean isRevealdBigBadPoo() {
        return this.status == FieldStatus.REVEALED_BIG_BAD_POO;
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

    public String getGameId() {
        return gameId;
    }
}
