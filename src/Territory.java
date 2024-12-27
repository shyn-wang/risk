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
    ArrayList<Territory> adjacentTerritories;
    String continent;
    JLabel troopsLabel;

    public Territory(String name, Player parent, Path2D path2d, int troops, float opacity, ArrayList<Territory> adjacentTerritories, String continent) {
        this.name = name;
        this.parent = parent;
        this.path2d = path2d;
        this.troops = troops;
        this.opacity = opacity;
        this.adjacentTerritories = adjacentTerritories;
        this.continent = continent;

        this.troopsLabel = new JLabel(String.valueOf(troops));
        this.troopsLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
    }

    public Color getColour() {
        return parent.colour;
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
}
