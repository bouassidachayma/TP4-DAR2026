package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client_4_2 {
    public static void main(String[] args) {
        try {
            final DatagramSocket socket = new DatagramSocket();
            final InetAddress adresseServeur = InetAddress.getByName("localhost");
            final int portServeur = 1234;

            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez votre nom d'utilisateur : ");
            final String nomUtilisateur = scanner.nextLine();

            // THREAD POUR LA RÉCEPTION (Non-bloquant)
            Thread threadReception = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] buffer = new byte[1024];
                        while (true) {
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                            socket.receive(packet);

                            String messageRecu = new String(packet.getData(), 0, packet.getLength());
                            System.out.println("\n[Serveur] " + messageRecu);
                            System.out.print("> ");
                        }
                    } catch (Exception e) {
                        System.out.println("Déconnexion du serveur.");
                    }
                }
            });
            threadReception.start();

            // BOUCLE D'ENVOI
            System.out.println("Vous êtes connecté au chat. Tapez 'quit' pour quitter.");

            while (true) {
                System.out.print("> ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("quit")) {
                    System.out.println("Déconnexion...");
                    break;
                }

                String messageComplet = "[" + nomUtilisateur + "] : " + message;
                byte[] donnees = messageComplet.getBytes();
                DatagramPacket packet = new DatagramPacket(donnees, donnees.length, adresseServeur, portServeur);
                socket.send(packet);
            }

            socket.close();
            scanner.close();
            System.out.println("Client arrêté.");
            System.exit(0);

        } catch (Exception e) {
            System.out.println("Erreur côté client : " + e.getMessage());
        }
    }
}