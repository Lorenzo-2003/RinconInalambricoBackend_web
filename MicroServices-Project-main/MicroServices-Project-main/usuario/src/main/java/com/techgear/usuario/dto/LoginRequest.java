package com.techgear.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @JsonProperty("correo")
    private String email;
    @JsonProperty("contrasena")
    private String password;
}