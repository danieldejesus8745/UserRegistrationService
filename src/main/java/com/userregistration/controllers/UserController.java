package com.userregistration.controllers;

import com.userregistration.dto.UserDTO;
import com.userregistration.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.userregistration.utils.Constants.AUTHENTICATION;
import static com.userregistration.utils.Constants.UNAUTHENTICATED;
import static com.userregistration.utils.Messages.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        try {
            userService.addUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(MESSAGE_1.getMessage());
        } catch (IllegalStateException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(MESSAGE_2.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getUser(@RequestHeader(AUTHENTICATION) String credentials) {
        String response = userService.getUser(credentials);

        if (Objects.isNull(response)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MESSAGE_3.getMessage());
        }

        if (response.equals(UNAUTHENTICATED)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MESSAGE_4.getMessage());
        }

        return ResponseEntity.ok(response);
    }

}
