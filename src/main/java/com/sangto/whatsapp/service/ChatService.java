package com.sangto.whatsapp.service;

import com.sangto.whatsapp.exception.ChatException;
import com.sangto.whatsapp.exception.UserException;
import com.sangto.whatsapp.modal.Chat;
import com.sangto.whatsapp.modal.User;
import com.sangto.whatsapp.request.GroupChatRequest;

import java.util.List;

public interface ChatService {

    public Chat createChat(User userRequest, Integer userId) throws UserException;

    public Chat findChatById(Integer chatId) throws ChatException;

    public List<Chat> findAllChatByUserId(Integer userId) throws UserException;

    public Chat createGroupChat(GroupChatRequest request, User userRequest) throws UserException, ChatException;

    public Chat addUserToGroupChat(Integer userId, Integer chatId, User userRequest) throws UserException, ChatException;

    public Chat renameGroupChat(Integer chatId, String groupName, User userRequest) throws UserException, ChatException;

    public Chat removeUserFromGroupChat(Integer chatId, Integer userId, User userRequest) throws UserException, ChatException;

    public void deleteGroupChat(Integer chatId, User userRequest) throws UserException, ChatException;
}
