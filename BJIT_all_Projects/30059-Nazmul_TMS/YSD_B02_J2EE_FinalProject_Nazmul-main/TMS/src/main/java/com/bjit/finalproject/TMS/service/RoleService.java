package com.bjit.finalproject.TMS.service;

import com.bjit.finalproject.TMS.model.Role;
import org.springframework.stereotype.Service;
@Service
public interface RoleService {
    Role getRole(String name);
    void addRole(String name);
}

