package com.algaworks.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Restaurante;

public class RestauranteSpecs {

	public static Specification<Restaurante> comFreteGratis() {
		return (root, querssy, builder) -> 
			builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}
	
	public static Specification<Restaurante> comNomeSemelhante(String nome) {
		return (root, query, builder) ->
			builder.like(root.get("nome"), "%" + nome + "%");
	}
	
	public static Specification<Restaurante> teste(){
		return (root, query, builder) -> {
			 return builder.like(root.get("nome"), "teste"+ "$" + "s");
		};
			
		
	}
	
}
