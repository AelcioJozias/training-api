package com.algaworks.algafood.infrastructure.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3FotoStorageService implements FotoStorageService {

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

    }

    @Override
    public void remover(String nomeFoto) {

    }

    @Override
    public void substituir(NovaFoto novaFoto, String nomeFotoExistente) {

    }
}
