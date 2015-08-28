package de.cofinpro.dojo.minefx;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public enum FieldStatus {
    COVERED("", "-fx-base: lightgray;"),
    HINT("", "-fx-base: lightgreen;"),
    MINE("*", "-fx-base: red;"),
    MARKED("X", "-fx-base: lightblue;");

    private String symbol;

    private String style;

    FieldStatus(String x, String style) {
        this.symbol = x;
        this.style = style;
    }

    public String getSymbol() {
            return symbol;
    }

    public String getStyle() { return style; }
}
