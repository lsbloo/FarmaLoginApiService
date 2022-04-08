package com.poc.FarmaLoginService.dataloader;

import com.poc.FarmaLoginService.dtos.CreateAccountDTO;
import com.poc.FarmaLoginService.model.Role;
import com.poc.FarmaLoginService.model.TypeRoles;
import com.poc.FarmaLoginService.model.UserAuth;
import com.poc.FarmaLoginService.repository.RoleRepository;
import com.poc.FarmaLoginService.repository.UserRepository;
import com.poc.FarmaLoginService.services.AuthenticatorService;
import com.poc.FarmaLoginService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;
    private final AuthenticatorService authenticatorService;


    @Autowired
    public DataLoader(UserService userService, AuthenticatorService authenticatorService){
        this.userService = userService;
        this.authenticatorService = authenticatorService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createRolesIfNotFound();
        authenticatorService.findUserByEmail("osvaldo.airon@dcx.ufpb.br").ifPresentOrElse(
                (value) -> {},
                () -> {

                    this.authenticatorService.createUserAdmin(new CreateAccountDTO(
                            "osvaldo.airon@dcx.ufpb.br",
                            "00000000",
                            "123",
                            "OsvaldoAiron"
                    ));
                }
        );

    }


    @Transactional
    private void createRolesIfNotFound(){
        List<TypeRoles> rolesSystem = new ArrayList<>();
        rolesSystem.add(TypeRoles.ROLE_USER);
        rolesSystem.add(TypeRoles.ROLE_ADMIN);
        rolesSystem.add(TypeRoles.ROLE_MODERATOR);

        for(TypeRoles typeRoles: rolesSystem) {
            this.userService.findRoleByName(typeRoles).ifPresentOrElse(
                    (value) -> {},
                    () -> {
                        Role role = new Role();
                        role.setName(typeRoles.toString());
                        this.userService.saveRole(role);
                    }
            );
        }
        }

}
