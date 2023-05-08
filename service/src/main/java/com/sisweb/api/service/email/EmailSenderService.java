package com.sisweb.api.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final TemplateEngine templateEngine;
    private final JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String email;
    @Value("${spring.mail.password}")
    private String senha;

    public void enviaEmail(String emailDestino, String assunto, String corpo){

        if(emailDestino == null || emailDestino.isEmpty()){log.info("EMAIL DESTINO NAO INFORMADO, EMAIL CANCELADO"); return; }

        Context context = new Context();
        context.setVariable("assunto", assunto);
        context.setVariable("corpo", corpo);

        String process = templateEngine.process("/email/email_padrao.html", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(assunto);
            helper.setText(process, true);
            helper.setTo(emailDestino);
        } catch ( MessagingException e) {
            e.printStackTrace();
        }

        log.info("ENVIANDO EMAIL....");
        javaMailSender.setUsername(email);
        javaMailSender.setPassword(senha);
        javaMailSender.send(mimeMessage);
        log.info("EMAIL ENVIADO");
    }
}
