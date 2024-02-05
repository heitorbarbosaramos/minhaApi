package com.sisweb.api.security.feign;

import com.sisweb.api.security.dto.GoogleUserDetailsDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "googleApisCliente", url = "https://www.googleapis.com")
public interface GoogleApisCliente {

    @GetMapping("/oauth2/v1/userinfo")
    @Headers("Authorization: {access_token}")
    GoogleUserDetailsDTO getUserInfo(@RequestParam("access_token") String accessToken);
}
