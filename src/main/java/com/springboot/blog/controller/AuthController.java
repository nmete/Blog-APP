package com.springboot.blog.controller;

import com.springboot.blog.payload.JwtAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "REST APIs for Authentication"
)
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    //build login rest apis
    @Operation(
            summary = "Login REST API",
            description = "Login REST APIs used to authenticate existing user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 201 SUCCESS"
    )
    @PostMapping(value = "signin")
    public ResponseEntity<JwtAuthResponse> login( @RequestBody LoginDto loginDto){
        String token  = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse= new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Register REST API",
            description = "Register REST API is used to create new user"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status : 201 CREATED"
    )
    @PostMapping(value = "signup")
    public ResponseEntity<String> register( @RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
