import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {
    public static List<Edge> runDijkstra(Graph g, String startID, String endID) {
        Map<Node, Boolean> visited = new HashMap<>();
        Map<Node, Double> distance = new HashMap<>();
        Map<Node, List<Edge>> path = new HashMap<>();

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> (int) (distance.get(a) - distance.get(b)));
        for (Node n : g.getNodes()) {
            visited.put(n, false);
            distance.put(n, Double.MAX_VALUE);
            path.put(n, null);
            if (n.getId().equals(startID)) {
                distance.replace(n, 0.0);
                path.replace(n, new ArrayList<Edge>());
                pq.add(n);
            }
        }
        
        while (pq.size() > 0) {
            Node previous = pq.poll();
            if (previous.getId() == endID) {
                return path.get(previous);
            }
            visited.replace(previous, true);
            for (Edge e : g.getConnectingEdges(previous)) {
                Node next = e.getNeighbor(previous);
                if (!visited.get(next)) {
                    if (distance.get(next) > distance.get(previous) + e.getDistance()) {
                        distance.replace(next, distance.get(previous) + e.getDistance());
                        List<Edge> newPath = new ArrayList<>(path.get(previous));
                        newPath.add(e);
                        path.replace(next, newPath);
                        pq.add(next);
                    }
                }
            }
        }
        return null;
    }
}