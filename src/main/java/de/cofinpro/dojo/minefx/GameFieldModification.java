package de.cofinpro.dojo.minefx;

import java.time.LocalDateTime;

/**
 * Created by stephannaecker on 29.08.15.
 */
public class GameFieldModification {
    private LocalDateTime timestamp;
    private String modifiedBy;

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        if (modifiedBy != null) {
            this.modifiedBy = modifiedBy;
        }
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
