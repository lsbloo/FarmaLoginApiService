package com.poc.FarmaLoginService.utils;

import com.poc.FarmaLoginService.dtos.network.AuthSuccessulResponseDTO;
import com.poc.FarmaLoginService.dtos.network.MessageResponseDTO;
import org.springframework.http.ResponseEntity;

public interface NetworkHandlerEvent {
    void onResult(MessageResponseDTO response);
    void onResult(AuthSuccessulResponseDTO response);
}
