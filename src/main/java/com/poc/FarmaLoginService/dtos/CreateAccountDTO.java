package com.poc.FarmaLoginService.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateAccountDTO {

    private String email;
    private String cpf;
    private String password;
    private String name;

    public CreateAccountDTO(String email, String cpf, String password, String name) {
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.name = name;
    }

}
