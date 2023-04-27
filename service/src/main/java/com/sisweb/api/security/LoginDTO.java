package com.sisweb.api.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginDTO {

    @NotNull(message = "Campo obrigatório")
    @Email(message = "Campo não é um email")
    private String login;
    @NotNull(message = "Campo obrigatório")
    private String senha;
}
