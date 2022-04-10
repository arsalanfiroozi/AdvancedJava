import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        //TEST

        
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
		Node n4 = new Node(4);
        Edge e1 = new DirectedEdge(n1, n2);
        Edge e2 = new DirectedEdge(n2, n3);
		Edge e3 = new DirectedEdge(n1, n4);
        //Edge e3 = new DirectedEdge(n3, n3);
        //Edge e4 = new UndirectedEdge(n1, n3);
        //Edge e5 = new UndirectedEdge(n1, n1);

		Tree G = new Tree();
		G.addNode(n1);
		G.addNode(n2);
		G.addNode(n3);
		G.addNode(n4);
		
		System.out.println(G.getFather(n1).nodeID);
		System.out.println(G.getFather(n2).nodeID);
		System.out.println(G.getFather(n3).nodeID);
		System.out.println(G.getFather(n4).nodeID);
		
		System.out.println(G.getChildren(n1).get(0).nodeID + " " + G.getChildren(n1).get(1).nodeID);
		
		System.out.println(G.getAncestors(n3).get(0).nodeID + " " + G.getAncestors(n3).get(1).nodeID + "\n");
		
		ArrayList<Edge> path = G.getPath(n1,n3);
		for (int i = path.size()-1; i >= 0; i--) {
			System.out.println(path.get(i).getSource().nodeID + " " + path.get(i).getDestination().nodeID);
		}
		
		
        /*ArrayList<Edge> ed = n1.getEdges();
        ArrayList<Node> no = e5.getNodes();*/
		
		/*for (int i = 0; i < 3; i++) {
			System.out.println(ed.get(i));
		}*/
		
        /*for (int i = 0; i < 4; i++) {
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
        }*/

    }
}