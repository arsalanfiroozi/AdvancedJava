import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        //TEST

        
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Edge e1 = new DirectedEdge(n1, n2);
        Edge e2 = new DirectedEdge(n2, n3);
        Edge e3 = new DirectedEdge(n3, n3);
        Edge e4 = new UndirectedEdge(n1, n3);
        Edge e5 = new UndirectedEdge(n1, n1);

        n1.addEdge(e1);
        n1.addEdge(e4);
        n1.addEdge(e5);
        n1.addEdge(e5);
        
        n2.addEdge(e1);
        n2.addEdge(e2);
        
        n3.addEdge(e2);
        n3.addEdge(e3);
        n3.addEdge(e3);
        n3.addEdge(e4);

        ArrayList<Edge> ed = n1.getEdges();
        ArrayList<Node> no = e5.getNodes();

         for (int i = 0; i < 4; i++) {
            if(ed.get(i).isDirected){
                System.out.println(i+ " " +ed.get(i).getSource().nodeID+"  "+ed.get(i).getDestination().nodeID);
            } 
        } 

        for (int i = 0; i < 4; i++) {
            //if(ed.get(i).isDirected){
                System.out.println(ed.get(i).isDirected);
            //} else {

            //}
        }        
        
        for (int i = 0; i < 4; i++) {
            //if(ed.get(i).isDirected){
                System.out.println(ed.get(i).isDirected);
            //} else {

            //}
        }

    }
}