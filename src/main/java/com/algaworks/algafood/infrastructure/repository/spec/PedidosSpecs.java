package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PedidosSpecs {
    public static Specification<Pedido> usandoFiltro(PedidoFilter pedidoFilter) {

        return (root, query, criteriaBuilder) -> {

            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");

            List<Predicate> predicates = new ArrayList<>();

            if (pedidoFilter.getClienteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cliente"), pedidoFilter.getClienteId()));
            }

            if (pedidoFilter.getDataCriacaoInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), pedidoFilter.getDataCriacaoInicio()));
            }

            if (pedidoFilter.getDataCriacaoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), pedidoFilter.getDataCriacaoFim()));
            }

            if (pedidoFilter.getRestauranteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("restaurante"), pedidoFilter.getRestauranteId()));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };

    }
}
