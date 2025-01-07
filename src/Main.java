import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends JFrame {
    // create game object
    Game game = new Game(new ArrayList<>() {}, "draft", true);

    // create player objects
    Player player1 = new Player("player 1", Color.decode("#04D4F0"), Color.decode("#04ECF0"), new ArrayList<>() {}, 0, 30, true);
    Player player2 = new Player("player 2", Color.decode("#FF0BAC"), Color.decode("#FA53A0"), new ArrayList<>() {}, 0, 30, true);

    // create territory objects

    // north america
    Territory nwt = new Territory("nwt", Util.getPath2d("nwt"), "north america");
    Territory alberta = new Territory("alberta", Util.getPath2d("alberta"), "north america");
    Territory alaska = new Territory("alaska", Util.getPath2d("alaska"), "north america");
    Territory ontario = new Territory("ontario", Util.getPath2d("ontario"), "north america");
    Territory westernUS = new Territory("western u.s.", Util.getPath2d("westernUS"), "north america");
    Territory mexico = new Territory("mexico", Util.getPath2d("mexico"), "north america");
    Territory easternUS = new Territory("eastern u.s.", Util.getPath2d("easternUS"), "north america");
    Territory easternCanada = new Territory("eastern canada", Util.getPath2d("easternCanada"), "north america");
    Territory greenland = new Territory("greenland", Util.getPath2d("greenland"), "north america");

    // south america
    Territory venezuela = new Territory("venezuela", Util.getPath2d("venezuela"), "south america");
    Territory peru = new Territory("peru", Util.getPath2d("peru"), "south america");
    Territory argentina = new Territory("argentina", Util.getPath2d("argentina"), "south america");
    Territory brazil = new Territory("brazil", Util.getPath2d("brazil"), "south america");

    // stores all territories
    ArrayList<Territory> allTerritories = new ArrayList<>();

    // create continent arraylists
    static ArrayList<Territory> northAmerica = new ArrayList<>();
    static ArrayList<Territory> southAmerica = new ArrayList<>();
    static ArrayList<Territory> europe = new ArrayList<>();
    static ArrayList<Territory> africa = new ArrayList<>();
    static ArrayList<Territory> asia = new ArrayList<>();
    static ArrayList<Territory> oceania = new ArrayList<>();

    public Main() throws Exception {
        // create main panel
        JPanel contentPane = new JPanel(null);

        // add players to game
        Collections.addAll(game.players, player1, player2);

        // set adjacent territories for each territory

        // north america
        Collections.addAll(nwt.adjacentTerritories, alaska, ontario, alberta, greenland);
        Collections.addAll(alberta.adjacentTerritories, alaska, ontario, nwt, westernUS);
        Collections.addAll(alaska.adjacentTerritories, alberta, nwt); // **add kamchatka once implemented**
        Collections.addAll(ontario.adjacentTerritories, alberta, nwt, easternCanada, easternUS, westernUS, greenland);
        Collections.addAll(westernUS.adjacentTerritories, alberta, ontario, mexico, easternUS);
        Collections.addAll(mexico.adjacentTerritories, westernUS, easternUS, venezuela);
        Collections.addAll(easternUS.adjacentTerritories, easternCanada, ontario, westernUS, mexico);
        Collections.addAll(easternCanada.adjacentTerritories, ontario, easternUS, greenland);
        Collections.addAll(greenland.adjacentTerritories, ontario, easternCanada, nwt); //

        // south america
        Collections.addAll(venezuela.adjacentTerritories, mexico, peru, brazil);
        Collections.addAll(peru.adjacentTerritories, venezuela, argentina, brazil);
        Collections.addAll(argentina.adjacentTerritories, peru, brazil);
        Collections.addAll(brazil.adjacentTerritories, venezuela, peru, argentina); //

        // south america

        // add territories to allTerritories arraylist

        // north america
        allTerritories.add(nwt);
        allTerritories.add(alberta);
        allTerritories.add(alaska);
        allTerritories.add(ontario);
        allTerritories.add(westernUS);
        allTerritories.add(mexico);
        allTerritories.add(easternUS);
        allTerritories.add(easternCanada);
        allTerritories.add(greenland);

        // south america
        allTerritories.add(venezuela);
        allTerritories.add(peru);
        allTerritories.add(argentina);
        allTerritories.add(brazil);

        // randomly assign territories to each player
        Collections.shuffle(allTerritories);

        // ********************************** add players 3 & 4 once created ************************************

        for (Territory territory : allTerritories) {
            if (player1.territories.size() < allTerritories.size() / game.players.size()) { // evenly distribute territories
                territory.parent = player1;
                player1.territories.add(territory);
                territory.colour = player1.defaultColour;

                player1.deployedTroops++;
                player1.undeployedTroops--;

            } else if (player2.territories.size() < allTerritories.size() / game.players.size()) {
                territory.parent = player2;
                player2.territories.add(territory);
                territory.colour = player2.defaultColour;

                player2.deployedTroops++;
                player2.undeployedTroops--;

            } else { // leftover territories present; randomly assigned to players
                int leftOverAssignment = (int) (Math.random() * game.players.size()) + 1;

                if (leftOverAssignment == 1) {
                    territory.parent = player1;
                    player1.territories.add(territory);
                    territory.colour = player1.defaultColour;

                    player1.deployedTroops++;
                    player1.undeployedTroops--;

                } else if (leftOverAssignment == 2) {
                    territory.parent = player2;
                    player2.territories.add(territory);
                    territory.colour = player2.defaultColour;

                    player2.deployedTroops++;
                    player2.undeployedTroops--;
                }
            }
        }

        // deploy troops to each player's territories
        for (Player player : game.players) {
            deployTroops:
            while (player.undeployedTroops > 3) {
                for (Territory territory : player.territories) {
                    int roll = (int) (Math.random() * 6) + 1;

                    if (roll == 1) {
                        territory.updateTroops(1);
                        player.undeployedTroops--;
                    }

                    if (player.undeployedTroops == 3) {
                        break deployTroops; // ends loop if troops is reduced to 3 before all territories are iterated through; prevents bug where troops is reduced to < 3 inside of the loop (while loop condition is not checked for until for loop ends)
                    }
                }
            }
        }

        // add territories belonging to each continent to their respective arraylist
        for (Territory territory : allTerritories) {
            switch (territory.continent) {
                case "north america":
                    northAmerica.add(territory);
                    break;
                case "south america":
                    southAmerica.add(territory);
                    break;
                case "europe":
                    europe.add(territory);
                    break;
                case "africa":
                    africa.add(territory);
                    break;
                case "asia":
                    asia.add(territory);
                    break;
                case "oceania":
                    oceania.add(territory);
                    break;
            }
        }

        // create panel for world map
        JPanel mapPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) { // called whenever gui is created/refreshed
                super.paintComponent(g);

                // render path2D shapes
                try {
                    Graphics2D g2d = (Graphics2D) g;

                    // antialiasing on = smooth edges
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // create fillings
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(2));

                    for (Territory territory : allTerritories) {
                        g2d.setColor(territory.colour);
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, territory.opacity));
                        g2d.fill(territory.path2d);
                    }

                    // create outlines
                    g2d.setColor(Color.BLACK);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0F));
                    g2d.setStroke(new BasicStroke(2));

                    for (Territory territory : allTerritories) {
                        g2d.draw(territory.path2d);
                    }


//                    // Get the dimensions of the panel
//                    int width = 1920;
//                    int height = 980;
//
//                    // Draw a grid with 20px spacing
//                    g.setColor(Color.LIGHT_GRAY);
//
//                    for (int i = 0; i < width; i += 20) {
//                        g.drawLine(i, 0, i, height); // Vertical lines
//                    }
//                    for (int i = 0; i < height; i += 20) {
//                        g.drawLine(0, i, width, i); // Horizontal lines
//                    }
//
//                    // Draw coordinate labels (x, y) at grid intersections
//                    g.setColor(Color.RED);
//                    g.setFont(new Font("Arial", Font.PLAIN, 10));
//
//                    for (int x = 0; x < width; x += 50) {
//                        for (int y = 0; y < height; y += 50) {
//                            g.drawString("(" + x + ", " + y + ")", x + 5, y + 15); // Display the coordinates
//                        }
//                    }

                } catch (Exception e) {
                    System.err.println("Error parsing path data: " + e.getMessage());
                }
            }
        };

        mapPanel.setBounds(0, 0, 1920, 980);

        // create troop count labels

        // north america
        nwt.createLabel(mapPanel,250, 85);
        alberta.createLabel(mapPanel, 235, 160);
        alaska.createLabel(mapPanel, 123, 85);
        ontario.createLabel(mapPanel, 320, 173);
        easternCanada.createLabel(mapPanel, 405, 170);
        greenland.createLabel(mapPanel, 485, 50);
        westernUS.createLabel(mapPanel, 235, 250);
        easternUS.createLabel(mapPanel, 333, 265);
        mexico.createLabel(mapPanel, 255, 365);

        // south america
        venezuela.createLabel(mapPanel, 350, 427);
        peru.createLabel(mapPanel, 340, 535);
        argentina.createLabel(mapPanel, 360, 650);
        brazil.createLabel(mapPanel, 440, 515);

        // create game info panel
        JPanel infoPanel = new JPanel(null);
        infoPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        infoPanel.setBounds(0, 980, 1920, 100);

        // create player stat panels
        infoPanel.add(player1.initializePanel(0, 0));
        infoPanel.add(player2.initializePanel(110, 0));

        // create round info panel
        infoPanel.add(game.initializeRoundInfoPanel());

        // create draft phase info panel
        infoPanel.add(game.initializeDraftPhaseInfoPanel());

        // create attack phase info panel
        infoPanel.add(game.initializeAttackPhaseInfoPanel());

        // create fortify phase info panel
        infoPanel.add(game.initializeFortifyPhaseInfoPanel());

        // add map & info panels to parent panel
        contentPane.add(mapPanel);
        contentPane.add(infoPanel);

        // click detection
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean territoryClicked = false;
                boolean ownedTerritorySelected = false;

                for (Territory territory : allTerritories) {
                    if (territory.path2d.contains(e.getPoint())) { // check if click is on a territory
                        territoryClicked = true;
                    }
                }

                if (territoryClicked) {
                    Player activePlayer = game.getTurn();

                    if (game.phase.equals("draft")) {
                        if (game.getTurn().undeployedTroops > 0) {
                            for (Territory territory : activePlayer.territories) {
                                if (territory.path2d.contains(e.getPoint())) { // checks if click is on a territory owned by the current turn's player
                                    if (game.draftSelectedTerritory != null) { // checks if click is on owned territory when starting territory was already previously selected (when the player selects a different owned territory to start from); prevents bug where both territories have the click indicator if a territory stored at a later point in the arraylist containing the player's territories is selected after a territory that is stored at an earlier point
                                        game.draftSelectedTerritory.opacity = 1.0F; // remove click indicator
                                    }

                                    territory.opacity = 0.3F; // indicates click - lighter shade
                                    repaint();

                                    game.draftStatus.setText(territory.name + " selected");
                                    game.addTroopsPanel.setVisible(true);

                                    game.draftSelectedTerritory = territory;
                                }
                            }
                        }

                    } else if (game.phase.equals("attack")) {
                        for (Territory territory : activePlayer.territories) {
                            if (territory.path2d.contains(e.getPoint())) { // check if click is on player owned territory
                                ownedTerritorySelected = true;

                                boolean validSelection = false;

                                for (Territory adjTerritory : territory.adjacentTerritories) { // check if clicked territory has at least 1 adjacent enemy territory that can be attacked
                                    if (adjTerritory.parent != territory.parent) {
                                        validSelection = true;
                                    }
                                }

                                if (game.attackStartingTerritory != null) { // check if click is on different owned territory when starting territory was already previously selected; selecting new owned territory to start from (territories are reset)
                                    if (validSelection && territory.troops >= 2) {
                                        game.attackStartingTerritory.removeAdjEnemyTerritoryHighlights();

                                        game.attackStartingTerritory.opacity = 1.0F;
                                        game.attackStartingTerritory = null;

                                        if (game.attackAttackingTerritory != null) {
                                            game.attackAttackingTerritory.opacity = 1.0F;
                                            game.attackAttackingTerritory = null;

                                            game.attackSelectedTerritories.setText(null);
                                            game.attackBtnContainer.setVisible(false);
                                        }

                                        game.attackStatus.setText("select a territory");
                                        game.endAttackBtnContainer.setVisible(true);
                                    }
                                }

                                if (validSelection && territory.troops >= 2) {
                                    game.attackStartingTerritory = territory; // select owned territory to start from

                                    territory.opacity = 0.3F;
                                    territory.highlightAdjEnemyTerritories();

                                    repaint();

                                    game.attackStatus.setText("select a territory to attack");
                                    game.endAttackBtnContainer.setVisible(false);
                                }
                            }
                        }

                        if (!ownedTerritorySelected) {
                            if (game.attackStartingTerritory != null) { // only runs when starting territory is already selected to check if click is on an adjacent enemy territory
                                for (Territory adjTerritory : game.attackStartingTerritory.adjacentTerritories) {
                                    if (adjTerritory.parent != game.attackStartingTerritory.parent) { // checks if territory selected is adjacent to the starting territory and owned by another player
                                        if (adjTerritory.path2d.contains(e.getPoint())) {
                                            if (game.attackAttackingTerritory != null) { // checks if a territory to attack was already previously chosen; resets its state (occurs when player selects a different territory to attack)
                                                game.attackAttackingTerritory.opacity = 1.0F;
                                            }

                                            game.attackAttackingTerritory = adjTerritory;
                                            game.attackStatus.setText(game.attackStartingTerritory.parent.name + " vs " + game.attackAttackingTerritory.parent.name);
                                            game.attackSelectedTerritories.setText(game.attackStartingTerritory.name + ": " + game.attackStartingTerritory.troops + "   |   " + game.attackAttackingTerritory.name + ": " + game.attackAttackingTerritory.troops);
                                            game.attackBtnContainer.setVisible(true);

                                            adjTerritory.opacity = 0.3F;
                                            repaint();
                                        }
                                    }
                                }
                            }
                        }

                    } else if (game.phase.equals("fortify")) {
                        if (!game.fortifyStatus.getText().equals("fortify complete")) { // checks if fortification already occurred; only one move is allowed per turn
                            for (Territory territory : activePlayer.territories) { // checks if click is on an owned territory
                                if (territory.path2d.contains(e.getPoint())) {
                                    boolean validSelection = false;

                                    for (Territory adjTerritory : territory.adjacentTerritories) {
                                        if (adjTerritory.parent == territory.parent) {
                                            validSelection = true; // territory clicked has at least one adjacent territory owned by the same player
                                        }
                                    }

                                    // resets states if a different territory is selected while both the starting and fortifying territory were previously selected
                                    if (game.fortifyStartingTerritory != null && game.fortifyFortifyingTerritory != null) {
                                        if (validSelection && territory.troops >= 2) {
                                            game.fortifyStartingTerritory.opacity = 1.0F;
                                            game.fortifyStartingTerritory = null;

                                            game.fortifyFortifyingTerritory.opacity = 1.0F;
                                            game.fortifyFortifyingTerritory = null;

                                            game.getTurn().resetTerritoryColours();
                                            game.getTurn().highlightFortifyEligibleStartingTerritories();

                                            game.fortifySelectedTerritories.setText(null);
                                            game.moveTroopsPanel.setVisible(false);

                                            game.fortifyStatus.setText("select a territory");
                                            game.endTurnBtnContainer.setVisible(true);
                                        }
                                    }

                                    if (game.fortifyStartingTerritory == null) { // checks if starting territory is chosen (yes = second territory must be selected, no = starting territory must be selected)
                                        if (validSelection && territory.troops >= 2) {
                                            game.fortifyStartingTerritory = territory; // select owned territory to start from
                                            territory.opacity = 0.3F;

                                            game.findAllTerritoriesThatCanBeFortified();

                                            for (Territory fortTerritory : game.fortifyValidTerritories) { // highlight all territories that can be fortified
                                                fortTerritory.colour = game.getTurn().highlightColour.brighter();
                                            }

                                            repaint();

                                            game.fortifyStatus.setText("select a territory to fortify");
                                            game.endTurnBtnContainer.setVisible(false);
                                        }

                                    } else if (game.fortifyFortifyingTerritory == null && territory != game.fortifyStartingTerritory) { // choose territory to fortify (cannot fortify the starting territory)
                                        boolean valid = false;

                                        if (game.fortifyValidTerritories.contains(territory)) {
                                            valid = true;
                                        }

                                        if (valid) {
                                            game.moveTroops.removeAllItems();
                                            game.fortifyFortifyingTerritory = territory;

                                            game.fortifyStatus.setText("fortify in progress");
                                            game.moveTroopsPanel.setVisible(true);
                                            game.fortifySelectedTerritories.setText(game.fortifyStartingTerritory.name + " -> " + game.fortifyFortifyingTerritory.name);

                                            for (int i = 1; i <= game.fortifyStartingTerritory.troops -1; i++) {
                                                game.moveTroops.addItem(String.valueOf(i));
                                            }

                                            territory.opacity = 0.3F;
                                            repaint();
                                        }
                                    }
                                }
                            }
                        }

                    }

                } else { // no territory clicked
                    if (game.phase.equals("draft")) {
                        // unselect selected territories + reset indicators
                        if (game.getTurn().undeployedTroops > 0) {
                            if (game.draftSelectedTerritory != null) {
                                game.draftSelectedTerritory.opacity = 1.0F;
                                game.draftSelectedTerritory = null;
                                repaint();
                            }

                            game.draftStatus.setText("select a territory");
                            game.addTroopsPanel.setVisible(false);
                        }

                    } else if (game.phase.equals("attack")) {
                        // unselect selected territories + reset indicators
                        if (game.attackStartingTerritory != null) {
                            game.attackStartingTerritory.removeAdjEnemyTerritoryHighlights();

                            game.attackStartingTerritory.opacity = 1.0F;
                            game.attackStartingTerritory = null;

                            if (game.attackAttackingTerritory != null) {
                                game.attackAttackingTerritory.opacity = 1.0F;
                                game.attackAttackingTerritory = null;

                                game.attackSelectedTerritories.setText(null);
                                game.attackBtnContainer.setVisible(false);
                            }

                            game.getTurn().highlightAtkEligibleTerritories();

                            game.attackStatus.setText("select a territory");
                            game.endAttackBtnContainer.setVisible(true);

                            repaint();
                        }

                    } else if (game.phase.equals("fortify")) {
                        // unselect selected territories + reset indicators
                        if (!game.fortifyStatus.getText().equals("fortify complete")) {
                            if (game.fortifyStartingTerritory != null) {
                                game.fortifyStartingTerritory.opacity = 1.0F;
                                game.fortifyStartingTerritory = null;

                                if (game.fortifyFortifyingTerritory != null) {
                                    game.fortifyFortifyingTerritory.opacity = 1.0F;
                                    game.fortifyFortifyingTerritory = null;

                                    game.fortifySelectedTerritories.setText(null);
                                    game.moveTroopsPanel.setVisible(false);
                                }

                                game.fortifyStatus.setText("select a territory");
                                game.endTurnBtnContainer.setVisible(true);

                                game.getTurn().resetTerritoryColours();
                                game.getTurn().highlightFortifyEligibleStartingTerritories();

                                repaint();
                            }
                        }
                    }
                }
            }
        });

        // draft phase btns
        game.addTroopsBtn.addActionListener(e -> {
            game.draftSelectedTerritory.updateTroops(Integer.parseInt(game.addTroops.getSelectedItem().toString()));

            game.getTurn().undeployedTroops -= Integer.parseInt(game.addTroops.getSelectedItem().toString());
            game.availableTroops.setText("available troops: " + game.getTurn().undeployedTroops);

            game.addTroops.removeAllItems();

            if (game.getTurn().undeployedTroops == 0) {
                game.draftStatus.setText("");
                game.nextPhaseBtnContainer.setVisible(true);

            } else {
                game.draftStatus.setText("select a territory");

                for (int i = 1; i <= game.getTurn().undeployedTroops; i++) {
                    game.addTroops.addItem(String.valueOf(i));
                }
            }

            game.draftSelectedTerritory.opacity = 1.0F;
            game.draftSelectedTerritory = null;

            game.addTroopsPanel.setVisible(false);
            repaint();
        });

        game.nextPhaseBtn.addActionListener(e -> {
            game.phase = "attack";
            game.updateRoundInfoPanel();
            game.draftPhaseInfo.setVisible(false);

            game.attackPhaseInfo.setVisible(true);

            game.getTurn().highlightAtkEligibleTerritories();
            repaint();
        });

        // attack phase btns
        game.attackBtn.addActionListener(e -> {
            game.simulateBattle();
            repaint();
            game.checkForWinner();
        });

        game.endAttackBtn.addActionListener(e -> {
            game.phase = "fortify";
            game.updateRoundInfoPanel();
            game.attackPhaseInfo.setVisible(false);

            game.fortifyPhaseInfo.setVisible(true);
            game.fortifyStatus.setText("select a territory");

            game.getTurn().resetTerritoryColours();
            game.getTurn().highlightFortifyEligibleStartingTerritories();
            repaint();
        });

        // fortify phase btns
        game.moveTroopsBtn.addActionListener(e -> {
            game.fortifyStartingTerritory.updateTroops(Integer.parseInt(game.moveTroops.getSelectedItem().toString()) * -1);
            game.fortifyFortifyingTerritory.updateTroops(Integer.parseInt(game.moveTroops.getSelectedItem().toString()));

            game.fortifyStartingTerritory.opacity = 1.0F;
            game.fortifyStartingTerritory = null;

            game.fortifyFortifyingTerritory.opacity = 1.0F;
            game.fortifyFortifyingTerritory = null;

            game.getTurn().resetTerritoryColours();

            repaint();

            game.fortifyStatus.setText("fortify complete");
            game.fortifySelectedTerritories.setText(null);
            game.moveTroops.removeAllItems();
            game.moveTroopsPanel.setVisible(false);
            game.endTurnBtnContainer.setVisible(true);
        });

        game.endTurnBtn.addActionListener(e -> {
            game.phase = "draft";
            game.turnCounter++;

            game.updateRoundInfoPanel();

            game.fortifyPhaseInfo.setVisible(false);
            game.draftPhaseInfo.setVisible(true);

            // update info panels for new player
            game.refreshDraftPhaseInfoPanel();
            game.refreshAttackPhaseInfoPanel();
            game.refreshFortifyPhaseInfoPanel();
        });

        // create gui
        setContentPane(contentPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
