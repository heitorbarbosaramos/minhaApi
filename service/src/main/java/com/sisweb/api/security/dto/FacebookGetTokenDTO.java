package com.sisweb.api.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacebookGetTokenDTO {

    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String code;

}
