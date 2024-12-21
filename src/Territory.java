import java.awt.*;
import java.awt.geom.Path2D;

public class Territory {
    Path2D path2d;
    int troops;
    Color colour;
    Player parent;
    String name;
    float opacity;
    Territory[] adjacentTerritories;

    public Territory(String name, Player parent, Path2D path2d, int troops, Color colour, float opacity, Territory[] adjacentTerritories) {
        this.name = name;
        this.parent = parent;
        this.path2d = path2d;
        this.troops = troops;
        this.colour = colour;
        this.opacity = opacity;
        this.adjacentTerritories = adjacentTerritories;
    }
}
