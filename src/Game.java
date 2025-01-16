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
    Territory atkWinner;
    int atkTroopsLost;
    int defTroopsLost;

    Territory fortifyStartingTerritory;
    Territory fortifyFortifyingTerritory;
    ArrayList<Territory> fortifyValidTerritories;

    // info panel
    JPanel roundInfo;
    JLabel turnLabel;
    JLabel phaseLabel;

    // draft phase info panel
    JPanel draftPhaseInfo;
    JLabel availableTroops;
    JLabel draftStatus;
    JPanel addTroopsPanel;
    JComboBox<String> addTroops;
    JButton addTroopsBtn;
    JPanel nextPhaseBtnContainer;
    JButton nextPhaseBtn;

    // attack phase info panel
    JPanel attackPhaseInfo;
    JLabel attackStatus;
    JLabel attackSelectedTerritories;
    JButton attackBtn;
    JPanel attackBtnContainer;
    JPanel endAttackBtnContainer;
    JButton endAttackBtn;

    // fortify phase info panel
    JPanel fortifyPhaseInfo;
    JLabel fortifyStatus;
    JLabel fortifySelectedTerritories;
    JPanel moveTroopsPanel;
    JComboBox<String> moveTroops;
    JButton moveTroopsBtn;
    JPanel endTurnBtnContainer;
    JButton endTurnBtn;

    public Game() {
        this.turnCounter = 1; // game will always start on player 1
        this.players = new ArrayList<>();
        this.phase = "draft"; // game will always start on draft phase
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

        this.turnLabel = new JLabel(); // displays current player
        this.turnLabel.setBounds(20, 22, 100, 20);
        this.turnLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.roundInfo.add(turnLabel);

        this.phaseLabel = new JLabel(); // displays current phase
        this.phaseLabel.setBounds(160, 22, 100, 20);
        this.phaseLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.roundInfo.add(phaseLabel);

        // create draft phase info panel
        this.draftPhaseInfo = new JPanel(null); // contains info required for draft phase
        this.draftPhaseInfo.setBounds(755, 0, 955, 100);
        draftPhaseInfo.setVisible(true);
        this.draftPhaseInfo.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 0, 0, Color.BLACK), // top line
                new EmptyBorder(0, -47, 0, 0) // padding
        ));

        this.availableTroops = new JLabel(); // displays # of troops that can be deployed
        this.availableTroops.setBounds(30, 22, 150, 20);
        this.availableTroops.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.draftPhaseInfo.add(availableTroops);

        this.draftStatus = new JLabel("select a territory"); // displays selected territory
        this.draftStatus.setBounds(220, 22, 190, 20);
        this.draftStatus.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.draftPhaseInfo.add(draftStatus);

        this.addTroopsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0)); // contains troop # combobox + deploy btn
        this.addTroopsPanel.setOpaque(false);
        this.addTroopsPanel.setBounds(465, 17, 200, 100);
        this.addTroopsPanel.setVisible(false);
        this.draftPhaseInfo.add(addTroopsPanel);

        this.addTroops = new JComboBox<>();
        this.addTroopsPanel.add(addTroops);

        this.addTroopsBtn = new JButton("add troops");
        this.addTroopsPanel.add(addTroopsBtn);

        this.nextPhaseBtnContainer = new JPanel(); // contains nextPhaseBtn; switches to atk phase
        this.nextPhaseBtnContainer.setOpaque(false);
        this.nextPhaseBtnContainer.setBounds(730, 12, 200, 100);
        this.nextPhaseBtnContainer.setVisible(false);
        this.draftPhaseInfo.add(nextPhaseBtnContainer);

        this.nextPhaseBtn = new JButton("next phase");
        this.nextPhaseBtnContainer.add(nextPhaseBtn);

        // create attack phase info panel
        this.attackPhaseInfo = new JPanel(null); // contains info required for attack phase
        this.attackPhaseInfo.setBounds(755, 0, 955, 100);
        this.attackPhaseInfo.setVisible(false);
        this.attackPhaseInfo.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 0, 0, Color.BLACK), // top line
                new EmptyBorder(22, -85, 0, 0) // padding
        ));

        this.attackStatus = new JLabel("select a territory"); // displays 'select a territory to attack' once one territory is selected, then displays the owners of the two territories once the second is selected
        this.attackStatus.setBounds(30, 22, 220, 20);
        this.attackStatus.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.attackPhaseInfo.add(this.attackStatus);

        this.attackSelectedTerritories = new JLabel(); // displays the two territories and their player counts once both selected
        this.attackSelectedTerritories.setBounds(220, 22, 300, 20);
        this.attackSelectedTerritories.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.attackPhaseInfo.add(this.attackSelectedTerritories);

        this.attackBtnContainer = new JPanel(); // contains attack btn
        this.attackBtnContainer.setOpaque(false);
        this.attackBtnContainer.setBounds(520, 12, 200, 100);
        this.attackBtnContainer.setVisible(false);
        this.attackPhaseInfo.add(attackBtnContainer);

        this.attackBtn = new JButton("attack");
        this.attackBtnContainer.add(attackBtn);

        this.endAttackBtnContainer = new JPanel(); // contains end attack btn
        this.endAttackBtnContainer.setOpaque(false);
        this.endAttackBtnContainer.setBounds(730, 12, 200, 100);
        this.attackPhaseInfo.add(endAttackBtnContainer);

        this.endAttackBtn = new JButton("end attack");
        this.endAttackBtnContainer.add(endAttackBtn);

        // create fortify phase info panel
        this.fortifyPhaseInfo = new JPanel(null); // contains info required for fortify phase
        this.fortifyPhaseInfo.setBounds(755, 0, 955, 100);
        this.fortifyPhaseInfo.setVisible(false);
        this.fortifyPhaseInfo.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 0, 0, Color.BLACK), // top line
                new EmptyBorder(22, -85, 0, 0) // padding
        ));

        this.fortifyStatus = new JLabel("select a territory"); // displays 'select a territory to fortify' once starting territory is selected, displays 'fortify in progress' when ending territory is selected, displays 'fortify complete' once troops have been moved
        this.fortifyStatus.setBounds(30, 22, 200, 20);
        this.fortifyStatus.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.fortifyPhaseInfo.add(this.fortifyStatus);

        this.fortifySelectedTerritories = new JLabel(); // displays starting and ending territories once selected
        this.fortifySelectedTerritories.setBounds(242, 22, 250, 20);
        this.fortifySelectedTerritories.setFont(new Font("Helvetica", Font.BOLD, 15));
        this.fortifyPhaseInfo.add(this.fortifySelectedTerritories);

        this.moveTroopsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0)); // contains moveTroops combobox & moveTroopsBtn
        this.moveTroopsPanel.setOpaque(false);
        this.moveTroopsPanel.setBounds(530, 17, 200, 100);
        this.moveTroopsPanel.setVisible(false);
        this.fortifyPhaseInfo.add(moveTroopsPanel);

        this.moveTroops = new JComboBox<>();
        this.moveTroopsPanel.add(moveTroops);

        this.moveTroopsBtn = new JButton("move troops");
        this.moveTroopsPanel.add(moveTroopsBtn);

        this.endTurnBtnContainer = new JPanel(); // contains end turn btn
        this.endTurnBtnContainer.setOpaque(false);
        this.endTurnBtnContainer.setBounds(740, 12, 200, 100);
        this.fortifyPhaseInfo.add(endTurnBtnContainer);

        this.endTurnBtn = new JButton("end turn");
        this.endTurnBtnContainer.add(endTurnBtn);

    }

    /*
    description: initializes round info panel to display current player and phase
    pre-condition: called by Main during game initialization
    post-condition: returns roundInfo panel to Main
    */
    public JPanel initializeRoundInfoPanel() {
        roundInfo.setBackground(getTurn().defaultColour);
        turnLabel.setText("turn: " + getTurn().name);
        phaseLabel.setText("phase: " + phase);

        return roundInfo;
    }

    /*
    description: updates round info panel to display current player and phase
    pre-condition: called by nextPhaseBtn, endAttackBtn, and endTurnBtn
    post-condition: updates labels and background colour
    */
    public void updateRoundInfoPanel() {
        roundInfo.setBackground(getTurn().defaultColour);
        turnLabel.setText("turn: " + getTurn().name);
        phaseLabel.setText("phase: " + phase);
    }

    /*
    description: initializes draft info panel information
    pre-condition: called by Main during game initialization
    post-condition: returns draftPhaseInfo panel to Main
    */
    public JPanel initializeDraftPhaseInfoPanel() {
        draftPhaseInfo.setBackground(getTurn().defaultColour);
        availableTroops.setText("available troops: " + getTurn().undeployedTroops);

        for (int i = 1; i <= getTurn().undeployedTroops; i++) {
            addTroops.addItem(String.valueOf(i));
        }

        return draftPhaseInfo;
    }

    /*
    description: updates draft info panel and calculates + displays gained troops after each round
    pre-condition: called by endTurnBtn
    post-condition: updates troop counts and player indicator
    */
    public void refreshDraftPhaseInfoPanel() {
        draftPhaseInfo.setBackground(getTurn().defaultColour);

        draftStatus.setText("select a territory");
        nextPhaseBtnContainer.setVisible(false);

        // create added troops popup starting after first round
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

            // roll for random bonus troops starting from round 3
            int randomBonusTroops = 0;

            if (round >= 3) {
                if ((int) (Math.random() * getTurn().bonusTroopsProbability + 1) == 1) {
                    randomBonusTroops += (int) (Math.random() * (15 - 7 + 1) + 7); // generates random troop count from 7-15
                    getTurn().bonusTroopsProbability = 5; // reset probability back to 5 = 1 in 5 = 20%
                }
            }

            int totalTroopsGained = baseTroopsGained + continentBonuses + randomBonusTroops;

            JFrame frame = new JFrame();

            JDialog dialog = new JDialog(frame, "troops gained", true); // modal = no other parts of the application can be accessed until the popup is responded to
            dialog.setResizable(false);
            dialog.setSize(300, 295);
            dialog.setLocationRelativeTo(frame);

            dialog.setLayout(new GridLayout(2, 1, 0, 15));

            JTextPane playerReport = new JTextPane();

            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            StyledDocument doc = playerReport.getStyledDocument();
            doc.setParagraphAttributes(0, 0, center, false);

            playerReport.setSize(400, 200);
            playerReport.setText("\n" + getTurn().name + " report\n\n" + getTurn().getTotalTerritories() + " territories occupied: " + baseTroopsGained + " troops\ncontinent bonuses: " + continentBonuses + " troops\nrandom bonus troops: " + randomBonusTroops + " troops\ntotal troops gained: " + totalTroopsGained + " troops");
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

    /*
    description: initializes attack phase info panel
    pre-condition: called by Main during game initialization
    post-condition: returns attackPhaseInfo panel to Main
    */
    public JPanel initializeAttackPhaseInfoPanel() {
        attackPhaseInfo.setBackground(getTurn().defaultColour);

        return attackPhaseInfo;
    }

    /*
    description: updates attack phase info panel
    pre-condition: called by endTurnBtn
    post-condition: updates background colour to correspond to current player
    */
    public void refreshAttackPhaseInfoPanel() {
        attackPhaseInfo.setBackground(getTurn().defaultColour);
    }

    /*
    description: initializes fortify phase info panel
    pre-condition: called by Main during game initialization
    post-condition: returns fortifyPhaseInfo panel to Main
    */
    public JPanel initializeFortifyPhaseInfoPanel() {
        fortifyPhaseInfo.setBackground(getTurn().defaultColour);

        return fortifyPhaseInfo;
    }

    /*
    description: updates fortify phase info panel
    pre-condition: called by endTurnBtn
    post-condition: updates background colour to correspond to current player
    */
    public void refreshFortifyPhaseInfoPanel() {
        fortifyPhaseInfo.setBackground(getTurn().defaultColour);
    }

    /*
    description: determines current active player based on turnCounter and which players have or have not been eliminated
    pre-condition: called by Game and Main when the current active player is required
    post-condition: returns current active player to caller in Game or Main
    */
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
            } else  { // resets turnCounter to 1 (player 1) if > 4 (incremented when active player is player 4)
                turnCounter = 1;
                round++; // new round is started once all players have had their turns
            }
        }
    }

    /*
    description: determines the winning and losing territory during an attack, and the # of troops each side loses
    pre-condition: called by attackBtn
    post-condition: updates winning and losing territory properties + ownership, displays results in popup gui
    */
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

            // roll dice for atk territory; max 4 dice rolled if troops > 4
            if (atkTroops > 4) {
                for (int i = 0; i < 4; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }

            } else if (atkTroops == 4) { // 3 dice rolled if troops == 4
                for (int i = 0; i < 4; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }

            } else if (atkTroops == 3) { // 2 dice rolled if troops == 3
                for (int i = 0; i < 3; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }

            }  else if (atkTroops == 2) { // 1 dice rolled if troops == 2
                for (int i = 0; i < 2; i++) {
                    atkDice.add((int) ((Math.random() * 6)) + 1);
                }
            }

            // roll dice for def territory; max 2 dice rolled when troops >= 2
            if (defTroops >= 2) {
                for (int i = 0; i < 2; i++) {
                    defDice.add((int) ((Math.random() * 6)) + 1);
                }

            } else if (defTroops == 1) { // 1 dice rolled when troops == 1
                for (int i = 0; i < 1; i++) {
                    defDice.add((int) ((Math.random() * 6)) + 1);
                }
            }

            // determine winner of round
            if (Collections.max(atkDice) > Collections.max(defDice)) { // highest dice roll on atk side is compared to highest dice roll on def side; winner = highest roll, dice are continuously rerolled and compared until either side no longer has enough troops to continue
                defTroops--;
                defTroopsLost++;
            } else {
                atkTroops--;
                atkTroopsLost++;
            }

            // check for winner
            if (atkTroops == 1) { // attacker loss
                atkWinner = attackAttackingTerritory; // def territory wins
                attackStartingTerritory.updateTroops(atkTroopsLost * -1); // update territory troop counts
                attackAttackingTerritory.updateTroops(defTroopsLost * -1);

                // create battle report popup gui
                JFrame frame = new JFrame();

                JDialog dialog = new JDialog(frame, "attack failed", true); // modal = no other parts of the application can be accessed until the popup is responded to
                dialog.setResizable(false);
                dialog.setSize(300, 290);
                dialog.setLocationRelativeTo(frame);


                dialog.setLayout(new GridLayout(2, 1, 0, 15));

                JTextPane battleReport = new JTextPane();

                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                StyledDocument doc = battleReport.getStyledDocument();
                doc.setParagraphAttributes(0, 0, center, false);

                battleReport.setSize(400, 220);
                battleReport.setText("\n" + attackStartingTerritory.name + " -> " + attackAttackingTerritory.name + "\n\nstarting troops: " + (attackStartingTerritory.troops + atkTroopsLost) + "\ntroops lost: " + atkTroopsLost + "\nremaining troops: " + attackStartingTerritory.troops);
                battleReport.setFont(new Font("Helvetica", Font.PLAIN, 15));
                battleReport.setOpaque(false);
                battleReport.setEditable(false);
                battleReport.setFocusable(false);

                JButton confirmButton = new JButton("confirm");

                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                confirmButton.addActionListener(e -> { // reset attacking and defending territory properties
                    attackStartingTerritory.opacity = 1.0F; // remove click indicators
                    attackAttackingTerritory.opacity = 1.0F;

                    attackStartingTerritory.removeAdjEnemyTerritoryHighlights(); // remove highlights of other enemy territories that were eligible for attack from the starting territory

                    attackStartingTerritory.parent.resetTerritoryColours(); // removes highlight from starting territory since troops = 1 = ineligible for launching attacks
                    attackStartingTerritory.parent.highlightAtkEligibleTerritories(); // highlight updated territories that are eligible for launching attacks

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
                // increase probability for bonus troops; occurs in each round past round 3 where a player successfully captures an enemy territory
                if (round >= 3 ) {
                    getTurn().bonusTroopsProbability--; // each decrement increases probability exponentially; 5 (base) = 20%, 4 = 25%, 3 = 33%, 2 = 50%, 1 = 100%
                }

                atkWinner = attackStartingTerritory; // atk territory wins
                attackStartingTerritory.updateTroops(atkTroopsLost * -1); // update territory troop counts
                attackAttackingTerritory.updateTroops(defTroopsLost * -1);

                // create battle report popup gui
                JFrame frame = new JFrame();

                JComboBox<String> moveTroopsSelector = new JComboBox<>();
                moveTroopsSelector.addItem("select number of troops to move");

                for (int i = 1; i <= attackStartingTerritory.troops - 1; i++) { // at least 1 troop must remain in starting territory
                    moveTroopsSelector.addItem(String.valueOf(i));
                }

                JDialog dialog = new JDialog(frame, "attack successful", true); // modal = no other parts of the application can be accessed until the popup is responded to
                dialog.setResizable(false);
                dialog.setSize(300, 290);
                dialog.setLocationRelativeTo(frame);


                dialog.setLayout(new GridLayout(2, 1, 0, 15));

                JTextPane battleReport = new JTextPane();

                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                StyledDocument doc = battleReport.getStyledDocument();
                doc.setParagraphAttributes(0, 0, center, false);

                battleReport.setSize(400, 220);
                battleReport.setText("\n" + attackStartingTerritory.name + " -> " + attackAttackingTerritory.name + "\n\nstarting troops: " + (attackStartingTerritory.troops + atkTroopsLost) + "\ntroops lost: " + atkTroopsLost + "\nremaining troops: " + attackStartingTerritory.troops);
                battleReport.setFont(new Font("Helvetica", Font.PLAIN, 15));
                battleReport.setOpaque(false);
                battleReport.setEditable(false);
                battleReport.setFocusable(false);

                JButton confirmButton = new JButton("confirm");

                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                confirmButton.addActionListener(e -> { // resets selected territories & updates troop counts + player ownership
                    if (!moveTroopsSelector.getSelectedItem().toString().equals("select number of troops to move")) {
                        int moveTroops = Integer.parseInt(moveTroopsSelector.getSelectedItem().toString());

                        attackStartingTerritory.parent.territories.add(attackAttackingTerritory); // add captured territory to winner's territory arraylist
                        attackStartingTerritory.parent.updateLabels();

                        attackAttackingTerritory.parent.territories.remove(attackAttackingTerritory); // remove captured territory from loser's territory arraylist
                        attackAttackingTerritory.parent.updateLabels();

                        // check if captured territory parent is eliminated (owns 0 territories)
                        if (attackAttackingTerritory.parent.territories.isEmpty()) {
                            attackAttackingTerritory.parent.inGame = false;
                            attackAttackingTerritory.parent.deployedtroopCounterLabel.setText("eliminated");
                            attackAttackingTerritory.parent.territoryCounterLabel.setText("");
                        }

                        attackStartingTerritory.opacity = 1.0F; // remove click indicators
                        attackAttackingTerritory.opacity = 1.0F;
                        attackAttackingTerritory.parent = attackStartingTerritory.parent; // captured territory parent changes to attacking territory parent

                        attackStartingTerritory.updateTroops(moveTroops * -1); // update troop counts of starting and captured territory once troops are moved
                        attackAttackingTerritory.updateTroops(moveTroops);

                        attackStartingTerritory.removeAdjEnemyTerritoryHighlights(); // removes highlights from other adjacent territories that were eligible for attack from the starting territory

                        attackAttackingTerritory.parent.resetTerritoryColours(); // removes highlight effect from starting territory; required in cases where all troops except 1 are moved to the captured territory or starting territory has no more enemy adjacent territories = no longer eligible for attacks
                        attackAttackingTerritory.parent.highlightAtkEligibleTerritories();

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
        }
    }

    /*
    description: scans for winner by checking if only one active player is left in the game = game over
    pre-condition: called by attackBtn
    post-condition: creates popup gui displaying winner if game is over
    */
    public void checkForWinner() {
        // check if only one player is in game = current player wins
        if (players.stream().filter(player -> player.inGame).count() == 1) {

            JFrame frame = new JFrame();

            JDialog dialog = new JDialog(frame, "game over", true); // modal = no other parts of the application can be accessed until the popup is responded to
            dialog.setResizable(false);
            dialog.setSize(300, 230);
            dialog.setLocationRelativeTo(frame);

            dialog.setLayout(new GridLayout(1, 1, 0, 15));

            JTextPane gameReport = new JTextPane();

            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            StyledDocument doc = gameReport.getStyledDocument();
            doc.setParagraphAttributes(0, 0, center, false);

            gameReport.setSize(250, 205);
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

    /*
    description: continuously loops through the owned adjacent territories of each owned adjacent territory starting from the starting territory until all player owned territories connected to the starting territory are found
    pre-condition: called by Main when a starting territory is selected during the fortify phase
    post-condition: sets fortifyValidTerritories to an arraylist containing all connected territories from a given starting territory owned by the same player
    */
    public void findAllTerritoriesThatCanBeFortified() {
        ArrayList<Territory> uncheckedAdjTerritories = new ArrayList<>(fortifyStartingTerritory.adjacentTerritories); // set to starting territory's adjacent territories; updated to only contain territories that have not been checked
        ArrayList<Territory> stagingArea = new ArrayList<>(); // stores valid territories from uncheckedAdjTerritories before adding the unchecked adjacent territories (compared against territories in checkedTerritories) of each territory to duplicateRemover
        ArrayList<Territory> checkedTerritories = new ArrayList<>(); // stores territories that have already been checked and cannot be readded to unchecked territories; checked territories cannot be added by stagingArea back into uncheckedTerritories
        HashSet<Territory> duplicateRemover = new HashSet<>(); // stores single copies of each unchecked adj territory of all territories within stagingArea before adding them into uncheckedAdjTerritories

        searchAlgorithm: // algorithm determines if click is on a territory that is connected to the starting territory either directly or through other territories owned by the player; troops can only be moved to territories that have a path to the starting territory that is comprised of player owned territories
        while (true) {
            stagingArea.clear();

            // loop through all adjacent territories (initially for the starting territory's adjacent territories, then for the adjacent territories of the adjacent territories, etc.)
            for (int i = 0; i < uncheckedAdjTerritories.size(); i++) {
                if (uncheckedAdjTerritories.get(i).parent.equals(fortifyStartingTerritory.parent)) { // checks for player owned territories
                    stagingArea.add(uncheckedAdjTerritories.get(i)); // adds all valid territories to stagingArea arraylist; represents all territories that may have unchecked adjacent territories
                    checkedTerritories.add(uncheckedAdjTerritories.get(i));
                }
            }

            if (!stagingArea.isEmpty()) { // not empty = more possible paths from the clicked territory that are unchecked
                uncheckedAdjTerritories.clear();
                duplicateRemover.clear();

                // find all unchecked adjacent territories of territories in staging area and add to a hashset (does not allow duplicates to be added; territories may share the same adjacent territories)
                for (int i = 0; i < stagingArea.size(); i++) {
                    for (int j = 0; j < stagingArea.get(i).adjacentTerritories.size(); j++) {
                        if (!checkedTerritories.contains(stagingArea.get(i).adjacentTerritories.get(j))) { // prevents infinite loop; territories that adjacent territories branch off from are not re-added
                            duplicateRemover.add(stagingArea.get(i).adjacentTerritories.get(j)); // prevents duplicates; territories may have the same adjacent territories
                        }
                    }
                }

                uncheckedAdjTerritories.addAll(duplicateRemover);

            } else { // no more paths exist = all paths found
                fortifyValidTerritories = checkedTerritories;
                break searchAlgorithm;
            }
        }
    }
}