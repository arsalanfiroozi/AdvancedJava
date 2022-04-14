import java.util.*;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files

public class App {
    public static void main(String[] args) throws Exception {
        // Read From File

        Map<String, Node> S2N = new HashMap<>();
        Tree G = new Tree();
        try {
            File myObj = new File("./Input.txt");
            System.out.println("Attempting to read from file in: " + myObj.getCanonicalPath());
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arrOfStr = data.split(" ", 5);
                if (!arrOfStr[0].equals("Find")) {
                    System.out.println(arrOfStr[0] + " ==> " + arrOfStr[1]);
                    Node n1;
                    if (S2N.get(arrOfStr[0]) != null)
                        n1 = S2N.get(arrOfStr[0]);
                    else {
                        n1 = new Node();
                        S2N.put(arrOfStr[0], n1);
                        G.addNode(n1);
                    }
                    Node n2;
                    if (S2N.get(arrOfStr[1]) != null)
                        n2 = S2N.get(arrOfStr[1]);
                    else {
                        n2 = new Node();
                        S2N.put(arrOfStr[1], n2);
                        G.addNode(n2);
                    }
                    Edge e = new DirectedEdge(n1, n2);
                    G.addEdge(e);
                } else {
                    data = myReader.nextLine();
                    arrOfStr = data.split(" ", 5);
                    System.out.println("From: " + arrOfStr[0] + ", To: " + arrOfStr[1]);
                    ArrayList<Edge> path = G.getPath(S2N.get(arrOfStr[0]), S2N.get(arrOfStr[1]));
                    if (path != null) {
                        try {
                            FileWriter myWriter = new FileWriter("path.txt");
                            for (int i = path.size() - 1; i >= 0; i--) {
                                for (String e : S2N.keySet()) {
                                    if (S2N.get(e).equals(path.get(i).getSource())) {
                                        System.out.println(e);
                                        myWriter.write(e + "\n");
                                    }
                                }
                            }
                            myWriter.write(arrOfStr[1]);
                            myWriter.close();
                            System.out.println("Successfully wrote to the file.");
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            FileWriter myWriter = new FileWriter("path.txt");
                            myWriter.write("No path was found!");
                            myWriter.close();
                            System.out.println("Successfully wrote to the file.");
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
        }
    }
}