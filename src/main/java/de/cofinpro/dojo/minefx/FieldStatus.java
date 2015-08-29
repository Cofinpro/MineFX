package de.cofinpro.dojo.minefx;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public enum FieldStatus {
    COVERED(null, "-fx-focus-color: transparent;"),
    HIDDEN_MINE(null, "-fx-focus-color: transparent;"),
    HINT(null, "-fx-base: white; -fx-focus-color: transparent;"),
    REVEALED_MINE("poo.png", "-fx-base: red; -fx-focus-color: transparent;"),
    MARKED("skull.png", "-fx-base: lightblue; -fx-focus-color: transparent;"),
    REVEALED_BIG_BAD_POO("BigBadPoo.png", "-fx-base: red; -fx-focus-color: transparent;"),
    HIDDEN_BIG_BAD_POO(null, "-fx-focus-color: transparent;");

    private String imageUrl;
    private String style;

    FieldStatus(String imageUrl, String style) {
        this.style = style;
        this.imageUrl = imageUrl;
    }

    public String getStyle() { return style; }

    public String getImageUrl() { return imageUrl; }
}
