package de.cofinpro.dojo.minefx;

import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;


/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class GameField extends ToggleButton {

    private boolean mine = false;
    private boolean marked = false;
    private boolean covered = true;

    private int mineCount = 0;

    public GameField() {
        super(" ");
        this.setOnMouseClicked(event -> {
            if ("PRIMARY".equals(event.getButton().name())) {
                if (marked) {
                    new Alert(Alert.AlertType.WARNING, "Oops.").show();
                } else {
                    if (mine) {
                        new Alert(Alert.AlertType.ERROR, "BANG!").show();
                    } else {
                        if (covered) {
                            this.uncover();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Oops.").show();
                        }
                    }
                }

            } else {
                if (covered) {
                    this.mark();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Oops.").show();
                }
            }
        });
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
        this.updateText();
    }

    public void incrementMineCount() {
        mineCount++;
    }

    public void uncover() {
        this.covered = false;
        this.updateText();
        this.setDisabled(true);
    }

    public void mark() {
        this.marked = ! this.marked;
        this.updateText();
        this.toggleEnabled();
    }

    private void toggleEnabled() {
        this.setDisabled(! this.isDisabled());
    }

    private void updateText() {
        if (marked) {
            this.setText("X");
        } else {
            if (covered) {
                this.setText(" ");
            } else {
                if (mine) {
                    this.setText("●");
                } else {
                    this.setText(mineCount == 0 ? " " : String.valueOf(mineCount));
                }
            }
        }
    }
}
