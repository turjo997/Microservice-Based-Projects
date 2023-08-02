package com.bjit.trainingmanagementsystem.service.auth;


import com.bjit.trainingmanagementsystem.configuration.JwtService;
import com.bjit.trainingmanagementsystem.entities.roleEntites.*;
import com.bjit.trainingmanagementsystem.exception.FileUploadFailedException;
import com.bjit.trainingmanagementsystem.exception.InvalidCredentialsException;
import com.bjit.trainingmanagementsystem.exception.UserEmailExistsException;
import com.bjit.trainingmanagementsystem.models.auth.AuthenticationResponse;
import com.bjit.trainingmanagementsystem.models.auth.LoginRequest;
import com.bjit.trainingmanagementsystem.models.auth.RegisterRequest;
import com.bjit.trainingmanagementsystem.repository.role.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final AdminRepository adminRepository;
    private static final String PROFILE_PICTURE_DIR = "src/main/resources/profilePictures";

    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserEmailExistsException("Email is already in use");
        }

        String role = registerRequest.getRole();
        RoleEntity roleEntity = roleRepository.findByRoleName(role);

        UserEntity userEntity = UserEntity.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roleEntity(roleEntity)
                .build();

        userRepository.save(userEntity);

        if (role.equalsIgnoreCase("TRAINER")) {
            TrainerEntity trainerEntity = TrainerEntity.builder()
                    .fullName(registerRequest.getFullName())
                    .designation(registerRequest.getDesignation())
                    .joiningDate(registerRequest.getJoiningDate())
                    .yearsOfExperience(registerRequest.getYearsOfExperience())
                    .expertises(registerRequest.getExpertises())
                    .contactNumber(registerRequest.getContactNumber())
                    .email(registerRequest.getEmail())
                    .presentAddress(registerRequest.getPresentAddress())
                    .user(userEntity)
                    .build();

            MultipartFile multipartFile = registerRequest.getProfilePictureMultipartFile();

            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();
                Path path = Paths.get(PROFILE_PICTURE_DIR, fileName);
                try {
                    Files.write(path, multipartFile.getBytes());
                    trainerEntity.setProfilePicturePath(path.toString());
                } catch (IOException ioException) {
                    throw new FileUploadFailedException("File upload failed: "+ioException.getMessage());
                }
            }
            trainerRepository.save(trainerEntity);

        } else if (role.equalsIgnoreCase("TRAINEE")) {
            TraineeEntity traineeEntity = TraineeEntity.builder()
                    .fullName(registerRequest.getFullName())
                    .gender(registerRequest.getGender())
                    .dateOfBirth(registerRequest.getDateOfBirth())
                    .email(registerRequest.getEmail())
                    .contactNumber(registerRequest.getContactNumber())
                    .degreeName(registerRequest.getDegreeName())
                    .educationalInstitute(registerRequest.getEducationalInstitute())
                    .cgpa(registerRequest.getCgpa())
                    .passingYear(registerRequest.getPassingYear())
                    .presentAddress(registerRequest.getPresentAddress())
                    .user(userEntity)
                    .build();

            MultipartFile multipartFile = registerRequest.getProfilePictureMultipartFile();

            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();
                Path path = Paths.get(PROFILE_PICTURE_DIR, fileName);

                try {
                    Files.write(path, multipartFile.getBytes());
                    traineeEntity.setProfilePicturePath(path.toString());
                } catch (IOException ioException) {
                    throw new FileUploadFailedException("File upload failed: "+ioException.getMessage());
                }

            }


            traineeRepository.save(traineeEntity);

        } else if (role.equalsIgnoreCase("ADMIN")) {
            AdminEntity adminEntity = AdminEntity.builder()
                    .fullName(registerRequest.getFullName())
                    .gender(registerRequest.getGender())
                    .email(registerRequest.getEmail())
                    .contactNumber(registerRequest.getContactNumber())
                    .user(userEntity)
                    .build();

            MultipartFile multipartFile = registerRequest.getProfilePictureMultipartFile();
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();
                Path path = Paths.get(PROFILE_PICTURE_DIR, fileName);

                try {
                    Files.write(path, multipartFile.getBytes());
                    adminEntity.setProfilePicturePath(path.toString());
                } catch (IOException ioException) {
                    throw new FileUploadFailedException("File upload failed: "+ioException.getMessage());
                }
            }
            adminRepository.save(adminEntity);
        }

        String jwtToken = jwtService.generateToken(userEntity);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {

        UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail());
        if (userEntity == null) {
            throw new InvalidCredentialsException("Invalid email");
        }

        boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException("Invalid password");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (Exception ex) {
            throw new InvalidCredentialsException("Invalid credentials");
        }


        var jwtToken = jwtService.generateToken(userEntity);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}


