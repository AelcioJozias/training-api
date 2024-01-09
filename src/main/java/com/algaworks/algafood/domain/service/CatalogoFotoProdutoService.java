package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream fileInputStream) {
        String nomeProdutoExistente = null;
        Optional<FotoProduto> optionalFotoProduto = produtoRepository
                .findFotoById(fotoProduto.getProduto().getId(), fotoProduto.getRestauranteId());

        if(optionalFotoProduto.isPresent()) {
            produtoRepository.delete(optionalFotoProduto.get());
            nomeProdutoExistente = optionalFotoProduto.get().getNomeArquivo();
        }

        fotoProduto.setNomeArquivo(fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo()));
        fotoProduto = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        fotoStorageService.substituir(
                FotoStorageService.NovaFoto.builder()
                .inputStream(fileInputStream)
                .nomeFoto(fotoProduto.getNomeArquivo())
                .build(), nomeProdutoExistente
        );

        return fotoProduto;
    }

    @Transactional
    public FotoProduto buscarOuFalhar(Long produtoId, Long restauranteId) {
        return produtoRepository.findFotoById(produtoId, restauranteId).orElseThrow(() -> new FotoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public void excluirFotoProduto(FotoProduto fotoProduto) {
        produtoRepository.delete(fotoProduto);
        fotoStorageService.remover(fotoProduto.getNomeArquivo());
    }
}
