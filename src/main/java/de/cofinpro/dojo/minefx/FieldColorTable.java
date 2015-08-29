package de.cofinpro.dojo.minefx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stephannaecker on 29.08.15.
 */
public class FieldColorTable {

    private FieldColor[] colors = new FieldColor [] { FieldColor.LIGHT_BLUE, FieldColor.LIGHT_CORAL, FieldColor.LIGHT_GREEN, FieldColor.LIGHT_PINK, FieldColor.LIGHT_SALMON, FieldColor.LIGHT_SKYBLUE, FieldColor.LIGHT_STEELBLUE, FieldColor.LIGHT_YELLOQ, FieldColor.LIGHT_CYAN };
    private Map<String, FieldColor> mapping = new HashMap<>();
    private int colorsOccupied = 0;

    public String getColor(String forUser) {
        if (mapping.containsKey(forUser)) {
            return mapping.get(forUser).getStyle();
        } else {
            if (colorsOccupied >= colors.length) {
                return FieldColor.LIGHT_BLUE.getStyle();
            } else {
                colors[colorsOccupied].setUser(forUser);
                mapping.put(forUser, colors[colorsOccupied]);
                return colors[colorsOccupied++].getStyle();
            }
        }
    }

    public void refresh() {
        Arrays.stream(colors).forEach(FieldColor::refresh);
        mapping.clear();
        colorsOccupied = 0;
    }
}
