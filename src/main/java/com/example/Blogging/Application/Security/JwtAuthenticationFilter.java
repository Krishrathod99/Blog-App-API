package com.example.Blogging.Application.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import java.io.IOException;
@Component        // component because we can autowired it wherever we want
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
    @Autowired       // Through JwtHelper we can perform an operation on token
    private JwtTokenHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override     // This method is call whenever api request hits
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        1.    get token
        //Authorization      //Bearer 2352345235djsgyy --> This is token Format
        String requestHeader = request.getHeader("Authorization");
        System.out.println(" Header : " + requestHeader);

        String username = null;
        String token = null;

//        If token is not null and must starts with Bearer
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            // Fetching original token after Bearer
            token = requestHeader.substring(7);

            try {
                username = this.jwtHelper.getUsernameFromToken(token);      // get username through token
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get Jwt Token");
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt Token has expired");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid Jwt Token");
            }
        } else {
            System.out.println("Jwt Token is null or does not begin with Bearer");
        }

//        After getting Token, Validate It...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //fetch user detail from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtHelper.validateToken(token, userDetails)) {   // This method validate the token
                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Invalid Jwt Token");
            }
        }else {
            System.out.println("Username is Null or Context is Not Null");
        }
        filterChain.doFilter(request, response);
    }

}