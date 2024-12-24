package com.sangto.whatsapp.controller;

import com.sangto.whatsapp.exception.ChatException;
import com.sangto.whatsapp.exception.MessageException;
import com.sangto.whatsapp.exception.UserException;
import com.sangto.whatsapp.modal.Message;
import com.sangto.whatsapp.modal.User;
import com.sangto.whatsapp.request.SendMessageRequest;
import com.sangto.whatsapp.response.ApiResponse;
import com.sangto.whatsapp.service.MessageServiceImp;
import com.sangto.whatsapp.service.UserServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private MessageServiceImp messageService;
    private UserServiceImp userService;

    public MessageController(MessageServiceImp messageService, UserServiceImp userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest request, @RequestHeader("Authorization") String token) throws ChatException, UserException {

        User user = userService.findUserProfile(token);
        request.setUserId(user.getId());
        Message message = messageService.sendMessage(request);

        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatsMessagesHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String token) throws ChatException, UserException {

        User user = userService.findUserProfile(token);
        List<Message> messages = messageService.getChatsMessages(chatId, user);

        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessagesHandler(@PathVariable Integer messageId, @RequestHeader("Authorization") String token) throws ChatException, UserException, MessageException {

        User user = userService.findUserProfile(token);
        messageService.deleteMessage(messageId, user);

        ApiResponse apiResponse = new ApiResponse("Message deleted successfully", true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
}
