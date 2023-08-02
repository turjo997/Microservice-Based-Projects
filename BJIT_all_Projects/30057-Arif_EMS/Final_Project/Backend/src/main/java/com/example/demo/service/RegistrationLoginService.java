package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.security.*;
import lombok.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor

public class RegistrationLoginService {

    private final UserRepository userRepo;
    private final AdminRepository adminRepo;
    private final TrainerRepository trainerRepo;
    private final TraineeRepository traineeRepo;
    private final AuthenticationManager authRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String UPLOAD_URL;

    {
        try {
            UPLOAD_URL = new ClassPathResource("static/pictures/").getFile().getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> createAdmin(MultipartFile image , CreateAdmin admin) throws IOException {

        if( userRepo.findByEmail(admin.getEmail()).isPresent() ){
            throw new RuntimeException("User Already Exist");
        }
        Files.copy( image.getInputStream() , Paths.get(UPLOAD_URL + File.separator + image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING); // + model.getName() + File.separator
        String pictureLink = ServletUriComponentsBuilder.fromCurrentContextPath().path("/pictures/" + image.getOriginalFilename()).toUriString();

        User newUser = User.builder()
                .fullName(admin.getFullName())
                .address(admin.getAddress())
                .profilePic(pictureLink)
                .email(admin.getEmail())
                .password(passwordEncoder.encode(admin.getPassword()))
                .role("ADMIN")
                .build();
        User savedUser = userRepo.save(newUser);

        Admin newAdmin = Admin.builder()
                .designation(admin.getDesignation())
                .yearOfExperience(admin.getYearOfExperience())
                .user(savedUser)
                .build();
        Admin savedAdmin = adminRepo.save(newAdmin);

        return new ResponseEntity<>(savedAdmin , HttpStatus.CREATED);

    }

    public ResponseEntity<Object> createTrainer(MultipartFile image , CreateTrainer trainer) throws IOException {

        if( userRepo.findByEmail(trainer.getEmail()).isPresent() ){
            throw new RuntimeException("User Already Exist");
        }

        Files.copy( image.getInputStream() , Paths.get(UPLOAD_URL + File.separator + image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING); // + model.getName() + File.separator
        String pictureLink = ServletUriComponentsBuilder.fromCurrentContextPath().path("/pictures/" + image.getOriginalFilename()).toUriString();

        User newUser = User.builder()
                .fullName(trainer.getFullName())
                .address(trainer.getAddress())
                .profilePic(pictureLink)
                .email(trainer.getEmail())
                .password(passwordEncoder.encode(trainer.getPassword()))
                .role("TRAINER")
                .build();
        User savedUser = userRepo.save(newUser);

        Trainer newTrainer = Trainer.builder()
                .designation(trainer.getDesignation())
                .fieldOfExpertise(trainer.getFieldOfExpertise())
                .user(savedUser)
                .build();
        Trainer savedTrainer = trainerRepo.save(newTrainer);

        return new ResponseEntity<>(savedTrainer , HttpStatus.CREATED);

    }

    public ResponseEntity<Object> createTrainee(MultipartFile image , CreateTrainee trainee) throws IOException {

        if( userRepo.findByEmail(trainee.getEmail()).isPresent() ){
            throw new RuntimeException("User Already Exist");
        }

        Files.copy( image.getInputStream() , Paths.get(UPLOAD_URL + File.separator + image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING); // + model.getName() + File.separator
        String pictureLink = ServletUriComponentsBuilder.fromCurrentContextPath().path("/pictures/" + image.getOriginalFilename()).toUriString();

        User newUser = User.builder()
                .fullName(trainee.getFullName())
                .address(trainee.getAddress())
                .profilePic(pictureLink)
                .email(trainee.getEmail())
                .password(passwordEncoder.encode(trainee.getPassword()))
                .role("TRAINEE")
                .build();
        User savedUser = userRepo.save(newUser);

        Trainee newTrainee = Trainee.builder()
                .institute(trainee.getInstitute())
                .cgpa(trainee.getCgpa())
                .user(savedUser)
                .build();
        Trainee savedTrainee = traineeRepo.save(newTrainee);

        return new ResponseEntity<>(savedTrainee , HttpStatus.CREATED);

    }

    public ResponseEntity<LoginResponseModel> loginUser(LoginRequest login) {

        authRepo.authenticate( new UsernamePasswordAuthenticationToken( login.getEmail() , login.getPassword() ) );

        if( userRepo.findByEmail(login.getEmail()).isEmpty() ){
            throw new RuntimeException("Credential did not match.");
        }
        else{

            User user = userRepo.findByEmail(login.getEmail()).get();

            String jwtToken = jwtService.generateToken(user);
            LoginResponseModel response = LoginResponseModel.builder()
                    .token(jwtToken)
                    .name(user.getFullName())
                    .role(user.getRole())
                    .userId(user.getUserId())
                    .build();
            return new ResponseEntity<>( response , HttpStatus.OK);
        }
    }

    public ResponseEntity<List<User>> viewAllUser() {

        List<User> users = userRepo.findAll();
        return new ResponseEntity<>(users , HttpStatus.OK);

    }

    public ResponseEntity<List<Admin>> viewAllAdmin() {

        List<Admin> admins = adminRepo.findAll();
        return new ResponseEntity<>(admins , HttpStatus.OK);

    }

    public ResponseEntity<List<Trainer>> viewAllTrainer() {

        List<Trainer> trainers = trainerRepo.findAll();
        return new ResponseEntity<>(trainers , HttpStatus.OK);

    }

    public ResponseEntity<List<Trainee>> viewAllTrainee() {

        List<Trainee> trainees = traineeRepo.findAll();
        return new ResponseEntity<>(trainees , HttpStatus.OK);

    }

    public ResponseEntity<User> findUserById(Long userId){

        if( userRepo.findById(userId).isEmpty()){
            throw new RuntimeException("User not found.");
        }
        else{
            return new ResponseEntity<>( userRepo.findById(userId).get() , HttpStatus.OK );
        }

    }

    public ResponseEntity<Admin> findAdminById(Long adminId) {

        if( adminRepo.findById(adminId).isEmpty()){
            throw new RuntimeException("Admin not found.");
        }
        else{
            return new ResponseEntity<>( adminRepo.findById(adminId).get() , HttpStatus.OK );
        }

    }

    public ResponseEntity<Trainer> findTrainerById(Long trainerId) {

        if( trainerRepo.findById(trainerId).isEmpty()){
            throw new RuntimeException("Trainer not found.");
        }
        else{
            return new ResponseEntity<>( trainerRepo.findById(trainerId).get() , HttpStatus.OK );
        }

    }

    public ResponseEntity<Trainee> findTraineeById(Long traineeId) {

        if( traineeRepo.findById(traineeId).isEmpty()){
            throw new RuntimeException("Trainee not found.");
        }
        else{
            return new ResponseEntity<>( traineeRepo.findById(traineeId).get() , HttpStatus.OK );
        }

    }
}