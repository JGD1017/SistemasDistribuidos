package es.ubu.lsi.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.ubu.lsi.common.ChatMessage;
import es.ubu.lsi.common.ChatMessage.MessageType;


/**
 * Clase servidor del chat.
 * 
 * @author José Antonio Gutiérrez Delgado
 *
 */
public class ChatServerImpl implements ChatServer {
	// Mapa de usuarios
	private Map<Integer, ServerThreadForClient> clientThreads = new HashMap<Integer, ServerThreadForClient>();

	private static ChatServerImpl instance;
    private int DEFAULT_PORT = 1500;
    private int port;
	private Socket socket;
	
	// Id temporal del usuario que se conecta
	private int clientId = 0;
	
	// Para mostrar la hora de los mensajes
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	private ServerSocket serverSocket;
	private boolean alive = true;

	/**
	 * Método principal del servidor.
	 * 
	 * @author José Antonio Gutiérrez Delgado
	 *
	 */
    public static void main(String[] args) {
    	
    	if (args.length!=0) {
            System.out.println(" Modo de uso: java es.ubu.lsi.server.ChatServerImpl");
		}
    	
    	// Lanzamos el servidor
        ChatServerImpl.getInstance().startup();
    }
    
    /**
     * Singleton del servidor.
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    public static ChatServerImpl getInstance() {
        if (instance == null) {
            instance = new ChatServerImpl();
        }
        return instance;
    } 
    
    /**
     * Guarda el ID definitivo del usuario y borra el antiguo, ahora es el hash de su username.
     * 
     * @param newID entero con el nuevo ID
     * @param oldID entero con el anterior ID temporal
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    public void setClientId(int newId, int oldId) {
    	clientThreads.put(newId, clientThreads.get(oldId));
    	clientThreads.remove(oldId);
    }
    
    
    /**
     * Método principal del hilo.
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    @Override
    public void startup() {
    	this.port = DEFAULT_PORT;
        try {
        	// Creamos los sockets del servidor
            serverSocket = new ServerSocket(this.port);
            System.out.println(sdf.format(new Date())+": Servidor iniciado...");

            // Bucle para recibir clientes
            while (alive) {
                socket = serverSocket.accept();
                // Se asigna un ID negativo para evitar que coincida con otro usuario
                clientId--;
                ServerThreadForClient clientThread = new ServerThreadForClient(socket, clientId);
                clientThreads.put(clientId,clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envía un mensaje a todos los usuarios registrados, cada usuario gestiona sus baneados.
     * 
     * @param message mensaje de tipo ChatMessage
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    @Override
    public void broadcast(ChatMessage message) {
        for (int clientThread : clientThreads.keySet()) {
        	if(clientThread != message.getId()) {
        		clientThreads.get(clientThread).sendMessage(message);
        	}
        }
    }

    /**
     * Elimina un usuario del chat.
     * 
     * @param id ID del usuario a eliminar
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    @Override
    public void remove(int id) {
    	broadcast(new ChatMessage(id, MessageType.MESSAGE, "LOGOUT"));
    	clientThreads.get(id).stopChat();
        clientThreads.remove(id);
    }

    /**
     * Cierre del servidor.
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    @Override
    public void shutdown() {
    	// Para los chats de cada usuario
        for (ServerThreadForClient clientThread : clientThreads.values()) {
            clientThread.stopChat();
        }
        
        // Cierra todo
		try {
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
    }

    /**
     * Clase que escucha a cada cliente.
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    private static class ServerThreadForClient extends Thread {
        private Socket socket;
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;
        private boolean active = true;
        private int id;
        private String username;

        /**
         * Creador de la clase.
         * 
         * @param socket que escucha al usuario
         * @param temp ID temporal del usuario
         * 
         * @author José Antonio Gutiérrez Delgado
         *
         */
        public ServerThreadForClient(Socket socket, int temp) {
            this.socket = socket;
            this.id = temp;
            this.active = true;
            
            // Se crean los streams de entrada y salida
            try {
                this.outputStream = new ObjectOutputStream(socket.getOutputStream());
                this.inputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Método principal del hilo.
         * 
         * @author José Antonio Gutiérrez Delgado
         *
         */
        @Override
        public void run() {
            try {
                this.username = ((ChatMessage) inputStream.readObject()).getMessage();
                
                // Nuevo ID del usuario
                int hash = this.username.hashCode();
                ChatServerImpl.getInstance().setClientId(hash, id);
                id=hash;
            	System.out.println(ChatServerImpl.getInstance().sdf.format(new Date())+": "+this.username+":"+this.id + ": CONNECT");

                while (active) {
                    ChatMessage message = (ChatMessage) inputStream.readObject();
                    if (message.getType() == MessageType.LOGOUT) {
                    	active = false;
                        System.out.println(ChatServerImpl.getInstance().sdf.format(new Date())+": "+this.username+":"+this.id + ": LOGOUT");
                    }
                    if(active) {
                    	System.out.println(ChatServerImpl.getInstance().sdf.format(new Date())+": "+this.username+":"+this.id + ": " + message.getMessage());
                    	ChatServerImpl.getInstance().broadcast(message);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                    ChatServerImpl.getInstance().remove(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Método que envía un mensaje al usuario.
         * 
         * @param message de tipo ChatMessage para el usuario
         * 
         * @author José Antonio Gutiérrez Delgado
         *
         */
        public void sendMessage(ChatMessage message) {
            try {
                outputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        /**
         * Para la escucha del chat.
         * 
         * @author José Antonio Gutiérrez Delgado
         *
         */
        public void stopChat() {
        	active = false;
        }
    }
}