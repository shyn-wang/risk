import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Territory {
    Path2D path2d;
    int troops;
    Player parent;
    String name;
    float opacity;
    ArrayList<Territory> adjacentTerritories;

    public Territory(String name, Player parent, Path2D path2d, int troops, float opacity, ArrayList<Territory> adjacentTerritories) {
        this.name = name;
        this.parent = parent;
        this.path2d = path2d;
        this.troops = troops;
        this.opacity = opacity;
        this.adjacentTerritories = adjacentTerritories;
    }

    public Color getColour() {
        return parent.colour;
    }
}
