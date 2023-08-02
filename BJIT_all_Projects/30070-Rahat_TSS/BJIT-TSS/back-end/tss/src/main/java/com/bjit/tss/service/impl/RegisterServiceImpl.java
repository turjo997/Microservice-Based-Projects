package com.bjit.tss.service.impl;

import com.bjit.tss.config.JwtService;
import com.bjit.tss.entity.EvaluatorInfo;
import com.bjit.tss.entity.LoginInfo;
import com.bjit.tss.entity.ValidationCodes;
import com.bjit.tss.exception.EmailException;
import com.bjit.tss.exception.UserException;
import com.bjit.tss.exception.ValidationException;
import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.model.request.EmailRequest;
import com.bjit.tss.model.request.EvaluatorRegisterRequest;
import com.bjit.tss.model.request.RegisterRequest;
import com.bjit.tss.model.request.ValidationRequest;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.response.AuthenticationResponse;
import com.bjit.tss.repository.ValidationRepository;
import com.bjit.tss.enums.Role;
import com.bjit.tss.entity.UserInfo;
import com.bjit.tss.exception.AuthenticationException;
import com.bjit.tss.repository.LoginRepository;
import com.bjit.tss.service.EmailService;
import com.bjit.tss.service.RegisterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ValidationRepository validationRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> applicantRegistration(RegisterRequest registerRequest) {
        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            throw new AuthenticationException("Email is required.");
        }

        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(registerRequest.getEmail()).matches()) {
            throw new EmailException("Invalid Email");
        }

        Optional<LoginInfo> checkAvailability = loginRepository.findByEmail(registerRequest.getEmail());
        if (checkAvailability.isPresent()) {
            throw new AuthenticationException("The Email " + registerRequest.getEmail() + " is already registered");
        }

        UserInfo userInfo = UserInfo.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .gender(registerRequest.getGender())
                .dateOfBirth(registerRequest.getDateOfBirth())
                .email(registerRequest.getEmail())
                .contactNumber(registerRequest.getContactNumber())
                .degreeName(registerRequest.getDegreeName())
                .educationalInstitute(registerRequest.getEducationalInstitute().toUpperCase())
                .cgpa(registerRequest.getCgpa())
                .passingYear(registerRequest.getPassingYear())
                .presentAddress(registerRequest.getPresentAddress())
                .build();
        LoginInfo loginInfo = LoginInfo.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .userInfo(userInfo)
                .build();
        ValidationCodes validationCodes = ValidationCodes.builder()
                .userInfo(userInfo)
                .build();

        validationRepository.save(validationCodes);
        LoginInfo saved = loginRepository.save(loginInfo);
        String jwtToken = jwtService.generateToken(loginInfo);
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .userInfo(saved.getUserInfo())
                .build();
        return ApiResponseMapper.mapToResponseEntityCreated(authenticationResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> adminRegistration(String email, String password) {
        Optional<LoginInfo> checkAvailability = loginRepository.findByEmail(email);
        if (checkAvailability.isEmpty()) {
            LoginInfo loginInfo = LoginInfo.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(Role.ADMIN)
                    .build();
            LoginInfo savedLoginInfo= loginRepository.save(loginInfo);
            return ApiResponseMapper.mapToResponseEntityCreated(savedLoginInfo);
        }
        return ApiResponseMapper.mapToResponseEntityOK(checkAvailability);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> mailValidation(ValidationRequest validationRequest) {
        LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<ValidationCodes> validationCodes = validationRepository.findByUserInfoUserId(loginInfo.getUserInfo().getUserId());
        if (validationCodes.isEmpty()) {
            throw new ValidationException("Cannot be validated");
        }

        if (validationRequest.getValidationCode().equals(validationCodes.get().getValidationCode())) {
            loginInfo.setRole(Role.APPLICANT);
            loginRepository.save(loginInfo);
        } else {
            throw new ValidationException("Invalid Validation Code");
        }

        return ApiResponseMapper.mapToResponseEntityOK(null, "Email is validated");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> evaluatorRegistration(EvaluatorRegisterRequest request) {
        Optional<LoginInfo> checkAvailability = loginRepository.findByEmail(request.getEmail());
        if (checkAvailability.isPresent()) {
            throw new AuthenticationException("The Email " + request.getEmail() + " is already registered");
        }

        EvaluatorInfo evaluatorInfo = EvaluatorInfo.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
        LoginInfo loginInfo = LoginInfo.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.EVALUATOR)
                .evaluatorInfo(evaluatorInfo)
                .build();

        List<String> to = new ArrayList<String>();
        to.add(request.getEmail());
        String[] toEmail = to.toArray(new String[0]);
        String emailSubject = "Evaluator Assignment";
        String emailBody = "Hi " + request.getName() +
                ",\nAdmin has made you an evaluator. \n" +
                "Your login Email : "
                + request.getEmail() +
                " \nYour login password : "
                + request.getPassword() +
                " \nPlease do not share this credentials. ";
        EmailRequest emailRequest = EmailRequest.builder()
                .to(toEmail)
                .body(emailBody)
                .subject(emailSubject)
                .build();
        ResponseEntity<ApiResponse<?>> emailResponse = emailService.sendEmail(emailRequest);
        LoginInfo saved = loginRepository.save(loginInfo);
        System.out.println("An Evaluator was registered with "+loginInfo.getEmail());
        return ApiResponseMapper.mapToResponseEntityCreated(saved.getEvaluatorInfo(), "Evaluator is created.");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> sendEmailVerification() {

        LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<ValidationCodes> validationCodes = validationRepository.findByUserInfoUserId(loginInfo.getUserInfo().getUserId());
        if (validationCodes.isEmpty()) {
            throw new UserException("No user found");
        }

        List<String> to = new ArrayList<String>();
        to.add(loginInfo.getEmail());
        String[] toEmail = to.toArray(new String[0]);
        String emailSubject = "Email Verification";
        String emailBody = "Your Verification Code is : " + validationCodes.get().getValidationCode();

        EmailRequest emailRequest = EmailRequest.builder()
                .to(toEmail)
                .body(emailBody)
                .subject(emailSubject)
                .build();
        ResponseEntity<ApiResponse<?>> emailResponse = emailService.sendEmail(emailRequest);
        System.out.println("Successfully send the verification code to "+loginInfo.getEmail()+" validation code is : "+validationCodes.get().getValidationCode());

        return ApiResponseMapper.mapToResponseEntityOK(emailResponse, "Successfully send the verification code");
    }
}