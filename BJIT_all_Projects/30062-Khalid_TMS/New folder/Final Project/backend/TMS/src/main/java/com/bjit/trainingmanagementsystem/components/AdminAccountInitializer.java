package com.bjit.trainingmanagementsystem.components;

import com.bjit.trainingmanagementsystem.configuration.JwtService;
import com.bjit.trainingmanagementsystem.entities.roleEntites.AdminEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.RoleEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import com.bjit.trainingmanagementsystem.repository.role.AdminRepository;
import com.bjit.trainingmanagementsystem.repository.role.RoleRepository;
import com.bjit.trainingmanagementsystem.repository.role.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Builder
public class AdminAccountInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {

        if (!userRepository.existsByEmail("superadmin@gmail.com")){

            RoleEntity admin = RoleEntity.builder()
                    .roleName("ADMIN")
                    .build();
            roleRepository.save(admin);

            RoleEntity trainee = RoleEntity.builder()
                    .roleName("TRAINEE")
                    .build();
            roleRepository.save(trainee);

            RoleEntity trainer = RoleEntity.builder()
                    .roleName("TRAINER")
                    .build();
            roleRepository.save(trainer);

            RoleEntity roleEntity = roleRepository.findByRoleName("ADMIN");

            UserEntity superAdmin = UserEntity.builder()
                    .email("superadmin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .roleEntity(roleEntity)
                    .build();
            userRepository.save(superAdmin);

            AdminEntity adminEntity = AdminEntity.builder()
                    .fullName("Admin")
                    .email("superadmin@gmail.com")
                    .contactNumber("012004933854")
                    .gender("male")
                    .user(superAdmin)
                    .build();

            adminRepository.save(adminEntity);

        }
    }
}
