package com.sangto.whatsapp.controller;

import com.sangto.whatsapp.exception.ChatException;
import com.sangto.whatsapp.exception.UserException;
import com.sangto.whatsapp.modal.Chat;
import com.sangto.whatsapp.modal.User;
import com.sangto.whatsapp.request.GroupChatRequest;
import com.sangto.whatsapp.request.SingleChatRequest;
import com.sangto.whatsapp.response.ApiResponse;
import com.sangto.whatsapp.service.ChatServiceImp;
import com.sangto.whatsapp.service.UserServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private ChatServiceImp chatService;
    private UserServiceImp userService;

    public ChatController(ChatServiceImp chatService, UserServiceImp userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/single")
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String token) throws UserException {

        User userRequest = userService.findUserProfile(token);

        Chat chat = chatService.createChat(userRequest, singleChatRequest.getUserId());

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);

    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupChatHandler(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String token) throws UserException, ChatException {

        User userRequest = userService.findUserProfile(token);

        Chat chat = chatService.createGroupChat(groupChatRequest, userRequest);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);

    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String token) throws UserException, ChatException {

        Chat chat = chatService.findChatById(chatId);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);

    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChatByUserIdHandler(@RequestHeader("Authorization") String token) throws UserException, ChatException {

        User userRequest = userService.findUserProfile(token);

        List<Chat> chats = chatService.findAllChatByUserId(userRequest.getId());

        return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);

    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupChatHandler(@PathVariable Integer chatId, @PathVariable Integer userId,@RequestHeader("Authorization") String token) throws UserException, ChatException {

        User userRequest = userService.findUserProfile(token);

        Chat chat = chatService.addUserToGroupChat(userId, chatId, userRequest);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);

    }

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserFromGroupChatHandler(@PathVariable Integer chatId, @PathVariable Integer userId,@RequestHeader("Authorization") String token) throws UserException, ChatException {

        User userRequest = userService.findUserProfile(token);

        Chat chat = chatService.removeUserFromGroupChat(userId, chatId, userRequest);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);

    }

    @PutMapping("/rename/{groupChatId}")
    public ResponseEntity<Chat> renameGroupChatHandler(@PathVariable Integer groupChatId, @RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String token) throws UserException, ChatException {

        User userRequest = userService.findUserProfile(token);

        Chat chat = chatService.renameGroupChat(groupChatId, groupChatRequest.getChat_name(), userRequest);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);

    }


    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String token) throws UserException, ChatException {

        User userRequest = userService.findUserProfile(token);

        chatService.deleteGroupChat(chatId, userRequest);

        ApiResponse response = new ApiResponse("Chat is deleted successfully", true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);

    }
}
