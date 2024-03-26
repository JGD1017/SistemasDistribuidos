package es.ubu.lsi.server;

import es.ubu.lsi.common.ChatMessage;

public interface ChatServer {
    void startup();
    void broadcast(ChatMessage message);
    void remove(int id);
    void shutdown();
}