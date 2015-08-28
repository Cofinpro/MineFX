package de.cofinpro.dojo.minefx;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public enum FieldStatus {
    COVERED(null, "", "-fx-base: lightgray; -fx-focus-color: transparent;"),
    HINT(null, "", "-fx-base: lightgreen; -fx-focus-color: transparent;"),
    REVEALED_MINE("poo.png", "*", "-fx-base: red; -fx-focus-color: transparent;"),
    MARKED("skull.png", "X", "-fx-base: lightblue; -fx-focus-color: transparent;");

    private String symbol;
    private String imageUrl;
    private String style;

    FieldStatus(String imageUrl, String x, String style) {
        this.symbol = x;
        this.style = style;
        this.imageUrl = imageUrl;
    }

    public String getSymbol() {
            return symbol;
    }

    public String getStyle() { return style; }

    public String getImageUrl() { return imageUrl; }
}
