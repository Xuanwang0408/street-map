import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapFrame extends JFrame {
    Set<double[]> roads = new HashSet<>();
    List<double[]> coloredRoads = new ArrayList<>();
    final static double RADIUS_MAJOR = 6378137.0;
    final static double RADIUS_MINOR = 6356752.3142;
    public MapFrame(Set<Edge> edges, List<Edge> coloredEdges) {
        super("Map");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (Edge e : edges) {
            this.roads.add(convertEdge(e));
        }
        if (coloredEdges != null) {
            for (Edge e : coloredEdges) {
                coloredRoads.add(convertEdge(e));
            }
        }
        
        MapPanel map = new MapPanel();
        add(map);
        normalize();
    }
    
    private double[] convertEdge(Edge e) {
        double lat1 = e.getInter1().getLatitude();
        double lat2 = e.getInter2().getLatitude();
        double long1 = e.getInter1().getLongitude();
        double long2 = e.getInter2().getLongitude();
        double x1 = Math.toRadians(long1) * RADIUS_MAJOR;
        double x2 = Math.toRadians(long2) * RADIUS_MAJOR;
        double y1 = - Math.log(Math.tan(Math.PI / 4 + Math.toRadians(lat1) / 2)) * RADIUS_MAJOR;
        double y2 = - Math.log(Math.tan(Math.PI / 4 + Math.toRadians(lat2) / 2)) * RADIUS_MAJOR;
        double[] arr = {x1, y1, x2, y2};
        return arr;
    }

    private void normalize() {
        double xMax = - Double.MAX_VALUE;
        double xMin = Double.MAX_VALUE;
        double yMax = - Double.MAX_VALUE;
        double yMin = Double.MAX_VALUE;
        for (double[] arr : roads) {
            if (arr[0] > xMax) {
                xMax = arr[0];
            }
            if (arr[0] < xMin) {
                xMin = arr[0];
            }
            if (arr[1] > yMax) {
                yMax = arr[1];
            }
            if (arr[1] < yMin) {
                yMin = arr[1];
            }
            if (arr[2] > xMax) {
                xMax = arr[2];
            }
            if (arr[2] < xMin) {
                xMin = arr[2];
            }
            if (arr[3] > yMax) {
                yMax = arr[3];
            }
            if (arr[3] < yMin) {
                yMin = arr[3];
            }
        }
        double xDiff = xMax - xMin;
        double yDiff = yMax - yMin;
        double ratio = yDiff / xDiff;
        int width = 1440;
        int height = (int)(width * ratio);
        if (height > 810) {
            height = 810;
            width = (int)(height / ratio);
        }
        setSize(width + 100, height + 100);

        double factor = width / xDiff;
        for (double[] arr : roads) {
            arr[0] = (arr[0] - xMin) * factor + 50;
            arr[1] = (arr[1] - yMin) * factor + 30;
            arr[2] = (arr[2] - xMin) * factor + 50;
            arr[3] = (arr[3] - yMin) * factor + 30;
        }
        for (double[] arr : coloredRoads) {
            arr[0] = (arr[0] - xMin) * factor + 50;
            arr[1] = (arr[1] - yMin) * factor + 30;
            arr[2] = (arr[2] - xMin) * factor + 50;
            arr[3] = (arr[3] - yMin) * factor + 30;
        }

    }

    public class MapPanel extends JPanel {
        public void paintComponent(Graphics g) {
            for (double[] a : roads) {
                g.drawLine((int)a[0], (int)a[1], (int)a[2], (int)a[3]);
            }
            for (double[] a : coloredRoads) {
                g.setColor(Color.RED);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));
                g.drawLine((int) a[0], (int) a[1], (int) a[2], (int) a[3]);
            }
        }
    }
}
