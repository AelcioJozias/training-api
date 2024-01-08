package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    InputStream recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeFoto);

    void substituir(NovaFoto novaFoto, String nomeFotoExistente);

    default String gerarNomeArquivo(String nomeArquivo) {
        return UUID.randomUUID() + "_" + nomeArquivo;
    }

    @Getter
    @Builder
    class NovaFoto {
        private String nomeFoto;
        private InputStream inputStream;
    }
}
