package de.cofinpro.dojo.minefx;

/**
 * Created by stephannaecker on 29.08.15.
 */
public class FieldColor {

    private static final String STYLE_PATTERN = "-fx-base: %s; -fx-focus-color: transparent;";

    public static final FieldColor LIGHT_BLUE = new FieldColor("lightblue");
    public static final FieldColor LIGHT_CORAL = new FieldColor("lightcoral");
    public static final FieldColor LIGHT_CYAN = new FieldColor("lightcyan");
    public static final FieldColor LIGHT_GREEN = new FieldColor("lightgreen");
    public static final FieldColor LIGHT_PINK = new FieldColor("lightpink");
    public static final FieldColor LIGHT_SALMON = new FieldColor("lightsalmon");
    public static final FieldColor LIGHT_YELLOQ = new FieldColor("lightyellow");
    public static final FieldColor LIGHT_SKYBLUE = new FieldColor("lightskyblue");
    public static final FieldColor LIGHT_STEELBLUE = new FieldColor("lightsteelblue");

    private String color;
    private String user;

    public FieldColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void refresh() {
        this.user = null;
    }

    public String getStyle() {
        return String.format(STYLE_PATTERN, this.color);
    }
}
