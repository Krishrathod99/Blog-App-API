package com.example.Blogging.Application.Controllers;

import com.example.Blogging.Application.Entities.User;
import com.example.Blogging.Application.Payloads.ApiResponse;
import com.example.Blogging.Application.Payloads.UserDTO;
import com.example.Blogging.Application.Services.UserService;
import jakarta.validation.Valid;
import org.hibernate.mapping.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;



    // POST - create user
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){  // @Valid is for Validation
        UserDTO createdUserDto = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUserDto , HttpStatus.CREATED);
    }


    // DELETE - delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> DeleteUser(@PathVariable("userId") Integer uId){       // ApiResponse is class for ResponseEntity

        userService.deleteUser(uId);
        return new ResponseEntity<>(new ApiResponse("User deleted Successfully",  true), HttpStatus.OK);

    }

    // PUT - update user
    @PutMapping("/{userId}")     // {userId} is pathVariable
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO ,@PathVariable Integer userId){

        UserDTO updatedUser = userService.updateUser(userDTO , userId);
        return ResponseEntity.ok(updatedUser);
    }


    // GET - get user
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users , HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId){

        return ResponseEntity.ok(userService.getUserById(userId));
    }


}
