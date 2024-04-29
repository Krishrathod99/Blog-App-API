package com.example.Blogging.Application.Services.implementation;

import com.example.Blogging.Application.Config.AppConstants;
import com.example.Blogging.Application.Entities.Role;
import com.example.Blogging.Application.Entities.User;
import com.example.Blogging.Application.Exceptions.ResourceNotFoundException;
import com.example.Blogging.Application.Payloads.UserDTO;
import com.example.Blogging.Application.Repositories.RoleRepository;
import com.example.Blogging.Application.Repositories.UserRepository;
import com.example.Blogging.Application.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;    // ModelMapper is used for convert one class object into another class object like DTO to user

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.dtoToUser(userDTO);      // convert dto to user
        User savedUser = userRepository.save(user);
        return this.userToDto(savedUser);         // convert user to dto
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", " Id ", userId));     // If user is not there , than this exception will occur

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = this.userRepository.save(user);
        return this.userToDto(updatedUser);

    }

    @Override
    public UserDTO getUserById(Integer userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = this.userRepository.findAll();

//        For convert user to userDTO, bcz return type is UserDTO
        List<UserDTO> userDTOs = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDTOs;
    }

    @Override
    public void deleteUser(Integer userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)); // if user isn't found, this exception wil occur
        this.userRepository.delete(user);
    }

    @Override
    public UserDTO registerNewUser(UserDTO userDto) {
        User user= this.modelMapper.map(userDto,User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));   // encoded the password

        Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();   // user created by this method, will treated as normal user
        user.getRoles().add(role);
        User updatedUser= this.userRepository.save(user);
        return this.modelMapper.map(updatedUser,UserDTO.class);
    }


//    to Convert UserDTO to User
    public User dtoToUser(UserDTO userDTO){
//        Step :- 1 --> Manually conversion
//        User user = new User();
//        user.setId(userDTO.getId());
//        user.setName(userDTO.getName());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
//        user.setAbout(userDTO.getAbout());
//        return user;

//        Step :- 2 --> Using ModelMapper class
        return this.modelMapper.map(userDTO , User.class);    // which object you want to convert into with class object

    }

//    to convert User to UserDTO
    public UserDTO userToDto(User user){
        return this.modelMapper.map(user , UserDTO.class);
    }



}
