import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sound.sampled.*;

public class Util {
    // converts svg path to path2d shape/coordinates and scales down
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

        AffineTransform scaleTransform = AffineTransform.getScaleInstance(0.7, 0.7);
        AffineTransform translate = AffineTransform.getTranslateInstance(143, 40);

        path2D = (Path2D.Double) scaleTransform.createTransformedShape(path2D);
        path2D.transform(translate);

        return path2D;
    }

    public static Map<String, HashMap<String, Object>> getPaths() throws IOException {
        // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Read the JSON file and convert it to a List of HashMaps
        File file = new File("src/paths.json");
        List<HashMap<String, Object>> list = objectMapper.readValue(
                file,
                objectMapper.getTypeFactory().constructCollectionType(List.class, HashMap.class)
        );

        // Convert the List to a Map with "id" as the key
        return list.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("id"),
                        item -> item
                ));
    }

    public static void playSoundtrack() {
        try {
            File soundFile = new File("src/sounds/riskSoundtrack.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
