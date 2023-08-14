package com.sisweb.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FacebookTokenDTO {

    private String access_token;
    private String token_type;
    private Long expires_in;
}
