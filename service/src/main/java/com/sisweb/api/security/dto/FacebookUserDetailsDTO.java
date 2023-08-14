package com.sisweb.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FacebookUserDetailsDTO {

    private String id;
    private String name;
    private String email;
}
