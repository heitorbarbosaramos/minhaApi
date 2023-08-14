package com.sisweb.api.security.feign;

import com.sisweb.api.security.dto.FacebookGetTokenDTO;
import com.sisweb.api.security.dto.FacebookTokenDTO;
import com.sisweb.api.security.dto.FacebookUserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "facebookCliente", url = "https://graph.facebook.com/v13.0")
public interface FacebookCliente {

    @RequestMapping(method = RequestMethod.POST, value = "/oauth/access_token")
    FacebookTokenDTO getToken(@RequestBody FacebookGetTokenDTO getTokenDTO);

    @RequestMapping(method = RequestMethod.GET, value = "/me?fields=id,name,email&access_token={tokenFacebook}")
    FacebookUserDetailsDTO getFacebookUserDetails(@PathVariable("tokenFacebook") String tokenFacebook);

}
