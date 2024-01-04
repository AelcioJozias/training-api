package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public FotoProduto salvar(FotoProduto fotoProduto) {

        Optional<FotoProduto> optionalFotoProduto = produtoRepository
                .findFotoById(fotoProduto.getProduto().getId(), fotoProduto.getRestauranteId());

        optionalFotoProduto.ifPresent(produto -> produtoRepository.delete(produto));

        return produtoRepository.save(fotoProduto);
    }
}
