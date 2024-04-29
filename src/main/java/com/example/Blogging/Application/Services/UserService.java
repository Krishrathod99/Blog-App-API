package com.example.Blogging.Application.Services;

import com.example.Blogging.Application.Payloads.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO registerNewUser(UserDTO user);

    UserDTO createUser(UserDTO user);
    UserDTO updateUser(UserDTO user , Integer userId);
    void deleteUser(Integer userId);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllUsers();


}
