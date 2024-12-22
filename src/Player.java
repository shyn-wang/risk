import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Player {
    String name;
    Color colour;
    ArrayList<Territory> territories;
    int deployedTroops;
    int undeployedTroops;
    JPanel statsPanel;
    JLabel nameLabel;
    JLabel deployedtroopCounterLabel;
    JLabel territoryCounterLabel;
    boolean inGame;

    public Player(String name, Color colour, ArrayList<Territory> territories, int deployedTroops, int undeployedTroops, boolean inGame) {
        this.name = name;
        this.colour = colour;
        this.territories = territories;
        this.deployedTroops = deployedTroops;
        this.undeployedTroops = undeployedTroops;
        this.inGame = inGame;

        this.statsPanel = new JPanel();
        BoxLayout layout = new BoxLayout(this.statsPanel, BoxLayout.Y_AXIS);
        statsPanel.setLayout(layout);
        statsPanel.setBackground(colour);
        this.statsPanel.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK), // top line
                new EmptyBorder(10, 10, 0, 0) // padding
        ));

        this.nameLabel = new JLabel(name);
        this.nameLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        statsPanel.add(nameLabel);

        this.deployedtroopCounterLabel = new JLabel("active troops: " + deployedTroops);
        this.deployedtroopCounterLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));
        statsPanel.add(deployedtroopCounterLabel);

        this.territoryCounterLabel = new JLabel("territories: " + getTotalTerritories());
        this.territoryCounterLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));
        statsPanel.add(territoryCounterLabel);
    }


    public int getTotalTroops() {
        return deployedTroops + undeployedTroops;
    }

    public int getTotalTerritories() {
        return territories.size();
    }

    public void createPanel(JPanel displayPanel, int x, int y) {
        statsPanel.setBounds(x, y, 110, 100);
        displayPanel.add(statsPanel);
    }

    public void updateLabels() {
        deployedtroopCounterLabel.setText("active troops: " + deployedTroops);
        deployedtroopCounterLabel.setText("territories: " + getTotalTerritories());
    }
}
