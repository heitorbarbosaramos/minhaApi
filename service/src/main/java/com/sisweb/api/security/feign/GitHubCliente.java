package com.sisweb.api.security.feign;

import com.sisweb.api.security.dto.GitHubUserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "gitHubCliente", url = "https://api.github.com")
public interface GitHubCliente {

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    GitHubUserDetailsDTO getUserDetails(@RequestHeader("Authorization") String token);
}
