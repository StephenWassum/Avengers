package avengers;
import java.util.*;

/**
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * Implementation uses a class using Djikstras shortest path algo.
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../Avengers directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan3.in locatetitan3.out
 */

public class LocateTitan {

    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

        //reads from input to initalize files an vertices in graph
        String LocateTitanInputFile = args[0];
        String LocateTitanOutputFile = args[1];
        StdIn.setFile(LocateTitanInputFile);
        int Vertices = StdIn.readInt();

        //initalizes list for vertice values
        LinkedList<Double> PortalFunctionality = new LinkedList<Double>();  
        
        for (int i= 0; i < Vertices; i++){
            int PortalNumber = StdIn.readInt();
            Double FunctionalityScore = StdIn.readDouble();
            PortalFunctionality.add(PortalNumber, FunctionalityScore);
        }

        //initialzies undirected graph of weighted edges for vertices
        int[][] PortalEnergyCosts = new int[Vertices][Vertices];

        for (int OutgoingPortal = 0; OutgoingPortal < Vertices; OutgoingPortal++) {
            for (int DestinationPortal = 0; DestinationPortal < Vertices; DestinationPortal++) {
                PortalEnergyCosts[OutgoingPortal][DestinationPortal] = StdIn.readInt();
            }
        }

        //mulitplies undirected graph by portal functionality equation. sets new values
        for (int OutgoingPortal = 0; OutgoingPortal < Vertices; OutgoingPortal++) {
            for (int DestinationPortal = 0; DestinationPortal < Vertices; DestinationPortal++) {
                Double OutgoingPortalFunctionality = PortalFunctionality.get(OutgoingPortal);
                Double DestinationPortalFunctionality = PortalFunctionality.get(DestinationPortal);
                int PortalEnergyCost = PortalEnergyCosts[OutgoingPortal][DestinationPortal];
                Double value = PortalEnergyCost/(OutgoingPortalFunctionality*DestinationPortalFunctionality);
                if(value.intValue() != 0){
                    PortalEnergyCosts[OutgoingPortal][DestinationPortal] = value.intValue();
                }
            }
        }

        //starting point always zero and end always last vetice
        int startingPortal = 0;
        int TitanPortal = Vertices - 1;

        //runs djikstras function below
        int[] ShortestPaths = dijkstra1(PortalEnergyCosts, startingPortal, Vertices);

        //finds value of shortest path to titan from djikstras funcition output
        int ToTitanEnergyCost = ShortestPaths[TitanPortal];

        //writes and outputs shortest path value to file.
        StdOut.setFile(LocateTitanOutputFile);
        LocateTitanOutputFile = "";
        LocateTitanOutputFile += ToTitanEnergyCost;
        StdOut.print(LocateTitanOutputFile);
    }

    //used in dijkstra
    private static int minDistance(int dist[], Boolean VertexIncluded[], int TotalVertice)
    {
        // start min as max so paths are always less than.
        int min = Integer.MAX_VALUE, minVertex = -1;

        //loop to find value to every vertice from start to target. Sets min if less than
        for (int i = 0; i < TotalVertice; i++)
            if (VertexIncluded[i] == false && dist[i] <= min) {
                min = dist[i];
                minVertex = i;
            }
 
        return minVertex;
    }

    //dijkstra function for array matrix
    private static int[] dijkstra1(int graph[][], int StartingVertice, int TotalVertice)
    {
        //Initialize list that will hold containt shortest distance from the starting 
        //vertex to all of the vertex in the graph.
        int ShortestDistanceFromStart[] = new int[TotalVertice]; 

        //Boolean array that will check if if the shortest path has been checked any vertice already
        Boolean VerticeIncluded[] = new Boolean[TotalVertice];
 
        // Starts the arrays as highest value for start array and false for boolean check array
        for (int i = 0; i < TotalVertice; i++) {
            ShortestDistanceFromStart[i] = Integer.MAX_VALUE;
            VerticeIncluded[i] = false;
        }
 
        // sets distance for starting vertice. always the same
        ShortestDistanceFromStart[StartingVertice] = 0;
 
        // loop to find shortest path for every vertice
        for (int count = 0; count < TotalVertice - 1; count++) {
            // uses minDistance above to find the shortest path to a vertice
            int ThisVertex = minDistance(ShortestDistanceFromStart, VerticeIncluded, TotalVertice);
 
            // checks the index
            VerticeIncluded[ThisVertex] = true;
 
            // updates the list for the vertex
            for (int i = 0; i < TotalVertice; i++){
                if (!VerticeIncluded[i] && graph[ThisVertex][i] != 0
                    && ShortestDistanceFromStart[ThisVertex] != Integer.MAX_VALUE
                    && ShortestDistanceFromStart[ThisVertex] + graph[ThisVertex][i] < ShortestDistanceFromStart[i]){
                        ShortestDistanceFromStart[i] = ShortestDistanceFromStart[ThisVertex] + graph[ThisVertex][i]; 
                }
            }
        }
 
        //returns the array
       return ShortestDistanceFromStart;
    }
}