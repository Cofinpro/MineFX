package de.cofinpro.dojo.minefx.model;

import javafx.beans.property.*;

/**
 * Created by ABorger on 29.08.2015.
 */
public class ConfigFx {

    private final IntegerProperty columns;

    private final IntegerProperty rows;

    private final IntegerProperty poos;

    private final BooleanProperty doMegaShit;

    public  ConfigFx(Integer columns, Integer rows, Integer poos, Boolean doMegaShit){
        this.columns = new SimpleIntegerProperty(columns);
        this.rows = new SimpleIntegerProperty(rows);
        this.poos = new SimpleIntegerProperty(poos);
        this.doMegaShit = new SimpleBooleanProperty(doMegaShit);
    }

    public int getColumns() {
        return columns.get();
    }

    public IntegerProperty columnsProperty() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns.set(columns);
    }

    public boolean getDoMegaShit() {
        return doMegaShit.get();
    }

    public BooleanProperty doMegaShitProperty() {
        return doMegaShit;
    }

    public void setDoMegaShit(boolean doMegaShit) {
        this.doMegaShit.set(doMegaShit);
    }

    public int getPoos() {
        return poos.get();
    }

    public IntegerProperty poosProperty() {
        return poos;
    }

    public void setPoos(int poos) {
        this.poos.set(poos);
    }

    public int getRows() {
        return rows.get();
    }

    public IntegerProperty rowsProperty() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows.set(rows);
    }




}
