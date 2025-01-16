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

    /*
    description: displays label corresponding to # of troops on screen
    pre-condition: called by Main during game initialization; requires a JPanel on which the label is created and two int values corresponding to the desired x and y coordinates of the label
    post-condition: sets the position of the label and adds it to the target JPanel
    */
    public void createLabel(JPanel displayPanel, int x, int y) {
        troopsLabel.setBounds(x, y, 100, 100);
        displayPanel.add(troopsLabel);
    }

    /*
    description: updates troop count of territory + parent and associated labels
    pre-condition: called by Game and Main; requires +/- int value change corresponding to the desired +/- troop change
    post-condition: updates troop counts and labels of the territory and parent player
    */
    public void updateTroops(int change) {
        troops += change;
        troopsLabel.setText(String.valueOf(troops));
        parent.deployedTroops += change;
        parent.deployedtroopCounterLabel.setText("active troops: " + parent.deployedTroops);
    }

    /*
    description: highlights all adjacent enemy territories
    pre-condition: called by Main during attack phase
    post-condition: sets colour of each adj territory with a different parent to the highlight colour of their respective parents
    */
    public void highlightAdjEnemyTerritories() {
        for (Territory adjTerritory : adjacentTerritories) {
            if (adjTerritory.parent != parent) {
                adjTerritory.colour = adjTerritory.parent.highlightColour;
            }
        }
    }

    /*
    description: removes all adjacent enemy territory highlights
    pre-condition: called by Main and Game during attack phase
    post-condition: sets colour of each adj territory with a different parent to the default colour of their respective parents
    */
    public void removeAdjEnemyTerritoryHighlights() {
        for (Territory adjTerritory : adjacentTerritories) {
            if (adjTerritory.parent != parent) {
                adjTerritory.colour = adjTerritory.parent.defaultColour;
            }
        }
    }
}
