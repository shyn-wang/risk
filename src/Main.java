import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Main extends JFrame {
    // create game object
    Game game = new Game(1, new ArrayList<>() {}, "draft", true);

    // create player objects
    Player player1 = new Player("player 1", Color.CYAN, new ArrayList<>() {}, 5, 30, true);
    Player player2 = new Player("player 2", Color.MAGENTA, new ArrayList<>() {}, 4, 3, true);

    // create territory objects

    // north america
    Territory nwt = new Territory("nwt", player1, Util.getPath2d("nwt"), 1, 1.0F, new ArrayList<>() {});
    Territory alberta = new Territory("alberta", player1, Util.getPath2d("alberta"), 1, 1.0F, new ArrayList<>() {});
    Territory alaska = new Territory("alaska", player1, Util.getPath2d("alaska"), 1, 1.0F, new ArrayList<>() {});
    Territory ontario = new Territory("ontario", player2, Util.getPath2d("ontario"), 1, 1.0F, new ArrayList<>() {});
    Territory westernUS = new Territory("western u.s.", player2, Util.getPath2d("westernUS"), 1, 1.0F, new ArrayList<>() {});
    Territory mexico = new Territory("mexico", player1, Util.getPath2d("mexico"), 1, 1.0F, new ArrayList<>() {});
    Territory easternUS = new Territory("eastern u.s.", player2, Util.getPath2d("easternUS"), 1, 1.0F, new ArrayList<>() {});
    Territory easternCanada = new Territory("eastern canada", player2, Util.getPath2d("easternCanada"), 1, 1.0F, new ArrayList<>() {});
    Territory greenland = new Territory("greenland", player1, Util.getPath2d("greenland"), 1, 1.0F, new ArrayList<>() {});

    public Main() throws Exception {
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
        Collections.addAll(mexico.adjacentTerritories, westernUS, easternUS); //
        Collections.addAll(easternUS.adjacentTerritories, easternCanada, ontario, westernUS, mexico);
        Collections.addAll(easternCanada.adjacentTerritories, ontario, easternUS, greenland);
        Collections.addAll(greenland.adjacentTerritories, ontario, easternCanada, nwt); //

        // stores all territories
        ArrayList<Territory> allTerritories = new ArrayList<>();

        // add territories to allTerritories arraylist
        allTerritories.add(nwt);
        allTerritories.add(alberta);
        allTerritories.add(alaska);
        allTerritories.add(ontario);
        allTerritories.add(westernUS);
        allTerritories.add(mexico);
        allTerritories.add(easternUS);
        allTerritories.add(easternCanada);
        allTerritories.add(greenland);

        // add territories owned by each player to player.territories arraylist
        for (Territory territory : allTerritories) {
            if (territory.parent == player1) {
                player1.territories.add(territory);
            } else if (territory.parent == player2) {
                player2.territories.add(territory);
            }
        }

        JPanel mapPanel = new JPanel(null) {
            // called whenever gui is created/refreshed
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // render path2D shapes
                try {
                    // Cast Graphics to Graphics2D
                    Graphics2D g2d = (Graphics2D) g;

                    // antialiasing = smooth edges
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // create fillings
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(2));

                    for (Territory territory : allTerritories) {
                        g2d.setColor(territory.getColour());
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

        JPanel infoPanel = new JPanel(null);
        infoPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, java.awt.Color.BLACK));
        infoPanel.setBounds(0, 980, 1920, 100);

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
                    if (territory.path2d.contains(e.getPoint())) {
                        territoryClicked = true;
                    }
                }

                if (territoryClicked) {
                    Player activePlayer = game.getTurn();

                    if (game.phase.equals("draft")) {
                        if (game.getTurn().undeployedTroops > 0) {
                            for (Territory territory : activePlayer.territories) {
                                if (territory.path2d.contains(e.getPoint())) {
                                    if (game.draftSelectedTerritory != null) { // = click is on owned territory when starting territory was already previously selected; selecting new owned territory to start from (territories are reset)
                                        game.draftSelectedTerritory.opacity = 1.0F;
                                    }

                                    territory.opacity = 0.3F;
                                    repaint();

                                    game.draftStatus.setText(territory.name + " selected");
                                    game.addTroopsPanel.setVisible(true);

                                    game.draftSelectedTerritory = territory;

                                } else {
                                    if (territory != game.draftSelectedTerritory) {
                                        territory.opacity = 1.0F;
                                        repaint();
                                    }
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

                                if (game.attackStartingTerritory != null) { // = click is on owned territory when starting territory was already previously selected; selecting new owned territory to start from (territories are reset)
                                    if (validSelection && territory.troops >= 2) {
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

                                    repaint();

                                    game.attackStatus.setText("select a territory to attack");
                                    game.endAttackBtnContainer.setVisible(false);
                                }

                            } else {
                                if (territory != game.attackStartingTerritory) { // do not reset opacity of starting territory if previously selected; for loop will always run to check if the click was on an owned territory
                                    territory.opacity = 1.0F;
                                    repaint();
                                }
                            }
                        }

                        if (!ownedTerritorySelected) {
                            if (game.attackStartingTerritory != null) { // only runs when starting territory is already selected to check if click is on an adjacent enemy territory
                                for (Territory adjTerritory : game.attackStartingTerritory.adjacentTerritories) {
                                    if (adjTerritory.parent != game.attackStartingTerritory.parent) {
                                        if (adjTerritory.path2d.contains(e.getPoint())) {

                                            if (game.attackAttackingTerritory != null) {
                                                game.attackAttackingTerritory.opacity = 1.0F;
                                            }

                                            game.attackAttackingTerritory = adjTerritory;
                                            game.attackStatus.setText(game.attackStartingTerritory.parent.name + " vs " + game.attackAttackingTerritory.parent.name);
                                            game.attackSelectedTerritories.setText(game.attackStartingTerritory.name + ": " + game.attackStartingTerritory.troops + "   |   " + game.attackAttackingTerritory.name + ": " + game.attackAttackingTerritory.troops);
                                            game.attackBtnContainer.setVisible(true);

                                            adjTerritory.opacity = 0.3F;
                                            repaint();

                                        } else {
                                            if (adjTerritory != game.attackAttackingTerritory) {
                                                adjTerritory.opacity = 1.0F;
                                                repaint();
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    } else if (game.phase.equals("fortify")) {
                        if (!game.fortifyStatus.getText().equals("fortify complete")) {
                            for (Territory territory : activePlayer.territories) {
                                if (territory.path2d.contains(e.getPoint())) {
                                    boolean validSelection = false;

                                    for (Territory adjTerritory : territory.adjacentTerritories) {
                                        if (adjTerritory.parent == territory.parent) {
                                            validSelection = true; // territory clicked has at least one adjacent territory owned by the same player
                                        }
                                    }

                                    if (game.fortifyStartingTerritory != null && game.fortifyFortifyingTerritory != null) {
                                        if (validSelection && territory.troops >= 2) {
                                            game.fortifyStartingTerritory.opacity = 1.0F;
                                            game.fortifyStartingTerritory = null;

                                            game.fortifyFortifyingTerritory.opacity = 1.0F;
                                            game.fortifyFortifyingTerritory = null;

                                            game.fortifySelectedTerritories.setText(null);
                                            game.moveTroopsPanel.setVisible(false);

                                            game.fortifyStatus.setText("select a territory");
                                            game.endTurnBtnContainer.setVisible(true);
                                        }
                                    }

                                    if (game.fortifyStartingTerritory == null) {
                                        if (validSelection && territory.troops >= 2) {
                                            game.fortifyStartingTerritory = territory; // select owned territory to start from

                                            territory.opacity = 0.3F;

                                            repaint();

                                            game.fortifyStatus.setText("select a territory to fortify");
                                            game.endTurnBtnContainer.setVisible(false);
                                        }

                                    } else if (game.fortifyFortifyingTerritory == null && territory != game.fortifyStartingTerritory) {
                                        boolean valid = false;
                                        ArrayList<Territory> uncheckedAdjTerritories = new ArrayList<>(game.fortifyStartingTerritory.adjacentTerritories); // ************** arraylists are reference types; uncheckedAdjTerritories must be given a copy, otherwise changes made to it will also affect the original
                                        ArrayList<Territory> stagingArea = new ArrayList<>();
                                        ArrayList<Territory> checkedTerritories = new ArrayList<>();
                                        HashSet<Territory> duplicateRemover = new HashSet<>();

                                        searchAlgorithm:
                                        while (true) {
                                            stagingArea.clear();

                                            for (int i = 0; i < uncheckedAdjTerritories.size(); i++) {
                                                if (uncheckedAdjTerritories.get(i).parent.equals(game.fortifyStartingTerritory.parent)) {
//                                                    uncheckedAdjTerritories.get(i).opacity = 0.3F;
//                                                    stagingArea.add(uncheckedAdjTerritories.get(i));
//                                                    checkedTerritories.add(uncheckedAdjTerritories.get(i));

                                                    if (uncheckedAdjTerritories.get(i).equals(territory)) {
                                                        valid = true;
                                                        break searchAlgorithm;

                                                    } else {
                                                        stagingArea.add(uncheckedAdjTerritories.get(i));
                                                        checkedTerritories.add(uncheckedAdjTerritories.get(i));
                                                    }
                                                }
                                            }

                                            if (!stagingArea.isEmpty()) {
                                                uncheckedAdjTerritories.clear();
                                                duplicateRemover.clear();

                                                // find all adjacent territories of territories in staging area
                                                for (int i = 0; i < stagingArea.size(); i++) {
                                                    for (int j = 0; j < stagingArea.get(i).adjacentTerritories.size(); j++) {
                                                        if (!checkedTerritories.contains(stagingArea.get(i).adjacentTerritories.get(j))) { // prevents infinite loop; territory that adjacent territories branch off from is not re-added
                                                            duplicateRemover.add(stagingArea.get(i).adjacentTerritories.get(j));
                                                        }
                                                    }
                                                }

                                                uncheckedAdjTerritories.addAll(duplicateRemover);

                                            } else {
                                                // repaint();
                                                break searchAlgorithm;
                                            }
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

                                } else {
                                    if (territory != game.fortifyStartingTerritory && territory != game.fortifyFortifyingTerritory) {
                                        territory.opacity = 1.0F;
                                        repaint();
                                    }
                                }
                            }
                        }

                    }

                } else { // no territory clicked
                    if (game.phase.equals("draft")) {
                        if (game.getTurn().undeployedTroops > 0) {
                            for (Territory territory : allTerritories) {
                                territory.opacity = 1.0F;
                                repaint();
                            }

                            game.draftStatus.setText("select a territory");
                            game.addTroopsPanel.setVisible(false);
                        }

                    } else if (game.phase.equals("attack")) {
                        if (game.attackStartingTerritory != null) {
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

                            repaint();
                        }

                    } else if (game.phase.equals("fortify")) {
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
        });

        // attack phase btns
        game.attackBtn.addActionListener(e -> {
            game.simulateBattle();
            repaint();
        });

        game.endAttackBtn.addActionListener(e -> {
            game.phase = "fortify";
            game.updateRoundInfoPanel();
            game.attackPhaseInfo.setVisible(false);

            game.fortifyPhaseInfo.setVisible(true);
            game.fortifyStatus.setText("select a territory");
        });

        // fortify phase btns
        game.moveTroopsBtn.addActionListener(e -> {
            game.fortifyStartingTerritory.updateTroops(Integer.parseInt(game.moveTroops.getSelectedItem().toString()) * -1);
            game.fortifyFortifyingTerritory.updateTroops(Integer.parseInt(game.moveTroops.getSelectedItem().toString()));

            game.fortifyStartingTerritory.opacity = 1.0F;
            game.fortifyStartingTerritory = null;

            game.fortifyFortifyingTerritory.opacity = 1.0F;
            game.fortifyFortifyingTerritory = null;

            repaint();

            game.fortifyStatus.setText("fortify complete");
            game.fortifySelectedTerritories.setText(null);
            game.moveTroops.removeAllItems();
            game.moveTroopsPanel.setVisible(false);
            game.endTurnBtnContainer.setVisible(true);
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
