package com.luis.leonardo.costumerregisterservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AuthenticationService authenticationService;

    public Long getUserIdAuthenticated(String token){
        String formattedToken = getFormattedToken(token);
        return authenticationService.getUserId(formattedToken);
    }

    private String getFormattedToken(String token) {

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        }

        return token.substring(7, token.length());
    }

}
