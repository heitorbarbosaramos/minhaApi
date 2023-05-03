package com.sisweb.api.security;

import com.sisweb.api.entity.UsuarioPerfil;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Service
public class CookieService {

    public void createCookiePerfil(HttpServletRequest request, HttpServletResponse response, Set<String> perfils){
        String perfil = "";
        for(String x : perfils){
            perfil += x+"-";
        }

        perfil = perfil.substring(0,perfil.length() -1);

        Cookie cookiePerfil = new Cookie("api-perfil", perfil);
        cookiePerfil.setPath("/");
        cookiePerfil.setHttpOnly(true);
        cookiePerfil.setMaxAge(7 * 24 * 60 * 60);
        cookiePerfil.setSecure(true);
        response.addCookie(cookiePerfil);
    }
}
