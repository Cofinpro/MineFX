package de.cofinpro.dojo.minefx;

import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;


/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class GameField extends ToggleButton {

    private boolean mine = false;
    private int mineCount = 0;

    public GameField() {
        super(" ");
        this.setOnAction(event -> {
            if (mine) {
                new Alert(Alert.AlertType.ERROR, "BANG!").show();
            } else {
                this.setText(String.valueOf(mineCount));
            }
        });
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void incrementMineCount() {
        mineCount++;
    }

    public void uncover() {
        if (isMine()) {
            this.setText("●");
        } else {
            this.setText(mineCount == 0 ? " " : String.valueOf(mineCount));
        }
    }
}
