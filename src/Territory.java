import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Territory {
    Path2D path2d;
    int troops;
    Player parent;
    String name;
    float opacity;
    Color colour;
    ArrayList<Territory> adjacentTerritories;
    String continent;
    JLabel troopsLabel;

    public Territory(String name, Path2D path2d, String continent) {
        this.name = name;
        this.parent = null;
        this.path2d = path2d;
        this.troops = 1;
        this.opacity = 1.0F;
        this.colour = null;
        this.adjacentTerritories = new ArrayList<>() {};
        this.continent = continent;

        this.troopsLabel = new JLabel(String.valueOf(troops));
        this.troopsLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
    }

    public void createLabel(JPanel displayPanel, int x, int y) {
        troopsLabel.setBounds(x, y, 100, 100);
        displayPanel.add(troopsLabel);
    }

    public void updateTroops(int change) {
        troops += change;
        troopsLabel.setText(String.valueOf(troops));
        parent.deployedTroops += change;
        parent.deployedtroopCounterLabel.setText("active troops: " + parent.deployedTroops);
    }

    public void highlightAdjEnemyTerritories() {
        for (Territory adjTerritory : adjacentTerritories) {
            if (adjTerritory.parent != parent) {
                adjTerritory.colour = adjTerritory.parent.highlightColour;
            }
        }
    }

    public void removeAdjEnemyTerritoryHighlights() {
        for (Territory adjTerritory : adjacentTerritories) {
            if (adjTerritory.parent != parent) {
                adjTerritory.colour = adjTerritory.parent.defaultColour;
            }
        }
    }
}
