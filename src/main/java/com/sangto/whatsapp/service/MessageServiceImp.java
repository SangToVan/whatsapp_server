package com.sangto.whatsapp.service;

import com.sangto.whatsapp.exception.ChatException;
import com.sangto.whatsapp.exception.MessageException;
import com.sangto.whatsapp.exception.UserException;
import com.sangto.whatsapp.modal.Chat;
import com.sangto.whatsapp.modal.Message;
import com.sangto.whatsapp.modal.User;
import com.sangto.whatsapp.repository.MessageRepository;
import com.sangto.whatsapp.request.SendMessageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImp implements MessageService {

    private MessageRepository messageRepository;
    private ChatServiceImp chatService;
    private UserServiceImp userService;

    public MessageServiceImp(MessageRepository messageRepository, ChatServiceImp chatService, UserServiceImp userService) {
        this.messageRepository = messageRepository;
        this.chatService = chatService;
        this.userService = userService;
    }

    @Override
    public Message sendMessage(SendMessageRequest request) throws UserException, ChatException {

        User user = userService.findUserById(request.getUserId());
        Chat chat = chatService.findChatById(request.getChatId());

        Message message = new Message();
        message.setUser(user);
        message.setChat(chat);
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getChatsMessages(Integer chatId, User userRequest) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);

        if (!chat.getUsers().contains(userRequest)) {
            throw new UserException("You are not related to this chat " + chat.getId());
        }

        List<Message> messages = messageRepository.findByChatId(chat.getId());

        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {

        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()) {
            return message.get();
        }
        throw new MessageException("Message not found with id " + messageId);
    }

    @Override
    public void deleteMessage(Integer messageId, User userRequest) throws MessageException {

        Message message = findMessageById(messageId);
        if (message.getUser().getId().equals(userRequest.getId())) {
            messageRepository.deleteById(messageId);
        }
        throw new MessageException("You can not delete another user message " + userRequest.getFull_name());
    }
}
