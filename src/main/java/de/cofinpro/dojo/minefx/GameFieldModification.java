package de.cofinpro.dojo.minefx;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by stephannaecker on 29.08.15.
 */
public class GameFieldModification implements Serializable {
    private LocalDateTime timestamp;
    private String modifiedBy;
    private boolean marked = false;

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        if (modifiedBy != null) {
            this.modifiedBy = modifiedBy;
        }
        this.timestamp = LocalDateTime.now();
    }

    public void toggleMarked() {
        marked = !marked;
    }

    public boolean isMarked() {
        return marked;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void mark(){
        this.marked = true;
    }
}
