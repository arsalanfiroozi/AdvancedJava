import java.util.*;

public class Node {
    ArrayList<Edge> connected_edges = new ArrayList<Edge>();
    String Name = null;

    public void AddEdge(Node n, Edge e, String name, String Stat) throws Exception {    
        Name = name;    
        if(Stat == "add"){
            n.connected_edges.add(e);
        } else {
            n.connected_edges.remove(e);
        }
    }
    public int getInDegree(Node n) throws Exception {
        System.out.println("Number of Edges: "+n.connected_edges.size());
        int in = 0;
        for (int i = 0; i < n.connected_edges.size(); i++){
            if(n.connected_edges.get(i).directed && n.connected_edges.get(i).end==n)
                in = in + 1;
            if(n.connected_edges.get(i).directed==false)
                in = in + 1;
        }
        return in;
    }
    public int getOutDegree(Node n) throws Exception {
        System.out.println("Number of Edges: "+n.connected_edges.size());
        int out = 0;
        for (int i = 0; i < n.connected_edges.size(); i++){
            if(n.connected_edges.get(i).directed && n.connected_edges.get(i).start==n)
                out = out + 1;
            if(n.connected_edges.get(i).directed==false)
                out = out + 1;
        }
        return out;
    }
}
