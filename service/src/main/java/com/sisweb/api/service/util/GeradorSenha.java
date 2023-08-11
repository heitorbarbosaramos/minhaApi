package com.sisweb.api.service.util;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GeradorSenha {

    public static String criar(){
        String senha = "";

        for(int i = 0; i < 10; i++){
            Random r = new Random();
            senha += Character.toString ((char) r.nextInt(26) + 'A');
        }

        return senha;
    }
}
