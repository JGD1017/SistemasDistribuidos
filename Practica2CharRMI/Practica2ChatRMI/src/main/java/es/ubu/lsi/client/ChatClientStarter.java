package es.ubu.lsi.client;

import java.applet.Applet;
import java.rmi.Naming;
import java.util.Scanner;

import es.ubu.lsi.common.ChatMessage;
import es.ubu.lsi.server.ChatServer;

/**
 * ChatClientStarter.
 * 
 * @author José Antonio Gutiérrez Delgado
 *
 */
public class ChatClientStarter extends Applet{

	private static final long serialVersionUID = 4132L;

	/**
	 * Constructor.
	 * 
	 * @param args argumentos de la llamada del cliente
	 */
	public ChatClientStarter(String[] args) {
		super();
		
		// Si recibimos más o menos de un argumento avisamos de que no es correcto
        if (args.length != 1) {
            System.out.println("Uso: java ChatClientStarter <nickname>");
            return;
        }

        try {
            String nickname = args[0];

        	// Crea el objeto cliente
            ChatClientImpl client = new ChatClientImpl(nickname);

         	// Construye cadena de conexión
         	String host =  "/Servidor";

         	// Resuelve el servidor
         	ChatServer server = (ChatServer) Naming.lookup(host);

         	// Invoca el método del servidor remoto para registrarse            
            int clientId = server.checkIn(client);
            client.setId(clientId);
            System.out.println("Te has unido al chat");

            // Inciamos el scanner de la entrada estándar
            Scanner scanner = new Scanner(System.in);
            String input;
            while (!(input = scanner.nextLine()).equalsIgnoreCase("logout")) {
                // Aquí puedes implementar la lógica para enviar mensajes al servidor
            	server.publish(new ChatMessage(clientId, nickname, input));
            }

            // Hemos recibido logout
            server.logout(client);
            System.out.println("Has dejado el chat");

            scanner.close();
            System.exit(0);

      	} 
      	catch (Exception e) {
         	System.err.println("Excepcion en el applet: " + e.toString());
      	}
    }
}
