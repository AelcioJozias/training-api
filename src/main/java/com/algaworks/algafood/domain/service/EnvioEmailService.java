package com.algaworks.algafood.domain.service;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

public interface EnvioEmailService {

    void enviar(Menssagem menssagem);

    @Getter
    @Setter
    @Builder
    class Menssagem {

        // adicionar como se fosse um add do set ou array para não precisar instanciar uma lista se quiser passar apenas um destinario
        @NonNull
        @Singular
        private Set<String> destinatarios = new HashSet<>();

        // se não for passado um valor na instanciacao, será jogada uma exception
        @NonNull
        private String assunto;

        @NonNull
        private String corpo;
    }


}
