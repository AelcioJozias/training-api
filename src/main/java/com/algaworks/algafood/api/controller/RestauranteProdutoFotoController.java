package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.dto.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.api.dto.input.FotoProdutoInput;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @GetMapping
    public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return fotoProdutoModelAssembler.toDTO(catalogoFotoProdutoService.buscarOuFalhar(produtoId, restauranteId));
    }
}
