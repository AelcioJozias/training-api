package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemakerConfiguration;

    @Override
    public void enviar(Menssagem menssagem) {
        try {
            String corpo = processarTemplateDoEmail(menssagem);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(menssagem.getDestinatarios().toArray(String[]::new));
            helper.setSubject(menssagem.getAssunto());
            helper.setText(corpo, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o email", e);
        }

    }

    private String processarTemplateDoEmail(Menssagem menssagem) throws Exception{
            Template template = freemakerConfiguration.getTemplate(menssagem.getCorpo());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, menssagem.getVariaveis());
    }


}
