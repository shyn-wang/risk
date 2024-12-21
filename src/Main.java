import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
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

        ArrayList<Territory> allTerritories = new ArrayList<>();
        ArrayList<Object> p1Territories = new ArrayList<>();
        ArrayList<Object> p2Territories = new ArrayList<>();

        // add svg paths of all territories to map with territory names as the keys
        Map<String, HashMap<String, Object>> paths = Util.getPaths();

        // create player objects
        Player player1 = new Player("player1", Color.CYAN, p1Territories, 4, 3);
        Player player2 = new Player("player2", Color.MAGENTA, p2Territories, 4, 3);

        // create territory objects
        Territory nwt = new Territory("nwt", player1, Util.getPath2d("nwt", paths), 1, player1.colour, 1.0F, new ArrayList<>() {});
        Territory alberta = new Territory("alberta", player1, Util.getPath2d("alberta", paths), 1, player1.colour, 1.0F, new ArrayList<>() {});
        Territory alaska = new Territory("alaska", player1, Util.getPath2d("alaska", paths), 1, player1.colour, 1.0F, new ArrayList<>() {});
        Territory ontario = new Territory("ontario", player1, Util.getPath2d("ontario", paths), 1, player1.colour, 1.0F, new ArrayList<>() {});

        // set adjacent territories for each territory
        Collections.addAll(nwt.adjacentTerritories, alaska, ontario, alberta);
        Collections.addAll(alberta.adjacentTerritories, alaska, ontario, nwt);
        Collections.addAll(alaska.adjacentTerritories, alberta, nwt); // **add kamchatka once implemented**
        Collections.addAll(ontario.adjacentTerritories, alberta, nwt);

        allTerritories.add(nwt);
        allTerritories.add(alberta);
        allTerritories.add(alaska);
        allTerritories.add(ontario);

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
                        g2d.setColor(territory.colour);
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, territory.opacity));
                        g2d.fill(territory.path2d);
                    }

                    // create outlines
                    g2d.setColor(Color.BLACK);
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
                // **note** every path2d shape is rendered again after calling repaint; only alaska is changed since only its opacity value is adjusted
                if (alaska.path2d.contains(e.getPoint())) {
                    alaska.opacity = 0.3F;
                    repaint();
                } else {
                    alaska.opacity = 1.0F;
                    repaint();
                }
            }
        });

        mapPanel.setBorder(new LineBorder(Color.BLACK, 2));
        mapPanel.setPreferredSize(new Dimension(1920, 980));

        infoPanel = new JPanel();
        infoPanel.setBorder(new LineBorder(Color.BLACK, 2));
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
