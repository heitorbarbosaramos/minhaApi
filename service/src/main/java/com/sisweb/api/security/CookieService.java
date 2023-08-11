package com.sisweb.api.security;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        cookiePerfil.setHttpOnly(false);
        cookiePerfil.setMaxAge(7 * 24 * 60 * 60);
        cookiePerfil.setSecure(false);
        response.addCookie(cookiePerfil);
    }

    public void createCookieToken(HttpServletRequest request, HttpServletResponse response, String tokenJWT) {

        Cookie cookieToken = new Cookie("api-token", tokenJWT);
        cookieToken.setPath("/");
        cookieToken.setHttpOnly(false);
        cookieToken.setMaxAge(7 * 24 * 60 * 60);
        cookieToken.setSecure(false);
        response.addCookie(cookieToken);
    }

    public void createCookieLogin(HttpServletRequest request, HttpServletResponse response, String login){

        Cookie cookie = new Cookie("api-login", login);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

    public void createCookieNome(HttpServletRequest request, HttpServletResponse response, String nome){

        Cookie cookie = new Cookie("api-nome", nome.replaceAll(" ", "_"));
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }
    public void addJSessionid(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();

        for (Cookie x : cookies){
            System.out.println("-------->>>> " + x.getName() + ", " + x.getValue());
            Cookie cookie = new Cookie(x.getName(), x.getValue());
            cookie.setPath("/");
            cookie.setHttpOnly(false);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setSecure(false);
            response.addCookie(cookie);
        }



    }

    public void  apagaCookies(HttpServletRequest request, HttpServletResponse response){

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                try {
                    response.getWriter().println("Cookie eliminado com sucesso!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
