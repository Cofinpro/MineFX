package de.cofinpro.dojo.minefx;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public enum FieldStatus {
    COVERED(""),
    HINT(""),
    MINE("*"),
    MARKED("X");

    private String symbol;


    FieldStatus(String x) {
        this.symbol = x;
    }

    public String getSymbol() {
            return symbol;
    }


}
