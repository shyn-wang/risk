import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
    // converts svg path to path2d shape/coordinates and scales down
    public static Path2D getPath2d(String territory) throws Exception {
        // add svg paths of all territories to map with territory names as the keys
        Map<String, HashMap<String, Object>> paths = Util.getPaths();
        String path = (String) paths.get(territory).get("path");

        AWTPathProducer pathProducer = new AWTPathProducer();
        PathParser pathParser = new PathParser();

        // Link the parser and producer
        pathParser.setPathHandler(pathProducer);

        // Parse the path data
        pathParser.parse(path);

        // Retrieve the ExtendedGeneralPath
        PathIterator pathIterator = pathProducer.getShape().getPathIterator(null);

        // Create a Path2D.Double to store the path
        Path2D.Double path2D = new Path2D.Double();

        // Copy the path data into the Path2D
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

        path2D = (Path2D.Double) scaleTransform.createTransformedShape(path2D);

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
}
