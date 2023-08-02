package com.bjit.trainingmanagementsystem.controllers;

import com.bjit.trainingmanagementsystem.models.Roles.AdminModel;
import com.bjit.trainingmanagementsystem.service.admin.AdminService;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<AdminModel>> getAdmins() {
        return new ResponseEntity<>(adminService.getAdmins(), HttpStatus.OK);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminModel> findAdminById(@PathVariable Long adminId) {
        return new ResponseEntity<>(adminService.findAdminById(adminId), HttpStatus.OK);
    }

    @GetMapping("/profile-picture/{adminId}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable Long adminId) {
        Resource resource = adminService.getProfilePicture(adminId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Adjust the content type based on your file type.
                .body(resource);
    }
}
