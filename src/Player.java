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
    JLabel statsPanelNameLabel;
    JLabel deployedtroopCounterLabel;
    JLabel territoryCounterLabel;
    boolean inGame;

    JPanel setNamePanel;
    JLabel setNamePanelNameLabel;
    JTextField setName;
    JButton setNameBtn;

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

        this.statsPanelNameLabel = new JLabel(this.name);
        this.statsPanelNameLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.statsPanel.add(this.statsPanelNameLabel);

        this.deployedtroopCounterLabel = new JLabel();
        this.deployedtroopCounterLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));
        this.statsPanel.add(this.deployedtroopCounterLabel);

        this.territoryCounterLabel = new JLabel();
        this.territoryCounterLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));
        this.statsPanel.add(this.territoryCounterLabel);

        this.setNamePanel = new JPanel();
        this.setNamePanel.setLayout(new GridLayout(3, 1, 0, 5));

        this.setNamePanelNameLabel = new JLabel(this.name, JLabel.CENTER);
        this.setNamePanelNameLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.setNamePanel.add(this.setNamePanelNameLabel);

        this.setName = new JTextField("", 10);
        this.setNamePanel.add(this.setName);

        this.setNameBtn = new JButton("set name");
        this.setNamePanel.add(this.setNameBtn);
    }


    public int getTotalTroops() {
        return deployedTroops + undeployedTroops;
    }

    public int getTotalTerritories() {
        return territories.size();
    }

    public JPanel initializeStatsPanel(int x, int y) {
        statsPanelNameLabel.setText(name);
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
