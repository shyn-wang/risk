import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class Main extends JFrame {
    // create game object
    Game game = new Game();

    // create player objects
    Player player1 = new Player("player 1", Color.decode("#04D4F0"), Color.decode("#04ECF0"));
    Player player2 = new Player("player 2", Color.decode("#FA53A0"), Color.decode("#FF0BAC"));
    Player player3 = new Player("player 3", Color.decode("#57cc99"), Color.decode("#80ed99"));
    Player player4 = new Player("player 4", Color.decode("#a663cc"), Color.decode("#c77dff"));

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

    // africa
    Territory northAfrica = new Territory("north africa", Util.getPath2d("north africa"), "africa");
    Territory centralAfrica = new Territory("central africa", Util.getPath2d("central africa"), "africa");
    Territory southAfrica = new Territory("south africa", Util.getPath2d("south africa"), "africa");
    Territory madagascar = new Territory("madagascar", Util.getPath2d("madagascar"), "africa");
    Territory egypt = new Territory("egypt", Util.getPath2d("egypt"), "africa");
    Territory easternAfrica = new Territory("eastern africa", Util.getPath2d("eastern africa"), "africa");

    // europe
    Territory iceland = new Territory("iceland", Util.getPath2d("iceland"), "europe");
    Territory greatBritain = new Territory("great britain", Util.getPath2d("great britain"), "europe");
    Territory westernEurope = new Territory("western europe", Util.getPath2d("western europe"), "europe");
    Territory scandinavia = new Territory("scandinavia", Util.getPath2d("scandinavia"), "europe");
    Territory northernEurope = new Territory("northern europe", Util.getPath2d("northern europe"), "europe");
    Territory southernEurope = new Territory("southern europe", Util.getPath2d("southern europe"), "europe");
    Territory russia = new Territory("russia", Util.getPath2d("russia"), "europe");

    // asia
    Territory middleEast = new Territory("middle east", Util.getPath2d("middle east"), "asia");
    Territory afghanistan = new Territory("afghanistan", Util.getPath2d("afghanistan"), "asia");
    Territory india = new Territory("india", Util.getPath2d("india"), "asia");
    Territory southeastAsia = new Territory("southeast asia", Util.getPath2d("southeast asia"), "asia");
    Territory china = new Territory("china", Util.getPath2d("china"), "asia");
    Territory mongolia = new Territory("mongolia", Util.getPath2d("mongolia"), "asia");
    Territory irkutsk = new Territory("irkutsk", Util.getPath2d("irkutsk"), "asia");
    Territory kamchatka = new Territory("kamchatka", Util.getPath2d("kamchatka"), "asia");
    Territory yakutsk = new Territory("yakutsk", Util.getPath2d("yakutsk"), "asia");
    Territory siberia = new Territory("siberia", Util.getPath2d("siberia"), "asia");
    Territory ural = new Territory("ural", Util.getPath2d("ural"), "asia");
    Territory japan = new Territory("japan", Util.getPath2d("japan"), "asia");

    // oceania
    Territory indonesia = new Territory("indonesia", Util.getPath2d("indonesia"), "oceania");
    Territory newGuinea = new Territory("new guinea", Util.getPath2d("new guinea"), "oceania");
    Territory westernAustralia = new Territory("western australia", Util.getPath2d("western australia"), "oceania");
    Territory easternAustralia = new Territory("eastern australia", Util.getPath2d("eastern australia"), "oceania");


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
        Collections.addAll(game.players, player1, player2, player3, player4);

        // set adjacent territories for each territory

        // north america
        Collections.addAll(nwt.adjacentTerritories, alaska, ontario, alberta, greenland);
        Collections.addAll(alberta.adjacentTerritories, alaska, ontario, nwt, westernUS);
        Collections.addAll(alaska.adjacentTerritories, alberta, nwt, kamchatka);
        Collections.addAll(ontario.adjacentTerritories, alberta, nwt, easternCanada, easternUS, westernUS, greenland);
        Collections.addAll(westernUS.adjacentTerritories, alberta, ontario, mexico, easternUS);
        Collections.addAll(mexico.adjacentTerritories, westernUS, easternUS, venezuela);
        Collections.addAll(easternUS.adjacentTerritories, easternCanada, ontario, westernUS, mexico);
        Collections.addAll(easternCanada.adjacentTerritories, ontario, easternUS, greenland);
        Collections.addAll(greenland.adjacentTerritories, ontario, easternCanada, nwt, iceland);

        // south america
        Collections.addAll(venezuela.adjacentTerritories, mexico, peru, brazil);
        Collections.addAll(peru.adjacentTerritories, venezuela, argentina, brazil);
        Collections.addAll(argentina.adjacentTerritories, peru, brazil);
        Collections.addAll(brazil.adjacentTerritories, venezuela, peru, argentina, northAfrica);

        // africa
        Collections.addAll(northAfrica.adjacentTerritories, brazil, centralAfrica, egypt, easternAfrica, westernEurope, southernEurope);
        Collections.addAll(centralAfrica.adjacentTerritories, northAfrica, southAfrica, easternAfrica);
        Collections.addAll(southAfrica.adjacentTerritories, centralAfrica, madagascar, easternAfrica);
        Collections.addAll(madagascar.adjacentTerritories, southAfrica, easternAfrica);
        Collections.addAll(egypt.adjacentTerritories, northAfrica, easternAfrica, southernEurope, middleEast);
        Collections.addAll(easternAfrica.adjacentTerritories, northAfrica, centralAfrica, southAfrica, madagascar, egypt, middleEast);

        // europe
        Collections.addAll(iceland.adjacentTerritories, greenland, greatBritain, scandinavia);
        Collections.addAll(greatBritain.adjacentTerritories, iceland, westernEurope, scandinavia, northernEurope);
        Collections.addAll(westernEurope.adjacentTerritories, greatBritain, northAfrica, northernEurope, southernEurope);
        Collections.addAll(scandinavia.adjacentTerritories, iceland, greatBritain, northernEurope, russia);
        Collections.addAll(northernEurope.adjacentTerritories, scandinavia, greatBritain, westernEurope, southernEurope, russia);
        Collections.addAll(southernEurope.adjacentTerritories, northernEurope, westernEurope, northAfrica, russia, egypt, middleEast);
        Collections.addAll(russia.adjacentTerritories, scandinavia, northernEurope, southernEurope, afghanistan, middleEast, ural);

        // asia
        Collections.addAll(middleEast.adjacentTerritories, russia, afghanistan, southernEurope, egypt, easternAfrica, india);
        Collections.addAll(india.adjacentTerritories, middleEast, afghanistan, china, southeastAsia);
        Collections.addAll(afghanistan.adjacentTerritories, russia, middleEast, india, china, ural);
        Collections.addAll(china.adjacentTerritories, afghanistan, india, southeastAsia, mongolia, siberia, ural);
        Collections.addAll(southeastAsia.adjacentTerritories, china, india, indonesia);
        Collections.addAll(mongolia.adjacentTerritories, irkutsk, kamchatka, japan, china, siberia);
        Collections.addAll(irkutsk.adjacentTerritories, mongolia, kamchatka, yakutsk, siberia);
        Collections.addAll(kamchatka.adjacentTerritories, alaska, yakutsk, irkutsk, mongolia, japan);
        Collections.addAll(yakutsk.adjacentTerritories, kamchatka, irkutsk, siberia);
        Collections.addAll(siberia.adjacentTerritories, yakutsk, irkutsk, mongolia, china, ural);
        Collections.addAll(ural.adjacentTerritories, russia, afghanistan, china, siberia);
        Collections.addAll(japan.adjacentTerritories, kamchatka, mongolia);

        // oceania
        Collections.addAll(indonesia.adjacentTerritories, southeastAsia, newGuinea, westernAustralia);
        Collections.addAll(newGuinea.adjacentTerritories, indonesia, westernAustralia, easternAustralia);
        Collections.addAll(westernAustralia.adjacentTerritories, indonesia, newGuinea, easternAustralia);
        Collections.addAll(easternAustralia.adjacentTerritories, westernAustralia, newGuinea);


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

        // africa
        allTerritories.add(northAfrica);
        allTerritories.add(centralAfrica);
        allTerritories.add(southAfrica);
        allTerritories.add(madagascar);
        allTerritories.add(egypt);
        allTerritories.add(easternAfrica);

        // europe
        allTerritories.add(iceland);
        allTerritories.add(greatBritain);
        allTerritories.add(westernEurope);
        allTerritories.add(scandinavia);
        allTerritories.add(northernEurope);
        allTerritories.add(southernEurope);
        allTerritories.add(russia);

        // asia
        allTerritories.add(middleEast);
        allTerritories.add(afghanistan);
        allTerritories.add(india);
        allTerritories.add(china);
        allTerritories.add(southeastAsia);
        allTerritories.add(mongolia);
        allTerritories.add(irkutsk);
        allTerritories.add(kamchatka);
        allTerritories.add(yakutsk);
        allTerritories.add(siberia);
        allTerritories.add(ural);
        allTerritories.add(japan);

        // oceania
        allTerritories.add(indonesia);
        allTerritories.add(newGuinea);
        allTerritories.add(westernAustralia);
        allTerritories.add(easternAustralia);

        // randomly assign territories to each player
        Collections.shuffle(allTerritories);

        for (Territory territory : allTerritories) {
            if (player1.territories.size() < allTerritories.size() / game.players.size()) { // evenly distribute territories
                territory.parent = player1;
                player1.territories.add(territory);
                territory.colour = player1.highlightColour; // player 1 starts in draft = highlight territories

                player1.deployedTroops++;
                player1.undeployedTroops--;

            } else if (player2.territories.size() < allTerritories.size() / game.players.size()) {
                territory.parent = player2;
                player2.territories.add(territory);
                territory.colour = player2.defaultColour;

                player2.deployedTroops++;
                player2.undeployedTroops--;

            } else if (player3.territories.size() < allTerritories.size() / game.players.size()) {
                territory.parent = player3;
                player3.territories.add(territory);
                territory.colour = player3.defaultColour;

                player3.deployedTroops++;
                player3.undeployedTroops--;

            } else if (player4.territories.size() < allTerritories.size() / game.players.size()) {
                territory.parent = player4;
                player4.territories.add(territory);
                territory.colour = player4.defaultColour;

                player4.deployedTroops++;
                player4.undeployedTroops--;

            } else { // leftover territories present; randomly assigned to players
                int leftOverAssignment = (int) (Math.random() * game.players.size()) + 1;

                if (leftOverAssignment == 1) {
                    territory.parent = player1;
                    player1.territories.add(territory);
                    territory.colour = player1.highlightColour;

                    player1.deployedTroops++;
                    player1.undeployedTroops--;

                } else if (leftOverAssignment == 2) {
                    territory.parent = player2;
                    player2.territories.add(territory);
                    territory.colour = player2.defaultColour;

                    player2.deployedTroops++;
                    player2.undeployedTroops--;

                } else if (leftOverAssignment == 3) {
                    territory.parent = player3;
                    player3.territories.add(territory);
                    territory.colour = player3.defaultColour;

                    player3.deployedTroops++;
                    player3.undeployedTroops--;

                } else if (leftOverAssignment == 4) {
                    territory.parent = player4;
                    player4.territories.add(territory);
                    territory.colour = player4.defaultColour;

                    player4.deployedTroops++;
                    player4.undeployedTroops--;
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

        // create welcome screen
        JFrame frame = new JFrame();

        JDialog dialog = new JDialog(frame, "welcome to risk", true); // modal = no other parts of the application can be accessed until the popup is responded to
        dialog.setResizable(false);
        dialog.setSize(600, 300);
        dialog.setLocationRelativeTo(frame);

        dialog.setLayout(new GridLayout(2, 1, 0, 15));

        JPanel playerNames = new JPanel();
        playerNames.setBorder(new EmptyBorder(20, 10, 10, 10));

        JPanel bottomContainer = new JPanel();
        bottomContainer.setLayout(new GridLayout(2, 1, 0, 5));

        JLabel statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        bottomContainer.add(statusLabel);

        JButton playButton = new JButton("play");
        JPanel playBtnContainer = new JPanel();

        playBtnContainer.add(playButton);
        bottomContainer.add(playBtnContainer);

        playerNames.add(player1.setNamePanel);
        player1.setNameBtn.addActionListener(e -> {
            if (player1.setName.getText().length() < 8 && !player1.setName.getText().isEmpty()) {
                // check if any other players have the same name
                boolean nameTaken = game.players.stream().anyMatch(obj -> obj.name.equals(player1.setName.getText()));

                if (!nameTaken) {
                    player1.name = player1.setName.getText();
                    player1.setNamePanelNameLabel.setText(player1.name);
                    statusLabel.setText("");
                } else {
                    statusLabel.setText("invalid input");
                }
            } else {
                statusLabel.setText("invalid input");
            }
        });

        playerNames.add(player2.setNamePanel);
        player2.setNameBtn.addActionListener(e -> {
            if (player2.setName.getText().length() < 8 && !player2.setName.getText().isEmpty()) {
                // check if any other players have the same name
                boolean nameTaken = game.players.stream().anyMatch(obj -> obj.name.equals(player2.setName.getText()));

                if (!nameTaken) {
                    player2.name = player2.setName.getText();
                    player2.setNamePanelNameLabel.setText(player2.name);
                    statusLabel.setText("");
                } else {
                    statusLabel.setText("invalid input");
                }
            } else {
                statusLabel.setText("invalid input");
            }
        });

        playerNames.add(player3.setNamePanel);
        player3.setNameBtn.addActionListener(e -> {
            if (player3.setName.getText().length() < 8 && !player3.setName.getText().isEmpty()) {
                // check if any other players have the same name
                boolean nameTaken = game.players.stream().anyMatch(obj -> obj.name.equals(player3.setName.getText()));

                if (!nameTaken) {
                    player3.name = player3.setName.getText();
                    player3.setNamePanelNameLabel.setText(player3.name);
                    statusLabel.setText("");
                } else {
                    statusLabel.setText("invalid input");
                }
            } else {
                statusLabel.setText("invalid input");
            }
        });

        playerNames.add(player4.setNamePanel);
        player4.setNameBtn.addActionListener(e -> {
            if (player4.setName.getText().length() < 8 && !player4.setName.getText().isEmpty()) {
                // check if any other players have the same name
                boolean nameTaken = game.players.stream().anyMatch(obj -> obj.name.equals(player4.setName.getText()));

                if (!nameTaken) {
                    player4.name = player4.setName.getText();
                    player4.setNamePanelNameLabel.setText(player4.name);
                    statusLabel.setText("");
                } else {
                    statusLabel.setText("invalid input");
                }
            } else {
                statusLabel.setText("invalid input");
            }
        });

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        playButton.addActionListener(e -> {
            if (!player1.name.equals("player 1") && !player2.name.equals("player 2") && !player3.name.equals("player 3") && !player4.name.equals("player 4")) {
                dialog.dispose();
                Util.playSoundtrack();
            } else {
                statusLabel.setText("set all player names");
            }
        });

        dialog.add(playerNames);
        dialog.add(bottomContainer);
        dialog.setVisible(true);

        // create panel for world map
        InputStream inputStream = getClass().getResourceAsStream("/continentConnections.png");
        Image connections = ImageIO.read(inputStream);

        JPanel mapPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) { // called whenever gui is created/refreshed
                super.paintComponent(g);
                g.drawImage(connections, -1, 0, 1710, 980, this);

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

                } catch (Exception e) {
                    System.err.println("Error parsing path data: " + e.getMessage());
                }
            }
        };

        mapPanel.setBounds(0, 0, 1710, 980);

        // create troop count labels

        // north america
        nwt.createLabel(mapPanel,393, 125);
        alberta.createLabel(mapPanel, 378, 200);
        alaska.createLabel(mapPanel, 266, 125);
        ontario.createLabel(mapPanel, 463, 213);
        easternCanada.createLabel(mapPanel, 548, 210);
        greenland.createLabel(mapPanel, 628, 90);
        westernUS.createLabel(mapPanel, 378, 290);
        easternUS.createLabel(mapPanel, 477, 305);
        mexico.createLabel(mapPanel, 398, 405);

        // south america
        venezuela.createLabel(mapPanel, 493, 467);
        peru.createLabel(mapPanel, 483, 575);
        argentina.createLabel(mapPanel, 503, 690);
        brazil.createLabel(mapPanel, 583, 555);

        // africa
        northAfrica.createLabel(mapPanel,778, 520);
        egypt.createLabel(mapPanel, 878, 480);
        easternAfrica.createLabel(mapPanel, 943, 580);
        centralAfrica.createLabel(mapPanel, 878, 630);
        southAfrica.createLabel(mapPanel, 883, 740);
        madagascar.createLabel(mapPanel, 990, 740);

        // europe
        iceland.createLabel(mapPanel, 728, 165);
        greatBritain.createLabel(mapPanel, 715, 258);
        westernEurope.createLabel(mapPanel, 723, 390);
        southernEurope.createLabel(mapPanel, 835, 352);
        northernEurope.createLabel(mapPanel, 825, 282);
        scandinavia.createLabel(mapPanel, 815, 175);
        russia.createLabel(mapPanel, 933, 240);

        // asia
        middleEast.createLabel(mapPanel, 970, 450);
        india.createLabel(mapPanel, 1115, 450);
        afghanistan.createLabel(mapPanel, 1045, 320);
        china.createLabel(mapPanel, 1205, 380);
        southeastAsia.createLabel(mapPanel, 1215, 475);
        mongolia.createLabel(mapPanel, 1225, 290);
        irkutsk.createLabel(mapPanel, 1205, 210);
        kamchatka.createLabel(mapPanel, 1320, 110);
        yakutsk.createLabel(mapPanel, 1220, 110);
        siberia.createLabel(mapPanel, 1125, 140);
        ural.createLabel(mapPanel, 1055, 180);
        japan.createLabel(mapPanel, 1350, 300);

        // oceania
        indonesia.createLabel(mapPanel, 1240, 600);
        newGuinea.createLabel(mapPanel, 1345, 580);
        westernAustralia.createLabel(mapPanel, 1290, 730);
        easternAustralia.createLabel(mapPanel, 1395, 720);

        // create game info panel
        JPanel infoPanel = new JPanel(null);
        infoPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        infoPanel.setBounds(0, 980, 1920, 100);

        // create player stat panels
        infoPanel.add(player1.initializeStatsPanel(0, 0));
        infoPanel.add(player2.initializeStatsPanel(110, 0));
        infoPanel.add(player3.initializeStatsPanel(220, 0));
        infoPanel.add(player4.initializeStatsPanel(330, 0));

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
                                            break; 
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
                game.getTurn().resetTerritoryColours();

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
            game.getTurn().resetTerritoryColours(); // reset if troops were never moved

            game.phase = "draft";
            game.turnCounter++;

            game.updateRoundInfoPanel();

            game.fortifyPhaseInfo.setVisible(false);
            game.draftPhaseInfo.setVisible(true);

            game.getTurn().highlightAllTerritories();

            repaint();

            // update info panels for new player
            game.refreshDraftPhaseInfoPanel();
            game.refreshAttackPhaseInfoPanel();
            game.refreshFortifyPhaseInfoPanel();
        });

        // create gui
        setContentPane(contentPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1710, 1070);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new Main();
    }
}
