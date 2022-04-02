import java.util.*;

public class Edge {
    public Boolean directed = null;
    public Node start = null;
    public Node end = null;
    public ArrayList<Node> Nodes = new ArrayList<Node>();
    public static void main(String[] args) throws Exception {
        System.out.println("This is Edge class.");
    }
}

class DirectedEdge extends Edge{
    public DirectedEdge(Node a, Node b){
        directed = true;
        start = a;
        end = b;
    }
    public void Info() throws Exception{
        System.out.println("From "+start.Name+" to "+end.Name);
    }
}

class UndirectedEdge extends Edge{
    public UndirectedEdge(Node a, Node b){
        directed = false;
        Nodes.add(a);
        Nodes.add(b);
    }
    public void Info() throws Exception{
        System.out.println("From "+Nodes.get(0).Name+" to "+Nodes.get(1).Name);
    }
}