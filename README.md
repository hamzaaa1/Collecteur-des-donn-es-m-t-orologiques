# Collecteur de Données Météorologiques

## Description

Ce projet est une application client-serveur permettant de collecter des données météo en temps réel pour une ville spécifique. Le client peut demander les informations météo d'une ville, et le serveur répond en interrogeant l'API OpenWeatherMap.

## Fonctionnalités

- Communication client-serveur via des sockets TCP.
- Consultation des informations météo en temps réel (température et description).
- Interaction continue : le client peut faire plusieurs requêtes sans redémarrer l'application.

## Prérequis

- **Java 8+**
- Une clé API valide pour OpenWeatherMap ([Obtenez une clé ici](https://openweathermap.org/appid)).

## Installation

1. **Clonez ce dépôt :**

   ```bash
   git clone https://github.com/hamzaaa1/Collecteur-des-donnees-meteorologiques
   cd Collecteur-des-donnees-meteorologiques
   ```

2. **Téléchargez la dépendance JSON-java :**

   - Téléchargez le fichier `json-*.jar` depuis [JSON-java](https://repo1.maven.org/maven2/org/json/json/).
   - Placez le fichier `.jar` dans le répertoire principal du projet.

3. **Ajoutez la dépendance au chemin de classe :**

   - Si vous compilez depuis le terminal :
     ```bash
     javac -cp .:json-*.jar MeteoServer.java MeteoClient.java
     ```
   - Si vous utilisez un IDE (Eclipse, IntelliJ, etc.), ajoutez le fichier `.jar` aux dépendances du projet.

## Exécution

1. **Démarrer le serveur :**

   - Compilez et exécutez `MeteoServer.java` :
     ```bash
     java -cp .:json-*.jar Server
     ```
   - Le serveur écoute les connexions sur le port `12345`.

2. **Démarrer le client :**

   - Compilez et exécutez `MeteoClient.java` :
     ```bash
     java -cp .:json-*.jar Client
     ```
   - Entrez le nom d'une ville pour obtenir ses données météo.
   - Tapez `exit` pour quitter l'application.

## Utilisation

1. Lorsque vous exécutez le client, il vous demandera d'entrer une ville.
2. Le serveur interrogera l'API OpenWeatherMap et retournera des données comme :
   ```
   Température : 25.3°C
   Description : Ciel dégagé
   ```
3. Vous pouvez effectuer une autre requête ou quitter en tapant `exit`.

## Configuration

- **Changer la clé API :**
  - Remplacez la valeur de `apiKey` dans le fichier `MeteoServer.java` par votre propre clé API OpenWeatherMap :
    ```java
    String apiKey = "votre-cle-api";
    ```
- **Changer le port :**
  - Par défaut, le serveur utilise le port `12345`. Vous pouvez le modifier dans `MeteoServer.java` et `MeteoClient.java` :
    ```java
    ServerSocket serverSocket = new ServerSocket(votre_port);
    Socket socket = new Socket("localhost", votre_port);
    ```

## Améliorations à venir

- Gérer plusieurs clients en simultané avec des threads.
- Ajouter une interface utilisateur graphique.
- Afficher des prévisions météo pour plusieurs jours.

## Auteur

- [hamzaaa1](https://github.com/hamzaaa1)

## Licence

Ce projet est sous licence MIT. Consultez le fichier [LICENSE](LICENSE) pour plus d'informations.

