package com.sangto.whatsapp.service;

import com.sangto.whatsapp.configuration.TokenProvider;
import com.sangto.whatsapp.exception.UserException;
import com.sangto.whatsapp.modal.User;
import com.sangto.whatsapp.repository.UserRepository;
import com.sangto.whatsapp.request.UpdateUserRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private TokenProvider tokenProvider;

    public UserServiceImp(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserException("User not found with id " + id);
    }

    @Override
    public User findUserProfile(String jwtToken) throws UserException {

        String email =  tokenProvider.getEmailFromToken(jwtToken);

        if (email == null) {
            throw new BadCredentialsException("received invalid");
        }

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("User not found with email" + email);
        }

        return user;
    }

    @Override
    public void updateUser(Integer userId, UpdateUserRequest updateUserRequest) throws UserException {

        User user = findUserById(userId);

        if (updateUserRequest.getFull_name() != null) {
            user.setFull_name(updateUserRequest.getFull_name());
        }
        if (updateUserRequest.getProfile_picture() != null) {
            user.setProfile_picture(updateUserRequest.getProfile_picture());
        }

        userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {

        List<User> users = userRepository.searchUser(query);

        return users;
    }

    @Override
    public List<User> searchUserByFull_name(String query) {
        List<User> users = userRepository.searchUserByFull_name(query);

        return users;
    }
}
