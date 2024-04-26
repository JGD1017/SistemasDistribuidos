package es.ubu.lsi.server;

import java.rmi.RMISecurityManager;
import java.rmi.server.RMIClassLoader;
import java.util.Properties;

/**
 * Dynamic server. 
 * 
 * @author Raúl Marticorena
 * @author Joaquin P. Seco
 */
public class ChatServerDynamic {
	/**
	 * Método raíz.
	 * 
	 * @param args
	 *            argumentos
	 */
	public static void main(String args[]) {

		try {
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new RMISecurityManager());
			}
			Properties p = System.getProperties();
			// lee el codebase
			String url = p.getProperty("java.rmi.server.codebase");
			// cargador de clases
			Class<?> serverClass = RMIClassLoader.loadClass(url,
					"es.ubu.lsi.server.ChatServerStarter");
			// inicia el cliente
			serverClass.newInstance();
		} catch (Exception e) {
			System.err.println("Excepcion en arranque del servidor " + e.toString());
		}	
	}

} // ServidorDinamico
