import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    // root path for the main entry file
    public static String pathMain = ".";
    // root path for the aux files
    public static String pathMemory = "./auxFiles";

    // file name of the entry file
    public static String entMainName = "entrada.txt";

    // file names of the aux files
    public static String aux1Name = "aux1.txt";
    public static String aux2Name = "aux2.txt";
    public static String aux3Name = "aux3.txt";
    public static String aux4Name = "aux4.txt";
    public static String aux5Name = "aux5.txt";
    public static String aux6Name = "aux6.txt";

    // sets the memory size of the buffer
    public static int bufferLimit = 3;

    // function to get the values from "entrada.txt". also does the first ordering
    // step of the algorithm while inserting them to the aux files.
    public static int splitMainEntry() {
        // variable to count and return the amount of entries on the main entry file
        int counterEntries = 0;

        try {
            // array of writers, each being a writer for the first three aux files
            FileWriter[] writerEntArray = {
                    new FileWriter(pathMemory + "/" + aux1Name),
                    new FileWriter(pathMemory + "/" + aux2Name),
                    new FileWriter(pathMemory + "/" + aux3Name)
            };

            // create a reader for the main entry file "entrada.txt"
            File entryMain = new File(pathMain, entMainName);
            Scanner reader = new Scanner(entryMain);

            // stores the amount of times while has runned
            int counter = 0;
            // runs repeatedly while there is more lines to read on the main entry file
            // "entrada.txt"
            while (reader.hasNextLine()) {
                // picks the file writer that corresponds to counter % bufferLimit
                // considering that we have a limit of three on the buffer memory, we may see
                // that the writing file will change to the next one every three loops
                FileWriter activeWriter = writerEntArray[counter % bufferLimit];

                // array that stores the amount values corresponding to the buffer
                int[] vals = new int[bufferLimit];
                // variable to help ordering the values on the above array
                int aux;

                // string to receive the values from vals array in order
                String ordered;

                // first position of the array receives the next number read from the main entry
                // file "entrada.txt"
                vals[0] = Integer.parseInt(reader.nextLine());
                // sums 1 to the amount of entries read on the main entry file "entrada.txt"
                counterEntries++;
                // if there is any more lines in entry main "entrada.txt"
                if (reader.hasNextLine()) {
                    // second position of the array receives the next number read from the main
                    // entry file "entrada.txt"
                    vals[1] = Integer.parseInt(reader.nextLine());
                    // sums 1 to the amount of entries read on the main entry file "entrada.txt"
                    counterEntries++;

                    // order the first two values on the array, changing their positions if the
                    // first is greater than the second
                    if (vals[0] > vals[1]) {
                        aux = vals[0];
                        vals[0] = vals[1];
                        vals[1] = aux;
                    }

                    // if there is any more lines in entry main "entrada.txt"
                    if (reader.hasNextLine()) {
                        // third position of the array receives the next number read from the main entry
                        // file "entrada.txt"
                        vals[2] = Integer.parseInt(reader.nextLine());
                        // sums 1 to the amount of entries read on the main entry file "entrada.txt"
                        counterEntries++;

                        // the next two orderings consider that the first two values are already ordered

                        // order the first and the third values on the array, changing their positions
                        // if the first is greater than the third
                        if (vals[0] > vals[2]) {
                            aux = vals[0];
                            vals[0] = vals[2];
                            vals[2] = aux;
                        }

                        // order the second and the third values on the array, changing their positions
                        // if the second is greater than the third
                        if (vals[1] > vals[2]) {
                            aux = vals[1];
                            vals[1] = vals[2];
                            vals[2] = aux;
                        }

                        // sets the ordered string with the values from the vals array already ordered
                        ordered = String.format("%d\n%d\n%d", vals[0], vals[1], vals[2]);
                    }
                    // if there is no more lines to fulfill the third val, the string receives only
                    // the first two
                    else {
                        ordered = String.format("%d\n%d", vals[0], vals[1]);
                    }
                }
                // if there is no more lines to fulfill the second and third vals, the string
                // receives only the first
                else {
                    ordered = String.format("%d", vals[0]);
                }

                // prints the vals ordered on the active writer
                activeWriter.write(counter >= bufferLimit ? "\n" + ordered : ordered);

                // count +1 loop runned
                counter++;
            }

            // closes all the writers from aux files
            for (int i = 0; i < writerEntArray.length; i++) {
                writerEntArray[i].close();
            }
            // closes the reader from the main entry file "entrada.txt"
            reader.close();

            // return the amout of entries read on the main entry file "entrada.txt"
            return counterEntries;
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        }

        // return 0 in case of error during the processing
        return 0;
    }

    // recursive function that goes through the aux files, ordering them based on
    // the external merge sort algorithm and finally returning the final file with
    // all the elements ordered
    public static File alternateOrder(int activeGroup) {
        // the activeGroup parameter defines which group of aux files will be the ones
        // to read and the ones to write on this run of the function based on if
        // activeGroup is odd or even. by default, the first run will be with the group
        // 1 (an odd number), that sets the aux files 1, 2 and 3 as readers and the aux
        // files 4, 5 and 6 as the writers. after the first run, if all elements are not
        // already ordered on the first reader file, it calls itself on a recursive
        // manner, setting the activeGroup as activeGroup+1. if activeGroup was odd on
        // the last run, as soon as it receives +1, it turns to even and runs the aux
        // files inverted (1, 2 and 3 as writers and 4, 5 and 6 as readers).

        try {
            // registers the reader actual column index
            int activeColumn = 0;

            // gets the correct aux files based on the group that is set to run by the
            // activeGroup parameter being odd or even
            File auxFile1 = new File(pathMemory, (activeGroup % 2 == 1 ? aux1Name : aux4Name));
            File auxFile2 = new File(pathMemory, (activeGroup % 2 == 1 ? aux2Name : aux5Name));
            File auxFile3 = new File(pathMemory, (activeGroup % 2 == 1 ? aux3Name : aux6Name));

            // sets the scanners to the aux reader files
            Scanner[] auxReader = {
                    new Scanner(auxFile1),
                    new Scanner(auxFile2),
                    new Scanner(auxFile3)
            };

            // if there are no lines to read on the second reader file, it means that the
            // first one has all the elements already ordered and the function can stop
            // running, returning the file that contains the ordered elements
            if (!(auxReader[1].hasNextLine())) {
                // closes all the reader files so it doesn't leak any memory
                for (int i = 0; i < auxReader.length; i++) {
                    auxReader[i].close();
                }

                // returns the file that contains all the elements ordered
                return auxFile1;
            }

            // the rest of the function code below this point will only run if there is at
            // least one element on the second reader file

            // sets the aux writer files
            FileWriter[] auxWriter = {
                    new FileWriter(pathMemory + "/" + (activeGroup % 2 != 1 ? aux1Name : aux4Name)),
                    new FileWriter(pathMemory + "/" + (activeGroup % 2 != 1 ? aux2Name : aux5Name)),
                    new FileWriter(pathMemory + "/" + (activeGroup % 2 != 1 ? aux3Name : aux6Name))
            };

            // sets the array of values that will store the ones from the aux readers
            int[] vals = { 0, 0, 0 };
            // sets the array that will store the index of the last read value in each file
            int[] lastPosFile = { -1, -1, -1 };

            // while the first reader has more lines to read, it will keep running. there is
            // no need to check if the other readers has more elements to read as the first
            // one will always have an amount of values greater or equal to the others
            while (auxReader[0].hasNextLine()) {
                // array that stores the status of the aux files based in if they have already
                // reached the stop point of the group that is actually running now. if it has
                // reached, the status of the correspondent file will turn to false and it will
                // not be considered on the ordering until all the column group also finishes
                // its values. the initial status of this files correspond to if the file still
                // have elements on the next lines to be read
                boolean[] activeFile = {
                        auxReader[0].hasNextLine(),
                        auxReader[1].hasNextLine(),
                        auxReader[2].hasNextLine()
                };

                // loop that checks each position of the activeFile array and, if the file is
                // still available, reads the next element and insert it on the corresponding
                // index on the vals array, after the insertion, the lastPosFile array sums 1 to
                // the corresponding index position, updating the registered last read position
                // on the file
                for (int i = 0; i < vals.length; i++) {
                    if (activeFile[i]) {
                        vals[i] = Integer.parseInt(auxReader[i].nextLine());
                        lastPosFile[i]++;
                    }
                }

                // variable that stores the groupSize on this run, this takes on counting that
                // the group size increases exponentially on each run, so the calculation will
                // be set to bufferLimit powered by the amount of times that the function has
                // been called. take as example, the first run will have the group size as 3,
                // since it is equal to the bufferLimit, the next run will be set to 9, as the
                // groups of 3 of the 3 files will be combined into one, therefore, 3*3
                // will be 9, same as 3^2. the next run will be combining the groups of 9 of the
                // 3 files into one group of 27, same as 9*3 or 3^3, and so on.
                int groupSize = (int) (Math.pow(bufferLimit, activeGroup + 1));

                // variable that counts the amount of times the next while has runned
                int counter = 0;
                // loops while the activeFile still have any group that has elements to be read
                // yet. once all the files has being completely read, the loop will break and
                // the father while will run again with the next set of groups if the first file
                // has not been completely read yet.
                while (Utils.boolArrayContains(activeFile, true)) {
                    // stores the index of the file that has the smaller value on the actual group
                    int smallestFile = -1;

                    // loops through each activeFile checking if it is available and setting the
                    // smallestFile index with the one from the smaller value detected on the group
                    for (int j = 0; j < activeFile.length; j++) {
                        // if the smallestFile is still set as -1, the function resolves and sets the
                        // smallestFile as j
                        if (activeFile[j] && (smallestFile < 0 || vals[smallestFile] > vals[j])) {
                            smallestFile = j;
                        }
                    }

                    // gets the file to be writen based on the activeColumn % bufferLimit, since
                    // each group combined will go to the same aux writer file and this will only
                    // change on the next run of the while and this while only treats the elements
                    // of the same group
                    auxWriter[activeColumn % bufferLimit].write(
                            // checks if it is the first line to be writen on the writerFile, if false,
                            // inserts a line breaker before inserting the value
                            (counter % groupSize != 0 || activeColumn >= 3)
                                    ? String.format("\n%d", vals[smallestFile])
                                    : String.format("%d", vals[smallestFile]));

                    // sets the file as inactive if there are no more lines on the file or if the
                    // last position read is on the end of the column/group
                    activeFile[smallestFile] = auxReader[smallestFile].hasNextLine()
                            && ((lastPosFile[smallestFile] + 1) < (Math.pow(3, activeGroup) * (activeColumn + 1)));

                    // if the file is not inactive yet, vals receive the next value from the same
                    // file that the one written came from
                    if (activeFile[smallestFile]) {
                        // receives the next value on the file
                        vals[smallestFile] = Integer.parseInt(auxReader[smallestFile].nextLine());
                        // increase the last position read by 1, following the value read
                        lastPosFile[smallestFile]++;
                    }

                    // increase the counter value by 1
                    counter++;
                }

                // increase the activeColumn value by 1 once the while has ended and all the
                // values from the last columns were written on the files
                activeColumn++;
            }

            // closes all the aux reader files and all the aux writer files
            for (int i = 0; i < auxReader.length; i++) {
                auxWriter[i].close();
                auxReader[i].close();
            }

            // calls itself again to proceed with the next group of files, changing the ones
            // that were written to be read and the ones that were read to be written the
            // new values ordered. it also returns the function so the result of it (the one
            // File with all values ordered in the same File) goes back to the main function
            // after it all end. the function parameter is activeGroup + 1 for the same
            // explanation I have gave on the beginning of the function
            return alternateOrder(activeGroup + 1);
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        } catch (Exception err) {
            System.out.println("Erro inesperado");
            err.printStackTrace();
        }

        // if something goes wrong on the code above, it returns null to the main
        // function
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
        // clears the console and sets the time of the beginning of the algorithm
        // execution
        Utils.initialize();

        // if a parameter is sent to the program, its assumed that the parameter is the
        // amount of random numbers desired
        if (args.length > 0) {
            try {
                // trys to parse the parameter to a integer
                int sizeArray = Integer.parseInt(args[0]);

                // creates a random int array with the size informed with the parameter
                int[] intArray = Utils.randomIntArray(sizeArray);

                // creates a writer to set all the random values to the main entry file
                FileWriter entryMain = new FileWriter(pathMain + "/" + entMainName);

                // function that prints all the values within the file
                Utils.printArrayToFile(entryMain, intArray);

                // closes the file
                entryMain.close();
            } catch (Exception err) {
                // if something goes wrong with the parameter or the file, returns the function
                // and cancels the execution
                err.printStackTrace();
                System.out.println("\n\nErro ao tentar processar o parâmetro informado.");
                return;
            }
        }

        // does the first ordering getting values from the main entry file "entrada.txt"
        // and writting them on the aux files. also retrieves the count of how many
        // entries were detected on the main entry file
        int entriesRead = splitMainEntry();

        // prints the amount of values found on the main entry file
        System.out.println(String.format("Foram lidos %d valores no arquivo %s", entriesRead, entMainName));

        // calls the recursive function that will call itself until that is just one
        // file left with all the values ordered and returns it as a File
        File auxFileResult = alternateOrder(1);

        // it there was no error during the above function call, the variable
        // auxFileResult will be not null, therefore, we can print the results on the
        // main entry file "entrada.txt" again
        if (auxFileResult != null) {
            // calls the function that clones the result file to the main entry file
            printResult(auxFileResult);
        }
        // if there was an error, auxFileResult will be null and a custom error message
        // is displayed on the console
        else {
            System.out.println("Erro ocorrido durante o processamento nos arquivos");
        }

        // calls the function that gets the time passed between the beginning of the
        // execution and its finish, printing the ending message along with it
        Utils.finish();
    }
}
