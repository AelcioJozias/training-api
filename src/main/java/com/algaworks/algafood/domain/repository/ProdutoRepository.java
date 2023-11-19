package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

    Optional<Produto> findProdutoByIdAndAndRestaurante(Long id, Restaurante restaurante);

    List<Produto> findByRestaurante(Restaurante restaurante);

    List<Produto> findProdutoByRestauranteAndAtivoTrue(Restaurante restaurante);

}
