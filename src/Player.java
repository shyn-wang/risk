import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Player {
    String name;
    Color defaultColour;
    Color highlightColour;
    ArrayList<Territory> territories;
    int deployedTroops;
    int undeployedTroops;
    JPanel statsPanel;
    JLabel nameLabel;
    JLabel deployedtroopCounterLabel;
    JLabel territoryCounterLabel;
    boolean inGame;

    public Player(String name, Color defaultColour, Color highlightColour) {
        this.name = name;
        this.defaultColour = defaultColour;
        this.highlightColour = highlightColour;
        this.territories = new ArrayList<>();
        this.deployedTroops = 0;
        this.undeployedTroops = 30;
        this.inGame = true;

        this.statsPanel = new JPanel();
        BoxLayout layout = new BoxLayout(this.statsPanel, BoxLayout.Y_AXIS);
        statsPanel.setLayout(layout);
        statsPanel.setBackground(defaultColour);
        this.statsPanel.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK), // top line
                new EmptyBorder(10, 10, 0, 0) // padding
        ));

        this.nameLabel = new JLabel(this.name);
        this.nameLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.statsPanel.add(nameLabel);

        this.deployedtroopCounterLabel = new JLabel();
        this.deployedtroopCounterLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));
        this.statsPanel.add(deployedtroopCounterLabel);

        this.territoryCounterLabel = new JLabel();
        this.territoryCounterLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));
        this.statsPanel.add(territoryCounterLabel);
    }


    public int getTotalTroops() {
        return deployedTroops + undeployedTroops;
    }

    public int getTotalTerritories() {
        return territories.size();
    }

    public JPanel initializePanel(int x, int y) {
        deployedtroopCounterLabel.setText("active troops: " + this.deployedTroops);
        territoryCounterLabel.setText("territories: " + getTotalTerritories());
        statsPanel.setBounds(x, y, 110, 100);
        return statsPanel;
    }

    public void updateLabels() {
        deployedtroopCounterLabel.setText("active troops: " + deployedTroops);
        territoryCounterLabel.setText("territories: " + getTotalTerritories());
    }

    public void highlightAllTerritories() {
        for (Territory territory : territories) {
            territory.colour = highlightColour;
        }
    }

    public void resetTerritoryColours() {
        for (Territory territory : territories) {
            territory.colour = defaultColour;
        }
    }

    public void highlightAtkEligibleTerritories() {
        for (Territory territory : territories) {
            for (Territory adjTerritory : territory.adjacentTerritories) {
                if (adjTerritory.parent != territory.parent && territory.troops > 1) {
                    territory.colour = territory.parent.highlightColour;
                    break; // only one valid adjacent territory is required at minimum
                }
            }
        }
    }

    public void highlightFortifyEligibleStartingTerritories() {
        for (Territory territory : territories) {
            for (Territory adjTerritory : territory.adjacentTerritories) {
                if (adjTerritory.parent == this && territory.troops > 1) {
                    territory.colour = highlightColour;
                    break; // only one valid adjacent territory is required at minimum
                }
            }
        }
    }
}
