package es.ubu.lsi.server;

import es.ubu.lsi.client.ChatClient;
import es.ubu.lsi.common.ChatMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ChatServerImpl
 * 
 * @author José Antonio Gutiérrez Delgado
 *
 */
public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {

	private static final long serialVersionUID = 1234L;

    private Map<Integer, ChatClient> clients;
	// Usuarios baneados
	private Map<String, Boolean> userBanned = new HashMap<String, Boolean>();

    private int nextClientId;

	// Para mostrar la hora de los mensajes
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
	/**
	 * Create a new client.
	 * 
	 * @throws RemoteException remote error
	 */
    public ChatServerImpl() throws RemoteException {
        super();
        clients = new HashMap<>();
        nextClientId = 1;
    }

	/**
	 * Registers a new client.
	 * 
	 * @param client client
	 * @return client id
	 * @throws RemoteException remote error
	 */
    @Override
    public synchronized int checkIn(ChatClient client) throws RemoteException {
        int clientId = nextClientId++;
        clients.put(clientId, client);
        System.out.println(sdf.format(new Date()) + " " + client.getNickName() + " se ha unido al chat. (ID: " + clientId + ")");
        // Avisamos de que un usuario se ha unido al chat
        publish(new ChatMessage(clientId, client.getNickName(), "Se ha unido al chat"));
        return clientId;
    }

	/**
	 * Unregisters a new client.
	 * 
	 * @param client current client
	 * @throws RemoteException remote error
	 */
    @Override
    public synchronized void logout(ChatClient client) throws RemoteException {
        clients.values().remove(client);
        System.out.println(sdf.format(new Date()) + " " + client.getNickName() + " ha dejado el  chat.");
        // Avisamos de que un usuario ha dejado el chat
        publish(new ChatMessage(client.getId(), client.getNickName(), "Ha dejado el chat"));
    }

	/**
	 * Publishs a received message.
	 * 
	 * @param msg message
	 * @throws RemoteException remote error
	 */
    @Override
    public synchronized void publish(ChatMessage msg) throws RemoteException {  
    	if (msg.getMessage().startsWith("ban")) {
    		// Baneo de usuario
    		String userBannedNew = msg.getMessage().split(" ")[1];
    		banned(userBannedNew, msg.getNickname());
            msg.setMessage("Ha baneado a " + userBannedNew);
    	} else if (msg.getMessage().startsWith("unban")) {
    		// Desbaneo de usuario
    		String userUnbanned = msg.getMessage().split(" ")[1];
    		unBanned(userUnbanned, msg.getNickname());
            msg.setMessage("Ha desbaneado a " + userUnbanned); 
    	}
        for (ChatClient client : clients.values()) {
        	// Solo enviamos el mensaje al resto de usuarios no al origen
        	if (!client.getNickName().equals(msg.getNickname())) {
        		client.receive(msg);
        	}
        }
    }

	/**
	 * Orders of shutdown server.
	 * 
	 * @param client current client sending the message
	 * @throws RemoteException remote error
	 */
    @Override
    public synchronized void shutdown(ChatClient client) throws RemoteException {
        // No implementado en esta práctica
    }
    
    /**
     * Banea un usuario.
     * 
     * @param nick nickname del usuario a banear
     * @param user nickname del usuario que banea
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    private void banned(String nick, String user){
    	userBanned.put(nick,true);
    	System.out.println(sdf.format(new Date()) + " " + user + " ha baneado a " + nick);    	
    }
    
    /**
     * Desbanea un usuario.
     * 
     * @param nick nickname del usuario a desbanear
     * @param user nickname del usuario que banea
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    private void unBanned(String nick, String user){
    	userBanned.put(nick,false);
    	System.out.println(sdf.format(new Date()) + " " + user + " ha desbaneado a " + nick);
    }
}