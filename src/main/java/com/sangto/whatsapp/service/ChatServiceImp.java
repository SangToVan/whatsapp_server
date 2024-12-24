package com.sangto.whatsapp.service;

import com.sangto.whatsapp.exception.ChatException;
import com.sangto.whatsapp.exception.UserException;
import com.sangto.whatsapp.modal.Chat;
import com.sangto.whatsapp.modal.User;
import com.sangto.whatsapp.repository.ChatRepository;
import com.sangto.whatsapp.request.GroupChatRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImp implements ChatService {

    private ChatRepository chatRepository;
    private UserServiceImp userService;

    public ChatServiceImp(ChatRepository chatRepository, UserServiceImp userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @Override
    public Chat createChat(User userRequest, Integer userId) throws UserException {

        User user = userService.findUserById(userId);
        Chat isChatExist = chatRepository.findSingleChatByUserIds(user, userRequest);

        if (isChatExist != null) {
            return isChatExist;
        }

        Chat chat = new Chat();
        chat.setCreatedBy(userRequest);
        chat.getUsers().add(userRequest);
        chat.getUsers().add(user);
        chat.setIs_group(false);

        return chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {

        Optional<Chat> chat = chatRepository.findById(chatId);

        if (chat.isPresent()) {
            return chat.get();
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {

        User user = userService.findUserById(userId);

        List<Chat> chats = chatRepository.findChatByUserId(userId);

        return chats;
    }

    @Override
    public Chat createGroupChat(GroupChatRequest request, User userRequest) throws UserException, ChatException {

        Chat groupChat = new Chat();
        groupChat.setIs_group(true);
        groupChat.setChat_image(request.getChat_image());
        groupChat.setChat_name(request.getChat_name());
        groupChat.setCreatedBy(userRequest);
        groupChat.getAdmins().add(userRequest);

        for (Integer userId : request.getUserIds()) {
            User user = userService.findUserById(userId);
            groupChat.getUsers().add(user);
        }

        return chatRepository.save(groupChat);
    }

    @Override
    public Chat addUserToGroupChat(Integer userId, Integer chatId, User userRequest) throws UserException, ChatException {

        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if (optionalChat.isPresent()) {

            Chat chat = optionalChat.get();
            if (chat.getAdmins().contains(userRequest)) {
                User user = userService.findUserById(userId);
                chat.getUsers().add(user);

                return chatRepository.save(chat);
            } else {
                throw new UserException("You are not allowed to add a user to a group chat");
            }
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public Chat renameGroupChat(Integer chatId, String groupName, User userRequest) throws UserException, ChatException {

        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            if (chat.getAdmins().contains(userRequest)) {
                chat.setChat_name(groupName);
                return chatRepository.save(chat);
            } else {
                throw new UserException("You are not allowed to rename a group chat");
            }
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public Chat removeUserFromGroupChat(Integer chatId, Integer userId, User userRequest) throws UserException, ChatException {

        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            User user = userService.findUserById(userId);

            if (chat.getAdmins().contains(userRequest)) {
                if (chat.getUsers().contains(user)) {
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }
                throw new UserException("User not in group chat");
            } else if (userId.equals(userRequest.getId())) {
                if (chat.getUsers().contains(user)) {
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }
                throw new UserException("User not in group chat");
            }
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public void deleteGroupChat(Integer chatId, User userRequest) throws UserException, ChatException {

        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            if (chat.getAdmins().contains(userRequest)) {
                chatRepository.delete(chat);
            }
        }
        throw new ChatException("Chat not found with id " + chatId);
    }
}
