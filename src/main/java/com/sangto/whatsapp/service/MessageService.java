package com.sangto.whatsapp.service;

import com.sangto.whatsapp.exception.ChatException;
import com.sangto.whatsapp.exception.MessageException;
import com.sangto.whatsapp.exception.UserException;
import com.sangto.whatsapp.modal.Message;
import com.sangto.whatsapp.modal.User;
import com.sangto.whatsapp.request.SendMessageRequest;

import java.util.List;

public interface MessageService {

    public Message sendMessage(SendMessageRequest request) throws UserException, ChatException;

    public List<Message> getChatsMessages(Integer chatId, User userRequest) throws ChatException, UserException;

    public Message findMessageById(Integer messageId) throws MessageException;

    public void deleteMessage(Integer messageId, User userRequest) throws MessageException;

}
