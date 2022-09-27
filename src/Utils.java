import java.io.FileWriter;
import java.util.Date;
import java.util.Random;

/**
 * Classe de utilidades para auxílio na class App (principal)
 */
public class Utils {
    // Variável para armazenar o horário de início de execução do código
    private static Date initialTime;

    /**
     * Função que limpa o console do terminal
     */
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Função que pula duas linhas no console do terminal
     */
    public static void jumpLines() {
        System.out.println("");
        System.out.println("");
    }

    /**
     * Função de pula um número especificado de linhas no console do terminal
     *
     * @param qtd quantidade de linhas a serem puladas no console do terminal
     */
    public static void jumpLines(int qtd) {
        for (int i = 0; i < qtd; i++) {
            System.out.println("");
        }
    }

    /**
     * Limpa o console e armazena o horário de início da execução do código
     */
    public static void initialize() {
        clear();
        initialTime = new Date();
    }

    /**
     * Função de finalização, onde é contabilizado o tempo total de execução do
     * código e uma mensagem de finalização é escrita no console.
     */
    public static void finish() {
        Date finalTime = new Date();
        // Quantifica a quantidade em milissegundos entre o horário inicial e agora
        var timeDifference = finalTime.getTime() - initialTime.getTime();

        // Pula 3 linhas e escreve a mensagem final de execução
        jumpLines(3);
        System.out.println(String.format("Execução finalizada com sucesso.\nTempo de execução: %d", timeDifference));
    }

    /**
     * Função que gera um vetor de inteiros preenchido com uma quantidade
     * especificada de valores aleatórios
     *
     * @param amount quantidade de valores aleatórios a serem gerados
     * @return int[] vetor de inteiros preenchido aleatóriamente
     */
    public static int[] randomIntArray(int amount) {
        // Cria um vetor de inteiros de tamanho definido pelo parâmetro amount
        int[] intArray = new int[amount];

        // Instancia o gerador de aleatórios
        Random rand = new Random();
        // Define o maior valor possível para os números aleatórios gerados
        int maxValue = 2000;

        // Roda o número de inteiros desejados e armazena os mesmos na posição
        // respectiva do vetor
        for (int i = 0; i < intArray.length; i++) {
            // Armazena um número aleatório inteiro
            intArray[i] = rand.nextInt(maxValue);
        }

        // Retorna o vetor de inteiros aleatório preenchido
        return intArray;
    }

    /**
     * Função que escreve um vetor de inteiros ao arquivo definido, linha por linha.
     * Além do arquivo original, escreve também um arquivo cópia de referência.
     * 
     * @param printTo  arquivo para escrita do vetor
     * @param intArray vetor a ser escrito no arquivo
     * @return boolean retorna true não houver erro, false se houver erro
     */
    public static boolean printArrayToFile(FileWriter printTo, int[] intArray) {
        try {
            // Cria um escritor para o arquivo de referência
            FileWriter writerReference = new FileWriter("referencia.txt", false);

            // Itera o vetor de inteiros, escrevendo os valores linha a linha nos arquivos
            for (int i = 0; i < intArray.length; i++) {
                String line = i > 0 ? String.format("\n%d", intArray[i])
                        : String.format("%d", intArray[i]);

                writerReference.write(line);
                printTo.write(line);
            }

            // Fecha o arquivo de referência
            writerReference.close();

            // Retorna true se nenhum erro foi gerado
            return true;
        } catch (Exception err) {
            // Retorna false se ocorreu algum erro
            return false;
        }
    }

    /**
     * Função que realiza uma verificação em um vetor booleano procurando por um
     * valor booleano. Retorna true se o valor estiver presente no vetor e false
     * caso contrário
     * 
     * @param arr vetor para procurar o valor
     * @param val valor a ser procurado no vetor
     * @return boolean retorna true se o valor for encontrado no vetor e false se
     *         não for encontrado
     */
    public static boolean boolArrayContains(boolean[] arr, boolean val) {
        // Itera pelo vetor procurando pelo valor
        for (int i = 0; i < arr.length; i++) {
            // Se o valor for encontrado, retorna true
            if (arr[i] == val) {
                return true;
            }
        }
        // Se não for encontrado, retorna false;
        return false;
    }
}
