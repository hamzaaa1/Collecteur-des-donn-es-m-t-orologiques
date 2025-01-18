import java.io.*;
import java.net.*;

public class WeatherClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            String serverMessage;
            String userResponse;

            while (true) {
                // Lire le message du serveur
                serverMessage = in.readLine();
                System.out.println(serverMessage);

                if (serverMessage == null || serverMessage.contains("Au revoir")) {
                    break;
                }

                // Lire l'entrée de l'utilisateur
                userResponse = userInput.readLine();

                // Envoyer la réponse de l'utilisateur au serveur
                out.println(userResponse);

                if (userResponse.equalsIgnoreCase("quitter")) {
                    break;
                }

                // Lire et afficher la réponse du serveur
                serverMessage = in.readLine();
                System.out.println("Réponse du serveur : " + serverMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
