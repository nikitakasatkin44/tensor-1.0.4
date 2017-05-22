package com.project.chat;

import java.util.List;

/**
 * NG.Kasatkin 17.05.2017
 */
public interface ChatRepository {

    List<String> getMessages(int messageIndex);

    void addMessage(String message);
}
