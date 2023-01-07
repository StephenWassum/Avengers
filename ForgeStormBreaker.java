package avengers;
/**
 * 
 * Given the 2D array where the values are the flux intensity data of the 
 * Neutron Star, calculate the total flux that Thor has to endure. 
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../Avengers directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/ForgeStormBreaker forgestormbreaker.in forgestormbreaker.out
 */

public class ForgeStormBreaker {
	public static void main (String [] args) {
        
        if ( args.length < 2 ) {
            StdOut.println("Execute: java ForgeStormBreaker <INput file> <OUTput file>");
            return;
        }

        // read file names from command line
        String forgeStormbreakerInputFile = args[0];
        String forgeStormbreakerOutputFile = args[1];

	    // Set the input file.
        StdIn.setFile(forgeStormbreakerInputFile);
        int rows = StdIn.readInt();
        int cols = StdIn.readInt();
        
        //Sets an Empty Flux
        Integer Flux = 0;
        //Calculates Flux by adding up every value of the input array.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Flux += StdIn.readInt();
            }
        }

        //Outputs flux value to File
        String FluxString = Flux.toString();
        StdOut.setFile(forgeStormbreakerOutputFile);
        forgeStormbreakerOutputFile = "";
        forgeStormbreakerOutputFile += FluxString;
        StdOut.print(forgeStormbreakerOutputFile);
    }
}
