package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client_4_1 {
    public static void main(String[] args) {
        try {
            // 1. Création de la socket UDP (le port est choisi automatiquement)
            DatagramSocket socket = new DatagramSocket();

            // Adresse du serveur (localhost pour les tests locaux)
            InetAddress adresseServeur = InetAddress.getByName("localhost");
            int portServeur = 1234;

            Scanner scanner = new Scanner(System.in);

            // 2. Demander le nom d'utilisateur
            System.out.print("Entrez votre nom d'utilisateur : ");
            String nomUtilisateur = scanner.nextLine();

            System.out.println("Vous pouvez commencer à envoyer des messages (tapez 'quit' pour quitter)...");

            while (true) {
                // 3. Lire le message saisi
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("quit")) {
                    break;
                }

                // 4. Préfixer le message avec le nom d'utilisateur
                String messageComplet = "[" + nomUtilisateur + "] : " + message;

                // 5. Convertir en tableau d'octets et envoyer
                byte[] donnees = messageComplet.getBytes();
                DatagramPacket packet = new DatagramPacket(donnees, donnees.length, adresseServeur, portServeur);

                socket.send(packet);
            }

            socket.close();
            scanner.close();
            System.out.println("Client arrêté.");

        } catch (Exception e) {
            System.out.println("Erreur côté client : " + e.getMessage());
        }
    }
}