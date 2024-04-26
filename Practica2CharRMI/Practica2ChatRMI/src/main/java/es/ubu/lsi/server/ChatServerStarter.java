package es.ubu.lsi.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServerStarter {

    public static void main(String[] args) {
        try {
            ChatServerImpl server = new ChatServerImpl();
            ChatServer stub = (ChatServer) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(1099); // Puerto por defecto
            registry.rebind("ChatServer", stub);

            System.out.println("Chat server ready.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
