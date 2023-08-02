package com.bjit.finalproject.TMS.service.impl;

import com.bjit.finalproject.TMS.exception.userExceptions.RoleNotFoundException;
import com.bjit.finalproject.TMS.model.Role;
import com.bjit.finalproject.TMS.repository.RoleRepository;
import com.bjit.finalproject.TMS.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepo;
    @Override
    public Role getRole(String roleName){
        Optional<Role> role = roleRepo.findByRoleName(roleName);
        if(role.isPresent()){
            Role requiredRole = role.get();

            return requiredRole;
        }
        else{
            throw new RoleNotFoundException("Role Not found ");
        }
    }

    @Override
    public void addRole(String roleName) {

        if(roleRepo.findByRoleName(roleName).isEmpty()){
            Role role = new Role();
            role.setRoleName(roleName);
            roleRepo.save(role);
        }else{
        }

    }
}
