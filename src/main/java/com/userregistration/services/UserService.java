package com.userregistration.services;

import com.userregistration.dto.UserDTO;
import com.userregistration.entities.User;
import com.userregistration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

import static com.userregistration.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addUser(UserDTO userDTO) {
        preventDuplicity(userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setCreatedAt(LocalDateTime.now());
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userRepository.save(user);
    }

    private void preventDuplicity(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (Objects.nonNull(user)) throw new IllegalStateException();
    }

    public String getUser(String credentials) {
        String base64Credentials = credentials.substring(BASIC.length()).trim();
        byte[] credentialsDecoded = Base64.getDecoder().decode(base64Credentials);
        credentials = new String(credentialsDecoded, StandardCharsets.UTF_8);
        final String[] splitedCredentials = credentials.split(REGEX, LIMIT);

        User user = userRepository.findByEmail(splitedCredentials[0]).orElse(null);

        if (Objects.isNull(user)) return null;

        if (!correctPassword(splitedCredentials[1], user.getPassword())) {
            return UNAUTHENTICATED;
        }

        return user.getEmail();
    }

    private boolean correctPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

}
