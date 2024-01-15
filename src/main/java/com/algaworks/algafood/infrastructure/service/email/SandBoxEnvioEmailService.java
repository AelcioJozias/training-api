package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SandBoxEnvioEmailService extends SmtpEnvioEmailService{

    /**
     * esse corte é apenas para modificar o endereco de email que a aplicação irá enviar
     *
     * @param menssagem contém as propridades necessárias para enviar o email e montar o templat e html
     * @return um mime message customizado para enviar pra um email especificado em properties
     * @throws MessagingException relancando para ser tratado em outro método
     */
    @Override
    protected @NotNull MimeMessage criarMime(Menssagem menssagem) throws MessagingException {
        MimeMessage mimeMessage = super.criarMime(menssagem);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailProperties.getSandbox().getRemetente());
        return mimeMessage;
    }
}
