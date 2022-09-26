import java.io.File;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.Map;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static String pathMain = ".";
    public static String pathMemory = "./memory";

    public static String entMainName = "entrada.txt";

    public static String aux1Name = "aux1.txt";
    public static String aux2Name = "aux2.txt";
    public static String aux3Name = "aux3.txt";
    public static String aux4Name = "aux4.txt";
    public static String aux5Name = "aux5.txt";
    public static String aux6Name = "aux6.txt";

    public static int bufferLimit = 3;

    // function to get the values from entrada.txt and insert them into the memory
    // entry files
    public static void splitMainEntry() {
        try {
            FileWriter[] writerEntArray = {
                    new FileWriter(pathMemory + "/" + aux1Name),
                    new FileWriter(pathMemory + "/" + aux2Name),
                    new FileWriter(pathMemory + "/" + aux3Name)
            };

            File entryMain = new File(pathMain, entMainName);
            Scanner reader = new Scanner(entryMain);

            int counter = 0;
            while (reader.hasNextLine()) {
                FileWriter activeWriter = writerEntArray[counter % bufferLimit];

                int val1;
                int val2;
                int val3;
                int aux;

                String ordered;

                val1 = Integer.parseInt(reader.nextLine());
                if (reader.hasNextLine()) {
                    val2 = Integer.parseInt(reader.nextLine());

                    if (val1 > val2) {
                        aux = val1;
                        val1 = val2;
                        val2 = aux;
                    }

                    if (reader.hasNextLine()) {
                        val3 = Integer.parseInt(reader.nextLine());

                        if (val1 > val3) {
                            aux = val1;
                            val1 = val3;
                            val3 = aux;
                        }

                        if (val2 > val3) {
                            aux = val2;
                            val2 = val3;
                            val3 = aux;
                        }

                        ordered = String.format("%d\n%d\n%d", val1, val2, val3);
                    } else {
                        ordered = String.format("%d\n%d", val1, val2);
                    }
                } else {
                    ordered = String.format("%d", val1);
                }

                activeWriter.write(counter >= bufferLimit ? "\n" + ordered : ordered);

                counter++;
            }

            for (int i = 0; i < writerEntArray.length; i++) {
                writerEntArray[i].close();
            }
            reader.close();
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public static File alternateOrder(int activeGroup) {
        try {
            int activeColumn = 0;

            File auxFile1 = new File(pathMemory, (activeGroup % 2 == 1 ? aux1Name : aux4Name));
            File auxFile2 = new File(pathMemory, (activeGroup % 2 == 1 ? aux2Name : aux5Name));
            File auxFile3 = new File(pathMemory, (activeGroup % 2 == 1 ? aux3Name : aux6Name));

            Scanner[] auxReader = {
                    new Scanner(auxFile1),
                    new Scanner(auxFile2),
                    new Scanner(auxFile3)
            };

            if (!(auxReader[1].hasNextLine())) {
                for (int i = 0; i < auxReader.length; i++) {
                    auxReader[i].close();
                }

                return auxFile1;
            }

            FileWriter[] auxWriter = {
                    new FileWriter(pathMemory + "/" + (activeGroup % 2 != 1 ? aux1Name : aux4Name)),
                    new FileWriter(pathMemory + "/" + (activeGroup % 2 != 1 ? aux2Name : aux5Name)),
                    new FileWriter(pathMemory + "/" + (activeGroup % 2 != 1 ? aux3Name : aux6Name))
            };

            int[] vals = { 0, 0, 0 };
            int[] lastPosFile = { -1, -1, -1 };

            while (auxReader[0].hasNextLine()) {
                boolean[] activeFile = {
                        auxReader[0].hasNextLine(),
                        auxReader[1].hasNextLine(),
                        auxReader[2].hasNextLine()
                };

                for (int i = 0; i < vals.length; i++) {
                    if (activeFile[i]) {
                        vals[i] = Integer.parseInt(auxReader[i].nextLine());
                        lastPosFile[i]++;
                    }
                }

                int groupSize = (int) (Math.pow(3, activeGroup + 1));

                int counter = 0;
                while (Utils.boolArrayContains(activeFile, true)) {
                    int menor = -1;

                    for (int j = 0; j < activeFile.length; j++) {
                        if (activeFile[j] && (menor < 0 || vals[menor] > vals[j])) {
                            menor = j;
                        }
                    }

                    auxWriter[activeColumn % 3].write(
                            (counter % groupSize != 0 || activeColumn>=3)
                                    ? String.format("\n%d", vals[menor])
                                    : String.format("%d", vals[menor]));

                    activeFile[menor] = auxReader[menor].hasNextLine()
                            && ((lastPosFile[menor] + 1) < (Math.pow(3, activeGroup) * (activeColumn + 1)));

                    if (activeFile[menor]) {
                        vals[menor] = Integer.parseInt(auxReader[menor].nextLine());
                        lastPosFile[menor]++;
                    }

                    counter++;
                }

                activeColumn++;
            }

            for (int i = 0; i < auxReader.length; i++) {
                auxWriter[i].close();
                auxReader[i].close();
            }

            return alternateOrder(activeGroup + 1);
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        } catch (Exception err) {
            System.out.println("Sei la mano");
            err.printStackTrace();
        }

        return null;
    }

    public static void printResult(File fileResult) {
        try {
            FileWriter entryMain = new FileWriter(pathMain + "/" + entMainName);

            Scanner reader = new Scanner(fileResult);

            while (reader.hasNextLine()) {
                String val = reader.nextLine();
                entryMain.write(reader.hasNextLine() ? val + "\n" : val);
            }

            entryMain.close();
            reader.close();
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        }

    }

    // main function, holds all the code to be executed in order
    public static void main(String[] args) throws Exception {
        Utils.inicializar();

        splitMainEntry();

        File auxFileResult = alternateOrder(1);

        printResult(auxFileResult);

        Utils.finalizar();
    }
}
