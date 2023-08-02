package com.bjit.trainingmanagementsystem.service.admin;

import com.bjit.trainingmanagementsystem.models.Roles.AdminModel;
import org.springframework.core.io.Resource;

import java.util.List;

public interface AdminService {
    List<AdminModel> getAdmins();

    AdminModel findAdminById(Long adminId);

    //Resource downloadProfilePicture(Long adminId);

    Resource getProfilePicture(Long adminId);
}
