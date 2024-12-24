package com.sangto.whatsapp.controller;

import com.sangto.whatsapp.exception.UserException;
import com.sangto.whatsapp.modal.User;
import com.sangto.whatsapp.request.UpdateUserRequest;
import com.sangto.whatsapp.response.ApiResponse;
import com.sangto.whatsapp.service.UserServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserServiceImp userService;

    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {

        User user = userService.findUserProfile(token);

        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query) {
        List<User> users = userService.searchUser(query);

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse>updateUserHandler(@RequestBody UpdateUserRequest request, @RequestHeader("Authorization") String token) throws UserException {

        User user = userService.findUserProfile(token);

        userService.updateUser(user.getId(), request);

        ApiResponse apiResponse = new ApiResponse("user updated successful", true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserByNameHandler(@RequestParam("name") String name) throws UserException {

        String trimmedInput = name.trim();

        // Nếu chuỗi rỗng sau khi loại bỏ khoảng trắng, trả về danh sách trống
        if (trimmedInput.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        }
        String likeQuery = "%" + trimmedInput + "%";
        List<User> users = userService.searchUserByFull_name(likeQuery);

        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
    }
}
