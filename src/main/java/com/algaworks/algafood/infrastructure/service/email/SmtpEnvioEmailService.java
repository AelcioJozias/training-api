package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    protected EmailProperties emailProperties;

    @Autowired
    private Configuration freemakerConfiguration;

    @Override
    public void enviar(Menssagem menssagem) {
        try {
            MimeMessage mimeMessage = criarMime(menssagem);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o email", e);
        }

    }

    @NotNull
    protected MimeMessage criarMime(Menssagem menssagem) throws MessagingException {
        String corpo = processarTemplateDoEmail(menssagem);

        // mime é a estrutura de dados que vai guardar o email
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // mime helper ajuda a construir um mime
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setFrom(emailProperties.getRemetente());
        helper.setTo(menssagem.getDestinatarios().toArray(String[]::new));
        helper.setSubject(menssagem.getAssunto());
        helper.setText(corpo, true);
        return mimeMessage;
    }

    protected String processarTemplateDoEmail(Menssagem menssagem) {
        try {
            Template template = freemakerConfiguration.getTemplate(menssagem.getCorpo());
            template.setLocale(new Locale("pt", "BR"));
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, menssagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível processar o template", e);
        }
    }


}
