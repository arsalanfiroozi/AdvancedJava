import java.util.*;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files


public class TableGraph {
   
    Map<String, Node> S2N = new HashMap<>();
    Graph G = new Graph();
    String fileName;
    String d_fileName;

    public TableGraph(String fileName, String d_fileName) throws Exception {
        this.fileName   = fileName;
        this.d_fileName = d_fileName;
        try {
            File myObj = new File(fileName);
            System.out.println("\nAttempting to read from file in: " + myObj.getCanonicalPath());
            Scanner myReader = new Scanner(myObj);
            
            int lineNo = 0;
            String nextInputLine = myReader.nextLine();
            while (myReader.hasNextLine()) {
                String currentInputLine = nextInputLine;
                nextInputLine = myReader.nextLine();
                if(currentInputLine.equals("Find")) {
                    break;
                }
                //Adding nodes
                for (int ci = 0; ci < currentInputLine.length(); ci++) {
                    if (currentInputLine.charAt(ci) == '&'){
                        String nodeName = String.format("%d_%d",lineNo, ci);
                        Node n1 = new Node();
                        S2N.put(nodeName, n1);
                        G.addNode(n1);
                    }
                }
                //Adding the portal node
                Node portal = new Node();
                S2N.put("portal", portal);
                G.addNode(portal);

                //for (String i : S2N.keySet()) {
                //    System.out.println("key: " + i );
                //}
                lineNo++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nFile Not Found!");
            e.printStackTrace();
        }


        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            int lineNo = 0;
            String nextInputLine = myReader.nextLine();
            while (myReader.hasNextLine()) {   
                String currentInputLine = nextInputLine;
                nextInputLine = myReader.nextLine();            
                //Adding edges
                for (int i = 0; i < currentInputLine.length(); i++) {
                    if (currentInputLine.charAt(i) == '&' || 
                        currentInputLine.charAt(i) == '0' ){
                        String nodeName1;
                        String nodeName2;
                        Edge e;
                        if (currentInputLine.charAt(i) == '&'){
                            nodeName1 = String.format("%d_%d", lineNo, i);
                        } else {
                            nodeName1 = "portal";
                        }
                                                       
                        //connecting to right node
                        if(i+1<currentInputLine.length()){
                            switch(currentInputLine.charAt(i+1)){
                                case '&':
                                nodeName2 = String.format("%d_%d", lineNo, i+1);
                                e = new UndirectedEdge(S2N.get(nodeName1), S2N.get(nodeName2));
                                G.addEdge(e);
                                break;
                                case '0':
                                nodeName2 = "portal";
                                e = new UndirectedEdge(S2N.get(nodeName1), S2N.get(nodeName2));
                                G.addEdge(e); 
                                break;                                   
                            }                                    
                        }
                        
                        //connecting to bottom node
                        if(i<nextInputLine.length()){
                            switch(nextInputLine.charAt(i)){
                                case '&':
                                nodeName2 = String.format("%d_%d", lineNo+1, i);
                                e = new UndirectedEdge(S2N.get(nodeName1), S2N.get(nodeName2));
                                G.addEdge(e);
                                break;
                                case '0':
                                nodeName2 = "portal";
                                e = new UndirectedEdge(S2N.get(nodeName1), S2N.get(nodeName2));
                                G.addEdge(e);                                    
                                break;
                            }                                    
                        }
                    }
                }
                lineNo++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nFile Not Found!");
            e.printStackTrace();
        }
    }

    public void setInputFile(String fileName) throws Exception {
        this.fileName = fileName;
    }

    public void setDestinationFile(String fileName) throws Exception {
        this.d_fileName = fileName;
    }
    
    public ArrayList<Node> FindPath() throws Exception {
        ArrayList<Node> path = null;
        try {
            File myObj = new File(fileName);
            System.out.println("\nAttempting to read path from file in: " + myObj.getCanonicalPath());
            Scanner myReader = new Scanner(myObj);
            
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arrOfStr = data.split(" ", 5);
                if (arrOfStr.length <= 2 && arrOfStr[0].equals("Find")) {
                    data = myReader.nextLine();
                    arrOfStr = data.split(" ", 5);
                    if (arrOfStr.length == 2) {
                        System.out.println("From: " + arrOfStr[0] + ", To: " + arrOfStr[1]);
                        path = G.DFS(S2N.get(arrOfStr[0]), S2N.get(arrOfStr[1]));
        //                path.add(0, new DirectedEdge(S2N.get(arrOfStr[1]), S2N.get(arrOfStr[1])));
                    } else {
                        FileException("\nCorrupted File.");
                    }
                } else if (arrOfStr.length > 2) {
                    FileException("\nCorrupted File.");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nFile Not Found!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        if (path == null)
            FileException("\nNo Request.");
        return path;
    }
    
    public void WritePath(ArrayList<Node> path) throws Exception {
        if (path.size() != 0) {
            try {
                FileWriter myWriter = new FileWriter(d_fileName);
                File myObj = new File(fileName);
                Scanner myReader = new Scanner(myObj);
                String nodeName; 
                int lineNo = 0;
                char at = '@';

                while (myReader.hasNextLine()) {
                    String currentInputLine = myReader.nextLine();
                    for (int i = 0; i < currentInputLine.length(); i++) {
                        nodeName = String.format("%d_%d", lineNo, i);
                        if (path.contains(S2N.get(nodeName))) {
                            currentInputLine = currentInputLine.substring(0, i) 
                                               + at + currentInputLine.substring(i+1);
                        }
                    }
                    myWriter.write(currentInputLine + "\n");
                    lineNo++;
                }

                myReader.close();
                myWriter.close();
                System.out.println("\nSuccessfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("\nAn error occurred.");
                e.printStackTrace();
            }
        } else {
            try {
                FileWriter myWriter = new FileWriter(d_fileName);
                myWriter.write("No path was found!" + "\n");
                myWriter.close();

                FileException("\nNo path was found!");
                System.out.println("\nSuccessfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("\nAn error occurred.");
                e.printStackTrace();
            }
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

