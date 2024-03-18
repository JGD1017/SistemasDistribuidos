package es.ubu.lsi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Cliente remoto.
 */
public class Cliente {

	/**
	 * Constructor oculto,
	 */
    private Cliente() {}


	/**
	 * Método raíz.
	 *
	 * @param args host con el registro
	 */
    public static void main(String[] args) {

		String host = (args.length < 1) ? null : args[0];
		try {
		   Registry registry = LocateRegistry.getRegistry(host);
		   // Resuelve el objeto remoto (la referencia a...)
	 	   HolaMundo stub = (HolaMundo) registry.lookup("Hola");
	 	   String respuesta = stub.decirHola();
	       System.out.println("Respuesta del servidor remoto: " + respuesta);	 	   
	       String respuesta1 = stub.decirHora();
	       System.out.println("Respuesta del servidor remoto: " + respuesta1);	 	   
	       String respuesta2 = stub.decirFecha();
	       System.out.println("Respuesta del servidor remoto: " + respuesta2);	 	   
	       String respuesta3 = stub.decirAdios();
	       System.out.println("Respuesta del servidor remoto: " + respuesta3);
		} 
		catch (Exception e) {
	    	System.err.println("Excepción en cliente: " + e.toString());
		} // try
		
    } // main
    
} // Cliente