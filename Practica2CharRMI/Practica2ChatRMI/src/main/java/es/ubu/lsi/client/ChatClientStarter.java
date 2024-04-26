package es.ubu.lsi.client;

import java.applet.Applet;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import es.ubu.lsi.common.ChatMessage;
import es.ubu.lsi.server.ChatServer;

public class ChatClientStarter extends Applet{

	public ChatClientStarter(String[] args) {
		super();
		
        if (args.length != 1) {
            System.out.println("Uso: java ChatClientStarter <nickname>");
            return;
        }

        try {
            String nickname = args[0];

        	// Exporta el objeto 
      		// Dado que no hay herencia múltiple en Java y que un applet
      		// hereda de Applet, no queda otra opción.
            ChatClientImpl client = new ChatClientImpl(nickname);
            ChatClient stub = (ChatClient) UnicastRemoteObject.exportObject(client, 0);

         	// construye cadena de conexión
         	String host = "rmi://" + getCodeBase().getHost()+ "/Servidor";
            Registry registry = LocateRegistry.getRegistry(host);
         	// resuelve
            ChatServer server = (ChatServer) Naming.lookup(host);

         	// invoca el método del servidor remoto            
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

      	} 
      	catch (Exception e) {
         	System.err.println("Excepcion en el applet: " + e.toString());
      	}
		
		
		


    }
}
