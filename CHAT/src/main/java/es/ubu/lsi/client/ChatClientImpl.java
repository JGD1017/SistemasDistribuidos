package es.ubu.lsi.client;

import es.ubu.lsi.common.ChatMessage;
import es.ubu.lsi.common.ChatMessage.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatClientImpl implements ChatClient {
    private String serverAddress;
    private int serverPort;
    private String nickname;
	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private ChatClientListener chatListener;
	private Map<Integer, String> userBanned = new HashMap<Integer, String>();

	
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
    	
        new ChatClientImpl(serverAddressArg, nicknameArg).start();
    }
    
    public ChatClientImpl(String serverAddress, String nickname) {
        this.serverAddress = serverAddress;
        this.serverPort = 1500;
        this.nickname = nickname;
    }

    @Override
    public boolean start() {
        try {
            socket = new Socket(serverAddress, serverPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeObject(nickname);

            chatListener = new ChatClientListener(inputStream);
            new Thread(chatListener).start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while ((input = reader.readLine()) != null) {
                if (input.equalsIgnoreCase("logout")) {
                    sendMessage(new ChatMessage(nickname.hashCode(), MessageType.LOGOUT,""));
                    break;
                } else if (input.toLowerCase().startsWith("ban")) {
                	String userBannedNew = input.split(" ")[1];
                	this.userBanned.put(userBannedNew.hashCode(),userBannedNew);
                    sendMessage(new ChatMessage(nickname.hashCode(), MessageType.MESSAGE,nickname+" ha baneado a "+userBannedNew));
                } else if (input.toLowerCase().startsWith("unban")){
                	String userUnbanned = input.split(" ")[1];
                	this.userBanned.remove(userUnbanned.hashCode());
                    sendMessage(new ChatMessage(nickname.hashCode(), MessageType.MESSAGE,nickname+" ha desbaneado a "+userUnbanned));
                } else {
                    sendMessage(new ChatMessage(nickname.hashCode(), MessageType.MESSAGE, input));
                }
            }

            disconnect();
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void sendMessage(ChatMessage message) {
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            outputStream.writeObject(message);

            socket.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        chatListener.stop();
        try {
			socket.close();
	        outputStream.close();
	        inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private static class ChatClientListener implements Runnable {
        private ObjectInputStream inputStream;
        private boolean active;
        
        public ChatClientListener(ObjectInputStream inputStream) {
            this.inputStream = inputStream;
            active = true;
        }

        @Override
        public void run() {
            try {
                while (active) {
                    ChatMessage message = (ChatMessage) inputStream.readObject();
                    System.out.println(message.getId() + ": " + message.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        public void stop() {
        	active = false;
        }
    }
}
