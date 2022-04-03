import java.util.*;

public class Node {
    int nodeID    = 0;
    ArrayList<Edge> edges  = new ArrayList<Edge>();
    
    public Node(){}
    public Node(int nodeID) {
        this.nodeID = nodeID;
    }
    

    
    public int getInDegree() throws Exception {
        int inDegree  = 0;
        for(Edge edge : edges) {
            if(edge.isDirected) {
                if(edge.getDestination().equals(this)){
                    inDegree++;
                }
            } else {
                inDegree++;
            }
        }
        return inDegree;
    }

    public int getOutDegree() throws Exception {
        int outDegree  = 0;
        for(Edge edge : edges) {
            if(edge.isDirected) {
                if(!edge.getDestination().equals(this)){
                    outDegree++;
                }
            } else {
                outDegree++;
            }
        }
        return outDegree;
    } 

    public void addEdge(Edge edgeA) throws Exception {
        edges.add(edgeA);
    }

    public void removeEdge(Edge edgeA) throws Exception {
        edges.remove(edgeA);
    }

    public ArrayList<Edge> getEdges() throws Exception {
        return edges;
    }
    
}
