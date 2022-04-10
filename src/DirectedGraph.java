import java.util.*;

public class DirectedGraph extends Graph {
	//ArrayList<DirectedEdge> edges = new ArrayList<Edge>();
    
	public DirectedGraph() {
    }
	
	public void addEdge(Edge e1) throws Exception {
		if (e1.isDirected) {
			e1.getSource().addEdge(e1);
			e1.getDestination().addEdge(e1);
			edges.add(e1);
		}
		else {
			System.out.println("Invalid Edge for DirectedGraph class");
		}
    }
	
	public void addNode(Node n1) throws Exception {
        for (int i = 0; i < edges.size(); i++) {
			if (n1.edges.get(i).isDirected) {
				addEdge(n1.edges.get(i));
			}
        }
        nodes.add(n1);
    }
	
}




