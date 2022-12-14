import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class GraphParser {
    public static Graph parse(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            List<String[]> edgeData = new ArrayList<>();
            Map<String, Node> nodeMap = new HashMap<>();
            Set<Node> nodes = new HashSet<>();
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                if (st.nextToken().equals("i")) {
                    Node n = new Node(st.nextToken(), Double.parseDouble(st.nextToken()), 
                            Double.parseDouble(st.nextToken()));
                    nodeMap.put(n.getId(), n);
                    nodes.add(n);
                } else {
                    edgeData.add(new String[]{st.nextToken(), st.nextToken(), st.nextToken()});
                }
            }
            
            Set<Edge> edges = new HashSet<>();
            for (String[] s : edgeData) {
                Edge e = new Edge(s[0], nodeMap.get(s[1]), nodeMap.get(s[2]));
                edges.add(e);
            }

            Graph g = new Graph(nodes, edges);
            br.close();
            return g;
        } catch (Exception ignored) {
            System.out.println("Read fail");
        }
        return null;
    }
}
