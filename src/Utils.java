import java.util.Date;

// class to hold every function that has no main effect on the execution of the code besides visualization on console
public class Utils {
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

    public static void inicializar() {
        clear();
        initialTime = new Date();
    }

    public static void finalizar() {
        Date finalTime = new Date();
        var timeDifference = finalTime.getTime() - initialTime.getTime();

        jumpLines(3);
        System.out.println(String.format("Execução finalizada com sucesso.\nTempo de execução: %d", timeDifference));
    }

    public static boolean boolArrayContains(boolean[] arr, boolean val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                return true;
            }
        }
        return false;
    }
}
