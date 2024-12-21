import java.awt.*;
import java.util.ArrayList;

public class Player {
    String name;
    Color colour;
    ArrayList<Territory> territories;
    int deployedTroops;
    int undeployedTroops;

    public Player(String name, Color colour, ArrayList<Territory> territories, int deployedTroops, int undeployedTroops) {
        this.name = name;
        this.colour = colour;
        this.territories = territories;
        this.deployedTroops = deployedTroops;
        this.undeployedTroops = undeployedTroops;
    }

    public int getTotalTroops() {
        return deployedTroops + undeployedTroops;
    }
}
