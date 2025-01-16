import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sound.sampled.*;

public class Util {
    /*
    description: converts svg path to coordinates and draws path2d shape
    pre-condition: called by Main during territory object creation; requires string corresponding to the id of the path of the territory to be drawn
    post-condition: returns Path2D of desired territory to Main
    */
    public static Path2D getPath2d(String territory) throws Exception {
        // add svg paths of all territories to map with territory names as the keys
        Map<String, HashMap<String, Object>> paths = getPaths();
        String path = (String) paths.get(territory).get("path");

        AWTPathProducer pathProducer = new AWTPathProducer();
        PathParser pathParser = new PathParser();

        pathParser.setPathHandler(pathProducer);

        pathParser.parse(path);

        PathIterator pathIterator = pathProducer.getShape().getPathIterator(null);

        Path2D.Double path2D = new Path2D.Double();

        // copy the path data into the path2d
        double[] cords = new double[6];
        while (!pathIterator.isDone()) {
            int segmentType = pathIterator.currentSegment(cords);

            switch (segmentType) {
                case PathIterator.SEG_MOVETO:
                    path2D.moveTo(cords[0], cords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    path2D.lineTo(cords[0], cords[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    path2D.quadTo(cords[0], cords[1], cords[2], cords[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    path2D.curveTo(cords[0], cords[1], cords[2], cords[3], cords[4], cords[5]);
                    break;
                case PathIterator.SEG_CLOSE:
                    path2D.closePath();
                    break;
            }

            pathIterator.next();
        }

        // rescale path2ds to be centered on screen
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(0.7, 0.7);
        AffineTransform translate = AffineTransform.getTranslateInstance(143, 40);

        path2D = (Path2D.Double) scaleTransform.createTransformedShape(path2D);
        path2D.transform(translate);

        return path2D;
    }

    /*
    description: converts JSON file containing SVG paths of each territory into a map
    pre-condition: called by getPath2d
    post-condition: returns map containing the ids and paths of all territory outlines to getPath2d
    */
    public static Map<String, HashMap<String, Object>> getPaths() throws IOException {
        // create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream file = Util.class.getResourceAsStream("/paths.json"); // grab json file containing paths using inputstream to support jar packaging
        List<HashMap<String, Object>> list = objectMapper.readValue(
                file,
                objectMapper.getTypeFactory().constructCollectionType(List.class, HashMap.class)
        );

        // convert list to map with "id" as the key
        return list.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("id"),
                        item -> item
                ));
    }

    /*
    description: plays the game soundtrack in a loop
    pre-condition: called by playButton in Main
    post-condition: loops the soundtrack infinitely while the program runs
    */
    public static void playSoundtrack() {
        try {
            InputStream soundFile = Util.class.getResourceAsStream("/riskSoundtrack.wav");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(soundFile);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
