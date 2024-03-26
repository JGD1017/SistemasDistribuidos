package es.ubu.lsi.server;

import es.ubu.lsi.common.ChatMessage;
import es.ubu.lsi.common.MessageType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServerImpl implements ChatServer {
    private List<ServerThreadForClient> clientThreads = new ArrayList<ServerThreadForClient>();

    @Override
    public void startup() {
        try {
            ServerSocket serverSocket = new ServerSocket(1500);
            System.out.println("Server started...");

            while (true) {
                Socket socket = serverSocket.accept();
                ServerThreadForClient clientThread = new ServerThreadForClient(socket);
                clientThreads.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void broadcast(ChatMessage message) {
        for (ServerThreadForClient clientThread : clientThreads) {
            clientThread.sendMessage(message);
        }
    }

    @Override
    public void removeClient(ServerThreadForClient clientThread) {
        clientThreads.remove(clientThread);
    }

    @Override
    public void shutdown() {
        // Implement server shutdown logic
    }

    private static class ServerThreadForClient extends Thread {
        private Socket socket;
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;

        public ServerThreadForClient(Socket socket) {
            this.socket = socket;
            try {
                this.outputStream = new ObjectOutputStream(socket.getOutputStream());
                this.inputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // Read the client's nickname
                String nickname = (String) inputStream.readObject();

                while (true) {
                    // Receive messages from the client
                    ChatMessage message = (ChatMessage) inputStream.readObject();
                    if (message.getType() == MessageType.LOGOUT) {
                        break;
                    }

                    // Broadcast the message to all clients
                    System.out.println(nickname + ": " + message.getContent());
                    ChatServerImpl.getInstance().broadcast(message);
                }
            } catch (IOException e) {
                e.printStackTrace();            
            } catch (ClassNotFoundException e) {
                    e.printStackTrace();
            } finally {
                // Clean up resources and remove client from the list
                try {
                    socket.close();
                    inputStream.close();
                    outputStream.close();
                    ChatServerImpl.getInstance().removeClient(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(ChatMessage message) {
            try {
                outputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
