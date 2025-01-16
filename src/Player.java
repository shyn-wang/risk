import javax.swing.*;
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

    int bonusTroopsProbability = 5;


    public Player(String name, Color defaultColour, Color highlightColour) {
        this.name = name;
        this.defaultColour = defaultColour;
        this.highlightColour = highlightColour;
        this.territories = new ArrayList<>();
        this.deployedTroops = 0;
        this.undeployedTroops = 30;
        this.inGame = true;

        this.statsPanel = new JPanel(null); // create stats panel containing player info
        this.statsPanel.setBackground(defaultColour);
        this.statsPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK)); // top line

        this.statsPanelNameLabel = new JLabel(this.name); // displays player name
        this.statsPanelNameLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.statsPanelNameLabel.setBounds(10, 12, 90, 17);
        this.statsPanel.add(this.statsPanelNameLabel);

        this.deployedtroopCounterLabel = new JLabel(); // displays # of deployed troops
        this.deployedtroopCounterLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));
        this.deployedtroopCounterLabel.setBounds(9, 29, 100, 12);
        this.statsPanel.add(this.deployedtroopCounterLabel);

        this.territoryCounterLabel = new JLabel(); // displays # of owned territories
        this.territoryCounterLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));
        this.territoryCounterLabel.setBounds(10, 41, 90, 12);
        this.statsPanel.add(this.territoryCounterLabel);

        // create panel for setting name
        this.setNamePanel = new JPanel();
        this.setNamePanel.setLayout(new GridLayout(3, 1, 0, 5));

        this.setNamePanelNameLabel = new JLabel(this.name, JLabel.CENTER); // displays chosen name
        this.setNamePanelNameLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.setNamePanel.add(this.setNamePanelNameLabel);

        this.setName = new JTextField("", 10); // enables chosen name to be entered
        this.setNamePanel.add(this.setName);

        this.setNameBtn = new JButton("set name"); // confirms chosen name
        this.setNamePanel.add(this.setNameBtn);
    }

    /*
    description: calculates the total (undeployed and deployed) troops owned
    pre-condition: called by checkForWinner() in Game
    post-condition: returns int value corresponding to total troops to checkForWinner()
    */
    public int getTotalTroops() {
        return deployedTroops + undeployedTroops;
    }

    /*
    description: calculates total # of territories owned
    pre-condition: called when the total territory count of a player is required by Player and Game
    post-condition: returns int value corresponding to the size of the territories arraylist to caller in Player or Game
    */
    public int getTotalTerritories() {
        return territories.size();
    }

    /*
    description: sets position of and initializes stats panel
    pre-condition: called by Main during game initialization; requires two int values corresponding to the desired x and y coordinates of the panel
    post-condition: returns initialized statsPanel to Main
    */
    public JPanel initializeStatsPanel(int x, int y) {
        statsPanelNameLabel.setText(name);
        deployedtroopCounterLabel.setText("active troops: " + this.deployedTroops);
        territoryCounterLabel.setText("territories: " + getTotalTerritories());
        statsPanel.setBounds(x, y, 110, 100);
        return statsPanel;
    }

    /*
    description: updates stat panel labels
    pre-condition: called by simulateBattle() in Game
    post-condition: updates values of labels based on current info
    */
    public void updateLabels() {
        deployedtroopCounterLabel.setText("active troops: " + deployedTroops);
        territoryCounterLabel.setText("territories: " + getTotalTerritories());
    }

    /*
    description: highlights all owned territories
    pre-condition: called by endTurnBtn
    post-condition: sets the colour of each owned territory to the player's highlight colour
    */
    public void highlightAllTerritories() {
        for (Territory territory : territories) {
            territory.colour = highlightColour;
        }
    }

    /*
    description: resets colour of all owned territories
    pre-condition: called by Game and Main
    post-condition: sets the colour of each owned territory to the player's default colour
    */
    public void resetTerritoryColours() {
        for (Territory territory : territories) {
            territory.colour = defaultColour;
        }
    }

    /*
    description: highlights all territories eligible for launching an attack
    pre-condition: called by Game and Main during attack phase
    post-condition: sets the colour of each owned territory with at least 1 adjacent territory owned by a different player and > 1 troops to the player's highlight colour
    */
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

    /*
    description: highlights all territories eligible for fortifying another territory
    pre-condition: called by Main during fortify phase
    post-condition: sets the colour of each owned territory with at least 1 adjacent territory owned by the same player and > 1 troops to the player's highlight colour
    */
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
