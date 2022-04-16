import java.util.*;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files

public class FileGraph {
    Map<String, Node> S2N = new HashMap<>();
    Tree G = new Tree();
    String fileName;
    String d_fileName;

    public FileGraph(String fileName, String d_fileName) throws Exception {
        this.fileName = fileName;
        this.d_fileName = d_fileName;
        try {
            File myObj = new File(fileName);
            System.out.println("Attempting to read from file in: " + myObj.getCanonicalPath());
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arrOfStr = data.split(" ", 5);
                if (!arrOfStr[0].equals("Find")) {
                    if (arrOfStr.length == 2) {
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
                        FileException("Corrupted File.");
                    }
                }
                if(arrOfStr[0].equals("Find")) {
                    break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
        }
    }

    public void setInputFile(String fileName) throws Exception {
        this.fileName = fileName;
    }

    public void setDestinationFile(String fileName) throws Exception {
        this.d_fileName = fileName;
    }

    public ArrayList<Edge> FindPath() throws Exception {
        ArrayList<Edge> path = null;
        try {
            File myObj = new File(fileName);
            System.out.println("Attempting to read path from file in: " + myObj.getCanonicalPath());
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arrOfStr = data.split(" ", 5);
                if (arrOfStr.length <= 2 && arrOfStr[0].equals("Find")) {
                    data = myReader.nextLine();
                    arrOfStr = data.split(" ", 5);
                    if (arrOfStr.length == 2) {
                        System.out.println("From: " + arrOfStr[0] + ", To: " + arrOfStr[1]);
                        path = G.getPath(S2N.get(arrOfStr[0]), S2N.get(arrOfStr[1]));
                        path.add(0, new DirectedEdge(S2N.get(arrOfStr[1]), S2N.get(arrOfStr[1])));
                    } else {
                        FileException("Corrupted File.");
                    }
                } else if (arrOfStr.length > 2) {
                    FileException("Corrupted File.");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        if (path == null)
            FileException("No Request.");
        return path;
    }

    public void WritePath(ArrayList<Edge> path) throws Exception {
        if (path != null) {
            try {
                FileWriter myWriter = new FileWriter(d_fileName);
                for (int i = path.size() - 1; i >= 0; i--) {
                    for (String e : S2N.keySet()) {
                        if (S2N.get(e).equals(path.get(i).getSource())) {
                            System.out.println(e);
                            myWriter.write(e + "\n");
                        }
                    }
                }
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            FileException("No path was found!");
        }
    }

    public void FileException(String s) throws Exception {
        try {
            FileWriter myWriter = new FileWriter(d_fileName);
            myWriter.write(s);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.exit(0);
    }

}
