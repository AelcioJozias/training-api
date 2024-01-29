package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoAssemblerDTO;
import com.algaworks.algafood.api.assembler.ProdutoDisassembler;
import com.algaworks.algafood.api.dto.ProdutoDTO;
import com.algaworks.algafood.api.dto.input.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController {

    @Autowired
    CadastroProdutoService cadastroProdutoService;

    @Autowired
    ProdutoAssemblerDTO produtoAssemblerDTO;

    @Autowired
    CadastroRestauranteService restauranteService;

    @Autowired
    ProdutoDisassembler produtoDisassembler;

    @GetMapping(value = "/{restauranteId}/produtos")
    public List<ProdutoDTO> listar(@PathVariable Long restauranteId,
                                   @RequestParam(required = false) boolean incluirInativos) {

        List<Produto> produtos = null;

        if(incluirInativos){
            produtos = cadastroProdutoService.listarTodosOsProdutosDeUmRestaurante(restauranteId);
        } else {
            produtos = cadastroProdutoService.listarTodosOsProdutosAtivosDeUmResutaurante(restauranteId);
        }


        return produtoAssemblerDTO.toCollectionDTO(produtos);
    }

    @GetMapping(value = "/{restauranteId}/produtos/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return produtoAssemblerDTO.toDTO(cadastroProdutoService.buscarOuFalhar(produtoId, restaurante));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{restauranteId}/produtos")
    public ProdutoDTO salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        Produto produto = produtoDisassembler.toDomainObject(produtoInputDTO);
        produto.setRestaurante(restaurante);
        cadastroProdutoService.salvar(produto);
        return produtoAssemblerDTO.toDTO(produto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{restauranteId}/produtos/{produtoId}")
    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        Produto produto = cadastroProdutoService.buscarOuFalhar(produtoId, restaurante);
        produtoDisassembler.copyToDomainObject(produtoInputDTO, produto);
        cadastroProdutoService.salvar(produto);
        return produtoAssemblerDTO.toDTO(produto);
    }




}
