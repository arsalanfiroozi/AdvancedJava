import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Read From File
        try {
            FileGraph G = new FileGraph("Input.txt","path.txt"); // Input1 => Input4
            ArrayList<Edge> path = G.FindPath();
            G.WritePath(path);
        } catch(Exception e) {
            System.out.println("Something wrong happened!");
        }
    }
}