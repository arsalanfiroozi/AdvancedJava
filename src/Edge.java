import java.util.*;

abstract public class Edge{
    int     edgeID = 0;
    boolean isDirected;

    //directed edges
    public Node getSource(){ return new Node();}
    public void setSource(Node source){}
    public Node getDestination(){ return new Node();}
    public void setDestination(Node destination){}

    //undirected edges
    public ArrayList<Node> getNodes(){ return new ArrayList<Node>();}
    public void setNodes(ArrayList<Node> nodes){}


}




