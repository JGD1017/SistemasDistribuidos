package es.ubu.lsi.server;

import java.rmi.RemoteException;

public class ChatServerStarter {

    public ChatServerStarter() throws RemoteException{
        super();

            ChatServerImpl server = new ChatServerImpl();
             System.out.println("Chat server ready.");        
    }
}
