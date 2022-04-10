import java.util.*;

public class Tree extends DirectedGraph {
	
	public Tree() {
    }
	
	public void addEdge(DirectedEdge e1) throws Exception {
		if (e1.getDestination().getInDegree() == 0 && e1.getDestination() != e1.getSource()) {
			e1.getSource().addEdge(e1);
			e1.getDestination().addEdge(e1);
			edges.add(e1);
		}
        else {
			System.out.println("Invalid edge for Tree class");
		}
    }
	
	public void addNode(Node n1) throws Exception {
		if (n1.getInDegree() <= 1) {
			for (int i = 0; i < edges.size(); i++) {
				if (n1.edges.get(i).isDirected) {
					addEdge(n1.edges.get(i));
				}
			}
			nodes.add(n1);
		}
		else {
			System.out.println("Invalid Node for Tree class");
		}
	}
	
	public Node getFather(Node child) {
		Node par = child;
		for (int i = 0; i < child.edges.size(); i++) {
			if (child.edges.get(i).getDestination() == child) {
				par = child.edges.get(i).getSource();
			}
		}
		return par;
	}
	
	public ArrayList<Node> getChildren(Node par) {
		ArrayList<Node> children = new ArrayList<>();
		for (int i = 0; i < par.edges.size(); i++) {
			if (par.edges.get(i).getSource() == par) {
				children.add(par.edges.get(i).getDestination());
			}
		}
		return children;
	}
	
	public ArrayList<Node> getAncestors(Node n) {
		ArrayList<Node> anc = new ArrayList<>();
		Node now = n;
		while (now.nodeID != getFather(now).nodeID) {
			now = getFather(now);
			anc.add(now);
		}
		return anc;
	}
	
}




