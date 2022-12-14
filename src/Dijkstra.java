import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {
    public static List<Edge> runDijkstra(Graph g, String startID, String endID) {
        Map<Node, Boolean> visited = new HashMap<>();
        Map<Node, Double> distance = new HashMap<>();
        Map<Node, Edge> path = new HashMap<>();

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> (int) (distance.get(a) - distance.get(b)));
        for (Node n : g.getNodes()) {
            visited.put(n, false);
            distance.put(n, Double.MAX_VALUE);
            path.put(n, null);
            if (n.getId().equals(startID)) {
                distance.replace(n, 0.0);
                pq.add(n);
            }
        }
        
        while (pq.size() > 0) {
            Node current = pq.poll();
            if (current.getId().equals(endID)) {
                List<Edge> shortestPath = new ArrayList<>();
                Edge e = path.get(current);
                while (e != null) {
                    shortestPath.add(e);
                    Node previous = e.getNeighbor(current);
                    e = path.get(previous);
                    current = previous;
                }
                return shortestPath;
            }
            visited.replace(current, true);
            for (Edge e : g.getConnectingEdges(current)) {
                Node next = e.getNeighbor(current);
                if (!visited.get(next)) {
                    if (distance.get(next) > distance.get(current) + e.getDistance()) {
                        distance.replace(next, distance.get(current) + e.getDistance());
                        path.replace(next, e);
                        pq.add(next);
                    }
                }
            }
        }
        return null;
    }
}