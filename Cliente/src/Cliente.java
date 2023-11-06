import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class Cliente {
    public static final String TIME_REQUEST = "TIME";
    public static final int TIMEOUT = 10;

    public static void main(String[] args) {

        InetAddress serverAddr = null;
        int serverPort = -1;
        Calendar response;

        /**
         * Nas linhas seguintes vamos testar a sintaxe.
         *
         * NOTA: isto não é importante para a realização do exercício, no entanto, é algo que devem fazer na resolução
         *       dos vossos exercícios.
         */
        if (args.length != 2) {
            System.out.println("Sintaxe: java Cliente serverAddress serverPort");
            return;
        }

        try {

            serverAddr = InetAddress.getByName(args[0]);
            serverPort = Integer.parseInt(args[1]);


            try (Socket socket = new Socket(serverAddr, serverPort)) {

            try {
                Thread.sleep(15000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }

               ObjectOutputStream bout = new ObjectOutputStream(socket.getOutputStream());
               bout.writeObject(TIME_REQUEST);
               bout.flush();

               ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
               response = (Calendar) oin.readObject() ;

                    System.out.println("Horas: " + response.get(GregorianCalendar.HOUR_OF_DAY) + " ; Minutos: " + response.get(GregorianCalendar.MINUTE) + " ; Segundos: " + response.get(GregorianCalendar.SECOND));
                //</editor-fold>
            } catch (ClassNotFoundException e) {
                System.out.println("Classe nao encontrada");

            }

            /**
             * Nas linhas seguintes estamos a fazer o tratamento das exceções.
             * NOTA: apesar de ser uma coisa "chata" faz todo o sentido e deve ser feito (diria que isto será alvo de avaliação
             *       no trabalho prático e/ou exame)
             *
             * NOTA2: como estamos a usar try-with-resources não precisamos de libertar os recursos, isto é feito automáticamente.
             */
        } catch (UnknownHostException e) {
            System.out.println("Destino desconhecido:\n\t" + e);
        } catch (NumberFormatException e) {
            System.out.println("O porto do servidor deve ser um inteiro positivo.");
        } catch (SocketTimeoutException e) {
            System.out.println("Nao foi recebida qualquer resposta:\n\t" + e);
        } catch (SocketException e) {
            System.out.println("Ocorreu um erro ao nivel do socket TCP:\n\t" + e);
        } catch (IOException e) {
            System.out.println("Ocorreu um erro no acesso ao socket:\n\t" + e);
        }
        }

}
