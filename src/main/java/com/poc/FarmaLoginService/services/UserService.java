package com.poc.FarmaLoginService.services;



import com.poc.FarmaLoginService.model.Role;
import com.poc.FarmaLoginService.model.TypeRoles;
import com.poc.FarmaLoginService.model.UserAuth;
import com.poc.FarmaLoginService.repository.RoleRepository;
import com.poc.FarmaLoginService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    public Optional<Role> findRoleByName(TypeRoles name){
        return this.roleRepository.findByName(name.toString());
    }

    public void saveRole(Role role){
        this.roleRepository.save(role);
    }

}
