package com.algaworks.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

//aqui tem que usar essa anotacao para que a validacao seja feita ao subir a aplicacao
@Validated
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "training-api.email")
public class EmailProperties {

    // caso não passarmos a propriedade do parametro, vai validar e jogar a excecao
    @NotNull
    private String remetente;

    private Implementacao impl = Implementacao.FAKE;

    private Sandbox sandbox = new Sandbox();

    public enum Implementacao {
        SMTP, FAKE, SANDBOX
    }


    @Getter
    @Setter
    public static class Sandbox {
        private String remetente;
    }

}
