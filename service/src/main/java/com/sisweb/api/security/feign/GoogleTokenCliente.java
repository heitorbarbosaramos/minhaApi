package com.sisweb.api.security.feign;

import com.sisweb.api.security.dto.GoogleAcessTokenDTO;
import com.sisweb.api.security.dto.GoogleTokenDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "googleTokenCliente", url = "https://oauth2.googleapis.com/token")
public interface GoogleTokenCliente {

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    GoogleAcessTokenDTO getGoogleToken(@RequestBody GoogleTokenDTO tokenDTO);
}
