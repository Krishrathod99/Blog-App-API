package com.example.Blogging.Application.Controllers;

import com.example.Blogging.Application.Exceptions.ApiException;
import com.example.Blogging.Application.Payloads.JwtAuthRequest;
import com.example.Blogging.Application.Payloads.JwtAuthResponse;
import com.example.Blogging.Application.Payloads.UserDTO;
import com.example.Blogging.Application.Security.JwtTokenHelper;
import com.example.Blogging.Application.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
        this.authenticate(request.getUserName(),request.getPassword());

        UserDetails userDetails= this.userDetailsService.loadUserByUsername(request.getUserName());
        String token= this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response= new JwtAuthResponse(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String userName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName , password);

        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e){
            System.out.println("Invalid Details");
            throw new ApiException("Invalid Username or Password");
        }
    }

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    private ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDto){
        UserDTO registerUser = this.userService.registerNewUser(userDto);
        return  new ResponseEntity<>(registerUser,HttpStatus.CREATED);
    }
}
