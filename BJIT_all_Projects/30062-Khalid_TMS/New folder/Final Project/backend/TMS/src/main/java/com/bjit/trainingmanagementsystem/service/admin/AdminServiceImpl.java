package com.bjit.trainingmanagementsystem.service.admin;

import com.bjit.trainingmanagementsystem.entities.roleEntites.AdminEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.models.Roles.AdminModel;
import com.bjit.trainingmanagementsystem.repository.role.AdminRepository;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AdminModel> getAdmins() {
        List<AdminEntity> admins = adminRepository.findAll();

        return admins.stream()
                .map(adminEntity -> {
                    AdminModel adminModel = modelMapper.map(adminEntity, AdminModel.class);
                    adminModel.setUserId(adminEntity.getUser().getUserId());
                    return adminModel;
                })
                .collect(Collectors.toList());
    }


    @Override
    public AdminModel findAdminById(Long adminId) {
        AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("Admin not found.  ID: " + adminId));

        AdminModel adminModel = modelMapper.map(adminEntity, AdminModel.class);
        adminModel.setUserId(adminEntity.getUser().getUserId());

        return adminModel;
    }

    @Override
    public Resource getProfilePicture(Long adminId) {
        AdminEntity adminEntity = adminRepository.findById(adminId).orElse(null);

        if (adminEntity == null || adminEntity.getProfilePicturePath() == null) {
            throw new NotFoundException("Profile picture not found.");
        }
        Path path = Paths.get(adminEntity.getProfilePicturePath());
        Resource resource = (Resource) new FileSystemResource(path.toFile());

        return resource;
    }
}
