package es.ubu.lsi.client;
import es.ubu.lsi.common.ChatMessage;

/**
 * Interfaz de cliente del chat.
 * 
 * @author José Antonio Gutiérrez Delgado
 *
 */

public interface ChatClient {
    void sendMessage(ChatMessage msg);
    void disconnect();
    boolean start();
}
