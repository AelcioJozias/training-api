package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{

    @Query("select p FROM Pedido p join fetch p.cliente join fetch p.restaurante ")
    List<Pedido> findAll();
}
