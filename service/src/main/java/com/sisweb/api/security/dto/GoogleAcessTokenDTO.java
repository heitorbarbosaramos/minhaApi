package com.sisweb.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GoogleAcessTokenDTO {

    private String access_token;
    private String expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
