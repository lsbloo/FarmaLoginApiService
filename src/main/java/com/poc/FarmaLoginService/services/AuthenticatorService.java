package com.poc.FarmaLoginService.services;

import com.poc.FarmaLoginService.dtos.CreateAccountDTO;
import com.poc.FarmaLoginService.dtos.LoginRequestDTO;
import com.poc.FarmaLoginService.dtos.network.AuthSuccessulResponseDTO;
import com.poc.FarmaLoginService.dtos.network.MessageResponseDTO;
import com.poc.FarmaLoginService.model.Role;
import com.poc.FarmaLoginService.model.TypeRoles;
import com.poc.FarmaLoginService.model.UserAuth;
import com.poc.FarmaLoginService.repository.RoleRepository;
import com.poc.FarmaLoginService.repository.UserRepository;
import com.poc.FarmaLoginService.security.JwtUtils;
import com.poc.FarmaLoginService.utils.NetworkHandlerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthenticatorService {


    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticatorService(AuthenticationManager
                                        authenticationManager1,
                                UserRepository userRepository1,
                                RoleRepository roleRepository1,
                                PasswordEncoder encoder,
                                JwtUtils jwtUtils1) {

        this.authenticationManager = authenticationManager1;
        this.userRepository = userRepository1;
        this.roleRepository = roleRepository1;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils1;
    }


    public void authenticateUser(LoginRequestDTO loginRequestDTO, NetworkHandlerEvent networkHandlerEvent) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtUtils.generateJwtToken(authentication);
        List<Integer> roles = this.roleRepository.findIdsRoleUserByUserId(this.userRepository.findByEmail(loginRequestDTO.getEmail()).get().getId());
        List<String> rolesUser = new ArrayList<>();
        for(Integer id_role: roles) {
            rolesUser.add(
                    this.roleRepository.getRoleById(id_role).getName()
            );
        }
        networkHandlerEvent.onResult(new AuthSuccessulResponseDTO(jwt, loginRequestDTO.getEmail(), rolesUser));
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void createUser(CreateAccountDTO createAccountDTO, NetworkHandlerEvent networkHandlerEvent) {
        findUserByEmail(createAccountDTO.getEmail()).ifPresentOrElse(
                (value) -> {
                    networkHandlerEvent.onResult(
                            new MessageResponseDTO("Error: Email is already taken! -> " + value.getEmail(), true));
                },
                () -> {
                    if (registerUser(createAccountDTO)) {
                        networkHandlerEvent.onResult(new MessageResponseDTO("OK: User Registred! ->", false));
                    } else {
                        networkHandlerEvent.onResult(
                                new MessageResponseDTO("Error: There was an error saving the user", true));
                    }
                });


    }

    public Optional<UserAuth> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private boolean registerUser(CreateAccountDTO createAccountDTO) {
        String pass = createAccountDTO.getPassword();
        UserAuth userAuth = new UserAuth(createAccountDTO.getEmail(), passwordEncoder().encode(pass), createAccountDTO.getName(), createAccountDTO.getCpf());
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepository.findByName(TypeRoles.ROLE_USER.name()).get());
        userAuth.setRoles(roles);
        UserAuth userAuth1 = userRepository.save(userAuth);
        return userAuth1 != null;
    }


    public void createUserAdmin(CreateAccountDTO createAccountDTO) {
        String pass = createAccountDTO.getPassword();
        UserAuth userAuth = new UserAuth(createAccountDTO.getEmail(), passwordEncoder().encode(pass), createAccountDTO.getName(), createAccountDTO.getCpf());
        userAuth.setActivated(true);
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepository.findByName(TypeRoles.ROLE_ADMIN.name()).get());
        userAuth.setRoles(roles);
        userRepository.save(userAuth);
    }

}
