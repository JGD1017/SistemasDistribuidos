package es.ubu.lsi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import es.ubu.lsi.common.ChatMessage;

/**
 * Chat client.
 * 
 * @author Raúl Marticorena
 * @author Joaquin P. Seco
 *
 */
public interface ChatClient extends Remote {
	
	/**
	 * Gets current id.
	 * 
	 * @return id
	 * @see #setId
	 * @throws RemoteException if remote communication has problems
	 */
	public abstract int getId() throws RemoteException;;
	
	/**
	 * Sets current id.
	 * 
	 * @param id id
	 * @see #getId
	 * @throws RemoteException if remote communication has problems
	 */
	public abstract void setId(int id) throws RemoteException;;
	
	/**
	 * Receives a new message.
	 * 
	 * @param msg message
	 * @throws RemoteException if remote communication has problems
	 */
	public abstract void receive(ChatMessage msg) throws RemoteException;

	
	/**
	 * Gets the current nickname.
	 * 
	 * @return nickname
	 * @throws RemoteException if remote communication has problems
	 */
	public abstract String getNickName() throws RemoteException;
}