import java.util.Date;

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
