package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroProdutoService {

    public static final String NAO_EXISTE_UM_CADASTRO_DE_PRODUTO_COM_CODIGO_X_PARA_O_RESTAURANTE_DE_CODIGO_Y = "Não existe um cadastro de produto com código %s para o restaurante de código %s ";
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    CadastroRestauranteService restauranteService;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarTodosOsProdutosDeUmRestaurante(Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);
        return restaurante.getProdutos();
    }

    public List<Produto> listarTodosOsProdutosAtivosDeUmResutaurante(Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return produtoRepository.findProdutoByRestauranteAndAtivoTrue(restaurante);
    }


    public Produto buscarOuFalhar(Long produtoId, Restaurante restaurante) {
        return produtoRepository.findProdutoByIdAndAndRestaurante(produtoId, restaurante).orElseThrow(() -> new ProdutoNaoEncontradoException(
                String.format(NAO_EXISTE_UM_CADASTRO_DE_PRODUTO_COM_CODIGO_X_PARA_O_RESTAURANTE_DE_CODIGO_Y,
                        produtoId, restaurante.getId())));
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

}
