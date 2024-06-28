package br.com.trader.esportivo.entradas.domain.infrastructure.spec;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.trader.esportivo.entradas.domain.model.Banca;
import br.com.trader.esportivo.entradas.domain.model.Banca_;
import br.com.trader.esportivo.entradas.domain.model.Metodo;
import br.com.trader.esportivo.entradas.domain.model.Metodo_;

public class MetodoSpecs {
	
	public static Specification<Metodo> usandoFiltro(Long bancaId, String nomeMetodo) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<>();
			Join<Metodo, Banca> joinBanca = root.join(Metodo_.banca);
			predicates.add(builder.equal(joinBanca.get(Banca_.id), bancaId));
			
			if (isNotBlank(nomeMetodo)) {
				predicates.add(builder.like(builder.upper(root.get(Metodo_.nome)), "%" + nomeMetodo.trim().toUpperCase() + "%"));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
