import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    // Caminho raiz para o arquivo de entrada
    public static String pathMain = ".";
    // Caminho raiz para os arquivos auxiliares
    public static String pathMemory = "./auxFiles";

    // Nome do arquivo de entrada
    public static String entMainName = "entrada.txt";

    // Nomes dos arquivos auxiliares
    public static String aux1Name = "aux1.txt";
    public static String aux2Name = "aux2.txt";
    public static String aux3Name = "aux3.txt";
    public static String aux4Name = "aux4.txt";
    public static String aux5Name = "aux5.txt";
    public static String aux6Name = "aux6.txt";

    // Tamanho da memória interna
    public static int memoryLimit = 3;

    /**
     * Recebe os valores do arquivo de entrada, faz a primeira ordenação de acordo
     * com o tamanho limite da memória e insere as colunas geradas nos arquivos
     * auxiliares 1, 2 e 3.
     * 
     * @return int retorna um valor inteiro representando a quantidade de valores
     *         lidos no arquivo de entrada.
     */
    public static int splitMainEntry() {
        // Variável contadora para somar a quantidade de valores no arquivo de entrada
        int counterEntries = 0;

        try {
            // Vetor de escritores para os arquivos auxiliares 1, 2 e 3
            FileWriter[] writerEntArray = {
                    new FileWriter(pathMemory + "/" + aux1Name),
                    new FileWriter(pathMemory + "/" + aux2Name),
                    new FileWriter(pathMemory + "/" + aux3Name)
            };

            // Leitor para o arquivo de entrada
            File entryMain = new File(pathMain, entMainName);
            Scanner reader = new Scanner(entryMain);

            // Contador de vezes que o while executou
            int counter = 0;

            /**
             * Roda repetidamente até que não existam mais linhas a serem lidos no arquivo
             * de entrada
             */
            while (reader.hasNextLine()) {
                /**
                 * Variável definida para receber escritor que corresponde ao arquivo que deverá
                 * ser preenchido durante essa execução do while.
                 */
                FileWriter activeWriter = writerEntArray[counter % memoryLimit];

                // Vetor que receberá um grupo de 3 valores lidos diretamente da entrada
                int[] vals = new int[memoryLimit];
                // Variável auxiliar para realizar as trocas de posições dentro do vetor
                int aux;

                // Variável que receberá os valores a serem escritos já ordenados
                String ordered;

                // Leitura da primeira posição do arquivo de entrada é armazenada no vetor
                vals[0] = Integer.parseInt(reader.nextLine());
                // Soma em um a quantidade de dados lidos do arquivo de entrada
                counterEntries++;
                // Verifica se ainda existe alguma linha a ser lida na entrada
                if (reader.hasNextLine()) {
                    // Leitura da segunda posição do arquivo de entrada é armazenada no vetor
                    vals[1] = Integer.parseInt(reader.nextLine());
                    // Soma em um a quantidade de dados lidos do arquivo de entrada
                    counterEntries++;

                    // Ordena os dois primeiros valores do vetor
                    if (vals[0] > vals[1]) {
                        aux = vals[0];
                        vals[0] = vals[1];
                        vals[1] = aux;
                    }

                    // Verifica se ainda existe alguma linha a ser lida na entrada
                    if (reader.hasNextLine()) {
                        // Leitura da terceira posição do arquivo de entrada é armazenada no vetor
                        vals[2] = Integer.parseInt(reader.nextLine());
                        // Soma em um a quantidade de dados lidos do arquivo de entrada
                        counterEntries++;

                        // Ordena o primeiro e o terceiro valor do vetor
                        if (vals[0] > vals[2]) {
                            aux = vals[0];
                            vals[0] = vals[2];
                            vals[2] = aux;
                        }

                        // Ordena o segundo e o terceiro valor do vetor
                        if (vals[1] > vals[2]) {
                            aux = vals[1];
                            vals[1] = vals[2];
                            vals[2] = aux;
                        }

                        // Define a string a ser escrita pelo activeWriter
                        ordered = String.format("%d\n%d\n%d", vals[0], vals[1], vals[2]);
                    }
                    // Caso não haja mais valores a serem lidos no arquivo de entrada
                    else {
                        // Define a string a ser escrita pelo activeWriter
                        ordered = String.format("%d\n%d", vals[0], vals[1]);
                    }
                }
                // Caso não haja mais valores a serem lidos no arquivo de entrada
                else {
                    // Define a string a ser escrita pelo activeWriter
                    ordered = String.format("%d", vals[0]);
                }

                /**
                 * Efetivamente escreve a coluna resultante no arquivo auxiliar e pula uma linha
                 * se for o início do arquivo
                 */
                activeWriter.write(counter >= memoryLimit ? "\n" + ordered : ordered);

                // Soma em um a quantidade de vezes que o while rodou
                counter++;
            }

            // Fecha todos os arquivos de escrita
            for (int i = 0; i < writerEntArray.length; i++) {
                writerEntArray[i].close();
            }
            // Fecha o arquivo de entrada
            reader.close();

            // Retorna a quantidade de valores lidos no arquivo de entrada
            return counterEntries;
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        }

        // Retorna 0 caso haja algum erro durante a execução da função
        return 0;
    }

    /**
     * Função recursiva que combina e ordena as colunas de valores dos arquivos
     * auxiliares conforme o método Merge Sort Externo.
     * 
     * A recursividade é utilizada para alternar qual grupo representa os escritores
     * e qual grupo representa os leitores durante a execução atual da função.
     * 
     * Grupos:
     * A -> arquivos auxiliares 1, 2 e 3.
     * B -> arquivos auxiliares 4, 5 e 6.
     * 
     * Os grupos escritor e leitor são definidos conforme o parâmetro activeGroup:
     * É ímpar -> leitores: Grupo A, escritores: Grupo B.
     * É par -> leitores: Grupo B, escritores: Grupo A.
     * 
     * @param activeGroup somatório de vezes que a função foi chamada
     *                    recursivamente. Define qual grupo será o escritor e qual
     *                    será o leitor.
     * @return File retorna o arquivo auxiliar final com todos os valores já
     *         ordenados. Caso haja erro durante a execução da função, é retornado
     *         null.
     */
    public static File alternateOrder(int activeGroup) {
        try {
            // Armazena o index da coluna que está sendo lida atualmente nos arquivos
            int activeColumn = 0;

            // Define os arquivos auxiliares que servirão de leitura
            File auxFile1 = new File(pathMemory, (activeGroup % 2 == 1 ? aux1Name : aux4Name));
            File auxFile2 = new File(pathMemory, (activeGroup % 2 == 1 ? aux2Name : aux5Name));
            File auxFile3 = new File(pathMemory, (activeGroup % 2 == 1 ? aux3Name : aux6Name));

            // Define os leitores conforme os arquivos de leitura
            Scanner[] auxReader = {
                    new Scanner(auxFile1),
                    new Scanner(auxFile2),
                    new Scanner(auxFile3)
            };

            // if there are no lines to read on the second reader file, it means that the
            // first one has all the elements already ordered and the function can stop
            // running, returning the file that contains the ordered elements
            /**
             * Verifica se o segundo arquivo de leitura possui algum valor a ser lido. Se
             * não houver valores a serem lidos, significa que todos os valores se encontram
             * no primeiro arquivo de leitura e que o processo de ordenação foi finalizado,
             * então retorna o primeiro arquivo de leitura.
             */
            if (!(auxReader[1].hasNextLine())) {
                // Fecha os arquivos dos leitores
                for (int i = 0; i < auxReader.length; i++) {
                    auxReader[i].close();
                }

                // Retorna o arquivo com todos os valores ordenados
                return auxFile1;
            }

            /**
             * O resto da função (código abaixo) só roda caso exista pelo menos uma linha a
             * ser lida no segundo arquivo de leitura
             */

            // Define os escritores com os outros arquivos auxiliares
            FileWriter[] auxWriter = {
                    new FileWriter(pathMemory + "/" + (activeGroup % 2 != 1 ? aux1Name : aux4Name)),
                    new FileWriter(pathMemory + "/" + (activeGroup % 2 != 1 ? aux2Name : aux5Name)),
                    new FileWriter(pathMemory + "/" + (activeGroup % 2 != 1 ? aux3Name : aux6Name))
            };

            // Define o vetor para receber os valores dos arquivos de leitura
            int[] vals = { 0, 0, 0 };
            // Define o vetor que armazenará a última posição lida de cada arquivo leitor
            int[] lastPosFile = { -1, -1, -1 };

            /**
             * Mantém o loop while enquanto ainda existem linhas a serem lidas no primeiro
             * arquivo leitor. Não é necessário verificar os outros arquivos pois a
             * quantidade de valores dele é sempre maior ou igual à dos outros
             */
            while (auxReader[0].hasNextLine()) {
                /**
                 * Armazena quais arquivos estão ativos ou inativos conforme a leitura das
                 * colunas de valores e se existem mais linhas a serem lidas no arquivo
                 */
                boolean[] activeFile = {
                        auxReader[0].hasNextLine(),
                        auxReader[1].hasNextLine(),
                        auxReader[2].hasNextLine()
                };

                /**
                 * Verifica se cada arquivo ainda está ativo. Se estiver, faz a primeira leitura
                 * do mesmo e armazena no vetor ed valores, atualizando também o index da última
                 * posição lida no arquivo
                 */
                for (int i = 0; i < vals.length; i++) {
                    if (activeFile[i]) {
                        vals[i] = Integer.parseInt(auxReader[i].nextLine());
                        lastPosFile[i]++;
                    }
                }

                /**
                 * Calcula e armazena qual a quantidade máxima de dados por coluna de escrita
                 * para a
                 * execução atual do while
                 * 
                 * A quantidade é definida pela potência do limite de memória pela quantidade de
                 * vezes que a função rodou mais um. É somado mais um pois a primeira ordenação
                 * já foi feita anteriormente à função.
                 * 
                 * -> Ex 1: 1° execução da função é representada por 3^(1+1) = 9 por coluna
                 * -> Ex 2: 2° execução da função é representada por 3^(2+1) = 27 por coluna
                 * -> Ex 3: 3° execução da função é representada por 3^(3+1) = 81 por coluna
                 */
                int groupSize = (int) (Math.pow(memoryLimit, activeGroup + 1));

                // Realiza a contagem de vezes que o while foi executado
                int counter = 0;
                /**
                 * A função Utils.boolArrayContains(arr: boolean[], val: boolean) verifica se o
                 * valor booleano existe dentro do vetor booleano informado
                 * 
                 * Mantém o loop rodando enquanto pelo menos um dos arquivos ainda está ativo
                 */
                while (Utils.boolArrayContains(activeFile, true)) {
                    // Variável para receber o index do arquivo com o menor valor do grupo
                    int smallestFile = -1;

                    // Procura pelo arquivo ativo com o menor valor do grupo
                    for (int j = 0; j < activeFile.length; j++) {
                        // Assume o primeiro valor encontrado caso nenhum tenha sido definido ainda
                        if (activeFile[j] && (smallestFile < 0 || vals[smallestFile] > vals[j])) {
                            smallestFile = j;
                        }
                    }

                    /**
                     * O arquivo a ser escrito é definido pelo resto da divisão do index da coluna
                     * ativa com o limite da memória. Cada nova coluna será inteiramente escrita
                     * dentro do mesmo arquivo, mudando o arquivo de escrita a cada execução do
                     * while pai deste while
                     */
                    auxWriter[activeColumn % memoryLimit].write(
                            // Caso não seja a 1° linha a ser escrita no arquivo, insere uma quebra de linha
                            (counter % groupSize != 0 || activeColumn >= 3)
                                    ? String.format("\n%d", vals[smallestFile])
                                    : String.format("%d", vals[smallestFile]));

                    /**
                     * Define o arquivo como inativo se não existem mais linhas a serem lidas ou a
                     * última posição lida corresponde ao fim da coluna atual
                     */
                    activeFile[smallestFile] = auxReader[smallestFile].hasNextLine()
                            && ((lastPosFile[smallestFile] + 1) < (Math.pow(3, activeGroup) * (activeColumn + 1)));

                    /**
                     * Caso o arquivo ainda não esteja inativo, vals recebe o próximo valor oriundo
                     * do mesmo arquivo em que o escrito veio
                     */
                    if (activeFile[smallestFile]) {
                        // Recebe o próximo valor do arquivo
                        vals[smallestFile] = Integer.parseInt(auxReader[smallestFile].nextLine());
                        // Atualiza o index da última posição lida no arquivo
                        lastPosFile[smallestFile]++;
                    }

                    // Soma 1 à quantidade de vezes que o while foi executado
                    counter++;
                }

                // Atualiza o index para o da próxima coluna a ser combinada
                activeColumn++;
            }

            // Fecha todos os arquivos auxiliares para realizar a troca dos grupos
            for (int i = 0; i < auxReader.length; i++) {
                auxWriter[i].close();
                auxReader[i].close();
            }

            /**
             * Chama a si mesma recursivamente adicionando um ao valor do grupo ativo,
             * gerando a alternância entre pares e ímpares que define quem será leitor e
             * quem será escritor.
             * 
             * Retorna o File que será gerado ao findar das chamadas recursivas.
             */
            return alternateOrder(activeGroup + 1);
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        } catch (Exception err) {
            System.out.println("Erro inesperado");
            err.printStackTrace();
        }

        // Caso haja algum erro durante a execução acima, retorna null
        return null;
    }

    /**
     * Função que realiza a leitura do arquivo auxiliar final ordenado e copia seus
     * valores para o arquivo de entrada original.
     * 
     * @param fileResult arquivo final com todos os valores ordenados.
     */
    public static void printResult(File fileResult) {
        try {
            // Define o escritor para o arquivo de entrada
            FileWriter entryMain = new FileWriter(pathMain + "/" + entMainName);

            // Define o leitor do arquivo auxiliar final
            Scanner reader = new Scanner(fileResult);

            // Enquanto existir mais valores a serem copiados
            while (reader.hasNextLine()) {
                // Armazena o valor lido
                String val = reader.nextLine();
                // Copia o valor para entrada e insere quebras de linha até o último valor
                entryMain.write(reader.hasNextLine() ? val + "\n" : val);
            }

            // Fecha o arquivo de entrada e o arquivo auxiliar final
            entryMain.close();
            reader.close();
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * Função principal, gere todo o código a ser executado em ordem.
     * 
     * @param args parametros provenientes da linha de comando.
     */
    public static void main(String[] args) throws Exception {
        // Função que limpa o código e salva o horário inicial da execução
        Utils.initialize();

        /**
         * Se existe algum argumento, assume que o mesmo é um inteiro e define uma
         * quantidade de números aleatórios iniciais.
         */
        if (args.length > 0) {
            try {
                // Tenta converter o parâmetro para inteiro
                int sizeArray = Integer.parseInt(args[0]);

                // Função que cria um vetor de inteiros aleatório do tamanho especificado
                int[] intArray = Utils.randomIntArray(sizeArray);

                // Cria um escritor para armazenar os inteiros aleatórios na entrada
                FileWriter entryMain = new FileWriter(pathMain + "/" + entMainName);

                // Função que escreve os valores no arquivo de entrada e gera um de referência
                Utils.printArrayToFile(entryMain, intArray);

                // Fecha o arquivo de entrada
                entryMain.close();
            } catch (Exception err) {
                // Caso haja erro com o parâmetro, cancela a execução do código
                err.printStackTrace();
                System.out.println("\n\nErro ao tentar processar o parâmetro informado.");
                return;
            }
        }

        /**
         * Recebe os valores do arquivo de entrada, faz a primeira ordenação de acordo
         * com o tamanho limite da memória e insere as colunas geradas nos arquivos
         * auxiliares 1, 2 e 3.
         */
        int entriesRead = splitMainEntry();

        // Escreve no console a quantidade de valores no arquivo de entrada
        System.out.println(String.format("Foram lidos %d valores no arquivo %s", entriesRead, entMainName));

        /**
         * A função é chamada recursivamente até que todos os valores estejam ordenados
         * no mesmo arquivo auxiliar, depois retorna esse arquivo.
         */
        File auxFileResult = alternateOrder(1);

        /**
         * Caso haja erro na execução da função acima, é retornado um valor nulo e o
         * mesmo é verificado abaixo. Caso não seja nulo, o arquivo resultante é enviado
         * para a função que realizará a cópia do mesmo para o arquivo de entrada
         * original.
         */
        if (auxFileResult != null) {
            printResult(auxFileResult);
        }
        // Caso seja nulo, escreve uma mensagem no console
        else {
            System.out.println("Erro ocorrido durante o processamento nos arquivos");
        }

        /**
         * Função de finalização, onde é contabilizado o tempo total de execução do
         * código e uma mensagem de finalização é escrita no console.
         */
        Utils.finish();
    }
}
