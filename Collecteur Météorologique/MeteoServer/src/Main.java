import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String API_KEY = "3d691a94e3c1aa84b7db4c2a1bc13d89"; // Ta clé API OpenWeatherMap

    public static void main(String[] args) throws IOException {
        int port = 1234;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Serveur météo démarré sur le port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String city;
            while (true) {
                // Envoyer une demande au client
                out.println("Veuillez entrer le nom d'une ville pour connaître sa météo (ou 'quitter' pour terminer) :");

                // Lire la ville envoyée par le client
                city = in.readLine();

                if (city == null || city.equalsIgnoreCase("quitter")) {
                    out.println("Au revoir !");
                    System.out.println("Client déconnecté.");
                    break;
                }

                System.out.println("Ville reçue du client : " + city);

                // Récupérer les données météo via OpenWeatherMap
                String weatherData = getWeatherDataFromAPI(city);

                // Envoyer les données météo au client
                out.println(weatherData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getWeatherDataFromAPI(String city) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" +
                URLEncoder.encode(city, StandardCharsets.UTF_8) +
                "&appid=" + API_KEY + "&units=metric&lang=fr";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout de connexion
            connection.setReadTimeout(5000);   // Timeout de lecture

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Lire la réponse JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return parseWeatherResponse(response.toString());
            } else {
                return "Erreur : Impossible de récupérer les données météo. Code HTTP : " + responseCode;
            }
        } catch (Exception e) {
            return "Erreur : " + e.getMessage();
        }
    }

    private static String parseWeatherResponse(String jsonResponse) {
        try {
            org.json.JSONObject json = new org.json.JSONObject(jsonResponse);
            String cityName = json.getString("name");
            double temp = json.getJSONObject("main").getDouble("temp");
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");

            return cityName + " : " + temp + "°C, " + description + ".";
        } catch (Exception e) {
            return "Erreur lors du traitement des données météo : " + e.getMessage();
        }
    }
}
