package com.algaworks.algafood.infrastructure.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${training-api.storage.local.diretorio-fotos}")
    private Path diretorioFoto;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = Path.of(nomeArquivo);
            return Files.newInputStream(diretorioFoto.resolve(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        Path arquivoPath =  getArquivoPath(novaFoto.getNomeFoto());
        try {
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo", e);
        }

    }

    @Override
    public void remover(String nomeFoto) {
        try {
            Files.deleteIfExists(diretorioFoto.resolve(nomeFoto));
        } catch (IOException e) {
            throw new StorageException("Não foi possível excluir o arquivo", e);
        }
    }

    @Override
    public void substituir(NovaFoto novaFoto, String nomeFotoExistente) {
        armazenar(novaFoto);
        if(nomeFotoExistente != null)
            remover(nomeFotoExistente);
    }

    private Path getArquivoPath(String nomeArquivo) {
        return diretorioFoto.resolve(nomeArquivo);
    }
}
