package es.ubu.lsi.server;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class ChatServerStarter {

    public ChatServerStarter() throws RemoteException{
        super();

		try {
			// Instancia el objeto remoto, no exportamos
	        ChatServerImpl server = new ChatServerImpl();
			System.out.println("Servidor instanciado" + server);

			// Ligado del objeto remoto en el registro
			Naming.rebind("/Servidor", server);
			System.out.println("Servidor registrado...");
		} catch (Exception e) {
			System.err.println("Excepción recogida: " + e.toString());
		} // try
    }
}
