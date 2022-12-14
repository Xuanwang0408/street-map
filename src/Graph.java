import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Set<Node> nodes;
    private Set<Edge> edges;
    private Map<Node, Set<Edge>> connectingEdges;

    public Graph(Set<Node> nodes, Set<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        connectingEdges = new HashMap<>();
        for (Node n: nodes) {
            connectingEdges.put(n, new HashSet<>());
        }
        
        for (Edge e: edges) {
            connectingEdges.get(e.getInter1()).add(e);
            connectingEdges.get(e.getInter2()).add(e);
        }
    }

    public Set<Node> getNodes() {
        return this.nodes;
    }

    public Set<Edge> getEdges() {
        return this.edges;
    }

    public Set<Edge> getConnectingEdges(Node n) {
        return connectingEdges.get(n);
    }
}
