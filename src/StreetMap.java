import java.util.List;

public class StreetMap {
    public static void main(String[] args) throws Exception {
        String filename = args[0];
        int i = 1;
        boolean show = false;
        boolean directions = false;
        String startNode = new String();
        String endNode = new String();
        while (i < args.length) {
            if (args[i].equals("--show")) {
                show = true;
            } else if (args[i].equals("--directions")) {
                directions = true;
                startNode = args[i + 1];
                endNode = args[i + 2];
                i += 2;
            }
            i += 1;
        }
        Graph g = GraphParser.parse(filename);
        List<Edge> shortestPath = null;
        if (directions) {
            shortestPath= Dijkstra.runDijkstra(g, startNode, endNode);
            Double shortestDistance = 0.0;
            for (Edge e: shortestPath) {
                shortestDistance = shortestDistance + e.getDistance();
            }
            System.out.println(shortestDistance);
        }
        if (show) {
            MapFrame map = new MapFrame(g.getEdges(), shortestPath);
            map.setVisible(true);
        }
        
    } 
}
