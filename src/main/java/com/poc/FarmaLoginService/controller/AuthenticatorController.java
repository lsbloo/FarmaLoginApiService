package com.poc.FarmaLoginService.controller;

import com.poc.FarmaLoginService.dtos.CreateAccountDTO;
import com.poc.FarmaLoginService.dtos.LoginRequestDTO;
import com.poc.FarmaLoginService.dtos.network.AuthSuccessulResponseDTO;
import com.poc.FarmaLoginService.dtos.network.MessageResponseDTO;
import com.poc.FarmaLoginService.services.AuthenticatorService;
import com.poc.FarmaLoginService.utils.NetworkHandlerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticatorController {

    @Autowired
    private AuthenticatorService authenticatorService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        AuthSuccessulResponseDTO authSuccessulResponseDTO = new AuthSuccessulResponseDTO();
        authenticatorService.authenticateUser(loginRequestDTO, new NetworkHandlerEvent() {
            @Override
            public void onResult(MessageResponseDTO response) {}
            @Override
            public void onResult(AuthSuccessulResponseDTO response) {
                authSuccessulResponseDTO.setEmail(response.getEmail());
                authSuccessulResponseDTO.setToken(response.getToken());
                authSuccessulResponseDTO.setRoles(response.getRoles());
            }
        });
        return ResponseEntity.accepted().body(authSuccessulResponseDTO);
    }

    @PostMapping("/singup")
    public ResponseEntity<?> createUser(@RequestBody CreateAccountDTO createAccountDTO) {
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        authenticatorService.createUser(createAccountDTO, new NetworkHandlerEvent() {
            @Override
            public void onResult(MessageResponseDTO response) {
                messageResponseDTO.setMessage(response.getMessage());
                messageResponseDTO.setIsError(response.getIsError());
            }

            @Override
            public void onResult(AuthSuccessulResponseDTO response) {}
        });

        if(messageResponseDTO.getIsError()) {
            return ResponseEntity.badRequest().body(messageResponseDTO);
        } else {
            return ResponseEntity.accepted().body(messageResponseDTO);
        }
    }

}
