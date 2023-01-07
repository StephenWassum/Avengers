package avengers;
import java.util.*;
/**
 * Given a starting event and an Adjacency Matrix representing a graph of all possible 
 * events once Thanos arrives on Titan, determine the total possible number of timelines 
 * that could occur AND the number of timelines with a total Expected Utility (EU) at 
 * least the threshold value.
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../Avengers directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 */

public class UseTimeStone {

    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java UseTimeStone <INput file> <OUTput file>");
            return;
        }

        //initalizes file and sets to int theshold and vertice (number of nodes in graph)
        String UseTimeStoneInputFile = args[0];
        String UseTimeStoneOutputFile = args[1];
        StdIn.setFile(UseTimeStoneInputFile);
        int Threshold = StdIn.readInt();
        int Vertices = StdIn.readInt();

        //creates list of event values to be used in finding final eu
        LinkedList<Integer> EventValues = new LinkedList<Integer>();  
        for (int i= 0; i < Vertices; i++){
            int EventNumber = StdIn.readInt();
            int EuValue = StdIn.readInt();
            EventValues.add(EventNumber, EuValue);
        }

          // creates graph represted as list of lists, reads from input file to crate
        LinkedList<LinkedList<Integer>> EventGraph = new LinkedList<LinkedList<Integer>>();
        for (int i = 0; i < Vertices; i++) {
            int EventIndex = i;
            LinkedList<Integer> DirectedEdges = new LinkedList<Integer>();  
            for (int j = 0; j < Vertices; j++) {
                int check = StdIn.readInt();
                if(check==1){
                    DirectedEdges.add(j);
                }     
            } 
            EventGraph.add(EventIndex, DirectedEdges);
        }

        /*
        Boolean[] NetworkCheck = new Boolean[Vertices];
        for (int index = 0; index < Vertices; index++) {
            NetworkCheck[index] = false;
        }*/

        //initalizes list of paths using dfs be;pw, sets timelines to size of list, and starts count at 0
        LinkedList<String> paths = DFS(0, "", EventGraph);
        int timelines = paths.size();
        int timelinesOverThreshold = 0;

        /*
        for (int index = 0; index < paths.size(); index++) {
            System.out.println(paths.get(index));
        }*/

        // reads over every string char in every path gets EU values and adds them to find final
        // eu value if over threshold ups count
        for (int index = 0; index < paths.size(); index++) {
            int EU = 0;
            for (int j = 0; j < paths.get(index).length(); j++) {
                EU += EventValues.get(Integer.parseInt(String.valueOf(paths.get(index).charAt(j))));
            }
            //System.out.println(EU);
            if(EU >= Threshold){
                timelinesOverThreshold++;
            }
        }

        //sets output file and write to it
        StdOut.setFile(UseTimeStoneOutputFile);
        UseTimeStoneOutputFile = "";
        UseTimeStoneOutputFile += timelines + "\n" + timelinesOverThreshold;
        StdOut.print(UseTimeStoneOutputFile);        
    }

    private static LinkedList<String> DFS(int vertice, String list, LinkedList<LinkedList<Integer>> EventGraph)
        {
   
        //creates a list of possible paths to be returned
        //adds a string that is equal to the input string which should alway be empty "" representing a linked
        //list of nodes
        //with every iteration possible paths should be filled with a new string which are all recursivley added
        // to string list.
        LinkedList<String> possiblepaths = new LinkedList<String>();
        String thisVertice = Integer.toString(vertice);
        list = list + thisVertice;
        possiblepaths.add(list);
 
        // Recur for all the vertices adjacent to this
        // vertex
        for (int i = 0; i < EventGraph.get(vertice).size(); i++) {

            int adjPerson = EventGraph.get(vertice).get(i);

            LinkedList<String> check = DFS(adjPerson, list, EventGraph);
                for (int index = 0; index < check.size(); index++) {
                    possiblepaths.add(check.get(index));
                }
            }
            return possiblepaths;
        }
}
