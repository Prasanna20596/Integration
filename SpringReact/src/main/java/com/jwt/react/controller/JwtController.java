package com.jwt.react.controller;

import com.jwt.react.entity.JwtRequest;
import com.jwt.react.entity.JwtResponse;
import com.jwt.react.entity.TokenRefreshResponse;
import com.jwt.react.entity.TokenRequest;
import com.jwt.react.entity.User;
import com.jwt.react.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class JwtController {

    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/login")
    public JwtResponse createsigninToken(@RequestBody JwtRequest request) throws Exception {
        return jwtService.signinToken(request);
    }

    @GetMapping("/refreshtoken")
    public TokenRefreshResponse geRefreshResponse(@RequestBody TokenRequest request) {
    	return jwtService.getNewAccessToken(request);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String forAdmin() {
    	return "Hello Admin";
    }
    
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/user")
    public String forUser() {
    	return "Hello User";
    }

}
