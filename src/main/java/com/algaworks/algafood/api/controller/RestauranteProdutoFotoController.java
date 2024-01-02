package com.algaworks.algafood.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")

public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, FotoProdutoInput fotoProdutoInput) {

        String nomeArquivo = UUID.randomUUID() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();
        
        // path local criado na máquina windows para fins de testes
        Path arquivoFoto = Path.of("D:\\projecs\\temp\\catalogo", nomeArquivo);

        System.out.println("descricao do produto: " + fotoProdutoInput.getDescricao());
        System.out.println("caminho da imagem: " + arquivoFoto);
        System.out.println("tipo do arquivo: " + fotoProdutoInput.getArquivo().getContentType());

        try {
            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
