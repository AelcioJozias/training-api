package com.algaworks.algafood.infrastructure.repository.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffSet) {

		var builder = manager.getCriteriaBuilder(); // cria o builder
		var query = builder.createQuery(VendaDiaria.class); // diz que o retorno de dados vai ser "encaixado" nesssa classe
		var root = query.from(Pedido.class); // from


		List<Predicate> predicates = new ArrayList<>();

		if(filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
		}

		if(filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
		}

		// convert the timezone of utc to the required time zone
		var functionConvertTzDataCriacao = builder.function("convert_tz", Date.class, root.get("dataCriacao")
			, builder.literal("+00:00"), builder.literal(timeOffSet));


		// esse código é para fazer a função date do mysql
		var functionDateDataCriacao = builder.function(
				"date", Date.class, functionConvertTzDataCriacao);



		// aqui tá sendo construido um bloco select cm o criteria
		var selection = builder.construct(VendaDiaria.class,
				functionDateDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		query.select(selection);
		query.groupBy(functionDateDataCriacao);
		query.where(predicates.toArray(Predicate[]::new));

//		builder.and();
		
		return manager.createQuery(query).getResultList();
	}

}
