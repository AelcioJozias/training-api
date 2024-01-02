package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {
    
    @NotNull
    @FileSize(max = "1MB")
    private MultipartFile arquivo;

    @NotEmpty
    private String descricao;
}
