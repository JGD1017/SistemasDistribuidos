package es.ubu.lsi.client;

import es.ubu.lsi.common.ChatMessage;

public interface ChatClient {
    void sendMessage(ChatMessage msg);
    void disconnect();
    boolean start();
}
