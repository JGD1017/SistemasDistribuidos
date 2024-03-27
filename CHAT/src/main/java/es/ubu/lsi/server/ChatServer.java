package es.ubu.lsi.server;
import es.ubu.lsi.common.ChatMessage;


/**
 * Interfaz del servidor del chat.
 * 
 * @author José Antonio Gutiérrez Delgado
 *
 */

public interface ChatServer {
    void startup();
    void broadcast(ChatMessage message);
    void remove(int id);
    void shutdown();
}