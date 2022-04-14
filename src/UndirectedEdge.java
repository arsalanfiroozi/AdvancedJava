import java.util.*;

public class UndirectedEdge extends Edge {
    ArrayList<Node> nodes  = new ArrayList<Node>();
    
    public UndirectedEdge() throws Exception {
        nodes.add(new Node());
        nodes.add(new Node());
        isDirected = false;
    }
    public UndirectedEdge(Node nodeA, Node nodeB/* , int edgeID */) throws Exception {
        nodes.add(nodeA);
        nodes.add(nodeB);
        //this.edgeID = edgeID;
		nodeA.edges.add(this);
		nodeB.edges.add(this);
        isDirected = false;
    }


    public ArrayList<Node> getNodes() {
        return nodes;
    }
    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
    /* public int getEdgeID(){
        return edgeID;
    }
    public void setEdgeID(int edgeID){
        this.edgeID = edgeID;
    } */



    

}
