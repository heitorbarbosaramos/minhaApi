package com.sisweb.api.service.email.corpo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CorpoEmail {

    public static String esqueciSenha(String nome, String email, LocalDateTime dataHora, Long idUsuario, Long timestamp){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

        String corpoEmail = "Prezado colaborador " + nome;
        corpoEmail += "<br><br>";
        corpoEmail += "Foi solicitado uma alteração de senha para o seu usuario " + email;
        corpoEmail += "<br><br>";
        corpoEmail += "Você poderá alterar sua senha no link http://localhost:3000/esqueciasenha/"+idUsuario+"/"+timestamp;
        corpoEmail += ", que estará disponivel até " + dataHora.format(formatter) + ", após esse horário será necessário gerar um novo link para recuperar a senha.";
        corpoEmail += "<br><br>";
        corpoEmail += "Caso não tenha solicitado a troca de senha favor desconsiderar esse email.";

        return corpoEmail;
    }
}
