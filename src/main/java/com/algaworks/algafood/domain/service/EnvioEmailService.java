package com.algaworks.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;

public interface EnvioEmailService {

    void enviar(Menssagem menssagem);

    @Getter
    @Setter
    @Builder
    class Menssagem {

        // adicionar como se fosse um add do set ou array para não precisar instanciar uma lista se quiser passar apenas um destinario
        @NonNull
        @Singular
        private Set<String> destinatarios;

        // se não for passado um valor na instanciacao, será jogada uma exception
        @NonNull
        private String assunto;

        @NonNull
        private String corpo;

        @Singular(value = "variavel")
        Map<String, Object> variaveis;
    }


}
