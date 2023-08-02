package com.bjit.finalproject.TMS;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bjit.finalproject.TMS.dto.UserRequestDTO;
import com.bjit.finalproject.TMS.dto.UserResponseDTO;
import com.bjit.finalproject.TMS.model.Role;
import com.bjit.finalproject.TMS.model.UserModel;
import com.bjit.finalproject.TMS.repository.TraineeRepository;
import com.bjit.finalproject.TMS.repository.TrainerRepository;
import com.bjit.finalproject.TMS.repository.UserRepository;
import com.bjit.finalproject.TMS.service.RoleService;
import com.bjit.finalproject.TMS.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepo;
    @Mock
    private RoleService roleService;

    @Mock
    private TraineeRepository traineeRepo;

    @Mock
    private TrainerRepository trainerRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    // You may also need to mock the dependencies for Files.write and Paths.get

    @BeforeEach
    public void setUp() {
        // Set up any common behavior or initializations for each test
    }

    @Test
    public void testRegisterUser_Success() {
        // Sample data for testing
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("user@example.com");
        userRequestDTO.setFirstName("John");
        userRequestDTO.setLastName("Doe");
        userRequestDTO.setPassword("secret123");
        userRequestDTO.setRole("ROLE_USER");
        userRequestDTO.setGender("Male");
        userRequestDTO.setPhoneNo("1234567890");

        Role role = new Role();
        role.setRoleName("ROLE_USER");

        // Mock the behavior of the userRepository.findByEmail method to return an empty optional
        when(userRepo.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(roleService.getRole(userRequestDTO.getRole())).thenReturn(role);
        when(passwordEncoder.encode(userRequestDTO.getPassword())).thenReturn("encodedPassword");

        // Mock the save method to return a valid UserModel with the associated Role
        UserModel savedUserMock = UserModel.builder()
                .userId(1L)
                .email(userRequestDTO.getEmail())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .profilePicture("no-image")
                .gender(userRequestDTO.getGender())
                .phoneNo(userRequestDTO.getPhoneNo())
                .role(role)
                .build();
        when(userRepo.save(any(UserModel.class))).thenReturn(savedUserMock);

        // Perform the method call
        UserResponseDTO responseDTO = userService.registerUser(userRequestDTO);

        // Assertions
        assertNotNull(responseDTO);
        assertEquals("John Doe", responseDTO.getFullName());
        assertEquals("user@example.com", responseDTO.getEmail());
        assertEquals("1234567890", responseDTO.getPhoneNo());
        assertEquals("ROLE_USER", responseDTO.getRole());
        assertEquals("no-image", responseDTO.getImage());
        assertEquals(1L, responseDTO.getId().longValue()); // Ensure user ID is returned correctly

        // Verify that the userRepo.save method was called once
        verify(userRepo, times(1)).save(any(UserModel.class));
    }

}

