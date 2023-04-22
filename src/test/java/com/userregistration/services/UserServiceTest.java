package com.userregistration.services;

import com.userregistration.dto.UserDTO;
import com.userregistration.entities.User;
import com.userregistration.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldNotFindaUserAlreadyRegisteredWithTheEnteredEmail() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("email@test.com");

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        userService.addUser(userDTO);

        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    void shouldThrowAnExceptionWhenTryingToRegisterAnEmailAlreadyRegisteredPreviously() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("email@test.com");

        User user = new User();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> userService.addUser(userDTO)
        );
    }

}
