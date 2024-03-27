package es.ubu.lsi.server;

public class ChatServerImpl {

    public static void main(String[] args) {
    	
        String serverAddressArg="";
        String nicknameArg="";
    	
    	switch (args.length) {
		case 1:
	        serverAddressArg = "localhost";
	        nicknameArg = args[0];
			break;		
		case 2:
			serverAddressArg = args[0];
		    nicknameArg = args[1];
			break;
		default:
            System.out.println(" Modo de uso: java es.ubu.lsi.client.ChatClientImpl [server_address] <nickname>");
			break;
		}
    	
    }
	
}
