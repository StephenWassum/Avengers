package avengers;

import java.util.ArrayList;

/**
 * Given an adjacency matrix as an input, randomly removes half of the matrix nodes. 
 * Then observes the matrix to see if the resulting adjacency matrix is still fully connected.
 * The output is a simple true or false statement to the preceding observation.
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../Avengers directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap3.in predictthanossnap3.out
*/

public class PredictThanosSnap {
    public static void main (String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }
        
        //reads info on graph from file
        String PredictThanosSnapInputFile = args[0];
        String PredictThanosSnapOutputFile = args[1];
        StdIn.setFile(PredictThanosSnapInputFile);
        int Seed = StdIn.readInt();
        StdRandom.setSeed(Seed);
        int Vertices = StdIn.readInt();
        ArrayList<SocialLife> NetworkMap = new ArrayList<>();


        //creates and map of peoples networks using the private class object in file.
        for (int i = 0; i < Vertices; i++) {
            int person = i;
            ArrayList<Integer> network = new ArrayList<>(0);

            for (int j = 0; j < Vertices; j++) {
                int acquaintance = StdIn.readInt();
                if (acquaintance == 1) {
                    network.add(j);
                }
            }

            SocialLife personsNetwork = new SocialLife(person, network);
            NetworkMap.add(personsNetwork);
        }

        //runs thanos snap using stdrandom adds people not snapped to a new array 
        ArrayList<SocialLife> RemainingPeople = new ArrayList<>(0);
        for (int i = 0; i < Vertices; i++) {
            if(StdRandom.uniform() > 0.5){
                RemainingPeople.add(NetworkMap.get(i));
            }
        }

        //fills remaining person with people not thanos snaped
        ArrayList<Integer> RemainingVertices = new ArrayList<>(0);
        for  (int i = 0; i < RemainingPeople.size(); i++) {
            RemainingVertices.add(RemainingPeople.get(i).getPerson());      
        }

        //creates boolean check and initalizes it to false
        Boolean[] NetworkCheck = new Boolean[Vertices]; //check the size depending on how i initialize arrays
        for (int index = 0; index < Vertices; index++) {
            NetworkCheck[index] = false;
        }

        DFS(RemainingPeople.get(1), NetworkCheck, RemainingVertices, NetworkMap);

        boolean Answer = true;
        for (int index = 0; index < RemainingVertices.size(); index++) {
            if(NetworkCheck[RemainingVertices.get(index)] != true){
                Answer = false;
                break;
            }
        }

        //Set final true or false value to output file.
        StdOut.setFile(PredictThanosSnapOutputFile);
        PredictThanosSnapOutputFile = String.valueOf(Answer);
        StdOut.print(PredictThanosSnapOutputFile);
    }

    private static void DFS(SocialLife person, Boolean visited[], ArrayList<Integer> alive, ArrayList<SocialLife> Network)
    {
    // Mark the current node as visited
    visited[person.getPerson()] = true;

    // Recur for all the vertices adjacent to this vertex
    for (int i = 0; i < person.getNetwork().size(); i++) {
        int adjPerson = person.getNetwork().get(i);

        //change boolean to int list where im setting values to -1; to mark visited
        // then to check get index of adj person and if they are in network coninue.
        if(!visited[adjPerson] && alive.contains(adjPerson)){
            DFS(Network.get(adjPerson), visited, alive, Network);
            }
        }
    }
 

    private static class SocialLife {
        private Integer person;
        private ArrayList<Integer> network;

        public SocialLife(Integer i, ArrayList<Integer> j) {
            person = i;
            network = j;
        }

        public Integer getPerson () {
            return person;
        }

        public ArrayList<Integer> getNetwork () {
            return network;
        }
    }
}