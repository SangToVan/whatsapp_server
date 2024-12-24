package com.sangto.whatsapp.service;

import com.sangto.whatsapp.exception.UserException;
import com.sangto.whatsapp.modal.User;
import com.sangto.whatsapp.request.UpdateUserRequest;

import java.util.List;

public interface UserService {

    public User findUserById(Integer id) throws UserException;

    public User findUserProfile(String jwtToken) throws UserException;

    public void updateUser(Integer userId, UpdateUserRequest updateUserRequest) throws UserException;

    public List<User> searchUser(String query);

    public List<User> searchUserByFull_name(String query);
}
