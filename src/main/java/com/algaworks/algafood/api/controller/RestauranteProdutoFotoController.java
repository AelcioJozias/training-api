package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.dto.FotoProdutoDTO;
import com.algaworks.algafood.api.dto.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")

public class RestauranteProdutoFotoController {


    @Autowired
    CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    CadastroProdutoService cadastroProdutoService;

    @Autowired
    FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @Autowired
    CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        MultipartFile arquivo = fotoProdutoInput.getArquivo();
        Produto produto = cadastroProdutoService.buscarOuFalhar(produtoId, restauranteId);

        FotoProduto fotoProduto = new FotoProduto();

        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());
        fotoProduto.setTamanho(arquivo.getSize());


        fotoProduto = catalogoFotoProdutoService.salvar(fotoProduto, fotoProdutoInput.getArquivo().getInputStream());

        return fotoProdutoModelAssembler.toDTO(fotoProduto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return fotoProdutoModelAssembler.toDTO(catalogoFotoProdutoService.buscarOuFalhar(produtoId, restauranteId));
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                                          @RequestHeader(name = "accept") String mediaTypeRequest)
            throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(produtoId, restauranteId);

            List<MediaType> contentTypesAccepted = MediaType.parseMediaTypes(mediaTypeRequest);
            MediaType fotoMediaType = MediaType.parseMediaType(fotoProduto.getContentType());
            verificarCompatibilidadeMediaType(contentTypesAccepted, fotoMediaType);

            FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            if(fotoRecuperada.temUrl()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(fotoMediaType)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }


        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound()
                    .build();
        }
    }

    private void verificarCompatibilidadeMediaType(List<MediaType> contentTypesAccepted, MediaType fotoMediaType) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = contentTypesAccepted.stream().anyMatch(mediasTypesAceitas -> mediasTypesAceitas.isCompatibleWith(fotoMediaType));
        if(!compativel)
            throw new HttpMediaTypeNotAcceptableException(contentTypesAccepted);
    }

    @DeleteMapping
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(produtoId, restauranteId);
        catalogoFotoProdutoService.excluirFotoProduto(fotoProduto);
    }

}
