import java.util.*;

public class Graph {
    ArrayList<Node> nodes = new ArrayList<Node>();
    ArrayList<Edge> edges = new ArrayList<Edge>();

    public Graph() {
    }
	
    public Graph(ArrayList<Edge> edges) throws Exception {
        this.edges = edges;
        boolean tmp;
        for(Edge edge : edges) {
            if (edge.isDirected) {
                tmp = false;
                for(Node n : nodes) {
                    if(n==edge.getSource())
                        tmp = true;
                }
                if(!tmp)
                    nodes.add(edge.getSource());
                    
                tmp = false;
                for(Node n : nodes) {
                    if(n==edge.getDestination())
                        tmp = true;
                }
                if(!tmp)
                    nodes.add(edge.getDestination());

                edge.getSource().addEdge(edge);
                edge.getDestination().addEdge(edge);
            } else {
                tmp = false;
                for(Node n : nodes) {
                    if(n==edge.getNodes().get(0))
                        tmp = true;
                }
                if(!tmp)
                    nodes.add(edge.getNodes().get(0));
                    
                tmp = false;
                for(Node n : nodes) {
                    if(n==edge.getNodes().get(1))
                        tmp = true;
                }
                if(!tmp)
                    nodes.add(edge.getNodes().get(1));

                edge.getNodes().get(0).addEdge(edge);
                edge.getNodes().get(1).addEdge(edge);
            }
        }
    }


    public void addNode(Node n1) throws Exception {
        for (int i = 0; i < edges.size(); i++) {
            addEdge(n1.edges.get(i));
        }
        nodes.add(n1);
    }

    public void removeNode(Node n1) throws Exception {
        for (int i = 0; i < edges.size(); i++) {
            removeEdge(n1.edges.get(i));
        }
        nodes.remove(n1);
    }

    public void addEdge(Edge e1) throws Exception {
        if (e1.isDirected) {
            e1.getSource().addEdge(e1);
            e1.getDestination().addEdge(e1);
        } else {
            e1.getNodes().get(0).addEdge(e1);
            e1.getNodes().get(1).addEdge(e1);
        }

        edges.add(e1);
    }

    public void removeEdge(Edge e1) throws Exception {
        if (e1.isDirected) {
            e1.getSource().removeEdge(e1);
            e1.getDestination().removeEdge(e1);
        } else {
            e1.getNodes().get(0).removeEdge(e1);
            e1.getNodes().get(1).removeEdge(e1);
        }
        
        edges.remove(e1);
    }

    public ArrayList<Node> getNodes() throws Exception {
        return nodes;
    }

    public ArrayList<Edge> getEdges() throws Exception {
        return edges;
    }

    public ArrayList<Node> DFS(Node st,Node ed) throws Exception {
		Stack<Node> stk = new Stack<>();
		boolean found = false;
		boolean next_found = false;
		Node now = st;
		Node next = st;
		Node nextt = st;
		
		while (found == false && st.visited == false) {
			stk.push(now);
			now.is_in = true;
			next_found = false;
			
			for (int i = 0; i < now.edges.size(); i++) {
				if (now.edges.get(i).getNodes().get(0) == now) {
					nextt = now.edges.get(i).getNodes().get(1);	
				}
				else {
					nextt = now.edges.get(i).getNodes().get(0);
				}
				
				if (nextt.visited == false && nextt.is_in == false && next_found == false) {
					next = nextt;
					next_found = true;
				}
			}
			
			if (next_found == true) {
				now = next;
				if (now == ed) {
					found = true;
					stk.push(ed);
				}
			}
			else {
				now.visited = true;
				stk.pop();
				if (stk.empty() == false) {
					now = stk.pop();
				}
				else {
					break;
				}
			}
		}
		
		Stack<Node> stk2 = new Stack<>();
		Node n = st;
		while (stk.empty() == false) {
			n = stk.pop();
			stk2.push(n);
		}
		
		ArrayList<Node> path = new ArrayList<>();
		while (stk2.empty() == false) {
			n = stk2.pop();
			path.add(n);
		}
		
		return path;
		
	}

}
