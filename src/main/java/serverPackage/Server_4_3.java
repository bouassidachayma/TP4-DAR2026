package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Server_4_3 {
    // Liste pour stocker les adresses (IP + Port) des clients connectés
    private static List<SocketAddress> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            InetSocketAddress adresse = new InetSocketAddress("0.0.0.0", 1234);
            DatagramSocket socket = new DatagramSocket(adresse);
            System.out.println("Serveur de Chat UDP (Bidirectionnel) démarré sur le port 1234...");

            byte[] buffer = new byte[1024];

            while (true) {
                // 1. Recevoir un datagramme
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                SocketAddress adresseExpediteur = packet.getSocketAddress();

                // 2. Enregistrer l'expéditeur s'il est nouveau
                if (!clients.contains(adresseExpediteur)) {
                    clients.add(adresseExpediteur);
                    System.out.println("Nouveau client connecté : " + adresseExpediteur);
                }

                System.out.println("Message reçu de " + adresseExpediteur + " : " + message);

                // 3. Diffuser le message à tous les autres clients
                for (SocketAddress client : clients) {
                    if (!client.equals(adresseExpediteur)) { // Ne pas renvoyer à l'expéditeur
                        byte[] data = message.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(data, data.length, client);
                        socket.send(sendPacket);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}






