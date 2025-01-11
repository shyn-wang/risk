import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Game {
    int turnCounter;
    ArrayList<Player> players;
    String phase;
    int round;

    Territory draftSelectedTerritory;
    Territory attackStartingTerritory;
    Territory attackAttackingTerritory;
    Territory fortifyStartingTerritory;
    Territory fortifyFortifyingTerritory;

    Territory atkWinner;
    int atkTroopsLost;
    int defTroopsLost;

    ArrayList<Territory> fortifyValidTerritories;

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

    public Game() {
        this.turnCounter = 1;
        this.players = new ArrayList<Player>();
        this.phase = "draft";
        this.draftSelectedTerritory = null;
        this.attackStartingTerritory = null;
        this.attackAttackingTerritory = null;
        this.fortifyStartingTerritory = null;
        this.fortifyFortifyingTerritory = null;
        this.atkWinner = null;
        this.atkTroopsLost = 0;
        this.defTroopsLost = 0;
        this.round = 1;
        this.fortifyValidTerritories = null;

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
        this.draftStatus.setBounds(250, 22, 190, 20);
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
        this.attackStatus.setBounds(30, 22, 220, 20);
        this.attackStatus.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.attackPhaseInfo.add(this.attackStatus);

        this.attackSelectedTerritories = new JLabel();
        this.attackSelectedTerritories.setBounds(240, 22, 300, 20);
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
        roundInfo.setBackground(getTurn().defaultColour);
        turnLabel.setText("turn: " + getTurn().name);
        phaseLabel.setText("phase: " + phase);

        return roundInfo;
    }

    public void updateRoundInfoPanel() {
        roundInfo.setBackground(getTurn().defaultColour);
        turnLabel.setText("turn: " + getTurn().name);
        phaseLabel.setText("phase: " + phase);
    }

    public JPanel initializeDraftPhaseInfoPanel() {
        draftPhaseInfo.setBackground(getTurn().defaultColour);
        availableTroops.setText("available troops: " + getTurn().undeployedTroops);

        for (int i = 1; i <= getTurn().undeployedTroops; i++) {
            addTroops.addItem(String.valueOf(i));
        }

        return draftPhaseInfo;
    }

    public void refreshDraftPhaseInfoPanel() {
        draftPhaseInfo.setBackground(getTurn().defaultColour);

        draftStatus.setText("select a territory");
        nextPhaseBtnContainer.setVisible(false);

        // create added troops popup
        if (round > 1) {
            int baseTroopsGained = (getTurn().getTotalTerritories() / 3);

            if (baseTroopsGained < 3) { // minimum 3 troops will always be rewarded
                baseTroopsGained = 3;
            }

            int continentBonuses = 0;

            // check if all territories in each continent are owned by the player
            if (Main.northAmerica.stream().allMatch(territory -> territory.parent == getTurn())) {
                continentBonuses += 5; // 5 troops for north america
            }

            if (Main.southAmerica.stream().allMatch(territory -> territory.parent == getTurn())) {
                continentBonuses += 2; // 2 troops for south america
            }

            if (Main.europe.stream().allMatch(territory -> territory.parent == getTurn())) {
                continentBonuses += 5; // 5 troops for europe
            }

            if (Main.africa.stream().allMatch(territory -> territory.parent == getTurn())) {
                continentBonuses += 3; // 3 troops for africa
            }

            if (Main.asia.stream().allMatch(territory -> territory.parent == getTurn())) {
                continentBonuses += 7; // 7 troops for asia
            }

            if (Main.oceania.stream().allMatch(territory -> territory.parent == getTurn())) {
                continentBonuses += 2; // 2 troops for oceania
            }

            int totalTroopsGained = baseTroopsGained + continentBonuses;

            JFrame frame = new JFrame();

            JDialog dialog = new JDialog(frame, "troops gained", true); // modal = no other parts of the application can be accessed until the popup is responded to
            dialog.setResizable(false);
            dialog.setSize(300, 250);
            dialog.setLocationRelativeTo(frame);


            dialog.setLayout(new GridLayout(2, 1, 0, 15));

            JTextPane playerReport = new JTextPane();

            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            StyledDocument doc = playerReport.getStyledDocument();
            doc.setParagraphAttributes(0, 0, center, false);

            playerReport.setSize(400, 175);
            playerReport.setText("\n" + getTurn().name + " report\n\n" + getTurn().getTotalTerritories() + " territories occupied: " + baseTroopsGained + " troops\ncontinent bonuses: " + continentBonuses + " troops\ntotal troops gained: " + totalTroopsGained + " troops");
            playerReport.setFont(new Font("Helvetica", Font.PLAIN, 15));
            playerReport.setOpaque(false);
            playerReport.setEditable(false);
            playerReport.setFocusable(false);

            JButton confirmButton = new JButton("confirm");

            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

            confirmButton.addActionListener(e -> {
                getTurn().undeployedTroops += totalTroopsGained;
                dialog.dispose();
            });

            JPanel panel = new JPanel();
            panel.add(confirmButton);

            dialog.add(playerReport);
            dialog.add(panel);

            dialog.setVisible(true);
        }

        availableTroops.setText("available troops: " + getTurn().undeployedTroops);

        for (int i = 1; i <= getTurn().undeployedTroops; i++) {
            addTroops.addItem(String.valueOf(i));
        }
    }

    public JPanel initializeAttackPhaseInfoPanel() {
        attackPhaseInfo.setBackground(getTurn().defaultColour);

        return attackPhaseInfo;
    }

    public void refreshAttackPhaseInfoPanel() {
        attackPhaseInfo.setBackground(getTurn().defaultColour);
    }

    public JPanel initializeFortifyPhaseInfoPanel() {
        fortifyPhaseInfo.setBackground(getTurn().defaultColour);

        return fortifyPhaseInfo;
    }

    public void refreshFortifyPhaseInfoPanel() {
        fortifyPhaseInfo.setBackground(getTurn().defaultColour);
    }

    public Player getTurn() {
        while (true) {
            if (turnCounter == 1) {
                if (players.get(0).inGame) {
                    return players.get(0);
                } else {
                    turnCounter++;
                }
            } else if (turnCounter == 2) {
                if (players.get(1).inGame) {
                    return players.get(1);
                } else {
                    turnCounter++;
                }
            } else if (turnCounter == 3) {
                if (players.get(2).inGame) {
                    return players.get(2);
                } else {
                    turnCounter++;
                }
            } else if (turnCounter == 4) {
                if (players.get(3).inGame) {
                    return players.get(3);
                } else {
                    turnCounter++;
                }
            } else  {
                turnCounter = 1;
                round++;
            }
        }
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

                // create battle report popup gui
                JFrame frame = new JFrame();

                JDialog dialog = new JDialog(frame, "attack failed", true); // modal = no other parts of the application can be accessed until the popup is responded to
                dialog.setResizable(false);
                dialog.setSize(300, 250);
                dialog.setLocationRelativeTo(frame);


                dialog.setLayout(new GridLayout(2, 1, 0, 15));

                JTextPane battleReport = new JTextPane();

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

                    // next three calls must be performed for starting territory parent; unlike during a win, the parent of the attacking territory never changes to that of the starting territory
                    attackStartingTerritory.removeAdjEnemyTerritoryHighlights();

                    attackStartingTerritory.parent.resetTerritoryColours();
                    attackStartingTerritory.parent.highlightAtkEligibleTerritories(); // must be called after troop counters are updated

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

                // create battle report popup gui
                JFrame frame = new JFrame();

                JComboBox<String> moveTroopsSelector = new JComboBox<>();
                moveTroopsSelector.addItem("select number of troops to move");

                for (int i = 1; i <= attackStartingTerritory.troops - 1; i++) {
                    moveTroopsSelector.addItem(String.valueOf(i));
                }

                JDialog dialog = new JDialog(frame, "attack successful", true); // modal = no other parts of the application can be accessed until the popup is responded to
                dialog.setResizable(false);
                dialog.setSize(300, 250);
                dialog.setLocationRelativeTo(frame);


                dialog.setLayout(new GridLayout(2, 1, 0, 15));

                JTextPane battleReport = new JTextPane();

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
                        if (attackAttackingTerritory.parent.territories.isEmpty()) {
                            attackAttackingTerritory.parent.inGame = false;
                            attackAttackingTerritory.parent.deployedtroopCounterLabel.setText("eliminated");
                            attackAttackingTerritory.parent.territoryCounterLabel.setText("");
                        }

                        attackStartingTerritory.opacity = 1.0F;
                        attackAttackingTerritory.opacity = 1.0F;
                        attackAttackingTerritory.parent = attackStartingTerritory.parent; // ********************** captured territory parent changes to attacking territory parent *************

                        attackStartingTerritory.updateTroops(moveTroops * -1);
                        attackAttackingTerritory.updateTroops(moveTroops);

                        attackStartingTerritory.removeAdjEnemyTerritoryHighlights();

                        attackAttackingTerritory.parent.resetTerritoryColours();
                        attackAttackingTerritory.parent.highlightAtkEligibleTerritories(); // must be called after troop counters are updated

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

            // roll dice for atk territory; max 4 dice rolled if troops > 4
            if (atkTroops > 4) {
                for (int i = 0; i < 4; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }

            } else if (atkTroops == 4) {
                for (int i = 0; i < 4; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }

            } else if (atkTroops == 3) {
                for (int i = 0; i < 3; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }

            }  else if (atkTroops == 2) {
                for (int i = 0; i < 2; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }
            }

            // roll dice for def territory; max 2 dice rolled
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
            if (Collections.max(atkDice) > Collections.max(defDice)) { // highest dice roll on atk side is compared to highest dice roll on def side; winner = highest roll, dice are continuously rerolled and compared until either side loses all troops
                defTroops--;
                defTroopsLost++;
            } else {
                atkTroops--;
                atkTroopsLost++;
            }
        }
    }

    public void checkForWinner() {
        // check if only one player is in game = current player wins
        if (players.stream().filter(player -> player.inGame).count() == 1) {

            JFrame frame = new JFrame();

            JDialog dialog = new JDialog(frame, "game over", true); // modal = no other parts of the application can be accessed until the popup is responded to
            dialog.setResizable(false);
            dialog.setSize(300, 140);
            dialog.setLocationRelativeTo(frame);


            dialog.setLayout(new GridLayout(1, 1, 0, 15));

            JTextPane gameReport = new JTextPane();

            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            StyledDocument doc = gameReport.getStyledDocument();
            doc.setParagraphAttributes(0, 0, center, false);

            gameReport.setSize(400, 175);
            gameReport.setText("\n" + getTurn().name + " wins " + "\n\ntotal territories: " + getTurn().getTotalTerritories() + "\ntotal troops: " + getTurn().getTotalTroops());
            gameReport.setFont(new Font("Helvetica", Font.PLAIN, 15));
            gameReport.setOpaque(false);
            gameReport.setEditable(false);
            gameReport.setFocusable(false);

            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

            dialog.add(gameReport);

            dialog.setVisible(true);
        }
    }

    public void findAllTerritoriesThatCanBeFortified() {
        ArrayList<Territory> uncheckedAdjTerritories = new ArrayList<>(fortifyStartingTerritory.adjacentTerritories); // ************** arraylists are reference types; uncheckedAdjTerritories must be given a copy, otherwise changes made to it will also affect the original
        ArrayList<Territory> stagingArea = new ArrayList<>();
        ArrayList<Territory> checkedTerritories = new ArrayList<>();
        HashSet<Territory> duplicateRemover = new HashSet<>();

        searchAlgorithm: // algorithm determines if click is on a territory that is connected to the starting territory either directly or through other territories owned by the player; troops can only be moved to territories that have a path to the starting territory that is comprised of player owned territories
        while (true) {
            stagingArea.clear();

            // loop through all adjacent territories (initially for the starting territory's adjacent territories, then for the adjacent territories of the adjacent territories, etc.)
            for (int i = 0; i < uncheckedAdjTerritories.size(); i++) {
                if (uncheckedAdjTerritories.get(i).parent.equals(fortifyStartingTerritory.parent)) { // checks for player owned territories
                    stagingArea.add(uncheckedAdjTerritories.get(i));
                    checkedTerritories.add(uncheckedAdjTerritories.get(i));
                }
            }

            if (!stagingArea.isEmpty()) { // not empty = more possible paths to the clicked territory that are unchecked
                uncheckedAdjTerritories.clear();
                duplicateRemover.clear();

                // find all adjacent territories of territories in staging area and add to a hashset (does not allow duplicates to be added; territories may share the same adjacent territories)
                for (int i = 0; i < stagingArea.size(); i++) {
                    for (int j = 0; j < stagingArea.get(i).adjacentTerritories.size(); j++) {
                        if (!checkedTerritories.contains(stagingArea.get(i).adjacentTerritories.get(j))) { // prevents infinite loop; territories that adjacent territories branch off from are not re-added
                            duplicateRemover.add(stagingArea.get(i).adjacentTerritories.get(j)); // prevents duplicates; territories may have the same adjacent territories
                        }
                    }
                }

                uncheckedAdjTerritories.addAll(duplicateRemover);

            } else { // no more paths exist
                fortifyValidTerritories = checkedTerritories;
                break searchAlgorithm;
            }
        }
    }
}