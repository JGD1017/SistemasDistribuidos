package EchoServer;

import java.net.*;
import java.io.*;
import java.util.stream.*;

public class EchoServer {
	
   public static void main(String[] args) throws IOException {
        
    	int[] blacklist = IntStream.rangeClosed(55000,57000).toArray();
    	
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
        
        int portNumber = Integer.parseInt(args[0]);
        System.out.println("Escuchando por puerto: " + portNumber);
        
        try  (
            	ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
   		)
        {
            while (true){
                Socket clientSocket = serverSocket.accept();     
                System.out.println("Nuevo Cliente: " + clientSocket.getInetAddress() + "/" + clientSocket.getPort());
                boolean blocked = false;
                for (int port : blacklist) {
                	if(port==clientSocket.getPort()) {
                		System.err.println("Puerto no admitido. Cliente rechazado");
                		blocked = true;
                		clientSocket.close();
                	}
                }
                if(!blocked) {
                	Thread hilonuevocliente = new ThreadServerHandler(clientSocket);
            		hilonuevocliente.start();
                }
            }
        	
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
    
class ThreadServerHandler extends Thread {
	
	private Socket clientSocket;
	
	public ThreadServerHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;
            
			while ((inputLine = in.readLine()) != null) {
            	System.out.println(clientSocket.getPort() + ":" + inputLine);
                out.println(inputLine);
            }
        }
        catch (IOException e) {
            System.out.println("Exception caught on thread");
            System.out.println(e.getMessage());
        }
      }
}