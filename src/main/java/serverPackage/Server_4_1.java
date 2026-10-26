package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Server_4_1 {
    public static void main(String[] args) {
        try {
            // 1. Liaison du serveur sur le port 1234
            InetSocketAddress adresse = new InetSocketAddress("0.0.0.0", 1234);
            DatagramSocket socket = new DatagramSocket(adresse);
            System.out.println("Serveur UDP (Étape 1) en attente sur le port 1234...");

            byte[] buffer = new byte[1024];

            while (true) {
                // 2. Préparation du paquet pour recevoir les données
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // 3. Réception du message (le programme attend ici)
                socket.receive(packet);

                // 4. Extraction des données du paquet
                String message = new String(packet.getData(), 0, packet.getLength());
                String ipExpediteur = packet.getAddress().getHostAddress();
                int portExpediteur = packet.getPort();

                // 5. Affichage sur la console du serveur
                System.out.println("Message reçu de [" + ipExpediteur + ":" + portExpediteur + "] -> " + message);
            }
        } catch (Exception e) {
            System.out.println("Erreur côté serveur : " + e.getMessage());
        }
    }
}