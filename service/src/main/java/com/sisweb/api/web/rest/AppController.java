package com.sisweb.api.web.rest;

import com.sisweb.api.security.AutenticacaoController;
import com.sisweb.api.security.AutenticacaoSocialController;
import com.sisweb.api.security.CookieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class AppController {

    @Value("${app.sistema.uri}")
    private String redirect;
    private final AutenticacaoSocialController socialController;

    @Autowired
    private final CookieService cookieService;

    @GetMapping
    public void redirecionar(@AuthenticationPrincipal OidcUser principal, HttpServletRequest request, HttpServletResponse response) throws IOException {

        cookieService.addJSessionid(request, response);

        socialController.loginComGoogle(principal, request, response);

        response.sendRedirect(redirect + "/login?loginSocial");
    }
}
