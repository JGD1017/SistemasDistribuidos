package es.ubu.lsi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import es.ubu.lsi.common.ChatMessage;
import es.ubu.lsi.common.ChatMessage.MessageType;

/**
 * Lanzador del cliente del chat
 * 
 * @author José Antonio Gutiérrez Delgado
 *
 */
public class ChatClientImpl implements ChatClient {
    private String server;
    private static int port = 1500;
    private String username;
	private Socket socket;
	private boolean carryOn = true;
	private int id;
	//Streams necesarios
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	private ChatClientListener chatListener;
	//Usuarios baneados
	private Map<Integer, String> userBanned = new HashMap<Integer, String>();

	/**
	 * Metodo que lanza el cliente.
	 * 
	 * @param Opcional. Cadena de texto con la ip o el nombre del servidor
	 * @param Username del usuario
	 * 
	 * @author José Antonio Gutiérrez Delgado
	 *
	 */
    public static void main(String[] args) {
    	
        String serverArg="";
        String usernameArg="";
    	
        //Comprobamos el número de argumentos
    	switch (args.length) {
		case 1:
	        serverArg = "localhost";
	        usernameArg = args[0];
			break;		
		case 2:
			serverArg = args[0];
		    usernameArg = args[1];
			break;
		default:
            System.out.println(" Modo de uso: java es.ubu.lsi.client.ChatClientImpl [server_address] <nickname>");
			break;
		}
    	
    	// Lanzamos el chat
        new ChatClientImpl(serverArg, port, usernameArg).start();
    }
    
    /**
     * Creador de la clase.
     * 
     * @param server dirección o nombre del servidor
     * @param port puerto del servidor
     * @param username nombre del usuario
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    public ChatClientImpl(String server, int port, String username) {
        this.server = server;
        ChatClientImpl.port = port;
        this.username = username;
        // Usamos como ID el hash del username que se supone unico
        this.id = username.hashCode();
    }
    
    /**
     * Inicio del chat
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    @Override
    public boolean start() {
        try {
        	// Creamos la conexión
            socket = new Socket(server, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Cliente iniciado en " + server + " como "+ username);

            outputStream.writeObject(new ChatMessage(id, MessageType.MESSAGE,username));

            // Creamos el oyente
            chatListener = new ChatClientListener(inputStream);
            chatListener.setOwner(this);
            new Thread(chatListener).start();

            // Esperamos la entrada de teclado
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input;
            
            while (((input = reader.readLine()) != null) && carryOn) {
            	// Solicitud de salida del chat
                if (input.equalsIgnoreCase("logout")) {
                    sendMessage(new ChatMessage(id, MessageType.LOGOUT,""));
                    carryOn = false;
                } else if (input.toLowerCase().startsWith("ban")) {
                	// Baneo de usuario
                	String userBannedNew = input.split(" ")[1];
                	this.userBanned.put(userBannedNew.hashCode(),userBannedNew);
                    sendMessage(new ChatMessage(id, MessageType.MESSAGE,username+" ha baneado a "+userBannedNew));
                } else if (input.toLowerCase().startsWith("unban")){
                	// Desbaneo de usuario
                	String userUnbanned = input.split(" ")[1];
                	this.userBanned.remove(userUnbanned.hashCode());
                    sendMessage(new ChatMessage(id, MessageType.MESSAGE,username+" ha desbaneado a "+userUnbanned));
                } else {
                	// Resto de mensajes
                    sendMessage(new ChatMessage(id, MessageType.MESSAGE, input));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        disconnect();
        return true;
    }

    /**
     * Envío de un mensaje al servidor.
     * 
     * @param msg Mensaje a enviar tipo ChatMessage
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    @Override
    public void sendMessage(ChatMessage msg) {
        try {
            outputStream.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desconexión delos sockects y salida de la aplicación.
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    @Override
    public void disconnect() {
    	carryOn = false;
        chatListener.stopChat();
        try {
	        outputStream.close();
	        inputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.exit(0);
    }
    
    /**
     * Comprueba si tenemos baneado a un usuario.
     * 
     * @param id ID del usuario a comprobar
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    private boolean isBanned(int id) {
    	return userBanned.get(id)!=null;
    }

    /**
     * Oyente del chat, como un hilo separado.
     * 
     * @author José Antonio Gutiérrez Delgado
     *
     */
    private static class ChatClientListener implements Runnable {
        private ObjectInputStream inputStream;
        private boolean active;
        private ChatClientImpl owner;
        
        /**
         * Creador de la clase.
         * 
         * @param inputStream stream de entrada de datos
         * 
         * @author José Antonio Gutiérrez Delgado
         *
         */
        public ChatClientListener(ObjectInputStream inputStream) {
            this.inputStream = inputStream;
            active = true;
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
                while (active) {
                    ChatMessage message = (ChatMessage) inputStream.readObject();
                    // Si el usuario está baneado no muestro el mensaje pero el si lee los míos
                    if(!owner.isBanned(message.getId())) {
                    	System.out.println(message.getId() + ": " + message.getMessage());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
            owner.disconnect();
        }
        
        /**
         * Establece el objeto que ha creado el oyente.
         * 
         * @param owner objeto que crea el oyente
         * 
         * @author José Antonio Gutiérrez Delgado
         *
         */
        private void setOwner(ChatClientImpl owner) {
        	this.owner = owner;
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
