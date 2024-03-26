package es.ubu.lsi.common;


import java.io.*;

/**
 * Message in chat system.
 * 
 * @author Raúl Marticorena
 * @author Joaquin P. Seco
 *
 */
public class ChatMessage implements Serializable {

	/** Serial version UID. */
	private static final long serialVersionUID = 7467237896682458959L;

	/**
	 * Message type.
	 * 
	 * @author Raúl Marticorena
	 * @author Joaquin P. Seco
	 */
	public enum MessageType {
		/** Message. */
		MESSAGE,
		/** Shutdown server. */
		SHUTDOWN,		
		/** Logout client. */
		LOGOUT;		
	}
	
	/** Type. */
	private MessageType type;
	
	/** Text. */
	private String message;
	
	/** Client id. */
	private int id;
	
	/**
	 * Constructor.
	 * 
	 * @param id client id
	 * @param type type
	 * @param message message
	 */
	public ChatMessage(int id, MessageType type, String message) {
		this.setId(id);
		this.setType(type);
		this.setMessage(message);
	}
	
	/**
	 * Gets type.
	 * 
	 * @return type
	 * @see #setType
	 */
	public MessageType getType() {
		return type;
	}
	
	/**
	 * Sets type.
	 * 
	 * @param type
	 * @see #getType()
	 */
	private void setType(MessageType type) {
		this.type = type;
	}
	
	/**
	 * Gets message.
	 * 
	 * @return message
	 * @see #setMessage
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets message.
	 * 
	 * @param message message
	 * @see #getMessage
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Gets id.
	 * 
	 * @return sender id
	 * @see #setId(int)
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets sender id.
	 * 
	 * @param id sender id
	 * @see #getId()
	 * 
	 */
	private void setId(int id) {
		this.id = id;
	}
}

