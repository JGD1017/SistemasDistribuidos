package es.ubu.lsi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import es.ubu.lsi.common.ChatMessage;
import es.ubu.lsi.server.ChatServer;

public class ChatClientStarter {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java ChatClientStarter <nickname> [<host>]");
            return;
        }

        try {
            String nickname = args[0];
            String host = (args.length > 1) ? args[1] : "localhost";

            ChatClientImpl client = new ChatClientImpl(nickname);
            ChatClient stub = (ChatClient) UnicastRemoteObject.exportObject(client, 0);

            Registry registry = LocateRegistry.getRegistry(host);
            ChatServer server = (ChatServer) registry.lookup("ChatServer");

            int clientId = server.checkIn(stub);
            client.setId(clientId);

            Scanner scanner = new Scanner(System.in);
            String input;
            while (!(input = scanner.nextLine()).equalsIgnoreCase("logout")) {
                // Aquí puedes implementar la lógica para enviar mensajes al servidor
                server.publish(new ChatMessage(clientId, nickname, input));
            }

            server.logout(stub);
            scanner.close();
            System.exit(0);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
