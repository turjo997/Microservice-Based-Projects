package com.bjit.finalproject.TMS.service.impl;

import com.bjit.finalproject.TMS.dto.ProfileDTO;
import com.bjit.finalproject.TMS.dto.UserRequestDTO;
import com.bjit.finalproject.TMS.dto.UserResponseDTO;
import com.bjit.finalproject.TMS.dto.authenticationDto.AuthenticationRequestDTO;
import com.bjit.finalproject.TMS.dto.authenticationDto.AuthenticationResponseDTO;
import com.bjit.finalproject.TMS.exception.CreationException;
import com.bjit.finalproject.TMS.exception.NameNotFoundException;
import com.bjit.finalproject.TMS.exception.AlreadyExistsException;
import com.bjit.finalproject.TMS.exception.userExceptions.PasswordNotMatchException;
import com.bjit.finalproject.TMS.model.Role;
import com.bjit.finalproject.TMS.model.TraineeModel;
import com.bjit.finalproject.TMS.model.TrainerModel;
import com.bjit.finalproject.TMS.model.UserModel;
import com.bjit.finalproject.TMS.repository.BatchTraineeRepository;
import com.bjit.finalproject.TMS.repository.TraineeRepository;
import com.bjit.finalproject.TMS.repository.TrainerRepository;
import com.bjit.finalproject.TMS.repository.UserRepository;
import com.bjit.finalproject.TMS.service.RoleService;
import com.bjit.finalproject.TMS.service.UserService;
import com.bjit.finalproject.TMS.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final TrainerRepository trainerRepo;
    private final TraineeRepository traineeRepo;
    private final RoleService roleService;
    private final BatchTraineeRepository btRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final String path = pathToSaveAndGet();
    @Override
    @Transactional
    public void registerAdmin(String email){
        Optional<UserModel> userModel = userRepo.findByEmail(email);
        if(userModel.isEmpty()){
            UserModel user = new UserModel();
            user.setEmail(email);
            user.setGender("M");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setFirstName("first");
            user.setLastName("admin");
            user.setRole(roleService.getRole("ADMIN"));
            userRepo.save(user);
        }
    }
    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = registerHelper(userRequestDTO);
        return responseDTO;
    }
    @Override
    public AuthenticationResponseDTO login(AuthenticationRequestDTO requestModel){
        try {
        // Authenticate the user based on email and password
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestModel.getEmail(), requestModel.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponseDTO.builder().token(jwtToken).build();
        } catch (AuthenticationException e) {
        // If authentication fails, throw a custom exception
        throw new PasswordNotMatchException("User or password did not match");
        }
    }
    @Override
    public List<UserResponseDTO> getAllUsers(){
        List<UserModel> requiredUsers = userRepo.findAll();
        List<UserResponseDTO> users = new ArrayList<>();
        for(UserModel user:requiredUsers){
            UserResponseDTO response = UserResponseDTO.builder()
                                            .id(user.getUserId())
                                            .email(user.getEmail())
                                            .fullName(user.getFirstName()+" "+user.getLastName())
                                            .image(user.getProfilePicture())
                                            .phoneNo(user.getPhoneNo())
                                            .role(user.getRole().getRoleName())
                                            .build();
            users.add(response);
        }
        return users;
    }

    @Override
    public ProfileDTO getUserProfile() {
        String email = loggedInUserEmail();
//        if(!email.equals(loggedInUserEmail)){
//            throw new NameNotFoundException("You do not have access to the individual users profile");
//        }
        String role = getUserRole();
        if(role.equals("TRAINER")){
            TrainerModel trainer = trainerRepo.findByEmail(email)
                                               .orElseThrow(()->{throw new NameNotFoundException("No trainer");});
            ProfileDTO response = trainerToDto(trainer);
            return response;
        }
        TraineeModel traineeModel = traineeRepo.findByEmail(email)
                                                .orElseThrow(()->{throw new NameNotFoundException("No trainee");});
        ProfileDTO response = traineeToDto(traineeModel);
        return response;
    }
    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO){
        String email = loggedInUserEmail();
        String role = getUserRole();
        if(role.equals("TRAINEE")){
            ProfileDTO traineeProfile = updateTraineeProfile(email, profileDTO);
            return traineeProfile;
        } else {
            ProfileDTO trainerProfile = updateTrainerProfile(email, profileDTO);
            return trainerProfile;
        }
    }
    @Override
    public List<UserResponseDTO> getAllTrainees(String roleName) {
        Role role = roleService.getRole(roleName);
        List<UserModel> allTrainees = userRepo.findTraineesByRole(role);
        List<UserResponseDTO> trainees = new ArrayList<>();
        for(UserModel trainee: allTrainees){
            boolean isAssigned = btRepo.existsByTraineeEmail(trainee.getEmail());
            if(!isAssigned){
                UserResponseDTO response = UserResponseDTO.builder()
                        .id(trainee.getUserId())
                        .email(trainee.getEmail())
                        .fullName(trainee.getFirstName()+" "+trainee.getLastName())
                        .image(trainee.getProfilePicture())
                        .phoneNo(trainee.getPhoneNo())
                        .role(trainee.getRole().getRoleName())
                        .build();
                trainees.add(response);
            }
        }
        return trainees;
    }
    @Override
    public List<UserResponseDTO> getAllTrainers(String roleName) {
        Role role = roleService.getRole(roleName);
        List<UserModel> allTrainees = userRepo.findTraineesByRole(role);
        List<UserResponseDTO> trainers = new ArrayList<>();
        for(UserModel trainee: allTrainees){
            UserResponseDTO response = UserResponseDTO.builder()
                    .id(trainee.getUserId())
                    .email(trainee.getEmail())
                    .fullName(trainee.getFirstName()+" "+trainee.getLastName())
                    .image(trainee.getProfilePicture())
                    .phoneNo(trainee.getPhoneNo())
                    .role(trainee.getRole().getRoleName())
                    .build();
            trainers.add(response);
        }
        return trainers;
    }
    @Override
    public String loggedInUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            return authentication.getName();
        }
        return "UNAUTHORIZED";
    }
    @Override
    public String getUserRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            String role = authentication.getAuthorities().stream().map(u->u.getAuthority()).findFirst().get();
            return role;
        }
        return "UNAUTHORIZED";
    }
    @Transactional
    public UserResponseDTO registerHelper(UserRequestDTO userRequestDTO){
        Optional<UserModel> userModel = userRepo.findByEmail(userRequestDTO.getEmail());
        if(userModel.isPresent()){
            throw new AlreadyExistsException("User is already present in the database");
        }
        UserModel savedUser = new UserModel();
        String assignedRole = userRequestDTO.getRole();
        Role role = roleService.getRole(assignedRole);
        String fullName = userRequestDTO.getFirstName()+ " "+userRequestDTO.getLastName();

        if(userRequestDTO.getProfilePicture()==null){
            UserModel requiredUser = UserModel.builder()
                    .email(userRequestDTO.getEmail())
                    .firstName(userRequestDTO.getFirstName())
                    .lastName(userRequestDTO.getLastName())
                    .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                    .profilePicture("no-image")
                    .gender(userRequestDTO.getGender())
                    .phoneNo(userRequestDTO.getPhoneNo())
                    .role(role)
                    .build();

            savedUser = userRepo.save(requiredUser);

            return UserResponseDTO.builder()
                    .id(savedUser.getUserId())
                    .fullName(fullName)
                    .email(savedUser.getEmail())
                    .phoneNo(savedUser.getPhoneNo())
                    .role(savedUser.getRole().getRoleName())
                    .image(savedUser.getProfilePicture())
                    .build();
        }
        String imageName = userRequestDTO.getProfilePicture().getOriginalFilename();
        try {
            Files.write(Paths.get(path + "\\"+imageName), userRequestDTO.getProfilePicture().getBytes());
        } catch (IOException e) {
                e.printStackTrace();
        }
        UserModel requiredUser = UserModel.builder()
                    .email(userRequestDTO.getEmail())
                    .firstName(userRequestDTO.getFirstName())
                    .lastName(userRequestDTO.getLastName())
                    .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                    .profilePicture(imageName)
                    .gender(userRequestDTO.getGender())
                    .phoneNo(userRequestDTO.getPhoneNo())
                    .role(role)
                    .build();

        savedUser = userRepo.save(requiredUser);

        if(assignedRole.equalsIgnoreCase("TRAINEE")){
            TraineeModel traineeModel = TraineeModel.builder()
                    .email(savedUser.getEmail())
                    .user(savedUser)
                    .build();
            traineeRepo.save(traineeModel);
        }
        else if(assignedRole.equalsIgnoreCase("TRAINER")){
            TrainerModel trainerModel = TrainerModel.builder()
                    .email(savedUser.getEmail())
                    .user(savedUser)
                    .build();
            trainerRepo.save(trainerModel);
        }

        return UserResponseDTO.builder()
                    .id(savedUser.getUserId())
                    .fullName(fullName)
                    .email(savedUser.getEmail())
                    .phoneNo(savedUser.getPhoneNo())
                    .role(savedUser.getRole().getRoleName())
                    .image(savedUser.getProfilePicture())
                    .build();
    }
    @Transactional
    public ProfileDTO updateTraineeProfile(String email, ProfileDTO profileDTO){
        TraineeModel traineeModel = traineeRepo.findByEmail(email).orElseThrow(()->{throw new NameNotFoundException("No trainee by this name");});
        UserModel editingUserModel = userRepo.findByEmail(email).orElseThrow(()->{throw new NameNotFoundException("No trainee by this name");});
        traineeModel.setEmail(profileDTO.getEmail());
        traineeModel.setFullName(profileDTO.getFullName());
        traineeModel.setCgpa(profileDTO.getCgpa());
        traineeModel.setInstitute(profileDTO.getInstitute());
        traineeModel.setBloodGroup(profileDTO.getBloodGroup());
        traineeModel.setNId(profileDTO.getNId());
        traineeModel.setPassingYear(profileDTO.getPassingYear());
        if( profileDTO.getImageFile()!=null && !profileDTO.getImageFile().isEmpty()){
            String fileName = profileDTO.getImageFile().getOriginalFilename();
            try {
                Files.write(Paths.get(path + "\\"+fileName), profileDTO.getImageFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            editingUserModel.setProfilePicture(fileName);
        }
        if(!profileDTO.getPhoneNo().isEmpty()){
            editingUserModel.setPhoneNo(profileDTO.getPhoneNo());
        }
        editingUserModel.setEmail(profileDTO.getEmail());
        userRepo.saveAndFlush(editingUserModel);
        traineeRepo.saveAndFlush(traineeModel);
        ProfileDTO profile = traineeToDto(traineeModel);
        return profile;
    }
    @Transactional
    public ProfileDTO updateTrainerProfile(String email, ProfileDTO profileDTO){
        TrainerModel trainerModel = trainerRepo.findByEmail(email).orElseThrow(()->{throw new NameNotFoundException("No trainer by this name");});
        UserModel userModel = userRepo.findByEmail(email).orElseThrow(()->{throw new NameNotFoundException("No trainer by this name");});
        trainerModel.setDesignation(profileDTO.getDesignation());
        trainerModel.setEmail(profileDTO.getEmail());
        trainerModel.setExperience(profileDTO.getExperience());
        trainerModel.setExpertise(profileDTO.getExpertise());
        if(!profileDTO.getPhoneNo().isEmpty()){
            userModel.setPhoneNo(profileDTO.getPhoneNo());
        }
        if(profileDTO.getImageFile()!=null){
            String fileName = profileDTO.getImageFile().getOriginalFilename();
            try {
                Files.write(Paths.get(path + "\\"+fileName), profileDTO.getImageFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            userModel.setProfilePicture(fileName);
        }
        userModel.setEmail(profileDTO.getEmail());
        userRepo.saveAndFlush(userModel);
        trainerRepo.saveAndFlush(trainerModel);
        return trainerToDto(trainerModel);
    }
    private ProfileDTO traineeToDto(TraineeModel trainee){
        return ProfileDTO.builder()
                .id(trainee.getUser().getUserId())
                .email(trainee.getEmail())
                .fullName(trainee.getUser().getFirstName() +" "+trainee.getUser().getLastName())
                .bloodGroup(trainee.getBloodGroup())
                .cgpa(trainee.getCgpa())
                .nId(trainee.getNId())
                .institute(trainee.getInstitute())
                .passingYear(trainee.getPassingYear())
                .profilePicture(trainee.getUser().getProfilePicture())
                .phoneNo(trainee.getUser().getPhoneNo())
                .build();
    }
    private ProfileDTO trainerToDto(TrainerModel trainer){
        return ProfileDTO.builder()
                .id(trainer.getUser().getUserId())
                .email(trainer.getEmail())
                .fullName(trainer.getUser().getFirstName() +" "+trainer.getUser().getLastName())
                .expertise(trainer.getExpertise())
                .experience(trainer.getExperience())
                .designation(trainer.getDesignation())
                .phoneNo(trainer.getUser().getPhoneNo())
                .profilePicture(trainer.getUser().getProfilePicture())
                .build();
    }
    private String pathToSaveAndGet(){
        String directoryName = "TMS files\\images"; // Specify the desired directory name
        String projectDirectory = System.getProperty("user.dir"); // Get the current project directory

        String directoryPath = projectDirectory + File.separator + directoryName;
        Path path = Paths.get(directoryPath);

        try {
            Files.createDirectories(path);
            System.out.println("Directory created successfully at: " + directoryPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directoryPath;
    }
}
