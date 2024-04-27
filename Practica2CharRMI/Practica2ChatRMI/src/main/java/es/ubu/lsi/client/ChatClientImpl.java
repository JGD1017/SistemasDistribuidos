package es.ubu.lsi.client;

import es.ubu.lsi.common.ChatMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * ChatClientImpl.
 * 
 * @author José Antonio Gutiérrez Delgado
 *
 */
public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {

	private static final long serialVersionUID = 4231L;

    private int id;
    private String nickname;

	/**
	 * Constructor.
	 * 
	 * @param nickname name of user
	 * @throws RemoteException if remote communication has problems
	 */
    public ChatClientImpl(String nickname) throws RemoteException {
        super();
        this.nickname = nickname;
    }

	/**
	 * Gets current id.
	 * 
	 * @return id
	 * @see #setId
	 * @throws RemoteException if remote communication has problems
	 */
    @Override
    public int getId() throws RemoteException {
        return id;
    }

	/**
	 * Sets current id.
	 * 
	 * @param id id
	 * @see #getId
	 * @throws RemoteException if remote communication has problems
	 */
    @Override
    public void setId(int id) throws RemoteException {
        this.id = id;
    }

	/**
	 * Receives a new message.
	 * 
	 * @param msg message
	 * @throws RemoteException if remote communication has problems
	 */
    @Override
    public void receive(ChatMessage msg) throws RemoteException {
        System.out.println(msg.getNickname() + ": " + msg.getMessage());
    }
 
	/**
	 * Gets the current nickname.
	 * 
	 * @return nickname
	 * @throws RemoteException if remote communication has problems
	 */
    @Override
    public String getNickName() throws RemoteException {
        return nickname;
    }
}