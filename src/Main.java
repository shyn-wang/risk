import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {
    private JPanel mapPanel;
    private JPanel infoPanel;
    boolean firstRender = true;

    public Main() throws Exception {
        JPanel contentPane = new JPanel(new BorderLayout());

        // stores all territories
        ArrayList<Territory> allTerritories = new ArrayList<>();

        // add svg paths of all territories to map with territory names as the keys
        Map<String, HashMap<String, Object>> paths = Util.getPaths();

        // create player objects
        Player player1 = new Player("player1", Color.CYAN, new ArrayList<>() {}, 5, 3);
        Player player2 = new Player("player2", Color.MAGENTA, new ArrayList<>() {}, 4, 3);

        // create territory objects

        // north america
        Territory nwt = new Territory("nwt", player1, Util.getPath2d("nwt", paths), 1, 1.0F, new ArrayList<>() {});
        Territory alberta = new Territory("alberta", player1, Util.getPath2d("alberta", paths), 1, 1.0F, new ArrayList<>() {});
        Territory alaska = new Territory("alaska", player1, Util.getPath2d("alaska", paths), 1, 1.0F, new ArrayList<>() {});
        Territory ontario = new Territory("ontario", player1, Util.getPath2d("ontario", paths), 1, 1.0F, new ArrayList<>() {});
        Territory westernUS = new Territory("western u.s.", player2, Util.getPath2d("westernUS", paths), 1, 1.0F, new ArrayList<>() {});
        Territory mexico = new Territory("mexico", player2, Util.getPath2d("mexico", paths), 1, 1.0F, new ArrayList<>() {});
        Territory easternUS = new Territory("eastern u.s.", player2, Util.getPath2d("easternUS", paths), 1, 1.0F, new ArrayList<>() {});
        Territory easternCanada = new Territory("eastern canada", player2, Util.getPath2d("easternCanada", paths), 1, 1.0F, new ArrayList<>() {});
        Territory greenland = new Territory("greenland", player1, Util.getPath2d("greenland", paths), 1, 1.0F, new ArrayList<>() {});

        // set adjacent territories for each territory

        // north america
        Collections.addAll(nwt.adjacentTerritories, alaska, ontario, alberta);
        Collections.addAll(alberta.adjacentTerritories, alaska, ontario, nwt);
        Collections.addAll(alaska.adjacentTerritories, alberta, nwt); // **add kamchatka once implemented**
        Collections.addAll(ontario.adjacentTerritories, alberta, nwt, easternCanada);
        Collections.addAll(westernUS.adjacentTerritories, alberta, ontario, mexico, easternUS);
        Collections.addAll(mexico.adjacentTerritories, westernUS, easternUS); //
        Collections.addAll(easternUS.adjacentTerritories, easternCanada, ontario, westernUS, mexico); //
        Collections.addAll(easternCanada.adjacentTerritories, ontario, easternUS);
        Collections.addAll(greenland.adjacentTerritories, ontario, easternCanada, nwt); //

        allTerritories.add(nwt);
        allTerritories.add(alberta);
        allTerritories.add(alaska);
        allTerritories.add(ontario);
        allTerritories.add(westernUS);
        allTerritories.add(mexico);
        allTerritories.add(easternUS);
        allTerritories.add(easternCanada);
        allTerritories.add(greenland);

        // create map
        mapPanel = new JPanel() {
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

                } catch (Exception e) {
                    System.err.println("Error parsing path data: " + e.getMessage());
                }
            }
        };

        // click detection
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // **note** every path2d shape is rendered again after calling repaint; only clicked territory is changed since only its opacity value is adjusted
                for (Territory territory : allTerritories) {
                    if (territory.path2d.contains(e.getPoint())) {
                        territory.opacity = 0.3F;
                        repaint();
                    } else {
                        territory.opacity = 1.0F;
                        repaint();
                    }
                }
            }
        });

        mapPanel.setPreferredSize(new Dimension(1920, 980));

        infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, java.awt.Color.BLACK));
        infoPanel.setPreferredSize(new Dimension(1920, 100));

        contentPane.add(mapPanel, BorderLayout.CENTER);
        contentPane.add(infoPanel, BorderLayout.SOUTH);

        // create gui
        setContentPane(contentPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
