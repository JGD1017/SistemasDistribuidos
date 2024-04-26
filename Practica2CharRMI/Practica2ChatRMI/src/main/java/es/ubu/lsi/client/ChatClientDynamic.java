package es.ubu.lsi.client;

import java.lang.reflect.Constructor;
import java.rmi.RMISecurityManager;
import java.rmi.server.RMIClassLoader;
import java.util.Properties;

/**
 * Dynamic client.
 * 
 * @author Raúl Marticorena
 * @author Joaquin P. Seco
 */
public class ChatClientDynamic {

	/**
	 * Método raíz.
	 * 
	 * @param args
	 *            parámetros
	 */
	public static void main(String[] args) {
		try {
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new RMISecurityManager());
			}
			Properties p = System.getProperties();
			// Reads codebase
			String url = p.getProperty("java.rmi.server.codebase");
			// Load class
			Class<?> clientClass;
			clientClass = RMIClassLoader.loadClass(url,
					"es.ubu.lsi.client.ChatClientStarter");
			// Starts client
			Constructor<?> cons = clientClass.getConstructor(String[].class);			
			cons.newInstance((Object)args);
		} catch (Exception e) {
			System.err.println("Excepcion en arranque del cliente " + e.toString());
		}
	}
}
