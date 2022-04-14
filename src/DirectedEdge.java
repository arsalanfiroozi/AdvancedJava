import java.util.*;

public class DirectedEdge extends Edge {
    Node source      = new Node();
    Node destination = new Node();


    public DirectedEdge() throws Exception {
        isDirected = true;
    }    
    public DirectedEdge(Node source, Node destination/*, int edgeID*/) throws Exception {
        this.source      = source;
        this.destination = destination;
        //this.edgeID    = edgeID;
		source.edges.add(this);
		destination.edges.add(this);
        isDirected       = true;
    }
    

    public Node getSource() {
        return source;
    }
    public void setSource(Node source) {
        this.source = source;
    }
    public Node getDestination() {
        return destination;
    }
    public void setDestination(Node destination) {
        this.destination = destination;
    }
    /*
    public int getEdgeID(){
        return edgeID;
    }
    public void setEdgeID(int edgeID){
        this.edgeID = edgeID;
    }
    */


    
}
