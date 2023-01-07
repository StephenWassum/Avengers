package avengers;
import java.util.*;
/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of the 
 * vertices that connect to the Mind Stone. 
 * List the names of these neurons in the output file.
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../Avengers directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons3.in mindstoneneighborneurons3.out
 */

public class MindStoneNeighborNeurons {

    public static void main (String [] args) {
        
    	if ( args.length < 2 ) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }
    	
        //read from input file initialize. Reads vertice number and initalizes graph to be filled
        String MindStoneInputFile = args[0];
        String MindStoneOutputFile = args[1];
        StdIn.setFile(MindStoneInputFile);
        int VerticeNum = StdIn.readInt();
        LinkedList<String> Vertices = new LinkedList<>();

        //fills the graph
        for (int i= 0; i < VerticeNum; i++){
            String Vertice = StdIn.readString();
            Vertices.add(Vertice);
        }

        // gets Mindstone from last vertice value
        String MindStone = Vertices.getLast();
        //System.out.println(MindStone);

        //creates list of edges 
        LinkedList<Synapses> Edges = new LinkedList<>();
        int EdgeNum = StdIn.readInt();

        //fills the list of graph edges
        for (int i= 0; i < EdgeNum; i++){
            String dendrite = StdIn.readString();
            String axon = StdIn.readString();
            Synapses Edge = new Synapses(dendrite, axon);
            Edges.add(Edge);
        }

        //initalize list of connections
        ArrayList<String> MindStoneConnections = new ArrayList<String>();

        //fills list of connections to mindstone
        for (int i = 0; i < Edges.size(); i++){
            if (Edges.get(i).getAxon().equals(MindStone)){
                MindStoneConnections.add(Edges.get(i).getDendrite());
            }
        }

        //initalizes and prints to output file.
        StdOut.setFile(MindStoneOutputFile);
        MindStoneOutputFile = "";
        for(int i = 0; i < MindStoneConnections.size(); i++){
            MindStoneOutputFile += MindStoneConnections.get(i) + "\n";
        }

        StdOut.print(MindStoneOutputFile);
    }

    // class to represent graph nodes
    private static class Synapses {
        private String dendrite;
        private String axon;

        public Synapses(String From, String Too) {
            dendrite = From;
            axon = Too;
        }

        public String getDendrite () {
            return dendrite;
        }

        public String getAxon () {
            return axon;
        }
    }

}
