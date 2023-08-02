package com.bjit.trainingmanagementsystem.service.user;

import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.models.Roles.UserModel;
import com.bjit.trainingmanagementsystem.repository.role.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public UserModel findUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found.  ID: " + userId));

        UserModel userModel = modelMapper.map(userEntity, UserModel.class);
        userModel.setRoleName(userEntity.getRoleEntity().getRoleName());

        return userModel;
    }
}
