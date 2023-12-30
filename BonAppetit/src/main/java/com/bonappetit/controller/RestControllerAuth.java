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
import java.util.List;

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
        List<String> errors = userService.checkCredentials(userRegisterDto);

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.IM_USED).body(String.join("\n", errors));
        }

        userService.registerUser(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialsDto credentialsDto) {
        UserJWTDto login = userService.login(credentialsDto);
        login.setToken(userAuthProvider.createToken(login));
        return ResponseEntity.ok(Collections.singletonMap("token", login.getToken()));

    }

}
