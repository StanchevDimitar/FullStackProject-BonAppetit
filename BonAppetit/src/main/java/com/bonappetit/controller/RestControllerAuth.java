package com.bonappetit.controller;

import com.bonappetit.config.JWT.UserAuthProvider;
import com.bonappetit.model.DTO.CredentialsDto;
import com.bonappetit.model.DTO.UserJWTDto;
import com.bonappetit.model.DTO.UserRegisterDto;
import com.bonappetit.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/user")
public class RestControllerAuth {


    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    public RestControllerAuth(UserService userService, UserAuthProvider userAuthProvider) {
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        boolean isUsed = userService.checkUsername(userRegisterDto.getUsername());

        if (isUsed) {
            return ResponseEntity.status(HttpStatus.IM_USED).body("Username is already in use");
        }

        UserJWTDto jwtDto = userService.registerUser(userRegisterDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(jwtDto.toString());
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialsDto credentialsDto){
        UserJWTDto login = userService.login(credentialsDto);
        login.setToken(userAuthProvider.createToken(login));
            return ResponseEntity.ok(Collections.singletonMap("token", login.getToken()));

    }

}
