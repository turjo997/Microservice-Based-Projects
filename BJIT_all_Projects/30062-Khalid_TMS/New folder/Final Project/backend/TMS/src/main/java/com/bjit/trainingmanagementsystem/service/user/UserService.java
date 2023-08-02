package com.bjit.trainingmanagementsystem.service.user;

import com.bjit.trainingmanagementsystem.models.Roles.UserModel;

public interface UserService {

    UserModel findUserById(Long UserId);
}
