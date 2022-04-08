package com.poc.FarmaLoginService.dtos.network;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthSuccessulResponseDTO {

    private String token;
    private String email;
    private List<String> roles;

    public AuthSuccessulResponseDTO(String token, String email, List<String> roles) {
       this.token = token;
       this.email = email;
       this.roles = roles;
   }
   public AuthSuccessulResponseDTO(){}
}
