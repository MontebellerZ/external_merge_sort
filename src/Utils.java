import java.io.FileWriter;
import java.util.Date;
import java.util.Random;

// class to hold every function that has no main effect on the execution of the code besides visualization on console
public class Utils {
    // variable to store the beginning time of the algorithm execution
    private static Date initialTime;

    // clear the console window
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // jumps 2 lines by default
    public static void jumpLines() {
        System.out.println("");
        System.out.println("");
    }

    /**
     * @param qtd
     */
    // jumps a qtd number of lines
    public static void jumpLines(int qtd) {
        for (int i = 0; i < qtd; i++) {
            System.out.println("");
        }
    }

    // clear the console for improved visualization and sets the time that the code
    // started to run
    public static void initialize() {
        clear();
        initialTime = new Date();
    }

    // prints the ending message along with the time passed between the beginning of
    // the execution until now
    public static void finish() {
        Date finalTime = new Date();
        // gets the time difference between the beginning of the execution and the time
        // now
        var timeDifference = finalTime.getTime() - initialTime.getTime();

        // jumps 3 lines and prints the ending message
        jumpLines(3);
        System.out.println(String.format("Execução finalizada com sucesso.\nTempo de execução: %d", timeDifference));
    }

    /**
     * @param amount
     * @return int[]
     */
    // generates a random int array with the size of the parameter amount
    public static int[] randomIntArray(int amount) {
        // creates the new array with the size requested
        int[] intArray = new int[amount];

        // creates the Random instance
        Random rand = new Random();
        // sets the upper bound for the random numbers
        int maxValue = 2000;

        // loops through the array filling it with random numbers
        for (int i = 0; i < intArray.length; i++) {
            // sets the random number
            intArray[i] = rand.nextInt(maxValue);
        }

        // returns the filled array
        return intArray;
    }

    /**
     * @param printTo
     * @param intArray
     * @return boolean
     */
    // prints the array to a file sent as parameter and to a reference file, line by
    // line
    public static boolean printArrayToFile(FileWriter printTo, int[] intArray) {
        try {
            // creates writer for the reference file
            FileWriter writerReference = new FileWriter("referencia.txt", false);

            // loops through array, writing each value to a new line in the file reference
            // and in the parameter file
            for (int i = 0; i < intArray.length; i++) {
                String line = i > 0 ? String.format("\n%d", intArray[i])
                        : String.format("%d", intArray[i]);

                writerReference.write(line);
                printTo.write(line);
            }

            // closes the reference file
            writerReference.close();

            // returns true if no Exception were thrown
            return true;
        } catch (Exception err) {
            // returns false if something goes wrong
            return false;
        }
    }

    /**
     * @param arr
     * @param val
     * @return boolean
     */
    // checks if there is a specific boolean value in the given boolean array
    public static boolean boolArrayContains(boolean[] arr, boolean val) {
        // loops through the array until it finds the value, otherwise, the loop ends
        // and false is sent meaning the value was not found
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                return true;
            }
        }
        return false;
    }
}
