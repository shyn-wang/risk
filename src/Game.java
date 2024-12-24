import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Game {
    int turnCounter;
    ArrayList<Player> players;
    String phase;
    boolean live;
    JPanel roundInfo;
    JLabel turnLabel;
    JLabel phaseLabel;
    JPanel draftPhaseInfo;
    JLabel availableTroops;
    JLabel draftStatus;
    JPanel addTroopsPanel;
    JComboBox<String> addTroops;
    JButton addTroopsBtn;
    JPanel nextPhaseBtnContainer;
    JButton nextPhaseBtn;

    public Game(int turnCounter, ArrayList<Player> players, String phase, boolean live) {
        this.turnCounter = turnCounter;
        this.players = players;
        this.phase = phase;
        this.live = live;

        // create round info panel
        this.roundInfo = new JPanel(null);
        this.roundInfo.setBounds(440, 0, 315, 100);
        this.roundInfo.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 0, 0, Color.BLACK), // top line
                new EmptyBorder(0, -85, 0, 0) // padding
        ));

        this.turnLabel = new JLabel();
        this.turnLabel.setBounds(20, 22, 100, 20);
        this.turnLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.roundInfo.add(turnLabel);

        this.phaseLabel = new JLabel();
        this.phaseLabel.setBounds(160, 22, 100, 20);
        this.phaseLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.roundInfo.add(phaseLabel);

        // create draft phase info panel
        this.draftPhaseInfo = new JPanel(null);
        draftPhaseInfo.setVisible(true);
        this.draftPhaseInfo.setBounds(755, 0, 955, 100);
        this.draftPhaseInfo.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 0, 0, Color.BLACK), // top line
                new EmptyBorder(0, -47, 0, 0) // padding
        ));

        this.availableTroops = new JLabel();
        this.availableTroops.setBounds(50, 22, 150, 20);
        this.availableTroops.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.draftPhaseInfo.add(availableTroops);

        this.draftStatus = new JLabel("select a territory");
        this.draftStatus.setBounds(270, 22, 150, 20);
        this.draftStatus.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.draftPhaseInfo.add(draftStatus);

        this.addTroopsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        this.addTroopsPanel.setOpaque(false);
        this.addTroopsPanel.setBounds(495, 17, 200, 100);
        this.addTroopsPanel.setVisible(false);
        this.draftPhaseInfo.add(addTroopsPanel);

        this.addTroops = new JComboBox<>();
        this.addTroopsPanel.add(addTroops);

        this.addTroopsBtn = new JButton("add troops");
        this.addTroopsPanel.add(addTroopsBtn);

        this.nextPhaseBtnContainer = new JPanel();
        this.nextPhaseBtnContainer.setOpaque(false);
        this.nextPhaseBtnContainer.setBounds(730, 12, 200, 100);
        this.nextPhaseBtnContainer.setVisible(false);
        this.draftPhaseInfo.add(nextPhaseBtnContainer);

        this.nextPhaseBtn = new JButton("next phase");
        this.nextPhaseBtnContainer.add(nextPhaseBtn);
    }

    public JPanel initializeRoundInfoPanel() {
        roundInfo.setBackground(getTurn().colour);
        turnLabel.setText("turn: " + getTurn().name);
        phaseLabel.setText("phase: " + phase);

        return roundInfo;
    }

    public JPanel initializeDraftPhaseInfoPanel() {
        draftPhaseInfo.setBackground(getTurn().colour);
        availableTroops.setText("available troops: " + getTurn().undeployedTroops);

        for (int i = 1; i <= getTurn().undeployedTroops; i++) {
            addTroops.addItem(String.valueOf(i));
        }

        return draftPhaseInfo;
    }

    public Player getTurn() {
        if (turnCounter == 1) {
            if (players.get(0).inGame) {
                return players.get(0);

            } else {
                turnCounter++;
                getTurn();
            }

        } else if (turnCounter == 2) {
            if (players.get(1).inGame) {
                return players.get(1);

            } else {
                turnCounter++;
                getTurn();
            }
        }

        return null;
    }
}
