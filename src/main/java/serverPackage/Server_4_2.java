package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Server_4_2 {
    public static void main(String[] args) {
        try {
            InetSocketAddress adresse = new InetSocketAddress("0.0.0.0", 1234);
            DatagramSocket socket = new DatagramSocket(adresse);
            System.out.println("Serveur UDP (Bidirectionnel 1-à-1) démarré sur le port 1234...");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String messageRecu = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Message reçu de " + packet.getSocketAddress() + " : " + messageRecu);

                // Préparer la réponse
                String reponse = "Serveur a bien reçu votre message : " + messageRecu;
                byte[] dataReponse = reponse.getBytes();

                // Renvoyer la réponse à l'expéditeur
                DatagramPacket packetReponse = new DatagramPacket(
                        dataReponse, dataReponse.length,
                        packet.getAddress(), packet.getPort()
                );
                socket.send(packetReponse);
            }
        } catch (Exception e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}