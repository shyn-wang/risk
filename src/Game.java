import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    int turnCounter;
    ArrayList<Player> players;
    String phase;
    boolean live;

    Territory draftSelectedTerritory;
    Territory attackStartingTerritory;
    Territory attackAttackingTerritory;
    Territory fortifyStartingTerritory;
    Territory fortifyFortifyingTerritory;

    Territory atkWinner;
    int atkTroopsLost;
    int defTroopsLost;

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

    JPanel attackPhaseInfo;
    JLabel attackStatus;
    JLabel attackSelectedTerritories;
    JButton attackBtn;
    JPanel attackBtnContainer;
    JPanel endAttackBtnContainer;
    JButton endAttackBtn;

    JPanel fortifyPhaseInfo;
    JLabel fortifyStatus;
    JLabel fortifySelectedTerritories;
    JPanel moveTroopsPanel;
    JComboBox<String> moveTroops;
    JButton moveTroopsBtn;
    JPanel endTurnBtnContainer;
    JButton endTurnBtn;

    public Game(int turnCounter, ArrayList<Player> players, String phase, boolean live) {
        this.turnCounter = turnCounter;
        this.players = players;
        this.phase = phase;
        this.live = live;
        this.draftSelectedTerritory = null;
        this.attackStartingTerritory = null;
        this.attackAttackingTerritory = null;
        this.fortifyStartingTerritory = null;
        this.fortifyFortifyingTerritory = null;
        this.atkWinner = null;
        this.atkTroopsLost = 0;
        this.defTroopsLost = 0;

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
        this.draftPhaseInfo.setBounds(755, 0, 955, 100);
        draftPhaseInfo.setVisible(true);
        this.draftPhaseInfo.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 0, 0, Color.BLACK), // top line
                new EmptyBorder(0, -47, 0, 0) // padding
        ));

        this.availableTroops = new JLabel();
        this.availableTroops.setBounds(30, 22, 150, 20);
        this.availableTroops.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.draftPhaseInfo.add(availableTroops);

        this.draftStatus = new JLabel("select a territory");
        this.draftStatus.setBounds(250, 22, 170, 20);
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
        this.nextPhaseBtnContainer.setBounds(770, 12, 200, 100);
        this.nextPhaseBtnContainer.setVisible(false);
        this.draftPhaseInfo.add(nextPhaseBtnContainer);

        this.nextPhaseBtn = new JButton("next phase");
        this.nextPhaseBtnContainer.add(nextPhaseBtn);

        // create attack phase info panel
        this.attackPhaseInfo = new JPanel(null);
        this.attackPhaseInfo.setBounds(755, 0, 955, 100);
        this.attackPhaseInfo.setVisible(false);
        this.attackPhaseInfo.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 0, 0, Color.BLACK), // top line
                new EmptyBorder(22, -85, 0, 0) // padding
        ));

        this.attackStatus = new JLabel("select a territory");
        this.attackStatus.setBounds(30, 22, 200, 20);
        this.attackStatus.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.attackPhaseInfo.add(this.attackStatus);

        this.attackSelectedTerritories = new JLabel();
        this.attackSelectedTerritories.setBounds(250, 22, 270, 20);
        this.attackSelectedTerritories.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.attackPhaseInfo.add(this.attackSelectedTerritories);

        this.attackBtnContainer = new JPanel();
        this.attackBtnContainer.setOpaque(false);
        this.attackBtnContainer.setBounds(540, 12, 200, 100);
        this.attackBtnContainer.setVisible(false);
        this.attackPhaseInfo.add(attackBtnContainer);

        this.attackBtn = new JButton("attack");
        this.attackBtnContainer.add(attackBtn);

        this.endAttackBtnContainer = new JPanel();
        this.endAttackBtnContainer.setOpaque(false);
        this.endAttackBtnContainer.setBounds(770, 12, 200, 100);
        this.attackPhaseInfo.add(endAttackBtnContainer);

        this.endAttackBtn = new JButton("end attack");
        this.endAttackBtnContainer.add(endAttackBtn);

        // create fortify phase info panel
        this.fortifyPhaseInfo = new JPanel(null);
        this.fortifyPhaseInfo.setBounds(755, 0, 955, 100);
        this.fortifyPhaseInfo.setVisible(false);
        this.fortifyPhaseInfo.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 0, 0, Color.BLACK), // top line
                new EmptyBorder(22, -85, 0, 0) // padding
        ));

        this.fortifyStatus = new JLabel("select a territory");
        this.fortifyStatus.setBounds(30, 22, 200, 20);
        this.fortifyStatus.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.fortifyPhaseInfo.add(this.fortifyStatus);

        this.fortifySelectedTerritories = new JLabel();
        this.fortifySelectedTerritories.setBounds(262, 22, 250, 20);
        this.fortifySelectedTerritories.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.fortifyPhaseInfo.add(this.fortifySelectedTerritories);

        this.moveTroopsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        this.moveTroopsPanel.setOpaque(false);
        this.moveTroopsPanel.setBounds(550, 17, 200, 100);
        this.moveTroopsPanel.setVisible(false);
        this.fortifyPhaseInfo.add(moveTroopsPanel);

        this.moveTroops = new JComboBox<>();
        this.moveTroopsPanel.add(moveTroops);

        this.moveTroopsBtn = new JButton("move troops");
        this.moveTroopsPanel.add(moveTroopsBtn);

        this.endTurnBtnContainer = new JPanel();
        this.endTurnBtnContainer.setOpaque(false);
        this.endTurnBtnContainer.setBounds(770, 12, 200, 100);
        this.fortifyPhaseInfo.add(endTurnBtnContainer);

        this.endTurnBtn = new JButton("end turn");
        this.endTurnBtnContainer.add(endTurnBtn);

    }

    public JPanel initializeRoundInfoPanel() {
        roundInfo.setBackground(getTurn().colour);
        turnLabel.setText("turn: " + getTurn().name);
        phaseLabel.setText("phase: " + phase);

        return roundInfo;
    }

    public void updateRoundInfoPanel() {
        roundInfo.setBackground(getTurn().colour);
        turnLabel.setText("turn: " + getTurn().name);
        phaseLabel.setText("phase: " + phase);
    }

    public JPanel initializeDraftPhaseInfoPanel() {
        draftPhaseInfo.setBackground(getTurn().colour);
        availableTroops.setText("available troops: " + getTurn().undeployedTroops);

        for (int i = 1; i <= getTurn().undeployedTroops; i++) {
            addTroops.addItem(String.valueOf(i));
        }

        return draftPhaseInfo;
    }

    public void refreshDraftPhaseInfoPanel() {
        draftPhaseInfo.setBackground(getTurn().colour);
        availableTroops.setText("available troops: " + getTurn().undeployedTroops);

        draftStatus.setText("select a territory");
        nextPhaseBtnContainer.setVisible(false);

        for (int i = 1; i <= getTurn().undeployedTroops; i++) {
            addTroops.addItem(String.valueOf(i));
        }
    }

    public JPanel initializeAttackPhaseInfoPanel() {
        attackPhaseInfo.setBackground(getTurn().colour);

        return attackPhaseInfo;
    }

    public void refreshAttackPhaseInfoPanel() {
        attackPhaseInfo.setBackground(getTurn().colour);
    }

    public JPanel initializeFortifyPhaseInfoPanel() {
        fortifyPhaseInfo.setBackground(getTurn().colour);

        return fortifyPhaseInfo;
    }

    public void refreshFortifyPhaseInfoPanel() {
        fortifyPhaseInfo.setBackground(getTurn().colour);
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

    public void simulateBattle() {
        int atkTroops = attackStartingTerritory.troops;
        int defTroops = attackAttackingTerritory.troops;

        atkWinner = null;
        atkTroopsLost = 0;
        defTroopsLost = 0;

        ArrayList<Integer> atkDice = new ArrayList<>();
        ArrayList<Integer> defDice = new ArrayList<>();

        while (atkTroops >= 1) {
            atkDice.clear();
            defDice.clear();

            // check for winner
            if (atkTroops == 1) { // attacker loss
                atkWinner = attackAttackingTerritory;
                attackStartingTerritory.updateTroops(atkTroopsLost * -1);
                attackAttackingTerritory.updateTroops(defTroopsLost * -1);

                JFrame frame = new JFrame();

                // Create a custom JDialog
                JDialog dialog = new JDialog(frame, "attack failed", true);  // true makes it modal
                dialog.setResizable(false);
                dialog.setSize(300, 250);
                dialog.setLocationRelativeTo(frame);


                dialog.setLayout(new GridLayout(2, 1, 0, 15));

                JTextPane battleReport = new JTextPane();

                // Create a center-aligned style
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                StyledDocument doc = battleReport.getStyledDocument();
                doc.setParagraphAttributes(0, 0, center, false);

                battleReport.setSize(400, 175);
                battleReport.setText("\n" + attackStartingTerritory.name + " -> " + attackAttackingTerritory.name + "\n\nstarting troops: " + (attackStartingTerritory.troops + atkTroopsLost) + "\ntroops lost: " + atkTroopsLost + "\nremaining troops: " + attackStartingTerritory.troops);
                battleReport.setFont(new Font("Helvetica", Font.PLAIN, 15));
                battleReport.setOpaque(false);
                battleReport.setEditable(false);
                battleReport.setFocusable(false);

                JButton confirmButton = new JButton("confirm");

                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                confirmButton.addActionListener(e -> {
                    attackStartingTerritory.opacity = 1.0F;
                    attackAttackingTerritory.opacity = 1.0F;

                    attackStartingTerritory = null;
                    attackAttackingTerritory = null;

                    attackSelectedTerritories.setText(null);
                    attackBtnContainer.setVisible(false);
                    attackStatus.setText("select a territory");
                    endAttackBtnContainer.setVisible(true);

                    dialog.dispose();
                });

                JPanel panel = new JPanel();
                panel.add(confirmButton);

                dialog.add(battleReport);
                dialog.add(panel);

                dialog.setVisible(true);

                break;

            } else if (defTroops == 0) { // attacker win
                atkWinner = attackStartingTerritory;
                attackStartingTerritory.updateTroops(atkTroopsLost * -1);
                attackAttackingTerritory.updateTroops(defTroopsLost * -1);

                JFrame frame = new JFrame();

                JComboBox<String> moveTroopsSelector = new JComboBox<>();
                moveTroopsSelector.addItem("select number of troops to move");

                for (int i = 1; i <= attackStartingTerritory.troops - 1; i++) {
                    moveTroopsSelector.addItem(String.valueOf(i));
                }

                // Create a custom JDialog
                JDialog dialog = new JDialog(frame, "attack successful", true);  // true makes it modal
                dialog.setResizable(false);
                dialog.setSize(300, 250);
                dialog.setLocationRelativeTo(frame);


                dialog.setLayout(new GridLayout(2, 1, 0, 15));

                JTextPane battleReport = new JTextPane();

                // Create a center-aligned style
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                StyledDocument doc = battleReport.getStyledDocument();
                doc.setParagraphAttributes(0, 0, center, false);

                battleReport.setSize(400, 175);
                battleReport.setText("\n" + attackStartingTerritory.name + " -> " + attackAttackingTerritory.name + "\n\nstarting troops: " + (attackStartingTerritory.troops + atkTroopsLost) + "\ntroops lost: " + atkTroopsLost + "\nremaining troops: " + attackStartingTerritory.troops);
                battleReport.setFont(new Font("Helvetica", Font.PLAIN, 15));
                battleReport.setOpaque(false);
                battleReport.setEditable(false);
                battleReport.setFocusable(false);

                JButton confirmButton = new JButton("confirm");

                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                confirmButton.addActionListener(e -> {
                    if (!moveTroopsSelector.getSelectedItem().toString().equals("select number of troops to move")) {
                        int moveTroops = Integer.parseInt(moveTroopsSelector.getSelectedItem().toString());

                        attackStartingTerritory.parent.territories.add(attackAttackingTerritory);
                        attackStartingTerritory.parent.updateLabels();

                        attackAttackingTerritory.parent.territories.remove(attackAttackingTerritory);
                        attackAttackingTerritory.parent.updateLabels();

                        // check if captured territory parent is eliminated
                            //


                        attackStartingTerritory.opacity = 1.0F;
                        attackAttackingTerritory.opacity = 1.0F;
                        attackAttackingTerritory.parent = attackStartingTerritory.parent; // ********************** captured territory parent changes to attacking territory parent *************

                        attackStartingTerritory.updateTroops(moveTroops * -1);
                        attackAttackingTerritory.updateTroops(moveTroops);

                        attackStartingTerritory = null;
                        attackAttackingTerritory = null;

                        attackSelectedTerritories.setText(null);
                        attackBtnContainer.setVisible(false);
                        attackStatus.setText("select a territory");
                        endAttackBtnContainer.setVisible(true);

                        dialog.dispose();
                    }
                });

                JPanel panel = new JPanel();
                panel.add(moveTroopsSelector);
                panel.add(confirmButton);

                dialog.add(battleReport);
                dialog.add(panel);

                dialog.setVisible(true);

                break;
            }

            // roll dice for atk territory
            if (atkTroops > 3) {
                for (int i = 0; i < 3; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }

            } else if (atkTroops == 3) {
                for (int i = 0; i < 2; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }

            } else if (atkTroops == 2) {
                for (int i = 0; i < 1; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }

            }

            // roll dice for def territory
            if (defTroops >= 2) {
                for (int i = 0; i < 2; i++) {
                    defDice.add((int) ((Math.random() * 6)) + 1);
                }

            } else if (defTroops == 1) {
                for (int i = 0; i < 1; i++) {
                    defDice.add((int) ((Math.random() * 6)) + 1);
                }
            }

            // determine winner of round
            if (Collections.max(atkDice) > Collections.max(defDice)) {
                defTroops--;
                defTroopsLost++;
            } else {
                atkTroops--;
                atkTroopsLost++;
            }
        }
    }
}
