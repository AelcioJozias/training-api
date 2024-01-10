package com.algaworks.algafood.infrastructure.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = Path.of(nomeArquivo);
            return FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(storageProperties.getLocal().getDiretorioFotos().resolve(arquivoPath)))
                    .build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path arquivoPath =  getArquivoPath(novaFoto.getNomeFoto());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo", e);
        }

    }

    @Override
    public void remover(String nomeFoto) {
        try {
            Files.deleteIfExists(storageProperties.getLocal().getDiretorioFotos().resolve(nomeFoto));
        } catch (IOException e) {
            throw new StorageException("Não foi possível excluir o arquivo", e);
        }
    }


    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(nomeArquivo);
    }
}
