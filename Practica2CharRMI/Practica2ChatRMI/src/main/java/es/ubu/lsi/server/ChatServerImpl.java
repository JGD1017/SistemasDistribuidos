package es.ubu.lsi.server;

import es.ubu.lsi.client.ChatClient;
import es.ubu.lsi.common.ChatMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {

	private static final long serialVersionUID = 1L;

    private Map<Integer, ChatClient> clients;
    private int nextClientId;

    public ChatServerImpl() throws RemoteException {
        super();
        clients = new HashMap<>();
        nextClientId = 1;
    }

    @Override
    public synchronized int checkIn(ChatClient client) throws RemoteException {
        int clientId = nextClientId++;
        clients.put(clientId, client);
        System.out.println(client.getNickName() + " has joined. (ID: " + clientId + ")");
        return clientId;
    }

    @Override
    public synchronized void logout(ChatClient client) throws RemoteException {
        clients.values().remove(client);
        System.out.println(client.getNickName() + " has left the chat.");
    }

    @Override
    public synchronized void publish(ChatMessage msg) throws RemoteException {
        for (ChatClient client : clients.values()) {
            client.receive(msg);
        }
    }

    @Override
    public synchronized void shutdown(ChatClient client) throws RemoteException {
        // No implementado en esta práctica
    }
    
}
