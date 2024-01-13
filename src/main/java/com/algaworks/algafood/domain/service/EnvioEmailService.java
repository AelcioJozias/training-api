package com.algaworks.algafood.domain.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public interface EnvioEmailService {

    void enviar(Menssagem menssagem);

    @Getter
    @Setter
    class Menssagem {
        private Set<String> destinatarios;
        private String assunto;
        private String corpo;
    }


}
