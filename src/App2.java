import java.util.*;

public class App2 {
    public static void main(String[] args) throws Exception {
        // Read From File
        try {
            TableGraph G = new TableGraph("Input4.txt","path.txt"); // Input1 => Input4
            ArrayList<Node> path = G.FindPath();
            G.WritePath(path);
        } catch(Exception e) {
            System.out.println("Something wrong happened!\n" + e);
        }
    }
}