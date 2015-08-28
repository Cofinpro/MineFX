package de.cofinpro.dojo.minefx;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public enum FieldStatus {
    COVERED("", "-fx-base: lightgray; -fx-focus-color: transparent;"),
    HINT("", "-fx-base: lightgreen; -fx-focus-color: transparent;"),
    REVEALED_MINE("*", "-fx-base: red; -fx-focus-color: transparent;"),
    MARKED("X", "-fx-base: lightblue; -fx-focus-color: transparent;");

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
