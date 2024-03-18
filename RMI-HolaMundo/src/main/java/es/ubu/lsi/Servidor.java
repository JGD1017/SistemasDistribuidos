package es.ubu.lsi;
	
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Servidor remoto.
 *
 */	
public class Servidor implements HolaMundo {
	
	/**
	 * {@inheritDoc}.
	 *
	 * @return {@inheritDoc}
	 */
    public String decirHola() {
		return "Hola mundo!";
    }
	
	/**
	 * {@inheritDoc}.
	 *
	 * @return {@inheritDoc}
	 */
    public String decirAdios() {
		return "Adios mundo!";
    }
	
	/**
	 * {@inheritDoc}.
	 *
	 * @return {@inheritDoc}
	 */
    public String decirHora() {
    	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    	Date date = new Date();
		return "Hora actual: "+dateFormat.format(date);
    }	
    
	/**
	 * {@inheritDoc}.
	 *
	 * @return {@inheritDoc}
	 */
    public String decirFecha() {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = new Date();
		return "Hoy es: "+dateFormat.format(date);
    }
	
	/**
	 * Método raíz.
	 *
	 * @param args argumentos
	 */
    public static void main(String args[]) {
	
		try {
		    Servidor obj = new Servidor();
		    
		    // si no hereda de UnicastRemoteObject es necesario exportar
	    	HolaMundo stub = (HolaMundo) UnicastRemoteObject.exportObject(obj, 0);

		    // Liga el resguardo de objeto remoto en el registro
	    	Registry registro = LocateRegistry.getRegistry();
	    	registro.bind("Hola", stub);
	
	    	System.out.println("Servidor preparado");
		}
		catch (Exception e) {
		    System.err.println("Excepción de servidor: " + e.toString());
		}
    } // main
    
} // Servidor