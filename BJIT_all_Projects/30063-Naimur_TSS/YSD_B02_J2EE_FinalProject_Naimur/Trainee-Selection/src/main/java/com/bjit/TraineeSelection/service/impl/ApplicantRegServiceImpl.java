package com.bjit.TraineeSelection.service.impl;

import com.bjit.TraineeSelection.entity.AdminEntity;
import com.bjit.TraineeSelection.entity.ApplicantEntity;
import com.bjit.TraineeSelection.entity.Role;
import com.bjit.TraineeSelection.entity.UserEntity;
import com.bjit.TraineeSelection.exception.EmailAlreadyTakenException;
import com.bjit.TraineeSelection.model.ApplicantDto;
import com.bjit.TraineeSelection.model.AuthenticationResponse;
import com.bjit.TraineeSelection.repository.AdminRepository;
import com.bjit.TraineeSelection.repository.ApplicantRepository;
import com.bjit.TraineeSelection.repository.UserRepository;
import com.bjit.TraineeSelection.service.ApplicantRegService;
import com.bjit.TraineeSelection.utils.JwtService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantRegServiceImpl implements ApplicantRegService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ApplicantRepository applicantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void AdminRegister() {
        UserEntity existingUser = userRepository.findByEmail("nazmul@gmail.com");
        if (existingUser == null) {
            // Create a UserEntity with hardcoded values
            UserEntity additionalUserEntity = UserEntity.builder()
                    .email("nazmul@gmail.com")
                    .password(passwordEncoder.encode("pass##"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(additionalUserEntity);
            AdminEntity adminEntity=AdminEntity.builder()
                    .firstName("Nazmul")
                    .lastName("Hassan")
                    .email(additionalUserEntity.getEmail())
                    .gender("male")
                    .user(additionalUserEntity)
                    .build();
            adminRepository.save(adminEntity);
        }
    }
    @Override
    public ResponseEntity<Object> register(ApplicantDto applicantDto) {

        String email = applicantDto.getEmail();
        Role desiredRole = Role.USER;

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyTakenException("Email is already taken");
        }

        UserEntity userEntity= UserEntity.builder()
                .email(applicantDto.getEmail())
                .password(passwordEncoder.encode(applicantDto.getPassword()))
                .role(desiredRole)
                .build();
        userRepository.save(userEntity);

        // Retrieve the generated userId after saving the userEntity
        Long userId = userEntity.getUserId();

        ApplicantEntity applicantEntity=ApplicantEntity.builder()
                .firstName(applicantDto.getFirstName())
                .lastName(applicantDto.getLastName())
                .gender(applicantDto.getGender())
                .dob(applicantDto.getDob())
                .email(applicantDto.getEmail())
                .contactNumber(applicantDto.getContactNumber())
                .degreeName(applicantDto.getDegreeName())
                .educationalInstitute(applicantDto.getEducationalInstitute())
                .cgpa(applicantDto.getCgpa())
                .passingYear(applicantDto.getPassingYear())
                .address(applicantDto.getAddress())
                .userId(userId)
                .build();
        applicantRepository.save(applicantEntity);
        AuthenticationResponse authRes = AuthenticationResponse.builder()
                .token(jwtService.generateToken(userEntity))
                .build();
        return new ResponseEntity<>(authRes, HttpStatus.CREATED);
    }

    @PostConstruct
    public void init() {
        AdminRegister();
    }


}
