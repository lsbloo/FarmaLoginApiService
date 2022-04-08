package com.poc.FarmaLoginService.dtos.network;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponseDTO {
    private String message;
    private String description;
    private Boolean isError;


    public MessageResponseDTO(String message){
        this.message = message;
    }

    public MessageResponseDTO(String message, Boolean isError){
        this.message = message;
        this.isError = isError;
    }

    public MessageResponseDTO(String message, String description) {
        this.message = message;
        this.description = description;
    }

}
