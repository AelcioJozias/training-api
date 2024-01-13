package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    StorageProperties storageProperties;

    @Autowired
    AmazonS3 amazonS3;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {

        return FotoRecuperada.builder()
                .url(amazonS3.getUrl(storageProperties.getS3().getBucket(), getCaminhoArquivo(nomeArquivo)).toString())
                .build();
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    getCaminhoArquivo(novaFoto.getNomeFoto()),
                    novaFoto.getInputStream(),
                    objectMetadata
            )
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar o arquivo para a Amazon S3", e);
        }
    }

    @Override
    public void remover(String nomeFoto) {
        var nomeArquivo = getCaminhoArquivo(nomeFoto);
        var deleteObejctRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), nomeArquivo);
        amazonS3.deleteObject(deleteObejctRequest);
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretoriosFotos(), nomeArquivo);
    }
}
