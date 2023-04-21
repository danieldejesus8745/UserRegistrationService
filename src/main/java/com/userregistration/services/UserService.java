package com.userregistration.services;

import com.userregistration.dto.UserDTO;
import com.userregistration.entities.User;
import com.userregistration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

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

}
