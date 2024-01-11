package com.algaworks.algafood.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "training-api.storage")
public class StorageProperties {

    private Local local  = new Local();
    private S3 s3 = new S3();
    private TipoStorage tipoStorage = TipoStorage.LOCAL;

    public enum TipoStorage {
        LOCAL,
        S3
    }

    @Getter
    @Setter
    public static class Local {
        private Path diretorioFotos;
    }

    @Getter
    @Setter
    public static class S3 {
        private String idChaveAcesso;
        private String ChaveAcessoSecreta;
        private String bucket;
        private Regions region;
        private String diretoriosFotos;
    }
}
